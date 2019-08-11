package com.sym.validate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * 验证码处理器 @link{ ValidateCodeProcessor } 管理器
 * <p>
 * Created by 沈燕明 on 2019/6/29.
 */
@Component
public class ValidateCodeProcessorHolder {

    /**
     * spring使用小技巧，会把所有 ValidateCodeProcessor 接口实现类注入到Map中
     */
    @Autowired
    private Map<String, ValidateCodeProcessor> validateCodeProcessors;

    public ValidateCodeProcessor findValidateCodeProcessor(ValidateCodeType type) {
        return this.findValidateCodeProcessor(type.toString());
    }

    public ValidateCodeProcessor findValidateCodeProcessor(String type) {
        String codeProcessorFullName = type.toLowerCase() + ValidateCodeProcessor.class.getSimpleName();
        ValidateCodeProcessor codeProcessor = validateCodeProcessors.get(codeProcessorFullName);
        Assert.notNull(codeProcessor, "处理类型为{" + type + "}的验证码处理器不存在");
        return codeProcessor;
    }
}
