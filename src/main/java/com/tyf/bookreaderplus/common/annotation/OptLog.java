package com.tyf.bookreaderplus.common.annotation;

import java.lang.annotation.*;

/**
 * @Description TODO
 * @Author shallow
 * @Date 2023/5/9 16:15
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OptLog {

    /**
     * 操作类型
     * @return
     */
    String optType() default "";
}
