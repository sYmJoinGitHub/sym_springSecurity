package com.sym.validate;

import org.springframework.security.core.AuthenticationException;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 验证码处理器接口，封装不同的验证码处理逻辑
 *
 * Created by 沈燕明 on 2019/6/29.
 */
public interface ValidateCodeProcessor {

    /**
     * 创建校验码
     * @param request
     */
    void create(ServletWebRequest request) throws Exception;

    /**
     * 核对验证码
     * @param request
     * @throws Exception
     */
    void validate(ServletWebRequest request) throws AuthenticationException;
}
