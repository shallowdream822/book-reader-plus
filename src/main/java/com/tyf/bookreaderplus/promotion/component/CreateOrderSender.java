package com.tyf.bookreaderplus.promotion.component;

import com.tyf.bookreaderplus.common.constant.CommonConstants;
import com.tyf.bookreaderplus.common.constant.QueueEnum;
import com.tyf.bookreaderplus.promotion.vo.SeckillVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.amqp.core.AmqpTemplate;
/**
 * @Author shallow
 * @Date: 2023/11/28/ 18:42
 * @description
 */

@Slf4j
@Component
public class CreateOrderSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendMessage(SeckillVo seckillVo){
        amqpTemplate.convertAndSend(QueueEnum.QUEUE_SECKILL.getExchange(), QueueEnum.QUEUE_SECKILL.getRouteKey(),
                seckillVo,message -> {
                    //给消息设置延迟毫秒值
                    message.getMessageProperties().setExpiration(CommonConstants.QUEUE_DEAD_TTL.toString());
                    return message;
                });
    }


}
