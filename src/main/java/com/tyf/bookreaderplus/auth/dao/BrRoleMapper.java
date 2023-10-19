package com.tyf.bookreaderplus.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tyf.bookreaderplus.auth.entity.BrRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * (BrRole)表数据库访问层
 *
 * @author shallow
 * @since 2023-04-01 17:54:57
 */
@Mapper
public interface BrRoleMapper extends BaseMapper<BrRole> {}

