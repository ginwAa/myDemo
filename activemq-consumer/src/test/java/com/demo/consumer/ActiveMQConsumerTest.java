package com.demo.consumer;

import org.apache.activemq.Message;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@SpringBootTest
public class ActiveMQConsumerTest {
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Test
    public void QueueConsumerTest() {
        String s = jmsMessagingTemplate.receiveAndConvert(new ActiveMQQueue("testQueue"), String.class);
        System.out.println(LocalDateTime.now() + " -> Receive from queue:" + s);
    }

    @Test
    public void TopicConsumerTest() {
        String s = jmsMessagingTemplate.receiveAndConvert(new ActiveMQTopic("testQueue"), String.class);
        System.out.println(LocalDateTime.now() + " -> Receive from topic:" + s);
    }

    @Test
    public void TopicConsumerTest2() {
        String s = jmsMessagingTemplate.receiveAndConvert(new ActiveMQTopic("testQueue"), String.class);
        System.out.println(LocalDateTime.now() + " -> Receive from topic:" + s);
    }

}
