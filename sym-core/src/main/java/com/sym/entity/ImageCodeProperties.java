package com.sym.entity;

import lombok.Data;
import lombok.ToString;

/**
 * 图片验证码配置类
 *
 * Created by 沈燕明 on 2019/6/13 17:04.
 */
@Data
@ToString
public class ImageCodeProperties {
    private int expireTime = 3*60;//单位秒，默认3分钟
    private int width = 68;//图片宽度，默认68
    private int height = 22;//图片高度，默认22
    private int length = 4;//验证码的位数
    private String url = "/signIn";//拦截的url
}
