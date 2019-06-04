package com.sym.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by 沈燕明 on 2019/5/26.
 */
@Controller
@RequestMapping("demo")
public class DemoController {

    @GetMapping("test")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public String test() {
        return "测试请求";
    }

    /**
     * 如果没引入模板引擎，只能把页面放在静态资源目录下，否则springBoot返回不了页面
     * 当然有了模板引擎，可以自定义视图目录
     * @return
     */
    @GetMapping("index")
    @ResponseStatus(code = HttpStatus.OK)
    public String index(){
        return "index";
    }

}
