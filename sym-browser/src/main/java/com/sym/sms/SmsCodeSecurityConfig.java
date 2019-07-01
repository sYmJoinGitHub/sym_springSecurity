package com.sym.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 短信登录的配置类，需要配置2个组件：一是token，二是生成token的Filter
 *
 * Created by 沈燕明 on 2019/6/23.
 */
@Configuration
public class SmsCodeSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private SmsAuthenticationProvider smsAuthenticationProvider;

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {

        AuthenticationManager authenticationManager = httpSecurity.getSharedObject(AuthenticationManager.class);
        SmsAuthenticationProcessingFilter smsAuthenticationProcessingFilter = new SmsAuthenticationProcessingFilter();
        smsAuthenticationProcessingFilter.setAuthenticationManager(authenticationManager);

        httpSecurity.addFilterAfter(smsAuthenticationProcessingFilter, UsernamePasswordAuthenticationFilter.class);
        httpSecurity.authenticationProvider(smsAuthenticationProvider);

        super.configure(httpSecurity);
    }
}
