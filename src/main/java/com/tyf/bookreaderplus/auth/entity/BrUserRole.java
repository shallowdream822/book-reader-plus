package com.tyf.bookreaderplus.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (BrUserRole)实体类
 *
 * @author shallow
 * @since 2023-04-01 17:52:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BrUserRole implements Serializable{
    private static final long serialVersionUID= -15891635176153850L;

    @TableId(value = "id" , type = IdType.AUTO)
    private Long id;

    /**用户id*/
    @TableField(value = "user_id")
    private Long userId;

    /**角色id*/
    @TableField(value = "role_id")
    private Long roleId;
}

