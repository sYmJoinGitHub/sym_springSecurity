package com.sym.authorization;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * 自定义的权限配置类，可以给每个模块配置各自的权限信息，然后交由
 * {@link SymAuthorizationConfigManager}，由它整合配置到spring security中
 *
 * @see SymAuthorizationConfigManager
 * Created by shenYm on 2019/8/28.
 */
public interface SymAuthorizationConfig  {

    /**
     * 通过参数config完成模块自己的配置
     * @param config
     */
    void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config);
}
