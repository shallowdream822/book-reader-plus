package com.tyf.bookreaderplus.book.service;

import com.tyf.bookreaderplus.book.dto.ChapterDto;
import com.tyf.bookreaderplus.book.dto.ContentDto;
import com.tyf.bookreaderplus.book.dto.LastChapterInfoDto;

import java.util.List;

/**
 * @Description TODO
 * @Author shallow
 * @Date 2023/10/19 13:24
 */
public interface ChapterService {
    LastChapterInfoDto getLastChapterAbout(Long bookId);

    List<ChapterDto> listChapters(Long bookId);

    ContentDto getContentByChapterId(Long chapterId);

    ContentDto getNeighborChapterId(Long chapterId,Boolean type);
}
