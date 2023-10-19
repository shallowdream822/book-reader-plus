package com.tyf.bookreaderplus.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tyf.bookreaderplus.auth.entity.BrPermission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * (BrPermission)表数据库访问层
 *
 * @author shallow
 * @since 2023-05-02 16:32:09
 */
@Mapper
public interface BrPermissionMapper extends BaseMapper<BrPermission> {

    List<String> selectPermissionsByUserId(Long id);
}

