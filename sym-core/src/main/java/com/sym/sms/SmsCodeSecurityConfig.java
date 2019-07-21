package com.sym.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import java.util.UUID;

/**
 * 短信登录的配置类，需要配置2个组件：一是token，二是生成token的Filter
 *
 * Created by 沈燕明 on 2019/6/23.
 */
@Configuration("smsCodeSecurityConfig")
public class SmsCodeSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PersistentTokenRepository persistentTokenRepository;

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {

        // 获取 AuthenticationManager 对象
        AuthenticationManager authenticationManager = httpSecurity.getSharedObject(AuthenticationManager.class);

        // 初始化自定义的短信token过滤器
        SmsAuthenticationProcessingFilter smsAuthenticationProcessingFilter = new SmsAuthenticationProcessingFilter();
        smsAuthenticationProcessingFilter.setAuthenticationManager(authenticationManager);
        smsAuthenticationProcessingFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        smsAuthenticationProcessingFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
        String key = UUID.randomUUID().toString();
        smsAuthenticationProcessingFilter.setRememberMeServices(
                new PersistentTokenBasedRememberMeServices(key,userDetailsService,persistentTokenRepository));

        // 初始化自定义的短信校验Provider
        SmsAuthenticationProvider smsAuthenticationProvider = new SmsAuthenticationProvider();
        smsAuthenticationProvider.setUserDetailsService(userDetailsService);

        // 将自定义的过滤器和Provider添加到springSecurity全局配置中
        httpSecurity.addFilterAfter(smsAuthenticationProcessingFilter, UsernamePasswordAuthenticationFilter.class);
        httpSecurity.authenticationProvider(smsAuthenticationProvider);

        super.configure(httpSecurity);
    }
}
