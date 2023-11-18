package com.tyf.bookreaderplus.book.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * (BrBookshelf)实体类
 *
 * @author shallow
 * @since 2023-04-01 17:52:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrBookshelf implements Serializable{
    private static final long serialVersionUID= -76474353370031318L;

    @TableId(value = "id" , type = IdType.AUTO)
    private Long id;

    /**用户id*/
    @TableField(value = "user_id")
    private Long userId;

    /**图书id*/
    @TableField(value = "book_id")
    private Long bookId;

    /**上次阅读章节号*/
    @TableField(value = "pre_read_id")
    private Long preReadId;

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

