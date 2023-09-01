package com.demo;

import com.demo.service.impl.MyGenericServiceImpl;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.config.*;
import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.dubbo.remoting.zookeeper.curator5.Curator5ZookeeperClient;
import org.apache.dubbo.rpc.service.GenericService;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableDubbo
public class ProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class, args);

        // TODO test username & password
//        RegistryConfig registryConfig = new RegistryConfig();
//        registryConfig.setAddress("zookeeper://172.27.23.85:2181");
//        registryConfig.setUsername("admin");
//        registryConfig.setPassword("admin");
//        ProtocolConfig protocol = new ProtocolConfig();
//        protocol.setName("dubbo");
//        protocol.setPort(12345);
//        protocol.setThreads(200);

        MethodConfig methodConfig = new MethodConfig();
        methodConfig.setName("getY");

        ArgumentConfig argumentConfig = new ArgumentConfig();
        argumentConfig.setType(String.class.getTypeName());
        methodConfig.addArgument(argumentConfig);

        ServiceConfig<GenericService> service = new ServiceConfig<GenericService>();
        service.setInterface("com.demo.service.MyGenericService");
        service.setVersion("1.0");
        service.setRef(new MyGenericServiceImpl());
        service.setGroup("testGroup");
        service.addMethod(methodConfig);

//        service.setProtocol(protocol);
//        service.setRegistry(registryConfig);
        service.export();

    }
}