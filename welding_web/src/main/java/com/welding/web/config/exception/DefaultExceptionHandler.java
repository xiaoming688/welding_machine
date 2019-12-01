package com.welding.web.config.exception;

import com.welding.util.MData;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author MM
 * 自定义异常处理器
 */
@Slf4j
@RestControllerAdvice
public class DefaultExceptionHandler {

    /**
     * 权限校验失败
     */
    @ExceptionHandler(AuthorizationException.class)
    public MData handleAuthorizationException(AuthorizationException e) {
        log.info(e.getMessage());
        return new MData().error(PermissionUtils.getMsg(e.getMessage()));
    }

    /**
     * 请求方式不支持
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public MData handleException(HttpRequestMethodNotSupportedException e) {
        log.info(e.getMessage());
        return new MData().error("不支持' " + e.getMethod() + "'请求");
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public MData notFount(RuntimeException e) {
        log.error(e.toString(), e);
        return new MData().error("运行时异常: " + e.getMessage());
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public MData handleException(Exception e) {
        log.error(e.toString(), e);
        return new MData().error("服务器错误，请联系管理员");
    }

    /**
     * 参数异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public MData methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.info(e.getMessage());
        BindingResult result = e.getBindingResult();
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                return new MData().error(error.getDefaultMessage());
            }

        }
        return new MData().error("参数错误，请联系管理员");
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public MData businessException(BusinessException e) {
        log.error(e.toString(), e);
        return new MData().error(e.getMessage());
    }

}
