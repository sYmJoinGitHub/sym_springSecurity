package com.sym.validate;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletResponse;

/**
 * 处理验证码请求的Controller
 *
 * Created by 沈燕明 on 2019/6/29.
 */
@RestController
@RequestMapping("/validate")
public class ValidateController {

    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;

    @GetMapping("/getCode/{type}")
    public void getCode(@PathVariable("type") String type, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ServletWebRequest webRequest = new ServletWebRequest(request,response);
        validateCodeProcessorHolder.findValidateCodeProcessor(type).create(webRequest);
    }

}
