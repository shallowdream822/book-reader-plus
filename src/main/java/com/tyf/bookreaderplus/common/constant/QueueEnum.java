package com.tyf.bookreaderplus.common.constant;

import lombok.Getter;

/**
 * @Description TODO
 * @Author shallow
 * @Date 2023/5/2 21:39
 */
@Getter
public enum QueueEnum {
    /**
     * 消息通知队列
     */
    QUEUE_ORDER_DELAY("relay_exchange", "book_reader.order.delay", "book_reader.order.delay"),
    /**
     * 消息通知ttl队列
     */
    QUEUE_ORDER_DEAD("dead_exchange", "book_reader.order.dead", "book_reader.order.dead");


    /**
     * 交换名称
     */
    private String exchange;
    /**
     * 队列名称
     */
    private String name;
    /**
     * 路由键
     */
    private String routeKey;

    QueueEnum(String exchange, String name, String routeKey) {
        this.exchange = exchange;
        this.name = name;
        this.routeKey = routeKey;
    }

}
