package org.grovee.mc.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author grovee
 * @version 1.0.0
 * @Description 标识被框架管理 控制层
 * @createTime 2022年10月26日 22:13:00
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE})
public @interface Controller {

    String value() default "";

}
