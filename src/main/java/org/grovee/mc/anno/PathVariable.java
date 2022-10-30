package org.grovee.mc.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author grovee
 * @version 1.0.0
 * @Description 路径参数注解
 * @createTime 2022年10月27日 21:36:00
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.PARAMETER})
public @interface PathVariable {
    /**
     * 路径参数
     */
    String value() default "";
}
