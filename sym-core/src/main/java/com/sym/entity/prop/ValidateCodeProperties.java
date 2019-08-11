package com.sym.entity.prop;

import lombok.Data;
import lombok.ToString;

/**
 * 图片验证码配置类
 * <p>
 * Created by 沈燕明 on 2019/6/13 17:04.
 */
@Data
@ToString
public class ValidateCodeProperties {

    // 短信验证码
    private int smsLength = 4;
    private int smsExpireTime = 3 * 60;//单位秒，默认3分钟
    private String smsFilterUrl = "/signIn/sms";//短信验证码需要拦截的url

    // 图片验证码
    private int imageExpireTime = 3 * 60;//单位秒，默认3分钟
    private int imageLength = 4;//验证码的位数
    private int imageWidth = 68;//图片宽度，默认68
    private int imageHeight = 35;//图片高度，默认35,配置文件修改为22
    private String imageFilterUrl = "/signIn";//图片验证码需要拦截的url
}
