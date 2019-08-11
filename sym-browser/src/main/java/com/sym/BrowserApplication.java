package com.sym;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * springBoot返回静态页面一定要经过模板引擎渲染,除非静态页面,直接放在springBoot定义的静态资源目录下
 * 所以springBoot若想重定向页面,要么重新定义一个RequestMapping映射,要么直接把页面放在静态资源目录下
 * <p>
 * Created by 沈燕明 on 2019/5/29.
 */
@SpringBootApplication
public class BrowserApplication {
    public static void main(String[] args) {
        SpringApplication.run(BrowserApplication.class, args);
    }
}
