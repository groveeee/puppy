package org.grovee.tomcat;

import jakarta.servlet.Servlet;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.grovee.context.ApplicationContext;
import org.grovee.servlet.DispatcherServlet;

import java.io.File;

/**
 * @author grovee
 * @version 1.0.0
 * @Description TODO
 * @createTime 2022年10月26日 18:00:00
 */
public class TomcatStart {

    public static void start(int port){
        File tmpDir = new File("D:\\Game\\tomcat");
        Tomcat tomcat = new Tomcat();

        tomcat.setBaseDir(tmpDir.getAbsolutePath()); // 设置工作目录
        tomcat.setHostname("localhost"); // 主机名, 将生成目录: {工作目录}/work/Tomcat/{主机名}/ROOT
        System.out.println("工作目录: " + tomcat.getServer().getCatalinaBase().getAbsolutePath());

        tomcat.setPort(port);
        Connector conn = tomcat.getConnector(); // Tomcat 9.0后 必须调用 Tomcat#getConnector() 方法之后才会监听端口 现在用的10版本
        System.out.println("连接器设置完成: " + conn);


        Context ctx = tomcat.addContext("", null);
        if(ApplicationContext.getObject(DispatcherServlet.class) instanceof Servlet servlet) {
            System.out.println("添加servlet");
            Tomcat.addServlet(ctx, "dispatcherServlet",servlet );
            ctx.addServletMappingDecoded("/*","dispatcherServlet");
        }
        try {
            tomcat.start();
            System.out.println("tomcat 已启动");
            tomcat.getServer().await();
        } catch (LifecycleException e) {
            throw new RuntimeException(e);
        }
    }
}
