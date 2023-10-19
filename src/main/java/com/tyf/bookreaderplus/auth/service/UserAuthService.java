package com.tyf.bookreaderplus.auth.service;


import com.tyf.bookreaderplus.auth.dto.UserDto;
import com.tyf.bookreaderplus.auth.vo.LoginVo;
import com.tyf.bookreaderplus.auth.vo.UserVo;
import com.tyf.bookreaderplus.common.component.Result;

/**
 * @Description TODO
 * @Author shallow
 * @Date 2023/5/2 17:11
 */

public interface UserAuthService {
    Result<UserDto> login(LoginVo loginVo);

    Result logout();

    Result register(UserVo userVo);
}
