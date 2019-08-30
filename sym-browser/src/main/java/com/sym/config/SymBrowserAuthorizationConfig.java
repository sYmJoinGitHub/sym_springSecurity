package com.sym.config;

import com.sym.authorization.AuthorizationOrder;
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
@AuthorizationOrder(100)
public class SymBrowserAuthorizationConfig implements SymAuthorizationConfig {

    @Autowired
    private SymSecurityProperties symSecurityProperties;

    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        config.
                antMatchers("/loginHandler/**", "/validate/getCode/**",
                        symSecurityProperties.getBrowser().getInvalidSessionUrl(),
                        symSecurityProperties.getBrowser().getSignInHtmlPath(),
                        symSecurityProperties.getBrowser().getInvalidSessionUrl())
                .permitAll()
                .antMatchers("/user/**").hasRole("USER")
                .antMatchers("/more").hasAuthority("see_more")
                .anyRequest().authenticated();
    }
}
