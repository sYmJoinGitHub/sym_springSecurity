package com.sym.social.qq.connection;

import com.sym.social.qq.api.QQ;
import com.sym.social.qq.api.QQUserInfoEntity;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.util.StringUtils;

/**
 * 接口ApiAdapter是用来对第三方返回的信息和我们自己业务系统的用户信息做一些映射转换用的。
 * 是创建ConnectionFactory的必要组件之一
 * <p>
 * Created by 沈燕明 on 2019/7/21.
 */
public class QQApiAdapter implements ApiAdapter<QQ> {

    @Override
    public boolean test(QQ api) {
        return !StringUtils.isEmpty(api.getQQInfo().getOpenId());
    }

    @Override
    public void setConnectionValues(QQ api, ConnectionValues values) {
        QQUserInfoEntity qqInfo = api.getQQInfo();
        values.setDisplayName(qqInfo.getNickname());
        values.setImageUrl(qqInfo.getFigureurl_qq_1());
        values.setProviderUserId(qqInfo.getOpenId());
    }

    @Override
    public UserProfile fetchUserProfile(QQ api) {
        // do nothing
        return null;
    }

    @Override
    public void updateStatus(QQ api, String message) {
        // do nothing
    }
}
