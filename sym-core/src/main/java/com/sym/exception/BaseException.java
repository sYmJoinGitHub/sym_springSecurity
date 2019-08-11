package com.sym.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * AuthenticationException 是springSecurity的异常超类，
 * 可以实现它来完成自己的异常基类
 * <p>
 * Created by 沈燕明 on 2019/7/1.
 */
public class BaseException extends AuthenticationException {

    private static final long serialVersionUID = -1143451322674709000L;

    // 错误码
    private int code;

    public BaseException(String msg, Throwable t) {
        super(msg, t);
    }

    public BaseException(String msg) {
        super(msg);
    }

    public BaseException(int code, String message) {
        this(message);
        this.code = code;
    }
}
