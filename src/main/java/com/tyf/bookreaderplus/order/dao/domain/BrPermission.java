package com.tyf.bookreaderplus.order.dao.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * (BrPermission)实体类
 *
 * @author shallow
 * @since 2023-05-02 16:24:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrPermission implements Serializable{
    private static final long serialVersionUID= 181583426894514677L;

    @TableId(value = "id" , type = IdType.AUTO)
    private Long id;

    /**权限名*/
    @TableField(value = "permission_name")
    private String permissionName;

    /**资源路径*/
    @TableField(value = "permission_url")
    private String permissionUrl;

    /**请求方式*/
    @TableField(value = "request_method")
    private String requestMethod;

    /**是否禁用 0-否；1-是*/
    @TableField(value = "is_disable")
    private Integer isDisable;

    /**创建时间*/

    @TableField(value = "create_time" , fill = FieldFill.INSERT)
    private Date createTime;

    /**更新时间*/

    @TableField(value = "update_time" , fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}

