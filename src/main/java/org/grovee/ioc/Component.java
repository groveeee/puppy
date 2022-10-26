package org.grovee.ioc;

import java.lang.annotation.*;

/**
 * @author grovee
 * @version 1.0.0
 * @Description 标识被框架管理
 * @createTime 2022年10月26日 11:28:00
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE,ElementType.FIELD})
public @interface Component {
    String value() default "";
}
