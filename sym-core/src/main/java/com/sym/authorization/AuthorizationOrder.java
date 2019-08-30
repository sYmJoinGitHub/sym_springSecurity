package com.sym.authorization;

import java.lang.annotation.*;

/**
 * 权限配置的优先级，值越小优先级越高
 *
 *
 * Created by shenym on 2019/8/30.
 */
@Documented
@Target(ElementType.TYPE)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthorizationOrder {

    int value() default Integer.MAX_VALUE;

}
