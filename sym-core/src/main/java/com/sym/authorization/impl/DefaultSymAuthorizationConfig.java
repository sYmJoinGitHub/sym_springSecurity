package com.sym.authorization.impl;

import com.sym.authorization.SymAuthorizationConfig;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * 全局默认的权限配置，这里可以配置通用的权限信息
 *
 * Created by shenYm on 2019/8/28.
 */
@Component
public class DefaultSymAuthorizationConfig implements SymAuthorizationConfig {

    /**
     * 自定义配置
     * @param config
     */
    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        // spring security 的权限配置，即设置权限表达式
        // 每个antMatchers()可以匹配一个或多个url地址，每个antMatchers()都可以跟着一个表示权限表达式的方法，如permitAll表达式队友permitAll()方法
        /*
        config
                .antMatchers("/list").permitAll()
                .antMatchers("/user/**").authenticated()
                .antMatchers("/getOne").hasRole("")
                //access()方法比较特殊，它可以自己写权限表达式，下例表示访问"/putOne"需要有一个ROLE_ADMIN角色和 ( 一个good权限 或 一个bad权限 )
                //所以只有access()方法可以对同一组url做多个权限的配置，其它权限表达式方法只能代表一项，不能为一组url做多种权限配置
                .antMatchers("putOne").access("hasRole('ADMIN') and hasAnyAuthority('good','bad')");
        */
    }
}
