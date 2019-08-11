package com.sym.session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 沈燕明 on 2019/7/31 15:52.
 */
public class SymSessionExpiredStrategy implements SessionInformationExpiredStrategy {

    private final static Logger LOGGER = LoggerFactory.getLogger(SymSessionExpiredStrategy.class);

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        HttpServletRequest request = event.getRequest();
        HttpServletResponse response = event.getResponse();
        SessionInformation sessionInformation = event.getSessionInformation();//表示Spring Security框架内的会话记录。
        Object source = event.getSource();
        long timestamp = event.getTimestamp();

        LOGGER.info("source={}", source);
        LOGGER.info("sessionInformation={}", sessionInformation);
        sessionInformation.expireNow();

        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write("您的账号在另一处登录...");
    }
}
