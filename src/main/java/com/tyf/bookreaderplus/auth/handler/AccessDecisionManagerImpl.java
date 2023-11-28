package com.tyf.bookreaderplus.auth.handler;


import com.tyf.bookreaderplus.auth.config.IgnoreUrlsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * @Description TODO
 * @Author shallow
 * @Date 2023/5/2 16:42
 */
@Component
public class AccessDecisionManagerImpl implements AccessDecisionManager {

    @Autowired
    private IgnoreUrlsConfig ignoreUrlsConfig;
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        List<String> permissionList = authentication.getAuthorities()
                .stream()
                .map((grantedAuthority) ->grantedAuthority.getAuthority()).toList();
        for (ConfigAttribute configAttribute : configAttributes) {
            if (permissionList.contains(configAttribute.getAttribute())){
                return;
            }
            if(ignoreUrlsConfig.getUrls().contains(configAttribute.getAttribute())){
                return;
            }
        }
        throw new AccessDeniedException("没有操作权限");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return false;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }
}
