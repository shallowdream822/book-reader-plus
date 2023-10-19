package com.tyf.bookreaderplus.common.exception;

import com.tyf.bookreaderplus.common.component.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Description TODO
 * @Author shallow
 * @Date 2023/5/4 15:04
 */
@Slf4j
@RestControllerAdvice
public class BrExceptionHandler {
    @ExceptionHandler(BrException.class)
    public Result handlerBrException(BrException e){
        log.debug(e.getMessage(),e);
        return Result.fail(e.getCode(),e.getMessage());
    }
}
