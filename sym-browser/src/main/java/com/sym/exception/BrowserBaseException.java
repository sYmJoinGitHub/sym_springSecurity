package com.sym.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 浏览器异常基础类
 *
 * Created by 沈燕明 on 2019/6/13 17:32.
 */
public class BrowserBaseException extends AuthenticationException {
    private int code;
    private String message;
    public BrowserBaseException(String message){super(message);}
    public BrowserBaseException(int code,String message){
        super(message);
        this.code = code;
    }

}
