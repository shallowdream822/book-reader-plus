package com.tyf.bookreaderplus.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (BrRolePermission)实体类
 *
 * @author shallow
 * @since 2023-05-02 16:25:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrRolePermission implements Serializable{
    private static final long serialVersionUID= -62312154791937126L;

    @TableId(value = "id" , type = IdType.AUTO)
    private Long id;

    /**角色id*/
    @TableField(value = "role_id")
    private Long roleId;

    /**菜单id*/
    @TableField(value = "permission_id")
    private Long permissionId;
}

