package com.sym.entity;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 映射springBoot的配置文件application.yml
 *
 * Created by 沈燕明 on 2019/6/2.
 */
@ConfigurationProperties(prefix = "sym.security")
@Data
public class SymSecurityProperties {

    private AppSecurityProperties app;

    private BrowserSecurityProperties browser;//窗口


    @Data
    class AppSecurityProperties{

    }

    @Data
    class BrowserSecurityProperties{

        private String signInHtmlPath = "/signIn.html";//默认的登录页面

    }
}
