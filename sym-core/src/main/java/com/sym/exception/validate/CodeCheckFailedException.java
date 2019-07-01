package com.sym.exception.validate;

import com.sym.exception.BaseException;

/**
 * 验证码校验失败异常
 *
 * Created by 沈燕明 on 2019/6/13 17:36.
 */
public class CodeCheckFailedException extends BaseException {

    private static final long serialVersionUID = 4656318304188492958L;

    public CodeCheckFailedException(String message){
        this(0,message);
    }

    public CodeCheckFailedException(int code,String message){
        super(code,message);
    }
}
