package com.sym.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * AuthenticationSuccessHandler接口，可以实现它定义登录成功后的处理方式。
 * 默认springSecurity是用SavedRequestAwareAuthenticationSuccessHandler这个类处理的
 * (它的处理方式是直接跳转到用户在登录之前请求的URL地址)
 * 如果我们不想全部定义，也可以继承它，重写onAuthenticationSuccess()方法即可
 *
 * Created by 沈燕明 on 2019/6/2.
 */
@Component(value="symSignInSuccessHandler")
public class SymSignInSuccessHandler implements AuthenticationSuccessHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(SymSignInSuccessHandler.class);

    /*
     * springMVC在启动时就会将ObjectMapper注入到IOC容器中
     */
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 此方法的Authentication参数里面封装用户请求的IP地址，和登录请求参数（用户名，密码被springSecurity清空了）
     * 以及用户的一些权限信息
     *
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        LOGGER.info("name=：",authentication.getName());
        LOGGER.info("Authorities：",authentication.getAuthorities());
        LOGGER.info("Credentials：",authentication.getCredentials());
        LOGGER.info("Details：",authentication.getDetails());//details就是我们从数据库查出来的用户信息
        LOGGER.info("Principal：",authentication.getPrincipal());

        // 处理登录成功的请求
        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().println(objectMapper.writeValueAsString(authentication));//这边直接把springSecurity封装权限信息对象返回回去
    }
}
