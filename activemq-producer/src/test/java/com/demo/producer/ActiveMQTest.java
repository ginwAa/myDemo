package com.demo.producer;

import org.apache.activemq.command.ActiveMQTopic;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;


@SpringBootTest
public class ActiveMQTest {
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Test
    public void QueueProducerTest() {
        jmsMessagingTemplate.convertAndSend("testQueue", "testMsg");
    }

    @Test
    public void TopicProducerTest() {
        jmsMessagingTemplate.convertAndSend(new ActiveMQTopic("testQueue"), "testMsg");
    }
}
