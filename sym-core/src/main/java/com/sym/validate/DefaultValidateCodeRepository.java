package com.sym.validate;

import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpSession;

/**
 * 默认的验证码存取器实现 @link{ValidateCodeRepository}
 * <p>
 * Created by 沈燕明 on 2019/7/1.
 */
public class DefaultValidateCodeRepository implements ValidateCodeRepository {

    private final static String SESSION_PREFIX = "sym_httpSession_Id_for";

    @Override
    public void save(ValidateCode code, ValidateCodeType type, ServletWebRequest request) {
        HttpSession session = request.getRequest().getSession();
        session.setAttribute(initKey(type), code);
    }

    @Override
    public ValidateCode get(ValidateCodeType type, ServletWebRequest request) {
        HttpSession session = request.getRequest().getSession();
        return (ValidateCode) session.getAttribute(initKey(type));
    }

    @Override
    public void remove(ValidateCodeType type, ServletWebRequest request) {
        HttpSession session = request.getRequest().getSession();
        session.removeAttribute(initKey(type));
    }

    /**
     * 获取存放session用的Key
     *
     * @param type
     * @return
     */
    private String initKey(ValidateCodeType type) {
        return SESSION_PREFIX + type.getParamNameOnValidate();
    }
}
