package com.sym.social.qq.provider;

import com.sym.social.qq.api.QQ;
import com.sym.social.qq.api.QQImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

/**
 * OAuth2ServiceProvider是完成OAuth2授权模式整个流程的接口，spring social提供其抽象实现类
 * AbstractOAuth2ServiceProvider，我们需要继承它并注入它需要的两个组件：API和OAuth2Operations
 * <p>
 * Created by 沈燕明 on 2019/7/21.
 */
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {

    private String appId;

    public QQServiceProvider(String clientId, String clientSecret) {
        super(new QQOAuth2Template(clientId, clientSecret));
        this.appId = clientId;
    }

    @Override
    public QQ getApi(String accessToken) {
        return new QQImpl(accessToken, appId);
    }
}
