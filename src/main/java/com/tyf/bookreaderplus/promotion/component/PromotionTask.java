package com.tyf.bookreaderplus.promotion.component;

import com.tyf.bookreaderplus.book.dao.BrBookMapper;
import com.tyf.bookreaderplus.common.utils.RedisUtil;
import com.tyf.bookreaderplus.promotion.constant.PromotionInitConstants;
import com.tyf.bookreaderplus.promotion.entity.BrFlashPromotion;
import com.tyf.bookreaderplus.promotion.dao.BrFlashPromotionMapper;
import com.tyf.bookreaderplus.promotion.dao.BrFlashPromotionSessionMapper;
import com.tyf.bookreaderplus.promotion.dao.BrPromotionBookMapper;
import com.tyf.bookreaderplus.promotion.service.FlashPromotionService;
import io.lettuce.core.RedisClient;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description TODO
 * @Author shallow
 * @Date 2023/11/20 14:03
 */
@Component
@Slf4j
public class PromotionTask {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private BrFlashPromotionMapper  flashPromotionMapper;

    @Autowired
    private FlashPromotionService flashPromotionService;

    @Autowired
    private RedissonClient redissonClient;


    /**
     * 每天凌晨1点执行，同时修改秒杀活动的状态
     */
    @Scheduled(cron = "* * 1 * * *")
    public void initPromotion() {
        log.debug("活动更新操作执行时间:{}", System.currentTimeMillis());
        //删除redis中的秒杀活动
        redisUtil.del(PromotionInitConstants.PROMOTION_LIST);
        //删除场次和商品信息
        redisUtil.del(PromotionInitConstants.PROMOTION_SESSION_LIST);
        //删除商品信息和商品信号量
        redisUtil.del(PromotionInitConstants.SESSION_BOOK);
        RLock lock = redissonClient.getLock(PromotionInitConstants.PROMOTION_UPLOAD_LOCK);
        try {
            lock.lock();
            flashPromotionService.uploadPromotionInfo();
        }finally {
            lock.unlock();
        }
    }
}
