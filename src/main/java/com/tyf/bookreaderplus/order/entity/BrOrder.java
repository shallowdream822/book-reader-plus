package com.tyf.bookreaderplus.order.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * (BrOrder)实体类
 *
 * @author shallow
 * @since 2023-05-03 17:36:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrOrder implements Serializable{
    private static final long serialVersionUID= -67896926990408328L;

    @TableId(value = "id" , type = IdType.ASSIGN_ID)
    private Long id;

    /**订单编号*/
    @TableField(value = "order_num")
    private String orderNum;

    /**用户id*/
    @TableField(value = "user_id")
    private Long userId;

    /**订单价格*/
    @TableField(value = "order_price")
    private Double orderPrice;

    /**0-未支付 1-已支付 2-已核销？3-已取消 4-退款中 5-已退款*/
    @TableField(value = "status")
    private Integer status;

    /**创建时间*/

    @TableField(value = "create_time" , fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**更新时间*/

    @TableField(value = "update_time" , fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**支付时间*/
    @TableField(value = "pay_time")
    private LocalDateTime payTime;

    /**退款时间*/
    @TableField(value = "refund_time")
    private LocalDateTime refundTime;

    /**逻辑删除 0 -正常，1-删除*/
    @TableLogic
    @TableField(value = "deleted")
    private Integer deleted;
}

