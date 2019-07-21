package com.sym.social.qq.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.io.IOException;

/**
 * 抽象父类 AbstractOAuth2ApiBinding ，提供了 OAuth2协议需要的令牌 accessToken 和 http工具类 RestTemplate。
 * 这个实现类是完成OAuth协议的最后一步，通过已获取到的令牌，向第三方资源服务器获取用户的信息。
 *
 * Created by 沈燕明 on 2019/7/21.
 */
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {

    private final static Logger LOGGER = LoggerFactory.getLogger(QQImpl.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    /*
     * QQ互联平台，获取QQ用户openId的URL地址
     */
    private final static String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";

    /*
     * QQ互联平台，获取QQ用户信息的URL地址，springSocial默认会把access_token拼接在url地址后面，所以这边不需要我们手动指定
     * oauth_consumer_key就是申请QQ开发者的AppID
     */
    private final static String URL_GET_USER_INFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";

    /**
     * 访问QQ用户信息，需要携带的参数（这是QQ开发者平台要求的）
     */
    private String openId;
    private String appId;

    public QQImpl(String accessToken,String appId){
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        this.appId = appId;
        // 这边请求QQ互联平台，获取openId
        String url = String.format(URL_GET_OPENID,accessToken);
        String s = getRestTemplate().getForObject(url, String.class);
        LOGGER.info("QQ获取到的openid信息,[{}]",s);
        this.openId = StringUtils.substringBetween(s,"\"openid\":\"","}");
    }

    /**
     * 获取腾讯QQ响应的用户信息
     * @return
     */
    @Override
    public QQUserInfoEntity getQQInfo() {
        // 获取用户的个人信息
        String url = String.format(URL_GET_USER_INFO,appId,openId);
        String result = getRestTemplate().getForObject(url, String.class);
        LOGGER.info("QQ获取到的用户个人信息,[{}]",result);
        try {
            return objectMapper.readValue(result, QQUserInfoEntity.class);
        } catch (IOException e) {
            LOGGER.error("转换第三方QQ个人信息失败，{}",e.getMessage());
        }
        return null;
    }
}
