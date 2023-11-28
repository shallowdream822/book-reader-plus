package com.tyf.bookreaderplus.promotion.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 活动图书关联表(BrPromotionBook)实体类
 *
 * @author shallow
 * @since 2023-11-18 20:18:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BrPromotionBook implements Serializable{
    private static final long serialVersionUID= -47025808171818903L;

    @TableId(value = "id" , type = IdType.AUTO)
    private Long id;


    @TableField(value = "promotion_id")
    private Long promotionId;


    @TableField(value = "promotion_session_id")
    private Long promotionSessionId;


    @TableField(value = "book_id")
    private Long bookId;


    @TableField(value = "promotion_price")
    private Integer promotionPrice;
}

