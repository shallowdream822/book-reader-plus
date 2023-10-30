package com.tyf.bookreaderplus.order.entity.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * (BrUserFeedback)实体类
 *
 * @author shallow
 * @since 2023-04-01 17:52:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrUserFeedback implements Serializable{
    private static final long serialVersionUID= -91805829068832116L;

    @TableId(value = "id" , type = IdType.AUTO)
    private Long id;

    /**用户id*/
    @TableField(value = "user_id")
    private Long userId;

    /**反馈内容*/
    @TableField(value = "content")
    private String content;

    /**创建时间*/

    @TableField(value = "create_time" , fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**更新时间*/

    @TableField(value = "update_time" , fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}

