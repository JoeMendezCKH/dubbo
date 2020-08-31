package com.alibaba.dubbo.demo.consumer;

import com.alibaba.dubbo.config.*;
import com.alibaba.dubbo.demo.DemoService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ckh
 * @create 8/30/20 3:50 PM
 */
public class ConsumerConfigTest {

    public static void main(String[] args) {

        // 当前应用配置
        ApplicationConfig application = new ApplicationConfig();
        application.setName("joe-demo-consumer-service");

        // 连接注册中心配置
        RegistryConfig registry = new RegistryConfig();
        registry.setProtocol("zookeeper");
        registry.setAddress("localhost:2181");
        registry.setUsername("aaa");
        registry.setPassword("bbb");

        // 方法级配置
        List<MethodConfig> methods = new ArrayList<MethodConfig>();
        MethodConfig method1 = new MethodConfig();
        MethodConfig method2 = new MethodConfig();
        method1.setName("createInt");
        method1.setTimeout(1000);
        method1.setRetries(1);
        method2.setName("sayHello");
        method2.setTimeout(2000);
        method2.setRetries(2);
        methods.add(method1);
        methods.add(method2);

        // 注意：ReferenceConfig为重对象，内部封装了与注册中心的连接，以及与服务提供方的连接

        // 引用远程服务
        // 此实例很重，封装了与注册中心的连接以及与提供者的连接，请自行缓存，否则可能造成内存和连接泄漏
        ReferenceConfig<DemoService> reference = new ReferenceConfig<DemoService>();
        reference.setApplication(application);
        reference.setRegistry(registry); // 多个注册中心可以用setRegistries()
        reference.setInterface(DemoService.class);
        reference.setVersion("1.0.0");

        // 和本地bean一样使用xxxService
        DemoService demoService = reference.get(); // 注意：此代理对象内部封装了所有通讯细节，对象较重，请缓存复用

        System.out.println("===================================");
        System.out.println(demoService.sayHello("joe"));
        System.out.println(demoService.createInt("asdfasdf"));
        System.out.println("===================================");
    }
}
