package com.tyf.bookreaderplus.order.dao.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * (BrNews)实体类
 *
 * @author shallow
 * @since 2023-04-01 17:52:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrNews implements Serializable{
    private static final long serialVersionUID= 235940268134378503L;

    @TableId(value = "id" , type = IdType.AUTO)
    private Long id;

    /**新闻类型id*/
    @TableField(value = "news_category_id")
    private Long newsCategoryId;

    /**消息来源*/
    @TableField(value = "source_name")
    private String sourceName;

    /**新闻标题*/
    @TableField(value = "title")
    private String title;

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

