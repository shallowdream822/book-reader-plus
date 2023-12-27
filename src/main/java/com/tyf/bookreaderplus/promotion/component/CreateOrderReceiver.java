package com.tyf.bookreaderplus.promotion.component;

import com.tyf.bookreaderplus.order.service.OrderService;
import com.tyf.bookreaderplus.order.vo.OrderDetailsVo;
import com.tyf.bookreaderplus.order.vo.OrderVo;
import com.tyf.bookreaderplus.promotion.constant.PromotionInitConstants;
import com.tyf.bookreaderplus.promotion.vo.SeckillVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @Author shallow
 * @Date: 2023/11/28/ 19:59
 * @description
 */

@Component
@Slf4j
public class CreateOrderReceiver {

    @Autowired
    private OrderService orderService;

    @RabbitListener(queues = "book_reader.seckill")
    public void handleCreateSeckillOrder(SeckillVo seckillVo){
        log.debug("秒杀订单信息是{}",seckillVo);
        OrderDetailsVo orderDetailsVo = OrderDetailsVo.builder().bookId(seckillVo.getBookId())
                .bookName(seckillVo.getBookName())
                .bookPrice(seckillVo.getBookPrice()).build();
        ArrayList<OrderDetailsVo> orderDetailsVoList = new ArrayList<>();
        orderDetailsVoList.add(orderDetailsVo);
        OrderVo orderVo = OrderVo.builder().isSeckill(PromotionInitConstants.SECKILL_ORDER_STATUS)
                .orderDetailsVoList(orderDetailsVoList).build();
        orderService.createOrder(orderVo);
    }
}
