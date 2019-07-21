package com.sym.entity.prop;

import lombok.Data;
import lombok.ToString;

/**
 * springSocial的配置类
 *
 * Created by 沈燕明 on 2019/7/21.
 */
@Data
@ToString
public class SocialSecurityProperties {

    // 第三方登录 for qq
    private QQSocialSecurityProperties qq;
}
