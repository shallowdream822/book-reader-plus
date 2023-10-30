package com.tyf.bookreaderplus.order.dao.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * (BrAuthor)实体类
 *
 * @author shallow
 * @since 2023-04-01 17:52:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrAuthor implements Serializable{
    private static final long serialVersionUID= 850505114611573260L;

    @TableId(value = "id" , type = IdType.AUTO)
    private Long id;

    /**作者名*/
    @TableField(value = "author_name")
    private String authorName;

    /**邮箱*/
    @TableField(value = "author_email")
    private String authorEmail;

    /**用户状态  0 -正常，1-封禁
*/
    @TableField(value = "status")
    private Integer status;

    /**用户id*/
    @TableField(value = "user_id")
    private Long userId;

    /**创建时间*/

    @TableField(value = "create_time" , fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**更新时间*/

    @TableField(value = "update_time" , fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}

