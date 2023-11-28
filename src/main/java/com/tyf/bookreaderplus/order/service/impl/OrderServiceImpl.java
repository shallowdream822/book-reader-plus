package com.tyf.bookreaderplus.order.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.tyf.bookreaderplus.auth.dao.BrBookUserMapper;
import com.tyf.bookreaderplus.auth.dao.BrUserMapper;
import com.tyf.bookreaderplus.auth.entity.BrBookUser;
import com.tyf.bookreaderplus.auth.entity.BrUser;
import com.tyf.bookreaderplus.common.component.LoginUser;
import com.tyf.bookreaderplus.common.component.PageVo;
import com.tyf.bookreaderplus.common.component.Result;
import com.tyf.bookreaderplus.common.constant.CommonConstants;
import com.tyf.bookreaderplus.common.constant.RedisConstants;
import com.tyf.bookreaderplus.common.dto.PageRespDto;
import com.tyf.bookreaderplus.common.exception.BrException;
import com.tyf.bookreaderplus.common.utils.RedisUtil;
import com.tyf.bookreaderplus.common.utils.UserUtils;
import com.tyf.bookreaderplus.order.component.CancelOrderSender;
import com.tyf.bookreaderplus.order.dao.BrOrderDetailsMapper;
import com.tyf.bookreaderplus.order.dao.BrOrderMapper;
import com.tyf.bookreaderplus.order.dto.OrderDetailsDto;
import com.tyf.bookreaderplus.order.dto.OrderDto;
import com.tyf.bookreaderplus.order.entity.BrOrder;
import com.tyf.bookreaderplus.order.entity.BrOrderDetails;
import com.tyf.bookreaderplus.order.service.OrderService;
import com.tyf.bookreaderplus.order.vo.OrderDetailsVo;
import com.tyf.bookreaderplus.order.vo.OrderVo;
import com.tyf.bookreaderplus.promotion.constant.PromotionInitConstants;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Description 订单模块实现
 * @Author shallow
 * @Date 2023/5/2 20:57
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private BrOrderDetailsMapper orderDetailsMapper;

    @Autowired
    private BrOrderMapper orderMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private BrUserMapper userMapper;

    @Autowired
    private BrBookUserMapper bookUserMapper;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private CancelOrderSender cancelOrderSender;

    @Override
    public void createOrder(OrderVo orderVo) {
        BrOrder order = new BrOrder();
        order.setOrderNum(IdWorker.getId());
        order.setUserId(UserUtils.getLoginUser().getUser().getId());
        double orderPrice = 0;
        //求订单总价，并把订单信息转化类型
        for (OrderDetailsVo detail : orderVo.getOrderDetailsVoList()) {
            if (UserUtils.getLoginUser().getBookIdList().contains(detail.getBookId())){
                throw new BrException("此书已购买，请勿重复下单");
            }
            orderPrice+=detail.getBookPrice();
            BrOrderDetails detailsToDb = BeanUtil.toBean(detail, BrOrderDetails.class);
            detailsToDb.setOrderNum(order.getOrderNum());
            orderDetailsMapper.insert(detailsToDb);
        }
        order.setOrderPrice(orderPrice);
        order.setStatus(0);
        order.setIsSeckill(orderVo.getIsSeckill());
        orderMapper.insert(order);
        senderOrderToDelayQueue(order.getOrderNum());
        redisUtil.hSet(RedisConstants.ORDER_KEY,String.valueOf(order.getOrderNum()),order,RedisConstants.COMMON_CACHE_TIME);
    }
    private void senderOrderToDelayQueue(Long orderNum) {
        //发送延迟消息
        cancelOrderSender.sendMessage(orderNum, CommonConstants.QUEUE_DEAD_TTL);
    }

    @Override
    @Transactional
    public void payOrder(Long orderNum) {
        //查询订单信息
        BrOrder order = (BrOrder) redisUtil.hGet(RedisConstants.ORDER_KEY, String.valueOf(orderNum));
        if (Objects.isNull(order)){
            order = orderMapper.selectOne(new LambdaQueryWrapper<BrOrder>().eq(BrOrder::getOrderNum, orderNum));
        }
        if (Objects.isNull(order)){
            throw new BrException("无订单信息");
        }
        List<BrOrderDetails> orderDetailsList = orderDetailsMapper.
                selectList(new LambdaQueryWrapper<BrOrderDetails>().eq(BrOrderDetails::getOrderNum, orderNum));
        if (order.getStatus() == 1){
            throw new BrException("订单已支付");
        }
        Long userId = UserUtils.getLoginUser().getUser().getId();
        //使用分布式锁防止多线程问题,一个用户同时只能支付一个订单
        RLock lock = redissonClient.getLock(RedisConstants.REDISSON_LOCK_PAY_ORDER + userId);
        try {
            if (lock.tryLock(RedisConstants.REDISSON_LOCK_WAIT,RedisConstants.REDISSON_LOCK_RELEASE, TimeUnit.SECONDS)) {
                //判断用户余额是否足够支付
                BrUser user = userMapper.selectById(userId);
                Double accountBalance = user.getAccountBalance();
                double balance = accountBalance - order.getOrderPrice();
                if (balance <0){
                    throw new BrException("用户余额不足");
                }
                //修改余额
                user.setAccountBalance(balance);
                userMapper.updateById(user);
                //修改订单信息
                order.setStatus(1);
                orderMapper.updateById(order);
                //记录已购图书
                //增加权限
                List<Long> tmp = UserUtils.getLoginUser().getBookIdList();
                for (BrOrderDetails orderDetails : orderDetailsList) {
                    bookUserMapper.insert(BrBookUser.builder()
                            .userId(userId)
                            .bookId(orderDetails.getBookId())
                            .build());
                    tmp.add(orderDetails.getBookId());
                }
                updateBookIdList(tmp);
            }else{
                throw new BrException("支付失败，其他订单支付中");
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            redisUtil.hDel(RedisConstants.ORDER_KEY,String.valueOf(orderNum));
            log.debug("bookList:{}",UserUtils.getLoginUser().getBookIdList());
        }
    }


    private  void updateBookIdList(List<Long> bookIdList){
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        loginUser.setBookIdList(bookIdList);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        redisUtil.set("login:token:" + loginUser.getUser().getId(), loginUser, RedisConstants.LOGIN_USER_TTL);
    }
    @Override
    public Result<OrderDto> selectOrderDetails(Long orderNum) {
        BrOrder order = orderMapper.selectOne(new LambdaQueryWrapper<BrOrder>().eq(BrOrder::getOrderNum, orderNum));
        if(Objects.isNull(order)){
            throw new BrException("无订单信息");
        }
        List<BrOrderDetails> detailsList = orderDetailsMapper
                .selectList(new LambdaQueryWrapper<BrOrderDetails>().eq(BrOrderDetails::getOrderNum, orderNum));
        OrderDto orderDto = OrderDto.builder()
                .orderNum(order.getOrderNum())
                .orderPrice(order.getOrderPrice())
                .status(order.getStatus())
                .payTime(order.getPayTime())
                .createTime(order.getCreateTime())
                .updateTime(order.getUpdateTime())
                .build();
        List<OrderDetailsDto> tmp = new ArrayList<>();
        for (BrOrderDetails details : detailsList) {
            OrderDetailsDto detailsDto = OrderDetailsDto.builder()
                    .bookName(details.getBookName())
                    .bookPrice(details.getBookPrice())
                    .bookId(details.getBookId())
                    .build();
            tmp.add(detailsDto);
        }
        orderDto.setOrderDetailsList(tmp);
        return Result.ok(orderDto);
    }

    @Override
    @Transactional
    public void cancelOrder(Long orderNum) {
        redisUtil.hDel(RedisConstants.ORDER_KEY,String.valueOf(orderNum));
        BrOrder order = orderMapper.selectOne(new LambdaQueryWrapper<BrOrder>().eq(BrOrder::getOrderNum, orderNum));
        if (Objects.isNull(order)||order.getDeleted() == 1){
            throw new BrException("订单已不存在");
        }
        order.setStatus(3);
        orderMapper.update(order,new LambdaQueryWrapper<BrOrder>().eq(BrOrder::getOrderNum, orderNum));
        if(order.getIsSeckill().equals(PromotionInitConstants.SECKILL_ORDER_STATUS)){
            //秒杀订单取消，库存+1

        }
        redisUtil.hSet(RedisConstants.ORDER_KEY,String.valueOf(order.getId()),order,RedisConstants.COMMON_CACHE_TIME);
    }

    @Override
    public Result<PageRespDto<OrderDto>> getOrders(PageVo pageVo) {
        Long userId = UserUtils.getLoginUser().getUser().getId();
        int pageNum = pageVo.getPageNum();
        int pageSize =pageVo.getPageSize();
        List<OrderDto> orderDtoList = new ArrayList<>();
        for (BrOrder order : orderMapper.selectList(new LambdaQueryWrapper<BrOrder>().eq(BrOrder::getUserId, userId))) {
            OrderDto orderDto= orderDetailsMapper.selectOrderDtoByOrderNum(order.getOrderNum());
            orderDtoList.add(orderDto);
        }
        Collections.sort(orderDtoList,(o1, o2) -> (int) (o1.getOrderNum()-o2.getOrderNum()));
        return Result.ok(new PageRespDto<>(pageNum,pageSize,orderDtoList.size(),orderDtoList));
    }

    @Override
    public void deleteOrder(Long orderId) {
        orderMapper.deleteById(orderId);
        redisUtil.hDel(RedisConstants.ORDER_KEY,String.valueOf(orderId));
    }
}
