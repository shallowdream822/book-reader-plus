package com.tyf.bookreaderplus.book.controller;

import com.tyf.bookreaderplus.book.dto.CommentDTO;
import com.tyf.bookreaderplus.book.service.CommentService;
import com.tyf.bookreaderplus.book.vo.CommentPageVo;
import com.tyf.bookreaderplus.common.component.Result;
import com.tyf.bookreaderplus.common.dto.PageRespDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description TODO
 * @Author shallow
 * @Date 2023/10/19 16:05
 */
@RestController
@RequestMapping("/api/comment")
@Api(tags = "评论controller")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ApiOperation(value = "根据bookId分页查看本书所有评论")
    @PostMapping("list")
    public Result<PageRespDto<CommentDTO>> getCommentList(@RequestBody CommentPageVo commentPageVo){
        return Result.ok(commentService.getCommentList(commentPageVo));
    }

    @ApiOperation(value = "获得本书热门评论")
    @GetMapping("hotComment/{bookId}")
    public Result<List<CommentDTO>> getHotCommentsById(@PathVariable("bookId") Long bookId){
        return Result.ok(commentService.getHotCommentsById(bookId));
    }

    @ApiOperation(value = "点赞评论")
    @PostMapping("star")
    public Result saveCommentLike(@RequestParam("commentId") Long commentId){
        commentService.optCommentStar(commentId);
        return Result.ok();
    }
}
