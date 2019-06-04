package com.sym.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 映射springBoot的配置文件application.yml
 * <p>
 * Created by 沈燕明 on 2019/6/2.
 */
@Data
@ToString
@ConfigurationProperties(prefix = "sym.security")
public class SymSecurityProperties {

    private AppSecurityProperties app = new AppSecurityProperties();//如果用户没配置，默认就是这个，否则用户的配置会覆盖掉此实例

    private BrowserSecurityProperties browser = new BrowserSecurityProperties();//如果用户没配置，默认就是这个，否则用户的配置会覆盖掉此实例

}
