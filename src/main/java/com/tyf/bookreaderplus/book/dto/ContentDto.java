package com.tyf.bookreaderplus.book.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description TODO
 * @Author shallow
 * @Date 2023/4/2 15:21
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContentDto {

    /**
     * 章节号
     */
    private Long chapterNum;

    /**
     * 章节名
     */
    private String chapterName;
    /**
     * 章节内容
     */
    private String content;
}
