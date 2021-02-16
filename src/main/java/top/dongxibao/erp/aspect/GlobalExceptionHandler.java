package top.dongxibao.erp.aspect;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.dongxibao.erp.common.Result;
import top.dongxibao.erp.exception.ServiceException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Objects;

/**
 * 异常统一拦截
 *
 * @author Dongxibao
 * @date 2020-11-27
 */
@Slf4j
@Component
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * HibernateValidator错误结果
     * 拦截表单参数校验1
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({BindException.class})
    public Result bindException(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        return new Result(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage(), false, 400,
                null);
    }

    /**
     * HibernateValidator错误结果
     * 拦截JSON参数校验
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result bindException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        return new Result(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage(), false, 400,
                null);
    }

    /**
     * 处理所有不可知的异常
     */
    @ExceptionHandler(Throwable.class)
    public Result handleException(Throwable e) {
        // 打印堆栈信息
        log.error(getStackTrace(e));
        return Result.fail(getStackTrace(e));
    }

    @ExceptionHandler(RuntimeException.class)
    public Result handleRuntimeException(Throwable ex) {
        if (ex instanceof DuplicateKeyException) {
            DuplicateKeyException e = (DuplicateKeyException) ex;
            log.error("[RuntimeException]：{}", e.getCause().getMessage());
            return Result.fail("DuplicateKeyException");
        }
        return Result.fail(ex.getMessage());
    }

    @ExceptionHandler(ServiceException.class)
    public Result businessException(ServiceException e) {
        if (e.getCode() == null) {
            return Result.fail(e.getMessage());
        }
        return Result.fail(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result businessException(Exception e) {
        log.error("[Exception]：{};{}", e.getMessage(), e);
        return Result.fail(e.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public Result handleUsernameNotFoundException(UsernameNotFoundException e) {
        log.error("[UsernameNotFoundException]：{};{}", e.getMessage(), e);
        return Result.fail(e.getMessage());
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public Result handleInternalAuthenticationServiceException(InternalAuthenticationServiceException e) {
        log.error("[InternalAuthenticationServiceException]：{};{}", e.getMessage(), e);
        return Result.fail(e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public Result handleAuthorizationException(AccessDeniedException e) {
        log.error(e.getMessage());
        return Result.fail("没有权限，请联系管理员授权");
    }

    /**
     * 获取堆栈信息
     */
    public static String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw)) {
            throwable.printStackTrace(pw);
            return sw.toString();
        }
    }
}
