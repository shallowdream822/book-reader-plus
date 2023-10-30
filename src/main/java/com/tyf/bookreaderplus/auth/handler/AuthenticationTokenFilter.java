package com.tyf.bookreaderplus.auth.handler;

import com.tyf.bookreaderplus.common.component.LoginUser;
import com.tyf.bookreaderplus.common.exception.BrException;
import com.tyf.bookreaderplus.common.utils.JwtUtil;
import com.tyf.bookreaderplus.common.utils.RedisUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class AuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private RedisUtil redisUtil;

    public AuthenticationTokenFilter() {
    }

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if (!StringUtils.hasLength(token)) {
            filterChain.doFilter(request, response);
        } else {
            String userId;
            try {
                Claims claims = JwtUtil.parseJWT(token);
                userId = claims.getSubject();
            } catch (Exception var8) {
                throw new BrException("token不合法");
            }

            LoginUser loginUser = (LoginUser)this.redisUtil.get("login:token:" + userId);
            if (Objects.isNull(loginUser)) {
                throw new RuntimeException("当前用户未登录");
            } else {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request, response);
            }
        }
    }
}
