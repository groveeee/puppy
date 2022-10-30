package org.grovee.ioc.di;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author grovee
 * @version 1.0.0
 * @Description 自动注入的注解
 * @createTime 2022年10月26日 15:46:00
 */
@Target({TYPE, FIELD, METHOD})
@Retention(RUNTIME)
public @interface Automatic {
    String value() default "";
}
