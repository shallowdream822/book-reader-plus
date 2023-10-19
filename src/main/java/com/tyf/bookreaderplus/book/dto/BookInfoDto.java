package com.tyf.bookreaderplus.book.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description TODO
 * @Author shallow
 * @Date 2023/4/2 11:09
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookInfoDto {
    /**
     * 小说id
     */
    private Long id;

    /**
     * 类别id
     */
    private Long categoryId;

    /**
     * 类别名，非本表
     */
    private String categoryName;

    /**
     * 小说简介
     */
    private String bookDesc;

    /**
     * 小说封面路径
     */
    private String bookImg;

    /**
     * 小说名
     */
    private String bookName;
    /**
     * 作者id
     */
    private Long authorId;

    /**
     * 作者名，非本表
     */
    private String authorName;

    /**
     * 最新章节
     */
    private Long bookLastedChapter;

    /**
     * 出版商名称
     */
    private String publisher;

    /**
     * 出版日期
     */
    private Integer publishYear;

    /**
     * 已读人数
     */
    private Integer bookHasRead;

    /**
     * 点赞数
     */
    private Integer bookStars;

    /**
     * 全本价格
     */
    private Double bookPrice;
}
