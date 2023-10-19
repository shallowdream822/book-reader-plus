package com.tyf.bookreaderplus.book.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description TODO
 * @Author shallow
 * @Date 2023/4/2 14:31
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LastChapterInfoDto {

    private ChapterDto chapterDto;

    /**
     * 章节总数
     */
    private Long chapterTotal;

    /**
     * 内容概要（30)字
     */
    private String contentSummary;
}
