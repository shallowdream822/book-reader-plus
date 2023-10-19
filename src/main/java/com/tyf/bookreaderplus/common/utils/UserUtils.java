package com.tyf.bookreaderplus.common.utils;

/**
 * @Description TODO
 * @Author shallow
 * @Date 2023/5/2 17:25
 */


import com.tyf.bookreaderplus.common.component.LoginUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserUtils {



    public static LoginUser getLoginUser() {
        return (LoginUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


}
