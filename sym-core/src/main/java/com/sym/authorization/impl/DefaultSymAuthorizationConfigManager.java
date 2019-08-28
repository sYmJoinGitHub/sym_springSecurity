package com.sym.authorization.impl;

import com.sym.authorization.SymAuthorizationConfig;
import com.sym.authorization.SymAuthorizationConfigManager;
import com.sym.entity.SymSecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Created by shenYm on 2019/8/28.
 */
@Component
public class DefaultSymAuthorizationConfigManager implements SymAuthorizationConfigManager {

    @Autowired
    private SymSecurityProperties symSecurityProperties;

    /**
     * spring 会将所有实现了{@link SymAuthorizationConfig}的实现类添加此集合中 适配
     */
    @Autowired
    private Set<SymAuthorizationConfig> symAuthorizationConfigs;

    @Override
    public void addConfig(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        for( SymAuthorizationConfig symConfig :  symAuthorizationConfigs){
            // 循环每个配置类，整合它们各自的配置信息
            symConfig.config(config);
        }
    }
}
