package com.tyf.bookreaderplus.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.tyf.bookreaderplus.auth.dao.BrBookUserMapper;
import com.tyf.bookreaderplus.auth.dao.BrPermissionMapper;
import com.tyf.bookreaderplus.auth.dao.BrUserMapper;
import com.tyf.bookreaderplus.auth.entity.BrBookUser;
import com.tyf.bookreaderplus.auth.entity.BrUser;
import com.tyf.bookreaderplus.common.component.LoginUser;
import com.tyf.bookreaderplus.common.exception.BrException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @Description TODO
 * @Author shallow
 * @Date 2023/4/17 15:59
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private BrUserMapper userMapper;

    @Autowired
    private BrPermissionMapper permissionMapper;

    @Autowired
    private BrBookUserMapper bookUserMapper;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        //1.根据用户名获取用户库中的系统用户
        BrUser user = userMapper.selectOne(new LambdaQueryWrapper<BrUser>()
                .eq(BrUser::getUserName, userName));
        if (Objects.isNull(user)){
            throw new BrException("用户名或密码错误");
        }

        //2. 查询权限信息
        List<String> permissionList = permissionMapper.selectPermissionsByUserId(user.getId());
        List<Long> bookIdList = bookUserMapper.selectList(new LambdaQueryWrapper<BrBookUser>().eq(BrBookUser::getUserId, user.getId()))
                .stream().map(BrBookUser::getBookId).toList();
        //3.返回UserDetails
        return new LoginUser(user,permissionList,bookIdList);
    }
}
