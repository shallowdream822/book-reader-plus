package com.tyf.bookreaderplus.book.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tyf.bookreaderplus.book.entity.BrComment;
import org.apache.ibatis.annotations.Mapper;

/**
 * (BrComment)表数据库访问层
 *
 * @author shallow
 * @since 2023-04-01 17:54:57
 */
@Mapper
public interface BrCommentMapper extends BaseMapper<BrComment> {}

