package org.grovee.mc.handler;

import com.alibaba.fastjson2.JSONObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.grovee.log.Log;
import org.grovee.mc.matcher.RequestMatcher;
import org.grovee.mc.matcher.UniqueHandlerMethod;
import org.grovee.mc.matcher.UniqueRequest;

import java.io.IOException;

/**
 * @author grovee
 * @version 1.0.0
 * @Description 请求处理程序
 * @createTime 2022年10月26日 23:13:00
 */
public class RequestHandler {

    /**
     * 根据请求去执行方法
     */
    public static void doo(HttpServletRequest request, HttpServletResponse response) {

        // 根据请求方法和路径 匹配对应的方法执行
        String uri = request.getRequestURI();
        String requestMethod = request.getMethod();
        UniqueRequest uniqueRequest = new UniqueRequest(uri, requestMethod);
        UniqueHandlerMethod uniqueHandlerMethod = RequestMatcher.get(uniqueRequest);
        Log.info("获取到的对应的执行方法:method:" + uniqueHandlerMethod);
        if (uniqueHandlerMethod == null){
            // 没有对应的处理方式
            try {
                notFound(response);
                return;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            Object invoke = uniqueHandlerMethod.getMethod().invoke(uniqueHandlerMethod.getInstance());
            String s = JSONObject.toJSONString(invoke);
            System.out.println("处理结果:" + s);
            response.getWriter().println(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 请求路径找不到对应的处理器
     * @param response 响应对象
     * @throws IOException
     */
    public static void notFound(HttpServletResponse response) throws IOException {
        response.getWriter().println("""
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <title>404 NOT FOUND </title>
                </head>
                <body>
                <p align="center" style="font-size:140px"> 404 NOT FOUND </p>
                <h5 align="center"> 404 NOT FOUND </h5>
                <h4 align="center"> 404 NOT FOUND </h4>
                <h3 align="center"> 404 NOT FOUND </h3>
                <h2 align="center"> 404 NOT FOUND </h2>
                <h1 align="center"> 404 NOT FOUND </h1>
                <h2 align="center"> 404 NOT FOUND </h2>
                <h3 align="center"> 404 NOT FOUND </h3>
                <h4 align="center"> 404 NOT FOUND </h4>
                <h5 align="center"> 404 NOT FOUND </h5>
                <p align="center" style="font-size:140px"> 404 NOT FOUND </p>
                </body>
                </html>
                        """);
    }
}
