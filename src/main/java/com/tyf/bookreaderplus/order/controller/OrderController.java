package com.tyf.bookreader.controller;

import com.tyf.bookreader.dto.OrderDto;
import com.tyf.bookreader.dto.PageRespDto;
import com.tyf.bookreader.service.OrderService;
import com.tyf.bookreader.vo.OrderVo;
import com.tyf.bookreader.component.Result;
import com.tyf.bookreader.vo.PageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description TODO
 * @Author shallow
 * @Date 2023/4/1 18:07
 */
@Api(tags = "订单模块")
@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "创建订单")
    @PostMapping("create_order")
    public Result createOrder(@RequestBody OrderVo orderVo){
        return orderService.createOrder(orderVo);
    }

    @ApiOperation(value = "支付订单")
    @PostMapping("pay")
    public Result payOrder(@RequestParam Long orderId){
        return orderService.payOrder(orderId);
    }

    @ApiOperation(value = "查看订单详情")
    @GetMapping("order_details")
    public Result<OrderDto> selectOrderDetails(@RequestParam Long orderId){
        return orderService.selectOrderDetails(orderId);
    }

    @ApiOperation(value = "取消订单")
    @PostMapping("cancel")
    public Result cancelOrder(@RequestParam Long orderId){
        return orderService.cancelOrder(orderId);
    }


    @ApiOperation(value = "分页查看本用户所有订单")
    @GetMapping("get_orders")
    public Result<PageRespDto<OrderDto>> getOrders(PageVo pageVo){
        return orderService.getOrders(pageVo);
    }

    @ApiOperation(value = "删除订单")
    @PostMapping("delete_order")
    public Result deleteOrder(@RequestParam Long orderId){
        return orderService.deleteOrder(orderId);
    }

}
