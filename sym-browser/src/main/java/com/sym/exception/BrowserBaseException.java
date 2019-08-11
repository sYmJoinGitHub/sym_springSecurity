package com.sym.exception;

/**
 * 浏览器异常基础类
 * <p>
 * Created by 沈燕明 on 2019/6/13 17:32.
 */
public class BrowserBaseException extends BaseException {


    public BrowserBaseException(String msg, Throwable t) {
        super(msg, t);
    }

    public BrowserBaseException(String msg) {
        super(msg);
    }

    public BrowserBaseException(int code, String message) {
        super(code, message);
    }
}
