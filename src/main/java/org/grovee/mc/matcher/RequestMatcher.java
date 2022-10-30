package org.grovee.mc.matcher;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author grovee
 * @version 1.0.0
 * @Description 请求匹配器
 * @createTime 2022年10月26日 23:13:00
 */

public class RequestMatcher {
    /**
     * 唯一请求与处理方法的映射集合
     */
    private static ConcurrentHashMap<UniqueRequest, UniqueHandlerMethod> registry = new ConcurrentHashMap<>();

    public static void add(UniqueRequest request,UniqueHandlerMethod method){
        registry.put(request, method);
    }

    public static UniqueHandlerMethod get(UniqueRequest request){

        //
        String path = request.getPath();

        return registry.get(request);
    }

    public static ConcurrentHashMap<UniqueRequest, UniqueHandlerMethod> getRegistry() {
        return registry;
    }

    public static void setRegistry(ConcurrentHashMap<UniqueRequest, UniqueHandlerMethod> registry) {
        RequestMatcher.registry = registry;
    }
}
