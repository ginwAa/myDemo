package com.demo;

import com.alibaba.cloud.sentinel.annotation.SentinelRestTemplate;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.demo.config.SentinelConfiguration;
import com.demo.controller.ConsumerController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.reactive.ClientHttpResponse;
import org.springframework.http.client.reactive.ClientHttpResponseDecorator;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;


@SpringBootApplication
@EnableDiscoveryClient
public class CloudConsumerApp {
    public static void main(String[] args) {
        SpringApplication.run(CloudConsumerApp.class, args);

        SentinelConfiguration.initFlowRules();
        SentinelConfiguration.initSystemRules();
//        SentinelConfiguration.initErrorCountRules();

//        SentinelConfiguration.initSlowRequestRatioRules();
//        SentinelConfiguration.registerStateChangeObserver();
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}