package com.tyf.bookreaderplus.book.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tyf.bookreaderplus.book.entity.BrContent;
import org.apache.ibatis.annotations.Mapper;

/**
 * (BrContent)表数据库访问层
 *
 * @author shallow
 * @since 2023-04-01 17:54:55
 */
@Mapper
public interface BrContentMapper extends BaseMapper<BrContent> {}

