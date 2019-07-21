package com.sym.social.qq.provider;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

/**
 * OAuth2Template是spring social提供的对OAuth2Operations的默认实现类。
 * OAuth2协议申请令牌就是用这个类完成的。
 *
 * 不过OAuth2Template实现类有几处地方不太适合第三方QQ登录，所以我们继承它，覆盖掉相应的方法
 * 来满足QQ登录的需求
 *
 * Created by 沈燕明 on 2019/7/21.
 */
public class QQOAuth2Template extends OAuth2Template {

    private final static Logger LOGGER = LoggerFactory.getLogger(QQOAuth2Template.class);

    /**
     * 引导用户跳转到QQ授权地址的url
     */
    private final static String URL_AUTHORIZE = "https://graph.qq.com/oauth2.0/authorize";
    /**
     * 申请令牌的地址
     */
    private final static String URL_ACCESSTOKEN = "https://graph.qq.com/oauth2.0/token";

    public QQOAuth2Template(String clientId, String clientSecret) {
        super(clientId, clientSecret, URL_AUTHORIZE, URL_ACCESSTOKEN);
    }

    /**
     * spring social 默认的RestTemplate没有加上对返回信息格式为text/html的处理。而恰好QQ就是以这种格式返回的
     * 所以这里我们在获取到父类创建好的RestTemplate后，自己手动加上。这边可以体会到继承的好处
     * @return
     */
    @Override
    protected RestTemplate getRestTemplate() {
        RestTemplate restTemplate = super.getRestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return restTemplate;
    }


    /**
     * spring social 默认的RestTemplate默认是把第三方返回的令牌当做JSON来处理。前面说过
     * QQ返回的信息是以text/html格式，而且它返回的信息是一条字符串，类似这样：
     * access_token=FE04************************CCE2&expires_in=7776000&refresh_token=88E4************************BE14。
     * 所以我们需要覆盖掉父类默认的实现方式，创建出OAuth2的令牌对象AccessGrant
     *
     * @param accessTokenUrl
     * @param parameters
     * @return
     */
    @Override
    protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
        String result = getRestTemplate().postForObject(accessTokenUrl, parameters, String.class);
        LOGGER.info("QQ申请令牌信息为,[{}]",result);
        if(!StringUtils.isEmpty( result )){
            String[] data = result.split("&");
            String accessToken = StringUtils.substringAfterLast(data[0],"=");
            Long expireIn = Long.valueOf(StringUtils.substringAfterLast(data[1],"="));
            String refreshToken = StringUtils.substringAfterLast(data[2],"=");
            return new AccessGrant(accessToken,null,refreshToken,expireIn);
        }
        return null;
    }
}

