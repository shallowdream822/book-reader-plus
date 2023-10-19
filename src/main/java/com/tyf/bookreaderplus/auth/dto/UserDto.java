package com.tyf.bookreaderplus.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description TODO
 * @Author shallow
 * @Date 2023/2/18 15:06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto implements Serializable {
    private Long id;
    private String userNickName;
    private String userImg;
    private String jwt;
}
