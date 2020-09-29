package top.dongxibao.erp.aspect;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.dongxibao.erp.common.Result;
import top.dongxibao.erp.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;

/**
 * 异常统一拦截
 *
 * @author Dongxibao
 * @date 2020-06-21
 */
@Slf4j
@Component
@RestControllerAdvice(annotations = {RestController.class})
public class ExceptionHandler {

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(RuntimeException.class)
    Result<?> handleRuntimeException(HttpServletRequest request, Throwable ex) {
        HttpStatus status = getStatus(request);
        if (ex instanceof DuplicateKeyException) {
            DuplicateKeyException e = (DuplicateKeyException) ex;
            System.out.println(e.getCause().getMessage());
            return new Result<>(HttpStatus.OK.value(), "DuplicateKeyException");
        }
        return new Result<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ServiceException.class)
    public Result businessException(ServiceException e) {
        if (e.getCode() == null) {
            return new Result<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
        return new Result<>(e.getCode(), e.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public Result<String> businessException(Exception e) {
        log.error("[Exception]：{};{}", e.getMessage(), e);
        return new Result<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }
}
