package com.tyf.bookreaderplus.book.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tyf.bookreaderplus.book.entity.BrUserStarBook;
import org.apache.ibatis.annotations.Mapper;

/**
 * (BrUserStarBook)表数据库访问层
 *
 * @author shallow
 * @since 2023-04-01 17:54:58
 */
@Mapper
public interface BrUserStarBookMapper extends BaseMapper<BrUserStarBook> {}

