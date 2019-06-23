package com.sym.sms;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 短信登录的拦截器，由它来生成短信验证的token，实现登录
 * 注意要和短信登录验证码拦截器的区别，后者是来判断短信验证码是否存在以及正确的
 *
 * Created by 沈燕明 on 2019/6/22.
 */
public class SmsAuthenticationProcessingFilter  extends AbstractAuthenticationProcessingFilter {


    public static final String SMSCODE_KEY = "mobile";

    private String mobile = SMSCODE_KEY;
    private boolean postOnly = true;



    public SmsAuthenticationProcessingFilter() {
        super(new AntPathRequestMatcher("/sms/login", "POST"));
    }


    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }

        String mobile = obtainMobile(request);

        if (mobile == null) {
            mobile = "";
        }

        mobile = mobile.trim();

        SmsCodeAuthenticationToken token = new SmsCodeAuthenticationToken(mobile);

        // Allow subclasses to set the "details" property
        setDetails(request, token);

        return this.getAuthenticationManager().authenticate(token);
    }




    protected String obtainMobile(HttpServletRequest request) {
        return request.getParameter(mobile);
    }


    protected void setDetails(HttpServletRequest request,
                              SmsCodeAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }


    public void setMobile(String mobileParameter) {
        Assert.hasText(mobileParameter, "Username parameter must not be empty or null");
        this.mobile = mobileParameter;
    }


    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public String getMobile() {
        return mobile;
    }
}

