package com.sym.validate;

import com.sym.exception.BaseException;
import com.sym.exception.validate.CodeCheckFailedException;
import com.sym.exception.validate.CodeExpireException;
import com.sym.exception.validate.CodeNotFoundException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

/**
 * 验证码处理器抽象父类
 *
 * Created by 沈燕明 on 2019/6/29.
 */
public abstract class AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor{

    /**
     * 使用spring的一个小技巧，可以收集系统中所有实现 ValidateCodeGenerator 接口的实现类
     */
    @Autowired
    private Map<String,ValidateCodeGenerator> validateCodeGeneratorMap;

    @Autowired
    private ValidateCodeRepository validateCodeRepository;

    /**
     * 创建验证码
     * @param request
     * @throws Exception
     */
    @Override
    public void create(ServletWebRequest request) throws Exception {
        C validateCode = generate(request);
        this.save(request,validateCode);
        this.send(request,validateCode);
    }

    /**
     * 核对验证码信息
     * @param request
     * @throws Exception
     */
    @Override
    @SuppressWarnings("unchecked")
    public void validate(ServletWebRequest request) throws ValidateCodeException {
        ValidateCodeType type = getValidateCodeType();
        // 获取服务器保存的验证码信息
        C sessionCode = (C)validateCodeRepository.get(type,request);
        // 获取客户端传过来的验证码信息
        String requestCode;
        try{
            requestCode = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), type.getParamNameOnValidate());
        }catch(Exception e){
            throw new BaseException("获取验证码的值失败");
        }

        if( StringUtils.isBlank( requestCode ) ){
            throw new CodeNotFoundException("验证码的值不能为空");
        }

        if( sessionCode == null ){
            throw new CodeNotFoundException("验证码不存在");
        }

        if( sessionCode.isExpire() ){
            throw new CodeExpireException("验证码已过期");
        }

        if( !StringUtils.equals( sessionCode.getCode(),requestCode ) ){
            throw new CodeCheckFailedException("验证码不匹配");
        }

        validateCodeRepository.remove(type,request);
    }

    /**
     * 生成校验码
     */
    @SuppressWarnings("unchecked")
    private C generate(ServletWebRequest request){
        String type = getValidateCodeType().toString().toLowerCase();
        String generatorName = type + ValidateCodeGenerator.class.getSimpleName();
        ValidateCodeGenerator validateCodeGenerator = validateCodeGeneratorMap.get(generatorName);
        Assert.notNull(validateCodeGenerator,"未找到对应type="+type+"的验证码生成器");
        return (C)validateCodeGenerator.createValidateCode(request);
    }

    /**
     * 获取验证码的类型
     * @return
     */
    private ValidateCodeType getValidateCodeType(){
        String codeProcessor = StringUtils.substringBefore(this.getClass().getSimpleName(), "ValidateCodeProcessor");
        return ValidateCodeType.valueOf(codeProcessor.toUpperCase());
    }

    /**
     * 保存验证码
     * @param request
     * @param code
     */
    private void save(ServletWebRequest request,ValidateCode code){
        validateCodeRepository.save(code,getValidateCodeType(),request);
    }

    /**
     * 发送验证码，由子类去实现
     * @param request
     * @param code
     */
    protected abstract void send(ServletWebRequest request,C code) throws Exception;
}
