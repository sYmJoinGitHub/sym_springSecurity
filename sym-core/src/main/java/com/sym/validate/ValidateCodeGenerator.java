package com.sym.validate;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 验证码生成类顶级接口
 * <p>
 * Created by 沈燕明 on 2019/6/29.
 */
public interface ValidateCodeGenerator {

    /**
     * 生成验证码
     *
     * @param request
     * @return
     */
    ValidateCode createValidateCode(ServletWebRequest request);
}
