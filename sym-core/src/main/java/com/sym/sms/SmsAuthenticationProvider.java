package com.sym.sms;

import lombok.Setter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 实际上对于账户信息的认证，springSecurity都是交给 AuthenticationProvider 实现类来完成的
 * 默认的表单登录使用的是 DaoAuthenticationProvider。
 * 这里我们自定义实现短信登录的 AuthenticationProvider实现类
 *
 * 注意：当我们自定义实现 AuthenticationProvider 接口，绝对不能使用@Compent注解将其注入到IOC容器
 *      查看源码 InitializeUserDetailsBeanManagerConfigurer的58行，当发现已经配置了authenticationProviders后
 *      springSecurity就不会自动配置DaoAuthenticationProvider，这会导致表单登录失败，报错为：
 *      No AuthenticationProvider found for org.springframework.security.authentication.UsernamePasswordAuthenticationToken
 *
 * Created by 沈燕明 on 2019/6/23.
 */
public class SmsAuthenticationProvider implements AuthenticationProvider {

    @Setter
    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsCodeAuthenticationToken token = (SmsCodeAuthenticationToken)authentication;
        String mobile = String.valueOf( token.getPrincipal() );//获取手机号
        // 通过手机号去寻找用户的信息
        UserDetails userDetails = userDetailsService.loadUserByUsername(mobile);
        return userDetails == null?null:new SmsCodeAuthenticationToken(userDetails,userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(SmsCodeAuthenticationToken.class);
    }
}
