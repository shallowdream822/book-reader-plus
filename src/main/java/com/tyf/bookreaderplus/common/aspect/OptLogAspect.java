package com.tyf.bookreaderplus.common.aspect;

import com.alibaba.fastjson.JSON;
import com.tyf.bookreaderplus.auth.dto.UserDto;
import com.tyf.bookreaderplus.common.annotation.OptLog;
import com.tyf.bookreaderplus.common.component.Result;
import com.tyf.bookreaderplus.common.constant.OptConstants;
import com.tyf.bookreaderplus.common.entity.BrOptLog;
import com.tyf.bookreaderplus.common.mapper.BrOptLogMapper;
import com.tyf.bookreaderplus.common.utils.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @Description TODO
 * @Author shallow
 * @Date 2023/5/9 16:16
 */
@Component
@Aspect
@Slf4j
public class OptLogAspect {

    @Autowired
    private BrOptLogMapper optLogMapper;

    /**
     * 日志切入点
     */
    @Pointcut("@annotation(com.tyf.bookreaderplus.common.annotation.OptLog)")
    public void optLogPointCut(){}

    /**
     *
     */
    @AfterReturning(value = "optLogPointCut()", returning = "keys")
    public void saveOptLog(JoinPoint joinPoint,Object keys){
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        HttpServletRequest request = (HttpServletRequest) Objects.requireNonNull(requestAttributes).resolveReference(RequestAttributes.REFERENCE_REQUEST);
        log.debug("request:{}",request.toString());

        //获取方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        log.debug("signature:{}",signature.toString());
        Method method = signature.getMethod();
        log.debug("method:{}",method.toString());

        // 获取操作
        Api api = (Api) signature.getDeclaringType().getAnnotation(Api.class);
        ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
        OptLog optLog = method.getAnnotation(OptLog.class);
        BrOptLog brOptLog = BrOptLog.builder()
                .optModule(api.tags()[0])
                .optType(optLog.optType())
                .optUrl(request.getRequestURI())
                .optMethod(joinPoint.getTarget().getClass().getName() + method.getName())
                .optDesc(apiOperation.value())
                .requestParam(JSON.toJSONString(joinPoint.getArgs()))
                .requestMethod(Objects.requireNonNull(request).getMethod())
                .responseData(JSON.toJSONString(keys))
                .ipAddress("ip地址")
                .ipSource("ip来源").build();
        if (optLog.optType().equals(OptConstants.LOGIN)){
            Result<UserDto> userDto = (Result<UserDto>) keys;
            brOptLog.setUserId(userDto.getData().getId());
            brOptLog.setUserNickname(userDto.getData().getUserNickName());
        }else{
            brOptLog.setUserId(UserUtils.getLoginUser().getUser().getId());
            brOptLog.setUserNickname(UserUtils.getLoginUser().getUser().getUserNickname());
        }
        optLogMapper.insert(brOptLog);
    }


}
