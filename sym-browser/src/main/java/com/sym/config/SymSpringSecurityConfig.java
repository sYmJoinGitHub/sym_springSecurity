package com.sym.config;

import com.sym.entity.SymSecurityProperties;
import com.sym.session.SymSessionExpiredStrategy;
import com.sym.sms.SmsCodeSecurityConfig;
import com.sym.validate.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * 要配置springSecurity就需要继承 WebSecurityConfigurerAdapter，
 * 然后重写它的 configure(HttpSecurity http)方法
 * <p>
 * Created by 沈燕明 on 2019/5/29.
 */
@Configuration
@EnableConfigurationProperties(SymSecurityProperties.class) //让自定义的配置属性类生效
class SymSpringSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 自定义登录成功后的处理类
     */
    @Autowired
    private SymSignInSuccessHandler symSignInSuccessHandler;

    /**
     * 自定义登录失败后的处理类
     */
    @Autowired
    private SymSignInFailedHandler symSignInFailedHandler;

    /**
     * 映射application.yml的配置属性类，提供多样的选择
     */
    @Autowired
    private SymSecurityProperties symSecurityProperties;

    /**
     * 自定义的认证逻辑
     */
    @Autowired
    private SymDetailsService symDetailsService;

    /**
     * 数据源
     */
    @Autowired
    private DataSource dataSource;

    /**
     * 短信登录的配置类
     */
    @Autowired
    private SmsCodeSecurityConfig smsCodeSecurityConfig;

    /**
     * 验证码的配置类
     */
    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    /**
     * 第三方登录的配置类
     */
    @Autowired
    private SpringSocialConfigurer springSocialConfigurer;


    /**
     * springSecurity的配置全在这个方法的参数HttpSecurity，通过它来配置认证、授权的方方面面
     * 重写 configure(HttpSecurity http) 方法来自定义我们的配置
     *
     * @param httpSecurity springSecurity的配置
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        /*
         * HttpSecurity里面的方法可以获取各个组件,如formLogin()获取表单登录组件,
         * 后面的代码就是基于表单验证组件做配置；当配置完表单登录组件后，想配置其它
         * 组件，就可以通过and()继续获取HttpSecurity对象，调用它其它方法获取其它组件
         */
        httpSecurity
                //添加将自定义的过滤器放在指定过滤器前面
                //.addFilterBefore(new SmsCodeFilter(symSecurityProperties,symSignInFailedHandler), UsernamePasswordAuthenticationFilter.class)
                //获取表单登录组件
                .formLogin()
                    //.loginPage("/signIn.html")//指定登陆页面，当springSecurity发现一个请求未认证时,就会将请求转发到这里
                    .loginPage("/loginHandler")//也可以将登录页面改为请求一个Controller的映射，其实建议这样做，这样可以区分不同请求，返回不同的内容
                    .usernameParameter("customerName")//指定登录请求的用户名，默认为username
                    .passwordParameter("customerPwd")//指定登录请求的密码，默认为password
                    //.successForwardUrl("/succeedAfterLogin")//登录成功后的跳转地址，默认为用户之前请求的地址，
                    //.failureForwardUrl("/failedAfterLogin")//登录失败后的跳转地址，默认会抛出异常。当我们指定登陆失败的跳转地址，一定要将它放行不再认证
                    .successHandler(symSignInSuccessHandler)//自定义登录成功后的处理方式
                    .failureHandler(symSignInFailedHandler)//自定义登录失败后的处理方式
                    .loginProcessingUrl("/loginIn")//指定校验的url，前端登录把请求提交到此url即可
                    .and()//切换到HttpSecurity组件
                .csrf()
                    .disable()//停止CSRF校验
                //获取授权组件
                .authorizeRequests()
                    // 不需要认证的地址的地址
                    .antMatchers("/loginHandler","/validate/getCode/**","/invalid/session",
                        symSecurityProperties.getBrowser().getSignInHtmlPath(),
                        symSecurityProperties.getBrowser().getInvalidSessionUrl()).permitAll()
                    .anyRequest().authenticated()//其它页面需要校验
                    .and()//切换到HttpSecurity组件
                //获取《记住我》组件，相对应的拦截器为 RememberMeAuthenticationFilter
                .rememberMe()
                    .userDetailsService(symDetailsService) //记住我组件会封装用户的认证权限信息，所以需要一个userDetailsService
                    .tokenValiditySeconds(60*60)//设置cookie的有效期,单位秒s
                    .tokenRepository(PersistentTokenRepository())//设置操作数据库的工具库
                    .and()
                // 获取会话管理组件
                .sessionManagement()
                    .invalidSessionUrl("/invalid/session")//当springSecurity检测到无效非法(未登录)的会话session时,就会将请求重定向到此url上
                    .maximumSessions(1)//表示最大只允许同一个用户在同一时间内登录
                    .maxSessionsPreventsLogin(true)//表示当同一个用户同时登录的个数达到最大值时，拒绝此用户后面的登录
                    .expiredSessionStrategy(new SymSessionExpiredStrategy())//配置当session过期时的处理策略
                    .and()
                    .and()
                // apply()可以整合另一个完整的springSecurityConfig配置类，这里整合短信登录配置类
                .apply(smsCodeSecurityConfig)
                    .and()
                // apply()可以整合另一个完整的springSecurityConfig配置类，这里整合验证码配置类
                .apply(validateCodeSecurityConfig)
                    .and()
                // apply()可以整合另一个完整的springSecurityConfig配置类，这里整合第三方配置类
                .apply(springSocialConfigurer);

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


    /**
     * springSecurity的记住我功能，会生成一个token，它会将这个token保存到数据库
     * 所以需要一个 PersistentTokenRepository 来操作这个token，其实现类
     * JdbcTokenRepositoryImpl里面存放着建表和CRUD的SQL语句
     *
     * @return
     */
    @Bean
    public PersistentTokenRepository PersistentTokenRepository(){
        JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
        repository.setDataSource(dataSource);
        return repository;
    }
}

