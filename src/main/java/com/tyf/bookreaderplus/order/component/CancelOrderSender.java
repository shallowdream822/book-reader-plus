package com.tyf.bookreaderplus.order.component;

import com.tyf.bookreaderplus.common.constant.QueueEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description TODO
 * @Author shallow
 * @Date 2023/5/5 13:09
 */
@Slf4j
@Component
public class CancelOrderSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendMessage(Long orderId,final long delayTimes){
        amqpTemplate.convertAndSend(QueueEnum.QUEUE_ORDER_DELAY.getExchange(), QueueEnum.QUEUE_ORDER_DELAY.getRouteKey(), orderId, message -> {
            //给消息设置延迟毫秒值
            message.getMessageProperties().setExpiration(String.valueOf(delayTimes));
            return message;
        });
        log.debug("orderNum:{}",orderId);
    }

}
