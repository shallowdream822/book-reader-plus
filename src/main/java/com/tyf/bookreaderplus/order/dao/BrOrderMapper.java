package com.tyf.bookreaderplus.order.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tyf.bookreader.domain.BrOrder;
import org.apache.ibatis.annotations.Mapper;

/**
 * (BrOrder)表数据库访问层
 *
 * @author shallow
 * @since 2023-05-03 17:36:38
 */
@Mapper
public interface BrOrderMapper extends BaseMapper<BrOrder> {}

