package org.grovee.ioc.context;

import org.grovee.log.Log;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author grovee
 * @version 1.0.0
 * @Description IOC容器
 * @createTime 2022年10月26日 12:00:00
 */
public class ApplicationContext {
    private static ConcurrentHashMap<String, Object> apps = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<String, Object> getApps() {
        return apps;
    }

    public static void setApps(ConcurrentHashMap<String, Object> apps) {
        ApplicationContext.apps = apps;
    }

    /**
     * 通过别名获取对象
     *
     * @param name
     * @return Object
     */
    public static Object getObject(String name) {
        return apps.get(name);
    }

    /**
     * 通过Class获取对象
     *
     * @param c 类
     * @return Object
     */
    public static Object getObject(Class<?> c) {
        Log.info("获取的对象的类的名字:" + c.getName());
        return apps.get(ClassContext.get(c));
    }

    /**
     * 通过接口类型 获取其对应的实现类的单例对象
     *
     * @param c
     * @return
     */
    public static Object getInstanceImp(Class<?> c) throws ClassNotFoundException {
        return apps.get(ClassContext.getClassImpl(c));
    }
}
