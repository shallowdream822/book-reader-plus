package com.tyf.bookreaderplus.book.controller;

import com.tyf.bookreaderplus.book.dto.ChapterDto;
import com.tyf.bookreaderplus.book.dto.ContentDto;
import com.tyf.bookreaderplus.book.dto.LastChapterInfoDto;
import com.tyf.bookreaderplus.book.service.ChapterService;
import com.tyf.bookreaderplus.common.component.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @Description TODO
 * @Author shallow
 * @Date 2023/10/19 13:20
 */
@RestController
@RequestMapping("/api/chapter")
public class ChapterController {

    @Autowired
    private ChapterService chapterService;

    @ApiOperation(value = "小说最新章节相关信息查询接口")
    @GetMapping("last_chapter_info/{bookId}")
    public Result<LastChapterInfoDto> getLastChapterAbout(@PathVariable("bookId") Long bookId){
        return Result.ok(chapterService.getLastChapterAbout(bookId));
    }

    @ApiOperation(value = "小说章节列表查询接口")
    @GetMapping("chapter_list/{bookId}")
    public Result<List<ChapterDto>> listChapters(@PathVariable("bookId") Long bookId){
        return Result.ok(chapterService.listChapters(bookId));
    }

    @ApiOperation(value = "章节信息获取接口")
    @GetMapping("content/{chapterId}")
    public Result<ContentDto> getContentByChapterId(@PathVariable("chapterId")Long chapterId){
        return Result.ok(chapterService.getContentByChapterId(chapterId));
    }

    @ApiOperation(value = "获取本章节内容")
    @GetMapping("read/{chapterId}")
    public Result<ContentDto> getContent(@PathVariable Long chapterId){
        return Result.ok(chapterService.getContentByChapterId(chapterId));
    }

    @ApiOperation(value = "获取相邻章节内容")
    @GetMapping("neighbor_chapter/{chapterId}")
    public Result<ContentDto> getPreChapterId(@PathVariable("chapterId")Long chapterId,@RequestParam("type")Boolean type){
        return Result.ok(chapterService.getNeighborChapterId(chapterId,type));
    }



}
