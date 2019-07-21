package com.sym.social.qq;

import com.sym.entity.SymSecurityProperties;
import com.sym.entity.prop.QQSocialSecurityProperties;
import com.sym.social.qq.connection.QQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;

/**
 * QQ的配置类，需要实现 SocialAutoConfigurerAdapter 抽象类，返回一个连接工厂
 *
 * Created by 沈燕明 on 2019/7/21.
 */
@Configuration
public class QQAutoConfig extends SocialAutoConfigurerAdapter {

    @Autowired
    private SymSecurityProperties symSecurityProperties;

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        QQSocialSecurityProperties qq = symSecurityProperties.getSocial().getQq();
        return new QQConnectionFactory(qq);
    }
}
