package org.grovee.ioc.init;

import org.grovee.context.ApplicationContext;
import org.grovee.context.ClassContext;
import org.grovee.di.Automatic;
import org.grovee.ioc.Component;
import org.grovee.ioc.Service;
import org.grovee.mc.anno.Controller;
import org.grovee.mc.anno.RequestMapping;
import org.grovee.mc.matcher.RequestMatcher;
import org.grovee.mc.matcher.UniqueHandlerMethod;
import org.grovee.mc.matcher.UniqueRequest;
import org.grovee.tomcat.TomcatStart;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author grovee
 * @version 1.0.0
 * @Description 扫描所有的标识了@Component @Controller @Service 的类并且初始化
 * @createTime 2022年10月26日 10:56:00
 */
public class ComponentScanAndInitAndStart {

    public static long startTime = System.currentTimeMillis();
    static {
        URL resource = ComponentScanAndInitAndStart.class.getClassLoader().getResource(getRootPath().replace(".", "/"));
        System.out.println("项目根路径:" + resource);
        // 通过扫描路径获取项目根路径文件对象
        assert resource != null;
        File[] files = new File(resource.getFile()).listFiles();
        // 加载文件夹下的所有的需要被管理的类
        try {
            assert files != null;
            loadFilesAToClass(files);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        attributeAssignment();
        System.out.println("====================类与对象初始化完成====================");
    }

    /**
     * 启动Tomcat容器
     * @param port
     */
    public static void start(int port){
        TomcatStart.start(port);
    }

    /**
     * 获取执行的main方法所在的路径 也就是项目根路径
     *
     * @return rootPath
     */
    public static String getRootPath() {
        StackTraceElement[] stackTrace = new RuntimeException().getStackTrace();
        for (StackTraceElement stackTraceElement : stackTrace) {
            if ("main".equals(stackTraceElement.getMethodName())) {
                try {
                    Class<?> aClass = Class.forName(stackTraceElement.getClassName());
                    System.out.println("main方法所在的包路径也就是项目根路径" + aClass.getPackageName());
                    return aClass.getPackageName();
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        throw new RuntimeException("RootPath Not Found");
    }

    /**
     * 加载文件夹下的所有的需要被管理的类进行实例化
     *
     * @param files 文件夹
     */
    private static void loadFilesAToClass(File[] files) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        for (File file : files) {
            if (file.isDirectory()) {
                loadFilesAToClass(Objects.requireNonNull(file.listFiles()));
            } else {
                String path = file.getAbsolutePath();
                if (path.endsWith(".class")) {
                    String classes = path.substring(path.indexOf("classes") + "classes".length() + 1, path.indexOf(".class")).replace("\\", ".");
                    Class<?> aClass = ComponentScanAndInitAndStart.class.getClassLoader().loadClass(classes);
                    Service service = aClass.getAnnotation(Service.class);
                    Controller controller = aClass.getAnnotation(Controller.class);
                    Component component = aClass.getAnnotation(Component.class);
                    // 加载标识了@Component和@Service注解的类
                    if (component!=null || service!=null) {
                        String beanName = component!=null?component.value():service.value();
                        LoadInstance(aClass, beanName);
                    }
                    // 加载控制器
                    if (controller!=null){
                        Object instance = LoadInstance(aClass, controller.value());
                        RequestMapping rootRequestMapping = aClass.getAnnotation(RequestMapping.class);
                        // 控制器上的路径 根路径
                        String rootPath = rootRequestMapping.value();
                        // 获取其所有加了@RequestMapping的方法
                        Method[] declaredMethods = aClass.getDeclaredMethods();
                        for (Method method : declaredMethods) {
                            // 打破封装
                            method.setAccessible(true);
                            RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                            if (requestMapping!=null){
                                UniqueRequest uniqueRequest = new UniqueRequest(rootPath+requestMapping.value(),requestMapping.method());
                                UniqueHandlerMethod uniqueHandlerMethod = new UniqueHandlerMethod(method, instance);
                                RequestMatcher.add(uniqueRequest,uniqueHandlerMethod);
                            }
                        }
                    }
                }
            }
        }
    }

    private static Object LoadInstance(Class<?> aClass, String beanName) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Object instance = aClass.getDeclaredConstructor().newInstance();
        if ("".equals(beanName)) {
            ClassContext.put(aClass, aClass.getName());
            ApplicationContext.getApps().put(aClass.getName(), instance);
        } else {
            ClassContext.put(aClass, beanName);
            ApplicationContext.getApps().put(beanName, instance);
        }
        return instance;
    }

    /**
     * 对实例的对象进行属性赋值
     */
    private static void attributeAssignment() {
        ConcurrentHashMap<Class<?>, String> classConfig = ClassContext.getClassConfig();
        classConfig.forEach((c, n) -> {
            Field[] declaredFields = c.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                if (declaredField.isAnnotationPresent(Automatic.class)) {
                    System.out.println("字段对应的类型:" + declaredField.getType());
                    // 打破封装 设置该字段的值
                    declaredField.setAccessible(true);
                    Object object = ApplicationContext.getObject(c);
                    System.out.println("为字段赋值");
                    System.out.println(object);
                    try {
                        declaredField.set(object, ApplicationContext.getObject(declaredField.getType()));
                        System.out.println(object);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }
}
