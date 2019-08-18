package com.sym.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sym.entity.ResultInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 退出登录成功后的处理器
 *
 * Created by shenYm on 2019/8/18.
 */
@Component("symLogoutSuccessHandler")
public class SymLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    private ObjectMapper objectMapper;

    private final static Logger LOGGER = LoggerFactory.getLogger(SymLogoutSuccessHandler.class);

    /**
     *
     * 此方法的Authentication参数里面封装用户请求的IP地址，和登录请求参数（用户名，密码被springSecurity清空了）
     * 以及用户的一些权限信息
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication) throws IOException, ServletException {
        // 这里可以自定义处理方式
        // 既可以返回JSON，也可以跳转页面....

        LOGGER.info("用户登出,信息为：",authentication);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().print(objectMapper.writeValueAsString(ResultInfo.success("退出登录成功~再会")));
    }
}
