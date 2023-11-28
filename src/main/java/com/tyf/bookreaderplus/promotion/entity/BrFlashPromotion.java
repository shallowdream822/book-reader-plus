package com.tyf.bookreaderplus.promotion.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * (BrFlashPromotion)实体类
 *
 * @author shallow
 * @since 2023-11-18 20:18:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BrFlashPromotion implements Serializable{
    private static final long serialVersionUID= 158626929570795698L;

    @TableId(value = "id" , type = IdType.AUTO)
    private Long id;

    /**标题*/
    @TableField(value = "title")
    private String title;

    /**开始日期*/
    @TableField(value = "start_date")
    private Date startDate;

    /**结束日期*/
    @TableField(value = "end_date")
    private Date endDate;

    /**状态： 0-未开始，1-进行中，2-已结束*/
    @TableField(value = "status")
    private Integer status;

    /**创建时间*/

    @TableField(value = "create_time" , fill = FieldFill.INSERT)
    private Date createTime;
}

