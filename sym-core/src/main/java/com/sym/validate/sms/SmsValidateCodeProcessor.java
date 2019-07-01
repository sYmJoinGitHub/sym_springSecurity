package com.sym.validate.sms;

import com.sym.entity.SecurityConstant;
import com.sym.validate.AbstractValidateCodeProcessor;
import com.sym.validate.ValidateCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * Created by 沈燕明 on 2019/6/29.
 */
@Component("smsValidateCodeProcessor")
public class SmsValidateCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode> {

    private final static Logger LOGGER = LoggerFactory.getLogger(SmsValidateCodeProcessor.class);

    @Autowired
    private SmsValidateCodeSender smsValidateCodeSender;

    @Override
    protected void send(ServletWebRequest request, ValidateCode code) {
        String mobile="";
        try {
             mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(),SecurityConstant.DEFAULT_PARAMETER_NAME_MOBILE);
        } catch (ServletRequestBindingException e) {
            LOGGER.error("手机号为空，无法发送验证码");
            return;
        }
        // 这边是因为我需要将验证码输出到页面上，实际上验证码是发送到手机的，所以不需要这步
        // 或者也可以修改 SmsValidateCodeSender 接口，send()方法加上一个response参数
        if( smsValidateCodeSender instanceof DefaultSmsValidateCodeSender ){
            ((DefaultSmsValidateCodeSender)smsValidateCodeSender).setResponse(request.getResponse());
        }
        smsValidateCodeSender.send(mobile,code.getCode());
    }
}
