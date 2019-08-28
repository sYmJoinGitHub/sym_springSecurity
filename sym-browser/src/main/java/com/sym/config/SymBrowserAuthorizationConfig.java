package com.sym.config;

import com.sym.authorization.SymAuthorizationConfig;
import com.sym.entity.SymSecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * 浏览器模块自己的配置
 *
 * Created by shenYm on 2019/8/28.
 */
@Component
public class SymBrowserAuthorizationConfig implements SymAuthorizationConfig {

    @Autowired
    private SymSecurityProperties symSecurityProperties;

    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        config.
                antMatchers("/loginHandler", "/validate/getCode/**", "/invalid/session",
                        symSecurityProperties.getBrowser().getSignInHtmlPath(),
                        symSecurityProperties.getBrowser().getInvalidSessionUrl())
                .permitAll()
                .anyRequest().authenticated();
    }
}
