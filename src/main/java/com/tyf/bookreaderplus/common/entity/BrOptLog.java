package com.tyf.bookreaderplus.common.entity;

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
 * (BrOptLog)实体类
 *
 * @author shallow
 * @since 2023-05-09 16:20:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BrOptLog implements Serializable{
    private static final long serialVersionUID= -57628666545657661L;
    /**
     * 主键id
     */
    @TableId(value = "id" , type = IdType.AUTO)
    private Long id;

    /**操作模块*/
    @TableField(value = "opt_module")
    private String optModule;

    /**操作类型*/
    @TableField(value = "opt_type")
    private String optType;

    /**操作url*/
    @TableField(value = "opt_url")
    private String optUrl;

    /**操作方法*/
    @TableField(value = "opt_method")
    private String optMethod;

    /**操作描述*/
    @TableField(value = "opt_desc")
    private String optDesc;

    /**请求参数*/
    @TableField(value = "request_param")
    private String requestParam;

    /**请求方式*/
    @TableField(value = "request_method")
    private String requestMethod;

    /**返回数据*/
    @TableField(value = "response_data")
    private String responseData;

    /**用户id*/
    @TableField(value = "user_id")
    private Long userId;

    /**用户昵称*/
    @TableField(value = "user_nickname")
    private String userNickname;

    /**操作ip*/
    @TableField(value = "ip_address")
    private String ipAddress;

    /**操作地址*/
    @TableField(value = "ip_source")
    private String ipSource;

    /**创建时间*/

    @TableField(value = "create_time" , fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**更新时间*/

    @TableField(value = "update_time" , fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}

