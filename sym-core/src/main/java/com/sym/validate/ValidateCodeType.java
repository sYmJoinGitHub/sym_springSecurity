package com.sym.validate;


import com.sym.entity.SecurityConstant;

/**
 * 验证码类型
 * <p>
 * Created by 沈燕明 on 2019/6/29.
 */
public enum ValidateCodeType {/**
 * 短信验证码
 */
SMS {
            @Override
            public String getParamNameOnValidate() {
                return SecurityConstant.DEFAULT_PARAMETER_NAME_CODE_SMS;
            }
        },

    /**
     * 图片验证码
     */
    IMAGE {
        @Override
        public String getParamNameOnValidate() {
            return SecurityConstant.DEFAULT_PARAMETER_NAME_CODE_IMAGE;
        }
    };

    /**
     * 校验时从请求中获取的参数的名字
     *
     * @return
     */
    public abstract String getParamNameOnValidate();}
