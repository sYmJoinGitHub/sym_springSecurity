package com.sym.exception.imageCode;

import com.sym.exception.BrowserBaseException;

/**
 * Created by 沈燕明 on 2019/6/13 17:36.
 */
public class ImageCodeCheckFailedException extends BrowserBaseException {

    public ImageCodeCheckFailedException(){
        this("");
    }

    public ImageCodeCheckFailedException(String message){
        super(400,message);
    }
}
