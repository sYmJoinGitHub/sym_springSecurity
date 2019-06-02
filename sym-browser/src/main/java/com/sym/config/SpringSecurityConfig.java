package com.sym.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 要配置springSecurity就需要继承 WebSecurityConfigurerAdapter，
 * 然后重写它的 configure(HttpSecurity http)方法
 * <p>
 * Created by 沈燕明 on 2019/5/29.
 */
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 重写 configure(HttpSecurity http) 方法来自定义我们的配置
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*
         * HttpSecurity里面的方法可以获取各个组件,如formLogin()获取表单登录组件,
         * 后面的代码就是基于表单验证组件做配置；当配置完表单登录组件后，想配置其它
         * 组件，就可以通过and()继续获取HttpSecurity对象，调用它其它方法获取其它组件
         */
        http.formLogin()//获取表单登录组件
                //.loginPage("/signIn.html")//配置表单登录页面
                .loginPage("/loginHandler")//配置表单登录请求，由它来决定返回一个HTML页面，还是一个JSON
                .usernameParameter("customerName")//配置表单登录时的用户名
                .passwordParameter("customerPwd")//配置表单登录时的密码
                .successForwardUrl("/succeedAfterLogin")//登录成功后的跳转页面
                .failureForwardUrl("/failedAfterLogin")//登录失败后的跳转页面
                .loginProcessingUrl("/signIn")//指定校验的url，前端登录把请求提交到此url即可
                .and()//切换到HttpSecurity组件
                .csrf().disable()//停止CSRF校验
                .authorizeRequests()//获取授权组件
                .antMatchers("/signIn","/loginHandler","/signIn.html").permitAll()//登录页不用校验
                .anyRequest().authenticated();//其它页面需要校验
    }

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
}
