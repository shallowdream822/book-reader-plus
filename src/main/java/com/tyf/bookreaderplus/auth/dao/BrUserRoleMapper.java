package com.tyf.bookreaderplus.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tyf.bookreaderplus.auth.entity.BrUserRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * (BrUserRole)表数据库访问层
 *
 * @author shallow
 * @since 2023-04-01 17:54:58
 */
@Mapper
public interface BrUserRoleMapper extends BaseMapper<BrUserRole> {}

