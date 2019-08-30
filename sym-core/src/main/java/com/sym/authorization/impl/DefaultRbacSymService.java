package com.sym.authorization.impl;

import com.sym.authorization.RbacSymService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by shenym on 2019/8/30.
 */
@Component("rbacSymService")
public class DefaultRbacSymService implements RbacSymService {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    /**
     * 判断当前用户是否有权限访问当前请求
     * @param request 当前请求
     * @param authentication 拥有的权限
     * @return
     */
    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {

        if( authentication instanceof UsernamePasswordAuthenticationToken){
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)authentication;
            String requestURI = request.getRequestURI();
            return false;
        }else{
            return false;
        }
    }
}
