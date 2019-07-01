package com.sym.exception.validate;

import com.sym.exception.BaseException;

/**
 * 验证码超时异常
 *
 * Created by 沈燕明 on 2019/6/13 17:37.
 */
public class CodeExpireException extends BaseException {

    private static final long serialVersionUID = 2465385281538884284L;

    public CodeExpireException(String message){
        this(0,message);
    }

    public CodeExpireException(int code,String message){
        super(code,message);
    }
}
