package com.tyf.bookreaderplus.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tyf.bookreaderplus.auth.entity.BrRolePermission;
import org.apache.ibatis.annotations.Mapper;

/**
 * (BrRolePermission)表数据库访问层
 *
 * @author shallow
 * @since 2023-05-02 16:25:28
 */
@Mapper
public interface BrRolePermissionMapper extends BaseMapper<BrRolePermission> {}

