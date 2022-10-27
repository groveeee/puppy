package org.grovee.mc.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author grovee
 * @version 1.0.0
 * @Description 接受请求参数 普通类型
 * @createTime 2022年10月27日 21:28:00
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.PARAMETER})
public @interface RequestParam {
    /**
     * 请求参数别名
     */
    String value() default "";

    /**
     * 是否是必须要的参数
     */
    boolean required() default false;
}
