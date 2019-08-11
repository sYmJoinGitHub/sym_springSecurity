package com.sym.social.qq.api;

/**
 * 由于spring social没办法对每个第三方登录的返回的信息做封装（每家公司的返回信息都不一样），
 * 所以它提供了一个API给开发者自己封装，实现 {@link AbstractOAuth2ApiBinding}
 * 这个接口是专门用来处理QQ的API接口
 * <p>
 * Created by 沈燕明 on 2019/7/21.
 */
public interface QQ {

    /**
     * 获取腾讯QQ响应的用户信息
     *
     * @return
     */
    QQUserInfoEntity getQQInfo();

}
