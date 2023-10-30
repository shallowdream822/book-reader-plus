package com.tyf.bookreaderplus.order.dao.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * (BrChapter)实体类
 *
 * @author shallow
 * @since 2023-04-01 17:52:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrChapter implements Serializable{
    private static final long serialVersionUID= -68048979976480008L;

    @TableId(value = "id" , type = IdType.AUTO)
    private Long id;

    /**图书id*/
    @TableField(value = "book_id")
    private Long bookId;

    /**章节号*/
    @TableField(value = "chapter_num")
    private Long chapterNum;

    /**章节名*/
    @TableField(value = "chapter_name")
    private String chapterName;

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

