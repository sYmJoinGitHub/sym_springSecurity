package com.sym.validate;

import org.springframework.stereotype.Repository;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 验证码存取器
 *
 * Created by 沈燕明 on 2019/6/29.
 */
public interface ValidateCodeRepository {

    /**
     * 保存验证码
     * @param code
     * @param type
     * @param request
     */
    void save(ValidateCode code, ValidateCodeType type, ServletWebRequest request);

    /**
     * 获取验证码
     * @param type
     * @param request
     * @return
     */
    ValidateCode get(ValidateCodeType type, ServletWebRequest request);

    /**
     * 移除验证码
     * @param type
     * @param request
     */
    void remove(ValidateCodeType type, ServletWebRequest request);
}
