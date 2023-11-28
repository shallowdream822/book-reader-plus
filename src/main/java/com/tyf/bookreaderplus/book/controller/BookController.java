package com.tyf.bookreaderplus.book.controller;

import com.tyf.bookreaderplus.common.component.Result;
import com.tyf.bookreaderplus.book.dto.*;
import com.tyf.bookreaderplus.book.service.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @Description 小说模块
 * @Author shallow
 * @Date 2023/4/1 18:06
 */
@Api(tags = "小说模块")
@RestController
@RequestMapping("/api/book")
public class BookController {
    @Autowired
    private BookService bookService;

    /**
     * 查看小说分类列表
     * @return
     */
    @ApiOperation(value = "查看小说分类列表")
    @GetMapping("category_list")
    public Result<List<BookCategoryDto>> listCategory(){
        return Result.ok(bookService.listCategory());
    }

    @ApiOperation(value = "查看小说信息")
    @GetMapping("/{bookId}")
    public Result<BookInfoDto> getBookById(@PathVariable("bookId")Long bookId){
        return Result.ok(bookService.getBookById(bookId));
    }

    @ApiOperation(value = "增加小说点击量接口")
    @PostMapping("visit")
    public Result addVisitCount(@RequestParam("bookId") Long bookId){
        bookService.addVisitCount(bookId);
        return Result.ok();
    }



    @ApiOperation(value = "小说推荐列表查询接口")
    @GetMapping("rec_list/{bookId}")
    public Result<List<BookDto>> listRecBooks(@PathVariable("bookId")Long bookId) throws NoSuchAlgorithmException {
        return Result.ok(bookService.listRecBooks(bookId));
    }



}
