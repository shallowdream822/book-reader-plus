package com.tyf.bookreaderplus.promotion.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * (BrFlashPromotionSession)实体类
 *
 * @author shallow
 * @since 2023-11-18 20:18:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BrFlashPromotionSession implements Serializable{
    private static final long serialVersionUID= -49435871325247725L;

    @TableId(value = "id" , type = IdType.AUTO)
    private Long id;

    /**促销活动id*/
    @TableField(value = "promotion_id")
    private Long promotionId;

    /**场次名称*/
    @TableField(value = "name")
    private String name;

    /**创建时间*/
    @TableField(value = "start_time")
    private LocalDateTime startTime;

    /**更新时间*/
    @TableField(value = "end_time")
    private LocalDateTime endTime;

    /**状态： 0-未开始，1-进行中，2-已结束*/
    @TableField(value = "status")
    private Integer status;

    /**创建时间*/

    @TableField(value = "create_time" , fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}

