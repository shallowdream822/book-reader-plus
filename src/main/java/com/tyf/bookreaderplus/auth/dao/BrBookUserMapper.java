package com.tyf.bookreaderplus.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tyf.bookreaderplus.auth.entity.BrBookUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * (BrBookUser)表数据库访问层
 *
 * @author shallow
 * @since 2023-05-04 16:38:05
 */
@Mapper
public interface BrBookUserMapper extends BaseMapper<BrBookUser> {}

