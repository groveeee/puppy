package org.grovee.mc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author grovee
 * @version 1.0.0
 * @Description 接收请求体中的参数
 * @createTime 2022年10月26日 23:07:00
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.PARAMETER})
public @interface RequestBody {
}
