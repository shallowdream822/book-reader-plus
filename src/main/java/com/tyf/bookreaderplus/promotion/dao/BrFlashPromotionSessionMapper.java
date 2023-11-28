package com.tyf.bookreaderplus.promotion.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.tyf.bookreaderplus.promotion.entity.BrFlashPromotionSession;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (BrFlashPromotionSession)表数据库访问层
 *
 * @author shallow
 * @since 2023-11-20 14:02:15
 */
@Mapper
public interface BrFlashPromotionSessionMapper extends BaseMapper<BrFlashPromotionSession> {
    List<BrFlashPromotionSession> selectSessionListByPromotionId(@Param("promotionId") Long promotionId);

    List<BrFlashPromotionSession> selectByPromotionIds(@Param("promotionIds") List<Long> promotionIds);
}

