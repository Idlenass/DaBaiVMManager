package Servlet.Listener;

import Agent.Thread.VPSTimeOutCheacker;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyServletContextLister implements ServletContextListener {

    //销毁函数
    public void contextDestroyed(ServletContextEvent servletContextEvent){
        System.out.println("MyServletContextLister: Destroyed.");
    }

    //初始化函数
    public void contextInitialized(ServletContextEvent servletContextEvent){
        System.out.println("MyServletContextLister: Initialized.");
        VPSTimeOutCheacker vpsTimeOutCheacker =
                new VPSTimeOutCheacker();
        vpsTimeOutCheacker.start();
    }
}
