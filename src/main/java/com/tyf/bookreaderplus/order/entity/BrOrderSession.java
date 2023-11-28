package com.tyf.bookreaderplus.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName br_order_session
 */
@TableName(value ="br_order_session")
@Data
public class BrOrderSession implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 订单号
     */
    private Long orderNum;

    /**
     * 场次id
     */
    private Long sessionId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}