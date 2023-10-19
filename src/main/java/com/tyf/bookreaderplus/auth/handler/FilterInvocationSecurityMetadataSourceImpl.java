
package com.tyf.bookreaderplus.auth.handler;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tyf.bookreaderplus.auth.dao.BrPermissionMapper;
import com.tyf.bookreaderplus.auth.entity.BrPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 动态配置资源权限
 */
@Component
public class FilterInvocationSecurityMetadataSourceImpl implements FilterInvocationSecurityMetadataSource {
    @Autowired
    private BrPermissionMapper permissionMapper;
    private static Map<String, BrPermission> permissionMap = new HashMap();

    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        if (CollectionUtils.isEmpty(permissionMap)) {
            this.loadPermission();
        }
        FilterInvocation fi = (FilterInvocation)object;
        String method = fi.getRequest().getMethod();
        String url = fi.getRequest().getRequestURI();
        BrPermission permission = permissionMap.get(url);
        return Objects.nonNull(permission) && permission.getRequestMethod().equals(method) ? SecurityConfig.createList(new String[]{permission.getPermissionName()}) : null;
    }

    private void loadPermission() {
        permissionMapper.selectList(new LambdaQueryWrapper<>())
                .forEach(permission->{
                    permissionMap.put(permission.getPermissionUrl(),permission);
                });
    }

    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    public boolean supports(Class<?> clazz) {
        return false;
    }
}
