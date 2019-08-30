package com.sym.authorization;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * 基于RBAC模型，判断一个用户是否有权限
 *
 * Created by shenym on 2019/8/30.
 */
public interface RbacSymService {

    /**
     * 判断是否有足够权限访问当前的请求
     * @param request 当前请求
     * @param authentication 拥有的权限
     * @return true-有权限,false-没权限
     */
     boolean hasPermission(HttpServletRequest request, Authentication authentication);

}
