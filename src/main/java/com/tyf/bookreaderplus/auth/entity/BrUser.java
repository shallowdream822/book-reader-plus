package com.tyf.bookreaderplus.auth.entity;

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
 * (BrUser)实体类
 *
 * @author shallow
 * @since 2023-10-18 21:09:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrUser implements Serializable{
    private static final long serialVersionUID= -15564691137204300L;

    @TableId(value = "id" , type = IdType.AUTO)
    private Long id;

    /**用户名*/
    @TableField(value = "user_name")
    private String userName;

    /**用户手机号*/
    @TableField(value = "user_phone")
    private String userPhone;

    /**密码*/
    @TableField(value = "user_pwd")
    private String userPwd;

    /**盐值*/
    @TableField(value = "user_salt")
    private String userSalt;

    /**昵称*/
    @TableField(value = "user_nickname")
    private String userNickname;

    /**用户性别 0-男 1-女*/
    @TableField(value = "user_sex")
    private Integer userSex;

    /**年龄*/
    @TableField(value = "user_age")
    private Integer userAge;

    /**用户头像地址*/
    @TableField(value = "user_img")
    private String userImg;

    /**账户余额*/
    @TableField(value = "account_balance")
    private Double accountBalance;

    /**用户状态 0 -正常，1-封禁*/
    @TableField(value = "status")
    private Integer status;

    /**最近登陆时间
*/
    @TableField(value = "last_login_time")
    private LocalDateTime lastLoginTime;

    /**创建时间*/

    @TableField(value = "create_time" , fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**更新时间*/

    @TableField(value = "update_time" , fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}

