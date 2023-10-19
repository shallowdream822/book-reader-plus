package com.tyf.bookreaderplus.common.constant;

/**
 * @Description TODO
 * @Author shallow
 * @Date 2023/4/2 16:37
 */

public class CommonConstants {



    /**
     * 用户登录相关
     */
    public static final String LOGIN_CODE_KEY = "login:code:";
    public static final Long LOGIN_CODE_TTL = 1L;
    public static final String LOGIN_USER_KEY = "login:token:";
    public static final Long LOGIN_USER_TTL = 30L;

    /**
     * mq相关
     */
    public static final Long QUEUE_DEAD_TTL = 15*60*1000L;

    public static final Long QUEUE_DEAD_TTL_TEST = 15*1000L;
}
