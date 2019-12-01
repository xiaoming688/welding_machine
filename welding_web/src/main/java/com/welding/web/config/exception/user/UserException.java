package com.welding.web.config.exception.user;


import com.welding.web.config.exception.BaseException;

/**
 * 用户信息异常类
 * 
 */
public class UserException extends BaseException
{
    private static final long serialVersionUID = 1L;

    public UserException(String code, Object[] args)
    {
        super("user", code, args, null);
    }
}
