package com.tyf.bookreaderplus.promotion.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.tyf.bookreaderplus.promotion.entity.BrFlashPromotion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * (BrFlashPromotion)表数据库访问层
 *
 * @author shallow
 * @since 2023-11-20 14:02:11
 */
@Mapper
public interface BrFlashPromotionMapper extends BaseMapper<BrFlashPromotion> {
    List<BrFlashPromotion> selectAllFromNowToTarget(@Param("now") Date now, @Param("end") Date end);

    BrFlashPromotion selectPromotionById(@Param("promotionId") Long promotionId);
}

