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
 * (BrRole)实体类
 *
 * @author shallow
 * @since 2023-04-01 17:52:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrRole implements Serializable{
    private static final long serialVersionUID= -66568888680460943L;

    @TableId(value = "id" , type = IdType.AUTO)
    private Long id;

    /**角色名*/
    @TableField(value = "role_name")
    private String roleName;

    /**角色描述*/
    @TableField(value = "role_desc")
    private String roleDesc;

    /**是否禁用 0-否；1-是*/
    @TableField(value = "is_disable")
    private Integer isDisable;

    /**创建时间*/

    @TableField(value = "create_time" , fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**更新时间*/

    @TableField(value = "update_time" , fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}

