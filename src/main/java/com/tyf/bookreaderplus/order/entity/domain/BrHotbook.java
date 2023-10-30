package com.tyf.bookreaderplus.order.entity.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * (BrHotbook)实体类
 *
 * @author shallow
 * @since 2023-04-01 17:52:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrHotbook implements Serializable{
    private static final long serialVersionUID= -34544138283696122L;

    @TableId(value = "id" , type = IdType.AUTO)
    private Long id;

    /**图书id*/
    @TableField(value = "book_id")
    private Long bookId;

    /**热门类型 0-今日推荐 1-最多点击 2-猜你喜欢*/
    @TableField(value = "book_type")
    private Integer bookType;

    /**热门权重*/
    @TableField(value = "sort_weight")
    private Integer sortWeight;

    /**创建时间*/

    @TableField(value = "create_time" , fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**更新时间*/

    @TableField(value = "update_time" , fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**逻辑删除 0 -正常，1-删除*/
                @TableLogic

    private Integer deleted;
}

