package com.welding.web.config.exception.user;

/**
 * 验证码校验失败
 *
 * @author MM
 * @create 2019-07-08 11:25
 **/
public class UserCaptchaNotMatchException extends UserException{
    public UserCaptchaNotMatchException() {
        super("user.captcha.error", null);
    }
}
