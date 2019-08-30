package com.sym.authorization.impl;

import com.sym.authorization.AuthorizationOrder;
import com.sym.authorization.SymAuthorizationConfig;
import com.sym.authorization.SymAuthorizationConfigManager;
import com.sym.entity.SymSecurityProperties;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * Created by shenYm on 2019/8/28.
 */
@Component
public class DefaultSymAuthorizationConfigManager implements SymAuthorizationConfigManager, InitializingBean {

    @Autowired
    private SymSecurityProperties symSecurityProperties;

    /**
     * spring 会将所有实现了{@link SymAuthorizationConfig}的实现类添加此集合中 适配
     */
    @Autowired
    private List<SymAuthorizationConfig> symAuthorizationConfigs;


    @Override
    public void addConfig(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        for( SymAuthorizationConfig symConfig :  symAuthorizationConfigs){
            // 循环每个配置类，整合它们各自的配置信息
            symConfig.config(config);
        }
    }

    /**
     * 对获取到的{@link SymAuthorizationConfig}集合进行排序
     *
     * @see AuthorizationOrder
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        if( symAuthorizationConfigs != null && symAuthorizationConfigs.size() > 0 ){
            symAuthorizationConfigs.sort((o1, o2) -> {

                // o1比o2是升序排序，o2比o1是降序排序
                // 现在要把 AuthorizationOrder 值小的权限类放在集合前面，所以要进行升序排序

                int firstVal,secondVal;
                AuthorizationOrder order1 = o1.getClass().getAnnotation(AuthorizationOrder.class);
                AuthorizationOrder order2 = o2.getClass().getAnnotation(AuthorizationOrder.class);
                firstVal = null == order1?Integer.MAX_VALUE:order1.value();
                secondVal = null == order2?Integer.MAX_VALUE:order2.value();

                return Integer.compare(firstVal,secondVal);
            });
        }
    }
}
