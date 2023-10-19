package com.tyf.bookreaderplus.auth.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @Description TODO
 * @Author shallow
 * @Date 2023/5/2 17:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserVo {
    @NotBlank(message = "用户名不能为空")
    private String userName;
    private String userPhone;
    @NotBlank(message = "密码不能为空")
    private String userPwd;
    private String userNickname;
    private Integer userSex;
    private Integer userAge;
    private String userImg;
}
