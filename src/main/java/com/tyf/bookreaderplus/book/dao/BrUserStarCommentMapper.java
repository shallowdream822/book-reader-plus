package com.tyf.bookreaderplus.book.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tyf.bookreaderplus.book.entity.BrUserStarComment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * (BrUserStarComment)表数据库访问层
 *
 * @author shallow
 * @since 2023-04-01 17:54:58
 */
@Mapper
public interface BrUserStarCommentMapper extends BaseMapper<BrUserStarComment> {
    Integer replace(@Param("commentId")Long commentId,@Param("userId")Long userId,@Param("status")Integer status);
}

