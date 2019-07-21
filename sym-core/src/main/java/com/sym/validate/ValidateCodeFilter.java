package com.sym.validate;

import com.sym.entity.SymSecurityProperties;
import com.sym.exception.BaseException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 沈燕明 on 2019/6/29.
 */
@Component("validateCodeFilter")
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

    private final static Logger LOGGER = LoggerFactory.getLogger(ValidateCodeFilter.class);

    /**
     * 验证码匹配失败时的处理器
     */
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    /**
     * 系统配置类
     */
    @Autowired
    private SymSecurityProperties symSecurityProperties;

    /**
     * 验证码处理器的管理器
     */
    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;

    /**
     * 存放所有需要校验验证码的url地址
     */
    private Map<String, ValidateCodeType> urlMap = new HashMap<>();

    /**
     * springSecurity提供的url匹配器
     */
    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public void afterPropertiesSet() throws ServletException {
        addUrlToMap(symSecurityProperties.getCode().getImageFilterUrl(), ValidateCodeType.IMAGE);
        addUrlToMap(symSecurityProperties.getCode().getSmsFilterUrl(), ValidateCodeType.SMS);
    }

    protected void addUrlToMap(String url, ValidateCodeType type) {
        if (StringUtils.isNotBlank(url)) {
            String[] urls = StringUtils.splitByWholeSeparatorPreserveAllTokens(url, ",");
            for (String s : urls) {
                urlMap.put(s, type);
            }
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ValidateCodeType type = getValidateType(request);
        if (type != null) {
            try {
                validateCodeProcessorHolder.findValidateCodeProcessor(type).validate(new ServletWebRequest(request, response));
                LOGGER.info("验证码校验成功");
            } catch (BaseException e) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    /**
     * 从request中判断这是属于哪种验证码
     *
     * @param request
     * @return
     */
    private ValidateCodeType getValidateType(HttpServletRequest request) {
        ValidateCodeType result = null;
        String uri = request.getRequestURI();
        for (Map.Entry<String, ValidateCodeType> entry : urlMap.entrySet()) {
            String url = entry.getKey();
            if (antPathMatcher.match(url, uri)) {
                result = entry.getValue();
                break;
            }
        }
        return result;
    }
}
