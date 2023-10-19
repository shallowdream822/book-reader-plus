package com.tyf.bookreaderplus.auth.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.tyf.bookreaderplus.auth.entity.BrUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * (BrUser)表数据库访问层
 *
 * @author shallow
 * @since 2023-04-01 17:54:57
 */
@Mapper
public interface BrUserMapper extends BaseMapper<BrUser> {}

