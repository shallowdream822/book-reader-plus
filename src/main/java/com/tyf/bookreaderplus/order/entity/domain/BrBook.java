package com.tyf.bookreaderplus.order.entity.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * (BrBook)实体类
 *
 * @author shallow
 * @since 2023-04-01 17:52:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrBook implements Serializable{
    private static final long serialVersionUID= -99859305241706718L;

    @TableId(value = "id" , type = IdType.AUTO)
    private Long id;

    /**类别id*/
    @TableField(value = "category_id")
    private Long categoryId;

    /**图书封面地址*/
    @TableField(value = "book_img")
    private String bookImg;

    /**图书名*/
    @TableField(value = "book_name")
    private String bookName;

    /**作者id*/
    @TableField(value = "author_id")
    private Long authorId;

    /**图书简介*/
    @TableField(value = "book_desc")
    private String bookDesc;

    /**图书最新章节*/
    @TableField(value = "book_latest_chapter")
    private Long bookLatestChapter;

    /**出版商名字*/
    @TableField(value = "book_publish")
    private String bookPublish;

    /**出版年份*/
    @TableField(value = "book_year")
    private Integer bookYear;

    /**已读人数*/
    @TableField(value = "book_hasread")
    private int bookHasread;

    /**点赞数*/
    @TableField(value = "book_stars")
    private int bookStars;

    /**图书价格*/
    @TableField(value = "book_price")
    private Double bookPrice;

    /**创建时间*/

    @TableField(value = "create_time" , fill = FieldFill.INSERT)
    private Date createTime;

    /**更新时间*/

    @TableField(value = "update_time" , fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**逻辑删除 0 -正常，1-删除*/
                @TableLogic

    private Integer deleted;
}

