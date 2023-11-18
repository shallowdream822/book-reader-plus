package com.tyf.bookreader.service;

import com.tyf.bookreader.dto.OrderDto;
import com.tyf.bookreader.dto.PageRespDto;
import com.tyf.bookreader.vo.OrderVo;
import com.tyf.bookreader.component.Result;
import com.tyf.bookreader.vo.PageVo;

import java.util.List;

/**
 * @Description 订单业务
 * @Author shallow
 * @Date 2023/5/2 20:57
 */

public interface OrderService {

    Result createOrder(OrderVo orderVo);

    Result payOrder(Long orderId);

    Result<OrderDto> selectOrderDetails(Long orderId);

    Result cancelOrder(Long orderId);

    Result<PageRespDto<OrderDto>> getOrders(PageVo pageVo);

    Result deleteOrder(Long orderId);
}
