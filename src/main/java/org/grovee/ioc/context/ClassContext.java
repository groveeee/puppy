package org.grovee.ioc.context;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author grovee
 * @version 1.0.0
 * @Description 管理加载的类
 * @createTime 2022年10月26日 11:52:00
 */
public class ClassContext {

    /**
     * String==>别名
     * Class==>类
     */
    private static ConcurrentHashMap<Class<?>,String> classConfig = new ConcurrentHashMap<>();

    public ClassContext() {
    }

    public static ConcurrentHashMap<Class<?>, String> getClassConfig() {
        return classConfig;
    }

    public static void setClassConfig(ConcurrentHashMap<Class<?>, String> classConfig) {
        ClassContext.classConfig = classConfig;
    }

    public static void put(Class<?> c, String name) {
        classConfig.put(c, name);
    }

    /**
     * 通过类对象获取别名
     * @param c 类对象
     * @return 别名
     */
    public static String get(Class<?> c) {
        return classConfig.get(c);
    }

    /**
     * 通过类型获取其实现类的对象别名
     * @param c 接口类型
     * @return 别名
     */
    public static String getClassImpl(Class<?> c) throws ClassNotFoundException {
        for (Class<?> aClass : classConfig.keySet()) {
            if (c.isAssignableFrom(aClass)){
                return get(aClass);
            }
        }
        throw new ClassNotFoundException();
    }
}
