package com.tyf.bookreaderplus.common.component;

import com.alibaba.fastjson.annotation.JSONField;


import com.tyf.bookreaderplus.auth.entity.BrUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @Description TODO
 * @Author shallow
 * @Date 2023/4/17 16:09
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser implements UserDetails {
    private static final long serialVersionUID = -4624078786627174554L;
    private BrUser user;

    private List<String> permissionList;

    private List<Long> bookIdList;

    @JSONField(serialize = false)
    private List<SimpleGrantedAuthority> authorities;


    public LoginUser(BrUser user, List<String> permissionList,List<Long> bookIdList) {
        this.user = user;
        this.permissionList = permissionList;
        this.bookIdList = bookIdList;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (Objects.nonNull(authorities)){
            return authorities;
        }
        this.authorities = this.permissionList.stream()
                .map((permission) ->new SimpleGrantedAuthority(permission)).toList();
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return user.getUserPwd();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getStatus()==0;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getStatus() ==0;
    }
}
