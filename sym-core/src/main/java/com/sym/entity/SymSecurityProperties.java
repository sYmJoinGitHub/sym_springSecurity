package com.sym.entity;

import com.sym.entity.prop.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 整个工程的顶级配置类，其它下属配置类都放置在这
 * <p>
 * Created by 沈燕明 on 2019/6/2.
 */
@Data
@ToString
@ConfigurationProperties(prefix = "sym.security")
public class SymSecurityProperties {

    private AppSecurityProperties app = new AppSecurityProperties();//如果用户没配置，默认就是这个，否则用户的配置会覆盖掉此实例

    private BrowserSecurityProperties browser = new BrowserSecurityProperties();//如果用户没配置，默认就是这个，否则用户的配置会覆盖掉此实例

    private ValidateCodeProperties code = new ValidateCodeProperties();//如果用户没配置，默认就是这个，否则用户的配置会覆盖掉此实例

   private SocialSecurityProperties social = new SocialSecurityProperties();//如果用户没配置，默认就是这个，否则用户的配置会覆盖掉此实例
}
