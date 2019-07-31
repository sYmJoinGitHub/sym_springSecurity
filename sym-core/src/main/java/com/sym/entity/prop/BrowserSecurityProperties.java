package com.sym.entity.prop;

import lombok.Data;
import lombok.ToString;

/**
 * 浏览器配置属性类
 *
 * Created by 沈燕明 on 2019/6/3 17:21.
 */
@Data
@ToString
public class BrowserSecurityProperties {
    private String signInHtmlPath = "/signIn.html";//默认的登录页面
    private String invalidSessionUrl = "/invalidSession.html";
}
