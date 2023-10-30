package com.tyf.bookreaderplus.order.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tyf.bookreader.domain.BrOrderDetails;
import com.tyf.bookreader.dto.OrderDto;
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

    OrderDto selectOrderDtoByOrderId(@Param("orderId") Long orderId);
}

