package com.tyf.bookreaderplus.book.service;


import com.tyf.bookreaderplus.book.dto.CommentDTO;
import com.tyf.bookreaderplus.book.vo.CommentPageVo;
import com.tyf.bookreaderplus.common.dto.PageRespDto;

import java.util.List;

/**
 * @Description 评论业务
 * @Author shallow
 * @Date 2023/4/3 16:58
 */
public interface CommentService {


    PageRespDto<CommentDTO> getCommentList(CommentPageVo commentPageVo);

    List<CommentDTO> getHotCommentsById(Long bookId);

    void optCommentStar(Long commentId);
}
