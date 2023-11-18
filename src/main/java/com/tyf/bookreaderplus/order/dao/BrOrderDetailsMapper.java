package com.tyf.bookreaderplus.order.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.tyf.bookreaderplus.order.dto.OrderDto;
import com.tyf.bookreaderplus.order.entity.BrOrderDetails;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * (BrOrderDetails)表数据库访问层
 *
 * @author shallow
 * @since 2023-05-03 17:36:38
 */
@Mapper
public interface BrOrderDetailsMapper extends BaseMapper<BrOrderDetails> {

    OrderDto selectOrderDtoByOrderNum(@Param("orderNum") Long orderId);
}

