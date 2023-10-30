package com.tyf.bookreaderplus.order.entity.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * (BrNewsCategory)实体类
 *
 * @author shallow
 * @since 2023-04-01 17:52:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrNewsCategory implements Serializable{
    private static final long serialVersionUID= 971727516600821658L;

    @TableId(value = "id" , type = IdType.AUTO)
    private Long id;

    /**类别名*/
    @TableField(value = "news_category_name")
    private String newsCategoryName;

    /**排序*/
    @TableField(value = "sort")
    private Integer sort;

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

