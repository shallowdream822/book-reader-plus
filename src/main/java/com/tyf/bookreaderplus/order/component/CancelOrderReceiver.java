package com.tyf.bookreader.component;

import com.tyf.bookreader.domain.BrOrder;
import com.tyf.bookreader.mapper.BrOrderMapper;
import com.tyf.bookreader.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
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
    public void handleCancel(Long orderId){
        log.debug("订单id是{}",orderId);
        BrOrder order = orderMapper.selectById(orderId);
        if (Objects.nonNull(order) && order.getStatus() == 0){
            orderService.cancelOrder(orderId);
        }
    }


}
