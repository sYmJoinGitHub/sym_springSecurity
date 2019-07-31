package com.sym.entity.prop;

import lombok.Data;
import lombok.ToString;

/**
 * 基于spring social 的QQ第三方登录，配置类
 * Created by 沈燕明 on 2019/7/20.
 */
@Data
@ToString
public class QQSocialSecurityProperties {
    //private String appId = "1109674624";
    //private String appKey = "wUSwFkzisC28fmuh";
    //private String providerID = "qq";

    /**
     * QQ开发平台分配给APP的客户端ID（需要申请）
     */
    private String clientId = "";
    /**
     * QQ开发平台分配给APP的秘钥ID（需要申请）
     */
    private String clientSecret = "";
    /**
     * spring social对第三方登录的处理器做了统一处理，以providerId做区分（自己配置）
     */
    private String providerId = "";
}
