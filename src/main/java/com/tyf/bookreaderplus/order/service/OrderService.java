package com.tyf.bookreaderplus.order.service;


import com.tyf.bookreaderplus.common.component.PageVo;
import com.tyf.bookreaderplus.common.component.Result;
import com.tyf.bookreaderplus.common.dto.PageRespDto;
import com.tyf.bookreaderplus.order.dto.OrderDto;
import com.tyf.bookreaderplus.order.vo.OrderVo;

/**
 * @Description 订单业务
 * @Author shallow
 * @Date 2023/5/2 20:57
 */

public interface OrderService {

    void createOrder(OrderVo orderVo);

    void payOrder(Long orderId);

    Result<OrderDto> selectOrderDetails(Long orderId);

    void cancelOrder(Long orderId);

    Result<PageRespDto<OrderDto>> getOrders(PageVo pageVo);

    void deleteOrder(Long orderId);
}
