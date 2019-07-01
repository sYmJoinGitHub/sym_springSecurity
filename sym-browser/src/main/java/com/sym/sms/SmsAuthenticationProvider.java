package com.sym.sms;

import com.sym.config.SymDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * 实际上对于账户信息的认证，springSecurity都是交给 AuthenticationProvider实现类来完成的
 * 默认的表单登录使用的是 DaoAuthenticationProvider。
 * 这里我们自定义实现短信登录的 AuthenticationProvider实现类
 *
 * Created by 沈燕明 on 2019/6/23.
 */
@Component
public class SmsAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private SymDetailsService symDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsCodeAuthenticationToken token = (SmsCodeAuthenticationToken)authentication;
        String mobile = String.valueOf( token.getPrincipal() );//获取手机号
        // 通过手机号去寻找用户的信息
        UserDetails userDetails = symDetailsService.loadUserByUsername(mobile);
        return userDetails == null?null:new SmsCodeAuthenticationToken(userDetails,userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(SmsCodeAuthenticationToken.class);
    }
}
