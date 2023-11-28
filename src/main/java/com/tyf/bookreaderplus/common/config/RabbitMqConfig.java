package com.tyf.bookreaderplus.common.config;


import com.tyf.bookreaderplus.common.constant.QueueEnum;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description rabbitMq配置类
 * @Author shallow
 * @Date 2023/5/3 15:09
 */
@Configuration
public class RabbitMqConfig {

    /**
     * 延迟交换机
     */
    @Bean
    DirectExchange delayExchange() {
        return ExchangeBuilder
                .directExchange(QueueEnum.QUEUE_ORDER_DELAY.getExchange())
                .durable(true)
                .build();
    }
    /**
     * 死信所绑定的交换机
     */
    @Bean
    DirectExchange deadExchange() {
        return ExchangeBuilder
                .directExchange(QueueEnum.QUEUE_ORDER_DEAD.getExchange())
                .durable(true)
                .build();
    }

    @Bean
    DirectExchange seckillExchange() {
        return ExchangeBuilder
                .directExchange(QueueEnum.QUEUE_SECKILL.getExchange())
                .durable(true)
                .build();
    }


    /**
     * 死信队列
     * @return
     */
    @Bean
    public Queue deadQueue(){
        return new Queue(QueueEnum.QUEUE_ORDER_DEAD.getName());
    }

    /**
     * 订单延迟队列
     */
    @Bean
    public Queue delayQueue(){
        return QueueBuilder.durable(QueueEnum.QUEUE_ORDER_DELAY.getName())
                .withArgument("x-message-ttl", 60000)
                .withArgument("x-dead-letter-exchange",QueueEnum.QUEUE_ORDER_DEAD.getExchange())//转发的交换机
                .withArgument("x-dead-letter-routing-key",QueueEnum.QUEUE_ORDER_DEAD.getRouteKey())//转发的路由key
                .build();
    }

    @Bean
    public Queue seckillQueue(){
        return new Queue(QueueEnum.QUEUE_SECKILL.getName());
    }

    /**
     * 绑定死信交换机和队列
     */

    @Bean
    Binding deadBinding(DirectExchange deadExchange, Queue deadQueue){
        return BindingBuilder
                .bind(deadQueue)
                .to(deadExchange)
                .with(QueueEnum.QUEUE_ORDER_DEAD.getRouteKey());
    }

    /**
     * 绑定延迟
     */
    @Bean
    Binding delayBinding(DirectExchange delayExchange,Queue delayQueue){
        return BindingBuilder
                .bind(delayQueue)
                .to(delayExchange)
                .with(QueueEnum.QUEUE_ORDER_DELAY.getRouteKey());
    }

    @Bean
    Binding seckillBinding(DirectExchange seckillExchange,Queue seckillQueue){
        return BindingBuilder
                .bind(seckillQueue)
                .to(seckillExchange)
                .with(QueueEnum.QUEUE_SECKILL.getRouteKey());
    }
}
