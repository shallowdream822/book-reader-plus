package com.tyf.bookreaderplus.promotion.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.tyf.bookreaderplus.common.constant.StatusCodeEnum;
import com.tyf.bookreaderplus.common.exception.BrException;
import com.tyf.bookreaderplus.common.utils.RedisUtil;
import com.tyf.bookreaderplus.common.utils.UserUtils;
import com.tyf.bookreaderplus.promotion.constant.PromotionInitConstants;
import com.tyf.bookreaderplus.promotion.dao.BrFlashPromotionMapper;
import com.tyf.bookreaderplus.promotion.dao.BrFlashPromotionSessionMapper;
import com.tyf.bookreaderplus.promotion.dao.BrPromotionBookMapper;
import com.tyf.bookreaderplus.promotion.dto.*;
import com.tyf.bookreaderplus.promotion.entity.BrFlashPromotion;
import com.tyf.bookreaderplus.promotion.entity.BrFlashPromotionSession;
import com.tyf.bookreaderplus.promotion.service.FlashPromotionService;
import com.tyf.bookreaderplus.promotion.vo.SeckillVo;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Description TODO
 * @Author shallow
 * @Date 2023/11/20 16:56
 */
@Service
@Slf4j
public class FlashPromotionServiceImpl implements FlashPromotionService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private BrFlashPromotionMapper flashPromotionMapper;

    @Autowired
    private BrFlashPromotionSessionMapper flashPromotionSessionMapper;

    @Autowired
    private BrPromotionBookMapper promotionBookMapper;

    @Override
    public void uploadPromotionInfo() {
        //查询所有秒杀活动
        Date now = new Date();
        long time = now.getTime();
        long endTime = time + PromotionInitConstants.DAYS_MILLS;
        Date end = new Date(endTime);
        List<BrFlashPromotion> flashPromotions = flashPromotionMapper.selectAllFromNowToTarget(now, end);
        List<PromotionDto> promotionDtos = flashPromotions.stream().map(flashPromotion ->{
            PromotionDto promotionDto = PromotionDto.builder()
                    .id(flashPromotion.getId())
                    .title(flashPromotion.getTitle())
                    .startDate(flashPromotion.getStartDate())
                    .endDate(flashPromotion.getEndDate())
                    .status(flashPromotion.getStatus())
                    .build();
            redisUtil.hSet(PromotionInitConstants.PROMOTION_LIST, promotionDto.getId().toString(), promotionDto, PromotionInitConstants.DAYS_MILLS);
            return promotionDto;
        }).toList();
        log.info(promotionDtos.toString());
        //将秒杀活动存入redis

        //将活动场次信息和场次相关图书信息存入redis
        List<CompletableFuture<PromotionSessionDto>> futures = flashPromotions.stream().map(flashPromotion -> CompletableFuture.supplyAsync(() -> {
            //组装sessionVo，需要先查book相关信息
            return getPromotionSessionVo(flashPromotion);
        })).toList();

        List<PromotionSessionDto> results = futures.stream()
                .map(CompletableFuture::join).toList();

        results.forEach(result -> {
            redisUtil.hSetAll(PromotionInitConstants.PROMOTION_SESSION_CONTENT + result.getPromotionId(),
                    result.getSessionList().stream().collect(Collectors.toMap(sessionDto -> sessionDto.getSessionId().toString(), Function.identity())));

            result.getSessionList().forEach(sessionDto -> {
                sessionDto.getSessionBookList().forEach(sessionBookDto -> {
                    redisUtil.hSet(PromotionInitConstants.SESSION_BOOK + sessionDto.getSessionId(), sessionBookDto.getBookId().toString(), sessionBookDto, PromotionInitConstants.DAYS_MILLS);
                    String key = PromotionInitConstants.BOOK_STOCK +'-' + sessionBookDto.getSessionId()+'-' + sessionBookDto.getBookId();
                    RSemaphore semaphore = redissonClient.getSemaphore(key);
                    redisUtil.del(key);
                    semaphore.trySetPermits(sessionBookDto.getStock());

                });
            });
        });



    }


    private PromotionSessionDto getPromotionSessionVo(BrFlashPromotion flashPromotion) {
        Long promotionId = flashPromotion.getId();
        List<BrFlashPromotionSession> sessions = flashPromotionSessionMapper.selectSessionListByPromotionId(promotionId);
        List<SessionDto> sessionDtoList = sessions.stream().map(session -> {
            Long sessionId = session.getId();
            List<SessionBookDto> sessionBookDtos = promotionBookMapper.selectBookListBySessionId(sessionId);
            return SessionDto.builder()
                    .sessionId(sessionId)
                    .name(session.getName())
                    .startTime(session.getStartTime())
                    .endTime(session.getEndTime())
                    .status(session.getStatus())
                    .sessionBookList(sessionBookDtos)
                    .build();
        }).toList();
        //活动基础信息
        return PromotionSessionDto.builder()
                .promotionId(promotionId)
                .promotionTitle(flashPromotion.getTitle())
                .startDate(flashPromotion.getStartDate())
                .endDate(flashPromotion.getEndDate())
                .status(flashPromotion.getStatus())
                .sessionList(sessionDtoList)
                .build();
    }


    @Override
    public List<PromotionDto> getPromotionList() {
        List<PromotionDto> promotionList;
        if (redisUtil.hasKey(PromotionInitConstants.PROMOTION_LIST)) {
            promotionList = new ArrayList<>();
            redisUtil.hGetAll(PromotionInitConstants.PROMOTION_LIST).forEach((k, v) -> {
                promotionList.add(BeanUtil.toBean(v, PromotionDto.class));
            });
        } else {
            Date now = new Date();
            long time = now.getTime();
            long endTime = time + PromotionInitConstants.DAYS_MILLS;
            Date end = new Date(endTime);
            promotionList = flashPromotionMapper.selectAllFromNowToTarget(now, end).stream().map(flashPromotion ->
                PromotionDto.builder()
                        .id(flashPromotion.getId())
                        .title(flashPromotion.getTitle())
                        .startDate(flashPromotion.getStartDate())
                        .endDate(flashPromotion.getEndDate())
                        .status(flashPromotion.getStatus())
                        .build()).toList();
        }
        return promotionList;
    }

    @Override
    public PromotionSessionDto getPromotionDetails(Long promotionId) {
        PromotionSessionDto promotionSessionDto;
        if(redisUtil.hasKey(PromotionInitConstants.PROMOTION_SESSION_CONTENT + promotionId)
                &&redisUtil.hasKey(PromotionInitConstants.PROMOTION_LIST)){
            List<SessionDto> sessionDtoList = new ArrayList<>();
            redisUtil.hGetAll(PromotionInitConstants.PROMOTION_SESSION_CONTENT + promotionId).forEach((k,v) -> {
                SessionDto sessionDto = BeanUtil.toBean(v, SessionDto.class);
                sessionDtoList.add(sessionDto);
            });
            PromotionDto promotionDto = BeanUtil.toBean(redisUtil.hGet(PromotionInitConstants.PROMOTION_LIST, promotionId.toString()), PromotionDto.class);
            promotionSessionDto = PromotionSessionDto.builder()
                    .promotionId(promotionId)
                    .promotionTitle(promotionDto.getTitle())
                    .startDate(promotionDto.getStartDate())
                    .endDate(promotionDto.getEndDate())
                    .status(promotionDto.getStatus())
                    .sessionList(sessionDtoList)
                    .build();
        }else{
            BrFlashPromotion flashPromotion = flashPromotionMapper.selectPromotionById(promotionId);
            promotionSessionDto = getPromotionSessionVo(flashPromotion);
        }

        return promotionSessionDto;
    }


    @Override
    public BookPromotionDto getBookPromotion(Long bookId) {
        //获取所有活动和场次信息和图书信息

        return null;
    }

    @Override
    public void seckill(SeckillVo seckillVo) {
        //1.校验是否为当前用户
        Long userId = seckillVo.getUserId();
        Long curId = UserUtils.getLoginUser().getUser().getId();
        if(!userId.equals(curId)){
            throw new BrException("非法请求");
        }
        //2.校验是否已经秒杀过
        if(Objects.nonNull(redisUtil.hGet(PromotionInitConstants.HAS_BUY_BOOK + seckillVo.getSessionId() + '-' + seckillVo.getBookId(), userId.toString()))){
            throw new BrException(StatusCodeEnum.SECKILL_FAIL.getCode(), StatusCodeEnum.SECKILL_FAIL.getDesc());
        }
        //3.校验时间
        SessionDto sessionDto = BeanUtil.toBean(redisUtil.hGet(PromotionInitConstants.PROMOTION_SESSION_CONTENT + seckillVo.getPromotionId(), seckillVo.getSessionId().toString()), SessionDto.class);
        if(Objects.isNull(sessionDto)){
            throw new BrException(StatusCodeEnum.SECKILL_FAIL.getCode(), StatusCodeEnum.SECKILL_FAIL.getDesc());
        }
        LocalDateTime now = LocalDateTime.now();
        if(now.isBefore(sessionDto.getStartTime()) || now.isAfter(sessionDto.getEndTime())){
            throw new BrException(StatusCodeEnum.SECKILL_FAIL.getCode(), StatusCodeEnum.SECKILL_FAIL.getDesc());
        }
        //4.校验库存
        String key = PromotionInitConstants.BOOK_STOCK +'-' + seckillVo.getSessionId()+'-' + seckillVo.getBookId();
        RSemaphore semaphore = redissonClient.getSemaphore(key);
        try {
            semaphore.acquire();
            //创建订单，发送给mq

            //减库存

            //增加购买记录

        } catch (InterruptedException e) {
            throw new BrException(StatusCodeEnum.SECKILL_FAIL.getCode(), StatusCodeEnum.SECKILL_FAIL.getDesc());
        }
    }
}
