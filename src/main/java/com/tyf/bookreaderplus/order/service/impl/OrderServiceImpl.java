package com.tyf.bookreader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.tyf.bookreader.domain.BrOrder;
import com.tyf.bookreader.domain.BrOrderDetails;
import com.tyf.bookreader.dto.OrderDetailsDto;
import com.tyf.bookreader.dto.OrderDto;
import com.tyf.bookreader.manager.OrderManager;
import com.tyf.bookreader.mapper.BrBookUserMapper;
import com.tyf.bookreader.mapper.BrOrderDetailsMapper;
import com.tyf.bookreader.mapper.BrOrderMapper;
import com.tyf.bookreader.mapper.BrUserMapper;
import com.tyf.bookreader.service.OrderService;
import com.tyf.bookreader.utils.UserUtils;
import com.tyf.bookreader.vo.OrderVo;
import com.tyf.bookreader.component.Result;
import com.tyf.bookreader.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.tyf.bookreader.dto.PageRespDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Description 订单模块实现
 * @Author shallow
 * @Date 2023/5/2 20:57
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private BrOrderDetailsMapper orderDetailsMapper;

    @Autowired
    private BrOrderMapper orderMapper;

    @Autowired
    private OrderManager orderManager;

    @Override
    public Result createOrder(OrderVo orderVo) {
        orderManager.createOrder(orderVo);
        return Result.ok();
    }

    @Override
    @Transactional
    public Result payOrder(Long orderId) {
        orderManager.payOrder(orderId);
        return Result.ok();
    }

    @Override
    public Result<OrderDto> selectOrderDetails(Long orderId) {
        BrOrder order = orderMapper.selectById(orderId);
        List<BrOrderDetails> detailsList = orderDetailsMapper
                .selectList(new LambdaQueryWrapper<BrOrderDetails>().eq(BrOrderDetails::getOrderId, orderId));
        OrderDto orderDto = OrderDto.builder().id(orderId)
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
    public Result cancelOrder(Long orderId) {
        orderManager.cancelOrder(orderId);
        return Result.ok();
    }

    @Override
    public Result<PageRespDto<OrderDto>> getOrders(PageVo pageVo) {
        Long userId = UserUtils.getLoginUser().getUser().getId();
        int pageNum = pageVo.getPageNum();
        int pageSize =pageVo.getPageSize();
        List<OrderDto> orderDtoList = new ArrayList<>();
        for (BrOrder order : orderMapper.selectList(new LambdaQueryWrapper<BrOrder>().eq(BrOrder::getUserId, userId))) {
            OrderDto orderDto= orderDetailsMapper.selectOrderDtoByOrderId(order.getId());
            orderDtoList.add(orderDto);
        }
        Collections.sort(orderDtoList,(o1, o2) -> (int) (o1.getId()-o2.getId()));
        return Result.ok(new PageRespDto<>(pageNum,pageSize,orderDtoList.size(),orderDtoList));
    }

    @Override
    public Result deleteOrder(Long orderId) {
        orderManager.deleteOrder(orderId);
        return Result.ok();
    }
}
