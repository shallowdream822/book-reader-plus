package com.tyf.bookreaderplus.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description TODO
 * @Author shallow
 * @Date 2023/3/29 14:26
 */
@Getter
@AllArgsConstructor
public enum StatusCodeEnum {

    /**
     * 成功
     */
    SUCCESS(20000, "操作成功"),
    /**
     * 未登录
     */
    NO_LOGIN(40001, "用户未登录"),
    /**
     * 没有操作权限
     */
    AUTHORIZED(40300, "没有操作权限"),
    /**
     * 系统异常
     */
    SYSTEM_ERROR(50000, "系统异常"),
    /**
     * 失败
     */
    FAIL(51000, "操作失败"),
    /**
     * 参数校验失败
     */
    VALID_ERROR(52000, "参数格式不正确"),
    /**
     * 用户名已存在
     */
    USERNAME_EXIST(52001, "用户名已存在"),
    /**
     * 用户名不存在
     */
    USERNAME_NOT_EXIST(52002, "用户名不存在"),

    LOGIN_FAIL(52003,"用户名或密码错误" ),

    SECKILL_FAIL(52010,"秒杀失败" ),

    HAS_SECKILL(52011,"已经秒杀过" ),

    USER_LOCKED(52004,"用户被锁定" );
    /**
     * 状态码
     */
    private final Integer code;
    /**
     * 描述
     */
    private final String desc;


}
