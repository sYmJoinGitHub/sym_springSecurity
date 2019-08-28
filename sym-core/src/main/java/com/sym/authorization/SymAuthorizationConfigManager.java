package com.sym.authorization;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * 自定义的权限配置类{@link SymAuthorizationConfig}的管理器
 * 它可以收集spring容器内所有实现{@link SymAuthorizationConfig}实现类，将其配置进
 * spring security权限配置中
 *
 * Created by shenYm on 2019/8/28.
 */
public interface SymAuthorizationConfigManager {

    /**
     * 将每个自定义的权限配置类添加到spring security中
     * @param config
     */
    void addConfig(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config);

}
