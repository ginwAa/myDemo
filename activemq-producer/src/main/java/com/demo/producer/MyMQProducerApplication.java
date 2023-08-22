package com.demo.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;

@SpringBootApplication
public class MyMQProducerApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyMQProducerApplication.class, args);

    }
}
