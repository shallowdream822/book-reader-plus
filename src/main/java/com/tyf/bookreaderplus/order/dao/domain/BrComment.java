package com.tyf.bookreaderplus.order.dao.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * (BrComment)实体类
 *
 * @author shallow
 * @since 2023-04-01 17:52:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BrComment implements Serializable{
    private static final long serialVersionUID= -38223688252654989L;

    @TableId(value = "id" , type = IdType.AUTO)
    private Long id;

    /**用户id*/
    @TableField(value = "user_id")
    private Long userId;

    /**图书id*/
    @TableField(value = "book_id")
    private Long bookId;

    /**父评论id*/
    @TableField(value = "parent_id")
    private Long parentId;

    /**评论内容*/
    @TableField(value = "comment_content")
    private String commentContent;

    /**点赞数*/
    @TableField(value = "comment_stars")
    private Integer commentStars;

//    /**评论层级*/
//    @TableField(value = "level")
//    private Integer level;

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

