package com.sym.exception.imageCode;

import com.sym.exception.BrowserBaseException;

/**
 * Created by 沈燕明 on 2019/6/13 17:34.
 */
public class ImageCodeNotFoundException extends BrowserBaseException {

    public ImageCodeNotFoundException(){
        this("");
    }

    public ImageCodeNotFoundException(String message){
        super(400,message);
    }
}
