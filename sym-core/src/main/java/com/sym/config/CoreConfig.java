package com.sym.config;

import com.sym.entity.SymSecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.social.security.SocialUserDetailsService;

import javax.sql.DataSource;

/**
 * Core模块的配置类，将一些公用的组件注册到IOC容器中
 * <p>
 * Created by shenym on 2019/8/26.
 */
@Configuration
@EnableConfigurationProperties(SymSecurityProperties.class) //让自定义的配置属性类生效
public class CoreConfig {

    @Autowired
    private DataSource dataSource;

    /**
     * 接口 PasswordEncoder 是用来对用户密码进行加密和匹配的。此接口只有2个方法：
     * String encode(CharSequence rawPassword) -- 将用户原始密码rawPassword加密后返回
     * boolean matches(CharSequence rawPassword, String encodedPassword)
     * -- 将用户原始密码rawPassword(从登陆页面用户自己输入的)和加密后的密码(保存在数据库的用户加密后的密码)进行校验
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * springSecurity的记住我功能，会生成一个token，它会将这个token保存到数据库
     * 所以需要一个 PersistentTokenRepository 来操作这个token，其实现类
     * JdbcTokenRepositoryImpl里面存放着建表和CRUD的SQL语句
     *
     * @return
     */
    @Bean
    public PersistentTokenRepository PersistentTokenRepository() {
        JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
        repository.setDataSource(dataSource);
        return repository;
    }


    /**
     * 实现{@link UserDetailsService}和{@link SocialUserDetailsService}的用户认证逻辑
     * @return
     */
    @Bean
    public SymDetailsService symDetailsService(){
        return new SymDetailsService();
    }


    /**
     * 成功登录后的处理器
     * @return
     */
    @Bean
    public AuthenticationSuccessHandler SymSignInSuccessHandler(){
        return new SymSignInSuccessHandler();
    }


    /**
     * 登录失败后的处理器
     * @return
     */
    @Bean
    public AuthenticationFailureHandler SymSignInFailedHandler(){
        return new SymSignInFailedHandler();
    }


    /**
     * 登出成功后的处理器
     * @return
     */
    @Bean
    public LogoutSuccessHandler SymLogoutSuccessHandler(){
        return new SymLogoutSuccessHandler();
    }
}
