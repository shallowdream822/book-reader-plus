package com.tyf.bookreaderplus.order.component;


import com.tyf.bookreaderplus.order.dao.BrOrderMapper;
import com.tyf.bookreaderplus.order.entity.BrOrder;
import com.tyf.bookreaderplus.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @Description 取消订单接受者
 * @Author shallow
 * @Date 2023/5/2 21:50
 */

@Slf4j
@Component

public class CancelOrderReceiver {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BrOrderMapper orderMapper;

    @RabbitListener(queues = "book_reader.order.dead")
    public void handleCancel(Long orderNum){
        log.debug("订单Num是{}",orderNum);
        BrOrder order = orderMapper.selectOrderByOrderNum(orderNum);
        if (Objects.nonNull(order) && order.getStatus() == 0){
            orderService.cancelOrder(orderNum);
        }
    }


}
