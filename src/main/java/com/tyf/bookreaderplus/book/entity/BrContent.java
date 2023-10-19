package com.tyf.bookreaderplus.book.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * (BrContent)实体类
 *
 * @author shallow
 * @since 2023-04-01 17:52:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrContent implements Serializable{
    private static final long serialVersionUID= 434946002886834497L;

    @TableId(value = "id" , type = IdType.AUTO)
    private Long id;

    /**章节id*/
    @TableField(value = "chapter_id")
    private Long chapterId;

    /**内容*/
    @TableField(value = "content")
    private String content;

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

