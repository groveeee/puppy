package org.grovee.mc.matcher;

import java.lang.reflect.Method;

/**
 * @author grovee
 * @version 1.0.0
 * @Description 处理程序执行的方法与其实例
 * @createTime 2022年10月27日 11:18:00
 */
public class UniqueHandlerMethod {

    private Method method;
    private Object instance;

    public UniqueHandlerMethod() {
    }

    public UniqueHandlerMethod(Method method, Object instance) {
        this.method = method;
        this.instance = instance;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getInstance() {
        return instance;
    }

    public void setInstance(Object instance) {
        this.instance = instance;
    }
}
