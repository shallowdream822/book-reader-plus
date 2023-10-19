package com.tyf.bookreaderplus.book.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * (BrUserStarComment)实体类
 *
 * @author shallow
 * @since 2023-04-01 17:52:42
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BrUserStarComment implements Serializable{
    private static final long serialVersionUID= 124359071765397139L;

    @TableId(value = "id" , type = IdType.AUTO)
    private Long id;


    @TableField(value = "user_id")
    private Long userId;


    @TableField(value = "comment_id")
    private Long commentId;

    @TableField(value = "status")
    private Integer status;

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

