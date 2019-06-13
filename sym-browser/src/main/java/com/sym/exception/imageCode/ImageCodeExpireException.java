package com.sym.exception.imageCode;

import com.sym.exception.BrowserBaseException;

/**
 * Created by 沈燕明 on 2019/6/13 17:37.
 */
public class ImageCodeExpireException extends BrowserBaseException {
    public ImageCodeExpireException(){
        this("");
    }

    public ImageCodeExpireException(String message){
        super(400,message);
    }
}
