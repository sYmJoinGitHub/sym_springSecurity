package com.sym.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 沈燕明 on 2019/5/26.
 */
@RestController
@RequestMapping("demo")
public class DemoController {

    @GetMapping
    public String demo(){
        return "测试请求";
    }
}
