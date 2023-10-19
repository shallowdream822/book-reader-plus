package com.tyf.bookreaderplus.common.exception;


import com.tyf.bookreaderplus.common.constant.StatusCodeEnum;
import lombok.Data;

/**
 * @Description 自定义异常
 * @Author shallow
 * @Date 2023/4/3 19:35
 */
@Data
public class BrException extends RuntimeException{

    private Integer code = StatusCodeEnum.FAIL.getCode();



    public BrException(String message) {
        super(message);
    }

    public BrException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
