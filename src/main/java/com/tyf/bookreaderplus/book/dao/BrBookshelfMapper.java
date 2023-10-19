package com.tyf.bookreaderplus.book.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.tyf.bookreaderplus.book.entity.BrBookshelf;
import org.apache.ibatis.annotations.Mapper;

/**
 * (BrBookshelf)表数据库访问层
 *
 * @author shallow
 * @since 2023-04-01 17:54:57
 */
@Mapper
public interface BrBookshelfMapper extends BaseMapper<BrBookshelf> {}

