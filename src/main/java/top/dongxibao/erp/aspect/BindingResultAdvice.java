package top.dongxibao.erp.aspect;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.dongxibao.erp.common.Result;

import java.util.Objects;

/**
 * @ClassName BindingResultAdvice
 * @description HibernateValidator错误结果处理切面
 * @author Dongxibao
 * @date 2020/6/14
 * @Version 1.0
 */
@Slf4j
@RestControllerAdvice
public class BindingResultAdvice {
    /**
     * 拦截表单参数校验
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({BindException.class})
    public Result bindException(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        return new Result<>(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage(),false, 404, null);
    }

    /**
     * 拦截JSON参数校验
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result bindException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        return new Result<>(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage(),false, 404, null);
    }
}
