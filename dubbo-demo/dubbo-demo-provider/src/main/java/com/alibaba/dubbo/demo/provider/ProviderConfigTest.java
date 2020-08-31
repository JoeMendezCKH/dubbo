package com.alibaba.dubbo.demo.provider;

import com.alibaba.dubbo.config.*;
import com.alibaba.dubbo.demo.DemoService;
import sun.management.counter.Variability;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ckh
 * @create 8/30/20 10:53 AM
 */
public class ProviderConfigTest {
    public static void main(String[] args) throws IOException {

        // 服务实现
        DemoService demoService = new DemoServiceImpl();

        // 当前应用配置
        ApplicationConfig application = new ApplicationConfig();
        application.setName("joe-demo-provider-service");

        // 连接注册中心配置
        RegistryConfig registry = new RegistryConfig();
        registry.setProtocol("zookeeper");
        registry.setAddress("localhost:2181");
        registry.setUsername("aaa");
        registry.setPassword("bbb");

        // 服务提供者协议配置
        ProtocolConfig protocol = new ProtocolConfig();
        protocol.setName("dubbo");
        protocol.setPort(20080);
        protocol.setThreads(200);

        // 注意：ServiceConfig为重对象，内部封装了与注册中心的连接，以及开启服务端口

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

        // 服务提供者暴露服务配置
        // 此实例很重，封装了与注册中心的连接，请自行缓存，否则可能造成内存和连接泄漏
        ServiceConfig<DemoService> service = new ServiceConfig<DemoService>();
        service.setApplication(application);
        service.setRegistry(registry); // 多个注册中心可以用setRegistries()
        service.setProtocol(protocol); // 多个协议可以用setProtocols()
        service.setMethods(methods);
        service.setInterface(DemoService.class);
        service.setRef(demoService);
        service.setVersion("1.0.0");

        // 暴露及注册服务
        service.export();

        System.in.read();

    }

}
