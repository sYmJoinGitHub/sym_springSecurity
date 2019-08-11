package com.sym.social.qq.connection;

import com.sym.entity.prop.QQSocialSecurityProperties;
import com.sym.social.qq.api.QQ;
import com.sym.social.qq.provider.QQServiceProvider;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * QQ连接工厂
 * <p>
 * Created by 沈燕明 on 2019/7/21.
 */
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ> {


    /**
     * @param qqProp
     */
    public QQConnectionFactory(QQSocialSecurityProperties qqProp) {
        super(qqProp.getProviderId(), new QQServiceProvider(qqProp.getClientId(), qqProp.getClientSecret()), new QQApiAdapter());
    }
}
