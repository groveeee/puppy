package org.grovee.mc.handler;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.grovee.log.Log;
import org.grovee.mc.anno.PathVariable;
import org.grovee.mc.anno.RequestBody;
import org.grovee.mc.anno.RequestParam;
import org.grovee.mc.matcher.RequestMatcher;
import org.grovee.mc.matcher.UniqueHandlerMethod;
import org.grovee.mc.matcher.UniqueRequest;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.grovee.mc.constant.BasicType.*;

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
        Log.info("获取到的对应的普通请求执行方法:method:" + uniqueHandlerMethod);

        // 用来存放uri转换为通配请求的路径 例: /*/*/*...
        String resultPath = uri;
        // 标识是否为resultful请求
        boolean resultful = false;

        // 当请求路径不匹配时 判断是否为resultful请求
        if (uniqueHandlerMethod == null) {
            // 匹配resultful风格请求路径
            String[] split = uri.split("/");
            List<String> strings = new ArrayList<>(List.of(split));
            for (int i = strings.size() - 1; i > -1; i--) {
                if (StrUtil.isNotBlank(strings.get(i))) {
                    strings.set(i, "*");
                    String join = StrUtil.join("/", strings);
                    System.out.println("join: " + join);
                    uniqueRequest = new UniqueRequest(join, requestMethod);
                    uniqueHandlerMethod = RequestMatcher.get(uniqueRequest);
                    // 匹配成功  提取转换后的通用请求路径
                    if (uniqueHandlerMethod != null) {
                        resultPath = join;
                        resultful = true;
                        Log.info("获取到的对应的resultful执行方法:method:" + uniqueHandlerMethod);
                        break;
                    }
                }
            }
            if (uniqueHandlerMethod == null) {
                // 没有对应的处理方式
                try {
                    notFound(response);
                    return;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        // 如果是resultful请求
        String[] resultSplit = null;
        String[] uriSplit = null;
        // 匹配通用请求路径时 开始的下标索引 用于处理多个这种请求 /*/*
        int num = 0;
        if (resultful) {
            resultSplit = resultPath.split("/");
            uriSplit = uri.split("/");
            if (resultSplit.length != uriSplit.length) {
                throw new RuntimeException("请求路径出错!");
            }
        }
        // 处理请求参数
        Method method = uniqueHandlerMethod.getMethod();
        // 获取所有的请求参数
        Parameter[] parameters = method.getParameters();
        // 定义一个要最终要传递执行的参数列表
        ArrayList<Object> finalParams = new ArrayList<>();
        for (Parameter p : parameters) {
            Log.info("方法上定义的请求参数:" + p);
            Annotation[] annotations = p.getAnnotations();
            Log.info("请求参数上的注解数量:" + annotations.length);
            if (annotations.length > 0) {
                Annotation annotation = p.getAnnotations()[0];
                Log.info("请求参数标识的注解:" + annotation);
                if (annotation instanceof RequestParam requestParam) {
                    // 获取注解内的别名
                    String value = requestParam.value();
                    // 从request中获取对应的参数
                    String parameter = request.getParameter(value);
                    // 判断必需参数是否存在
                    if (requestParam.required() && parameter == null) {
                        throw new RuntimeException("没了这个参数 你让我如何交差");
                    }
                    // 通过类型名字强转成对应的参数类型 存入集合中
                    finalParams.add(cast2RealType(parameter, p.getType().getName()));
                } else if (annotation instanceof PathVariable pathVariable) {
                    // 获取注解中的value值
                    String value = pathVariable.value();
                    Log.info("pathVariable value: " + value);


                    // 获取所有的通配符 * 所对应的uri路径参数
                    for (int i = num; i < resultSplit.length; i++) {
                        if ("*".equals(resultSplit[i])) {
                            // 获取原始数据类型 加载到最终执行参数列表中
                            finalParams.add(cast2RealType(uriSplit[i], p.getType().getName()));
                            // 修改下次匹配开始的索引
                            num = i + 1;
                            // 只匹配一个  一个参数对应一个{*}
                            break;
                        }
                    }
                } else if (annotation instanceof RequestBody requestBody) {
                    try {
                        // 获取请求体中的内容
                        String collect = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
                        // 判断是否为JSON数据
                        if (collect.startsWith("{") && collect.endsWith("}")) {
                            // JSON字符串转换为Java对象
                            Object o = JSONObject.parseObject(collect, p.getType());
                            finalParams.add(o);
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }

            }
        }
        try {
            System.out.println("最终执行的参数结果:" + finalParams);
            Object invoke = uniqueHandlerMethod.getMethod().invoke(uniqueHandlerMethod.getInstance(), finalParams.toArray());
            String s = JSONObject.toJSONString(invoke);
            System.out.println("处理结果:" + s);
            response.getWriter().println(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据传入的TypeName 获取该值的原本真实类型
     *
     * @param value
     * @param type
     * @return
     */
    private static Object cast2RealType(String value, String type) {
        Log.info("真实的的请求参数的类型:" + type);
        return switch (type) {
            case BYTE -> Byte.valueOf(value);
            case SHORT -> Short.valueOf(value);
            case INTEGER -> Integer.valueOf(value);
            case LONG -> Long.valueOf(value);
            case FLOAT -> Float.valueOf(value);
            case DOUBLE -> Double.valueOf(value);
            case BOOLEAN -> Boolean.valueOf(value);
            case CHARACTER -> value.toCharArray()[0];
            case STRING -> value;
            default -> null;
        };
    }

    /**
     * 请求路径找不到对应的处理器
     *
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

