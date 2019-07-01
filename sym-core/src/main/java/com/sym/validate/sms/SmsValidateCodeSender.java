package com.sym.validate.sms;

/**
 * 短信验证码发送接口
 *
 * Created by 沈燕明 on 2019/6/29.
 */
public interface SmsValidateCodeSender {

    /**
     * 发送验证码到指定手机
     * @param mobile
     * @param code
     */
    void send(String mobile, String code);
}
