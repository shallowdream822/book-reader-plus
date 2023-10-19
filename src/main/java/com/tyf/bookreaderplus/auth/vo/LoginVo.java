package com.tyf.bookreaderplus.auth.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description TODO
 * @Author shallow
 * @Date 2023/5/2 17:07
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginVo {
    private String userName;
    private String password;

}
