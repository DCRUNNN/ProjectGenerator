package cn.edu.nju.dc.projectgeneratorserver.support.log.aspect;

import cn.edu.nju.dc.projectgeneratorserver.constant.LogType;
import cn.edu.nju.dc.projectgeneratorserver.service.LogService;
import cn.edu.nju.dc.projectgeneratorserver.support.log.annotation.Log;
import cn.edu.nju.dc.projectgeneratorserver.support.log.domain.LogEntity;
import cn.edu.nju.dc.projectgeneratorserver.utils.DateUtil;
import cn.edu.nju.dc.projectgeneratorserver.utils.IPUtil;
import cn.edu.nju.dc.projectgeneratorserver.utils.RequestHolder;
import com.google.common.base.Preconditions;
import java.util.stream.IntStream;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author dc
 * @date 2019/10/6 0:55
 */
@Component
@Aspect
@Slf4j
public class LogAspect {

    @Autowired
    private LogService logService;

    @Pointcut("@annotation(cn.edu.nju.dc.projectgeneratorserver.support.log.annotation.Log)")
    public void logPointcut() {
    }

    @Around(value = "logPointcut() && @annotation(log)")
    public Object doAround(ProceedingJoinPoint joinPoint, Log log) throws Throwable {
        long beginTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        LogEntity logEntity = LogEntity.builder()
            .description(log.description())
            .logType(LogType.BUSINESS.getValue())
            .costTime(getCostTime(beginTime))
            .build();
        logService.insertLog(fillLogEntity(joinPoint, logEntity));
        return result;
    }

    @AfterThrowing(pointcut = "logPointcut() && @annotation(log) ", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e, Log log) {
        long beginTime = System.currentTimeMillis();

        LogEntity logEntity = LogEntity.builder()
            .logType(LogType.ERROR.getValue())
            .description(log.description())
            .costTime(getCostTime(beginTime))
            .exceptionDetails(ExceptionUtils.getStackTrace(e))
            .build();
        logService.insertLog(fillLogEntity(joinPoint, logEntity));
    }

    private LogEntity fillLogEntity(JoinPoint joinPoint, LogEntity logEntity) {
        // request请求
        HttpServletRequest servletRequest = RequestHolder.getHttpServletRequest();
        // 方法签名
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        // 参数名称
        String[] argNames = signature.getParameterNames();
        // 参数值
        Object[] argValues = joinPoint.getArgs();
        String params = createParams(argNames, argValues);

        return logEntity.toBuilder()
            .username(servletRequest.getUserPrincipal().getName())
            .methodName(joinPoint.getTarget().getClass().getName() + "." + signature.getName() + "()")
            .params(params)
            .requestIp(IPUtil.getIpAddress(servletRequest))
            .createTime(DateUtil.getCurrentFormatTime())
            .build();
    }

    private String createParams(String[] argNames, Object[] argValues) {
        Preconditions.checkNotNull(argNames, "argNames is null");
        Preconditions.checkNotNull(argValues, "argValues is null");

        StringBuilder paramBuilder = new StringBuilder("{");
        IntStream.range(0, argNames.length)
            .forEach(i -> paramBuilder.append(" ").append(argNames[i]).append(": ").append(argValues[i]));
        paramBuilder.append("}");
        return paramBuilder.toString();
    }

    private String getCostTime(long beginTime) {
        return (System.currentTimeMillis() - beginTime) + "ms";
    }
}
