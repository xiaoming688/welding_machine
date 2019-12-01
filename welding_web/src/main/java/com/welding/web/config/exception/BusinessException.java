package com.welding.web.config.exception;

/**
 * 业务异常
 * 
 */
public class BusinessException extends RuntimeException
{

    private static final long serialVersionUID = 3964668986902539009L;
    protected final String message;

    public BusinessException(String message)
    {
        this.message = message;
    }

    @Override
    public String getMessage()
    {
        return message;
    }
}
