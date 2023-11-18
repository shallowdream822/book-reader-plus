package com.tyf.bookreaderplus.order.controller;

import com.tyf.bookreaderplus.common.component.PageVo;
import com.tyf.bookreaderplus.common.component.Result;
import com.tyf.bookreaderplus.common.dto.PageRespDto;
import com.tyf.bookreaderplus.order.dto.OrderDto;
import com.tyf.bookreaderplus.order.service.OrderService;
import com.tyf.bookreaderplus.order.vo.OrderVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description TODO
 * @Author shallow
 * @Date 2023/4/1 18:07
 */
@Api(tags = "订单模块")
@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "生成订单页信息")
    @PostMapping("/generate_order_info")
    public Result generateOrderInfo(@RequestBody OrderVo orderVo){
        orderService.createOrder(orderVo);
        return Result.ok();
    }


    @ApiOperation(value = "创建订单")
    @PostMapping("/create_order")
    public Result createOrder(@RequestBody OrderVo orderVo){
        orderService.createOrder(orderVo);
        return Result.ok();
    }

    @ApiOperation(value = "支付订单")
    @PostMapping("/pay")
    public Result payOrder(@RequestParam Long orderId){
        orderService.payOrder(orderId);
        return Result.ok();
    }

    @ApiOperation(value = "查看订单详情")
    @GetMapping("/order_details")
    public Result<OrderDto> selectOrderDetails(@RequestParam Long orderNum){
        return orderService.selectOrderDetails(orderNum);
    }

    @ApiOperation(value = "取消订单")
    @PostMapping("/cancel")
    public Result cancelOrder(@RequestParam Long orderId){
        orderService.cancelOrder(orderId);
        return Result.ok();
    }


    @ApiOperation(value = "分页查看本用户所有订单")
    @GetMapping("/get_orders")
    public Result<PageRespDto<OrderDto>> getOrders(@RequestBody PageVo pageVo){
        return orderService.getOrders(pageVo);
    }

    @ApiOperation(value = "删除订单")
    @PostMapping("/delete_order")
    public Result deleteOrder(@RequestParam Long orderId){
        orderService.deleteOrder(orderId);
        return Result.ok();
    }

}
