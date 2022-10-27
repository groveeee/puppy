package org.grovee.mc.anno;

/**
 * @author grovee
 * @version 1.0.0
 * @Description 路径参数注解
 * @createTime 2022年10月27日 21:36:00
 */
public @interface PathVariable {
    /**
     * 路径参数
     */
    String value() default "";
}
