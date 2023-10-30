package com.tyf.bookreaderplus.order.entity.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * (BrBookUser)实体类
 *
 * @author shallow
 * @since 2023-05-04 16:38:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BrBookUser implements Serializable{
    private static final long serialVersionUID= -95657524412409173L;

    @TableId(value = "id" , type = IdType.AUTO)
    private Long id;

    /**图书id*/
    @TableField(value = "book_id")
    private Long bookId;

    /**用户id*/
    @TableField(value = "user_id")
    private Long userId;

    /**创建时间*/

    @TableField(value = "create_time" , fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**更新时间*/

    @TableField(value = "update_time" , fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}

