package com.tyf.bookreaderplus.auth.controller;

/**
 * @Description TODO
 * @Author shallow
 * @Date 2023/5/2 17:06
 */



import com.tyf.bookreaderplus.auth.dto.UserDto;
import com.tyf.bookreaderplus.auth.service.UserAuthService;
import com.tyf.bookreaderplus.auth.vo.LoginVo;
import com.tyf.bookreaderplus.auth.vo.UserVo;
import com.tyf.bookreaderplus.common.annotation.OptLog;
import com.tyf.bookreaderplus.common.component.Result;
import com.tyf.bookreaderplus.common.constant.OptConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Api(tags = "登录模块")
public class LoginController {
    @Autowired
    private UserAuthService userAuthService;

    public LoginController() {
    }

    @OptLog(optType = OptConstants.LOGIN)
    @PostMapping({"/login"})
    @ApiOperation("登录接口")
    public Result<UserDto> login(@RequestBody LoginVo loginVo) {
        return this.userAuthService.login(loginVo);
    }

    @PostMapping({"logout1"})
    @ApiOperation("退出登录接口")
    public Result<String> logout() {
        return this.userAuthService.logout();
    }

    @PostMapping({"register"})
    @ApiOperation("注册接口")
    public Result<String> register(@Valid @RequestBody UserVo userVo) {
        return this.userAuthService.register(userVo);
    }



}
