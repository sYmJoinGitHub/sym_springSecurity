package com.sym.validate.sms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 默认的短信验证码发送器
 * <p>
 * Created by 沈燕明 on 2019/6/29.
 */
@NoArgsConstructor
@AllArgsConstructor
public class DefaultSmsValidateCodeSender implements SmsValidateCodeSender {

    private final static Logger LOGGER = LoggerFactory.getLogger(DefaultSmsValidateCodeSender.class);

    private ThreadLocal<HttpServletResponse> threadLocal = new ThreadLocal<>();


    /**
     * 将短信验证码用response写回到页面上
     *
     * @param mobile
     * @param code
     */
    @Override
    public void send(String mobile, String code) {
        HttpServletResponse response = threadLocal.get();
        Assert.notNull(response, "未设置HttpResponse");
        response.setContentType("text/html;charset=utf-8");
        StringBuilder jsBuilder = new StringBuilder();
        jsBuilder.append("<script>");
        jsBuilder.append("alert(\"您的验证码是").append(code).append("\");");
        jsBuilder.append("</script>");
        try {
            response.getWriter().println(jsBuilder.toString());
            LOGGER.info("手机号为：{},验证码为：{}", mobile, code);
        } catch (IOException e) {
            LOGGER.info("手机号：{},短信验证码发送失败，原因：{}", mobile, e.getCause());
        } finally {
            threadLocal.remove();
        }
    }

    public void setResponse(HttpServletResponse response) {
        threadLocal.set(response);
    }
}
