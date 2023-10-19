package com.tyf.bookreaderplus.author.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tyf.bookreaderplus.author.entity.BrAuthor;
import org.apache.ibatis.annotations.Mapper;

/**
 * (BrAuthor)表数据库访问层
 *
 * @author shallow
 * @since 2023-04-01 17:54:57
 */
@Mapper
public interface BrAuthorMapper extends BaseMapper<BrAuthor> {}

