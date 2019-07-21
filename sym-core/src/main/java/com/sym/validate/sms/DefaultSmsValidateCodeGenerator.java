package com.sym.validate.sms;

import com.sym.entity.SymSecurityProperties;
import com.sym.validate.ValidateCode;
import com.sym.validate.ValidateCodeGenerator;
import lombok.Data;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 默认的短信验证码生成器
 *
 * Created by 沈燕明 on 2019/6/29.
 */
@Data
@Component("smsValidateCodeGenerator")
public class DefaultSmsValidateCodeGenerator implements ValidateCodeGenerator {

    @Autowired
    private SymSecurityProperties symSecurityProperties;

    @Override
    public ValidateCode createValidateCode(ServletWebRequest request) {
        String code = RandomStringUtils.randomNumeric(symSecurityProperties.getCode().getSmsLength());
        return new ValidateCode(code,symSecurityProperties.getCode().getSmsExpireTime());
    }
}
