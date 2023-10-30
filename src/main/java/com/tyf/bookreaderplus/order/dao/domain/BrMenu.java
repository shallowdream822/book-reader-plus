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

/**
 * (BrMenu)实体类
 *
 * @author shallow
 * @since 2023-04-01 17:52:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrMenu implements Serializable{
    private static final long serialVersionUID= 496112819228873669L;

    @TableId(value = "id" , type = IdType.AUTO)
    private Long id;

    /**父菜单id;一级菜单为0*/
    @TableField(value = "parent_id")
    private Long parentId;

    /**菜单名称*/
    @TableField(value = "name")
    private String name;

    /**菜单url*/
    @TableField(value = "url")
    private String url;

    /**菜单排序*/
    @TableField(value = "sort")
    private Integer sort;

    /**菜单图片*/
    @TableField(value = "menu_pic")
    private String menuPic;

    /**是否隐藏0-否；1-是*/
    @TableField(value = "is_hidden")
    private Integer isHidden;

    /**创建时间*/

    @TableField(value = "create_time" , fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**更新时间*/

    @TableField(value = "update_time" , fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}

