package cn.edu.nju.dc.projectgeneratorserver.support.exception;

import cn.edu.nju.dc.projectgeneratorserver.support.response.BaseResult;
import cn.edu.nju.dc.projectgeneratorserver.support.response.BaseResultUtil;
import java.sql.SQLException;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * @author dc
 * @date 2019/12/17 23:16
 */
@RestControllerAdvice
@Slf4j
public class ExceptionResolver
{

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public BaseResult validatorException(final ConstraintViolationException e)
    {
        log.error("验证实体异常 => {}", e.getMessage());
        final String msg =
            e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(","));
        return BaseResultUtil.createFailedResult(msg);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({ServiceException.class, ServletException.class})
    public BaseResult serviceException(final Throwable e)
    {
        log.error("服务异常 => {}", e);
        return BaseResultUtil.createFailedResult(e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({DBException.class, SQLException.class, DataAccessException.class})
    public BaseResult databaseException(final Throwable e)
    {
        log.error("数据库操作异常 => {}", e.getMessage());
        return BaseResultUtil.createFailedResult("database error");
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({BadCredentialsException.class, AuthenticationException.class})
    public BaseResult authException(final Throwable e)
    {
        log.error("身份验证异常 => {}", e.getMessage());
        return BaseResultUtil.createUnauthorizedResult(e.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({AccessDeniedException.class, UsernameNotFoundException.class})
    public BaseResult userException(final Throwable e)
    {
        log.error("用户异常 => {}", e.getMessage());
        return BaseResultUtil.createFailedResult(e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public BaseResult apiNotFound(final Throwable e, final HttpServletRequest request)
    {
        log.error("API 不存在 => {}", e.getMessage());
        return BaseResultUtil.createFailedResult("API [" + request.getRequestURI() + "] not existed");
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public BaseResult methodNotSupport(final Throwable e)
    {
        log.error("方法异常 => {}", e.getMessage());
        return BaseResultUtil.createMethodErrorResult();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public BaseResult globalException(final HttpServletRequest request, final Throwable e)
    {
        log.error("全局异常 => {}", e.getMessage());
        return BaseResultUtil.createInternalServerErrorResult(String.format("%s => %s",
            request.getRequestURI(),
            e.getMessage()));
    }
}
