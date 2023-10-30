//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tyf.bookreaderplus.auth.handler;

import com.alibaba.fastjson.JSON;
import com.tyf.bookreaderplus.common.component.Result;
import com.tyf.bookreaderplus.common.constant.StatusCodeEnum;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理授权异常
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        if (authException instanceof BadCredentialsException) {
            response.getWriter().write(JSON.toJSONString(Result.fail(StatusCodeEnum.LOGIN_FAIL)));
        } else if (authException instanceof LockedException) {
            response.getWriter().write(JSON.toJSONString(Result.fail(StatusCodeEnum.USER_LOCKED)));
        } else if (authException instanceof AuthenticationException){
            response.getWriter().write(JSON.toJSONString(Result.fail(StatusCodeEnum.NO_LOGIN)));
        }else {
            response.getWriter().write(JSON.toJSONString(Result.fail(525, authException.getMessage())));
        }

    }
}
