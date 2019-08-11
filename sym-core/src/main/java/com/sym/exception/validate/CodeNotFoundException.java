package com.sym.exception.validate;

import com.sym.exception.BaseException;

/**
 * 验证码不存在异常
 * <p>
 * Created by 沈燕明 on 2019/6/13 17:34.
 */
public class CodeNotFoundException extends BaseException {

    private static final long serialVersionUID = -5729843138950168149L;

    public CodeNotFoundException(String message) {
        this(0, message);
    }

    public CodeNotFoundException(int code, String message) {
        super(code, message);
    }
}
