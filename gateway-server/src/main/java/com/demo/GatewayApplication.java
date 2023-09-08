package com.demo;

import com.alibaba.cloud.sentinel.custom.SentinelAutoConfiguration;
import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import com.demo.properties.MyApolloProperties;
import com.demo.properties.MySentinelProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import javax.annotation.Resource;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {
    @Resource
    MySentinelProperties sentinelProperties;
    @Resource
    MyApolloProperties apolloProperties;
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}