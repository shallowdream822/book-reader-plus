package com.tyf.bookreaderplus.auth.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.tyf.bookreaderplus.auth.dao.BrUserMapper;
import com.tyf.bookreaderplus.auth.dto.UserDto;
import com.tyf.bookreaderplus.auth.entity.BrUser;
import com.tyf.bookreaderplus.auth.service.UserAuthService;
import com.tyf.bookreaderplus.auth.vo.LoginVo;
import com.tyf.bookreaderplus.auth.vo.UserVo;
import com.tyf.bookreaderplus.common.component.LoginUser;
import com.tyf.bookreaderplus.common.component.Result;
import com.tyf.bookreaderplus.common.constant.RedisConstants;
import com.tyf.bookreaderplus.common.exception.BrException;
import com.tyf.bookreaderplus.common.utils.JwtUtil;
import com.tyf.bookreaderplus.common.utils.RedisUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @Description TODO
 * @Author shallow
 * @Date 2023/5/2 17:06
 */

@Service
public class UserAuthServiceImpl implements UserAuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private BrUserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserAuthServiceImpl() {
    }

    public Result<UserDto> login(LoginVo loginVo) {
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(loginVo.getUserName(), loginVo.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authRequest);
        if (Objects.isNull(authenticate)) {
            throw new BadCredentialsException("用户名密码错误");
        } else {
            LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
            String userId = loginUser.getUser().getId().toString();
            String jwt = JwtUtil.createJWT(userId);
            redisUtil.set("login:token:" + userId, loginUser, RedisConstants.LOGIN_USER_TTL);
            return Result.ok(UserDto.builder()
                    .id(loginUser.getUser().getId())
                    .userNickName(loginUser.getUser().getUserNickname())
                    .userImg(loginUser.getUser().getUserImg())
                    .jwt(jwt).build());
        }
    }

    public Result<String> logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getUser().getId();
        this.redisUtil.del("login:token:" + userId);
        return Result.ok("退出成功");
    }

    public Result register(UserVo userVo) {
        List<BrUser> userList = this.userMapper.selectList(new LambdaQueryWrapper<BrUser>().eq(BrUser::getUserName, userVo.getUserName())
                .or()
                .eq(BrUser::getUserPhone, userVo.getUserPhone()));
        if (!userList.isEmpty()) {
            throw new BrException("用户已存在");
        } else {
            BrUser user = BeanUtil.toBean(userVo, BrUser.class);
            user.setUserPwd(this.passwordEncoder.encode(userVo.getUserPwd()));
            user.setId(IdWorker.getId());
            user.setStatus(0);
            user.setAccountBalance(0.0);
            this.userMapper.insert(user);
            return Result.ok();
        }
    }
}
