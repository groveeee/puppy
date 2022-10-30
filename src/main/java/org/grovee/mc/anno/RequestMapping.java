package org.grovee.mc.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.grovee.mc.constant.RequestMethod.*;

/**
 * @author grovee
 * @version 1.0.0
 * @Description 匹配请求路径 参数 类型
 * @createTime 2022年10月26日 22:16:00
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE, ElementType.METHOD})
public @interface RequestMapping {
    /**
     * 匹配路径
     */
    String value() default "/";

    /**
     * 请求类型
     */
    String[] method() default {GET,POST,PUT,DELETE};

    /**
     * 请求参数
     */
    String[] params() default {};
}
