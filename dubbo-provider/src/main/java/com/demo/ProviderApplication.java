package com.demo;

import com.demo.service.impl.YServiceImpl;
import org.apache.dubbo.config.ServiceConfig;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.dubbo.rpc.service.GenericService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class ProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class, args);

        GenericService YService = new YServiceImpl();

        ServiceConfig<GenericService> service = new ServiceConfig<GenericService>();
        service.setInterface("com.demo.service.YService");
        service.setVersion("1.0");
        service.setRef(YService);


        service.export();
    }
}