package com.tyf.bookreaderplus.order.entity;

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
 * (BrOrderDetails)实体类
 *
 * @author shallow
 * @since 2023-05-03 17:36:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrOrderDetails implements Serializable{
    private static final long serialVersionUID= -52998575529655487L;

    @TableId(value = "id" , type = IdType.AUTO)
    private Long id;


    @TableField(value = "order_num")
    private Long orderNum;

    /**本书id*/
    @TableField(value = "book_id")
    private Long bookId;

    /**本书名称*/
    @TableField(value = "book_name")
    private String bookName;

    /**本书价格*/
    @TableField(value = "book_price")
    private Double bookPrice;

    /**创建时间*/

    @TableField(value = "create_time" , fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**更新时间*/

    @TableField(value = "update_time" , fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}

