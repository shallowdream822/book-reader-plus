package com.tyf.bookreaderplus.promotion.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.tyf.bookreaderplus.promotion.entity.BrPromotionBook;
import com.tyf.bookreaderplus.promotion.dto.SessionBookDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 活动图书关联表(BrPromotionBook)表数据库访问层
 *
 * @author shallow
 * @since 2023-11-20 14:02:15
 */
@Mapper
public interface BrPromotionBookMapper extends BaseMapper<BrPromotionBook> {
    List<SessionBookDto> selectBookListBySessionId(@Param("sessionId") Long sessionId);
}

