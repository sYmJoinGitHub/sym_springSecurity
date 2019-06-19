package com.sym.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * AuthenticationFailureHandler，可以实现它定义登录失败后的处理方式。
 * 默认springSecurity是用 SimpleUrlAuthenticationFailureHandler 这个类处理的
 * (它的处理方式是直接抛出异常到全局异常处理器)
 * 如果我们不想全部定义，也可以继承它，onAuthenticationFailure()方法即可
 * <p>
 * Created by 沈燕明 on 2019/6/2.
 */
@Component(value = "symSignInFailedHandler")
public class SymSignInFailedHandler implements AuthenticationFailureHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(SymSignInFailedHandler.class);

    /*
     * springMVC在启动时就会将ObjectMapper注入到IOC容器中
     */
    @Autowired
    private ObjectMapper objectMapper;


    /**
     * 由于这是登录失败的处理逻辑，所以springSecurity不再提供封装的权限信息，
     * 而是提供了一个异常，它里面定义为什么登录失败的原因
     *
     * @param request
     * @param response
     * @param exception
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        LOGGER.info("用户：{}，登录失败的原因：{}", request.getParameter("customerName"), exception.getMessage());
        // 处理登录失败的请求
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setContentType("application/json;charset=utf-8");
        //这边直接把springSecurity封装异常信息对象返回回去
        response.getWriter().println(objectMapper.writeValueAsString("登陆失败原因："+exception.getMessage()));
    }
}
