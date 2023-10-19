package com.tyf.bookreaderplus.auth.handler;

import com.alibaba.fastjson.JSON;
import com.tyf.bookreaderplus.common.component.Result;
import com.tyf.bookreaderplus.common.constant.StatusCodeEnum;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description 处理认证异常
 * @Author shallow
 * @Date 2023/5/2 16:42
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    public AccessDeniedHandlerImpl() {
    }

    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(Result.fail(StatusCodeEnum.AUTHORIZED)));
    }
}
