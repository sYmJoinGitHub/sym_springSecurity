package com.sym.validate;

import com.sym.entity.SymSecurityProperties;
import com.sym.validate.image.DefaultImageValidateCodeGenerator;
import com.sym.validate.sms.DefaultSmsValidateCodeSender;
import com.sym.validate.sms.SmsValidateCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by 沈燕明 on 2019/6/29.
 */
@Configuration
public class ValidateBeanConfig {

    @Autowired
    private SymSecurityProperties symSecurityProperties;

    /**
     * 图片验证码生成器
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(name = "imageValidateCodeGenerator")
    public ValidateCodeGenerator imageValidateCodeGenerator() {
        DefaultImageValidateCodeGenerator validateCodeGenerator = new DefaultImageValidateCodeGenerator();
        validateCodeGenerator.setSymSecurityProperties(symSecurityProperties);
        return validateCodeGenerator;
    }

    /**
     * 短信验证码发送器
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(SmsValidateCodeSender.class)
    public SmsValidateCodeSender smsValidateCodeSender() {
        return new DefaultSmsValidateCodeSender();
    }

    /**
     * 验证码存取器
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(ValidateCodeRepository.class)
    public ValidateCodeRepository validateCodeReposity() {
        return new DefaultValidateCodeRepository();
    }


}
