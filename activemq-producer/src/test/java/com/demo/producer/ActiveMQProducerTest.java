package com.demo.producer;

import org.apache.activemq.*;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.activemq.command.ActiveMQTopic;
import org.junit.jupiter.api.Test;
import org.messaginghub.pooled.jms.JmsPoolMessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.*;
import java.time.LocalDateTime;


@SpringBootTest
public class ActiveMQProducerTest {
    @Autowired
    @Qualifier("syncJmsTemplate")
    private JmsTemplate jmsTemplate;

    @Autowired
    @Qualifier("asyncJmsTemplate")
    private JmsTemplate asyncJmsTemplate;


//    @Autowired
//    private JmsMessagingTemplate jmsMessagingTemplate;

    @Test
    public void QueueProducerTest() {
        for (int i = 0; i < 10; ++i) {
            jmsTemplate.convertAndSend(new ActiveMQQueue("testQueue"), "testMsg " + LocalDateTime.now() + " " + i);
        }
    }

    @Test
    public void TopicProducerTest() {
        for (int i = 0; i < 10; ++i) {
            jmsTemplate.convertAndSend(new ActiveMQTopic("testQueue"), "testMsg " + LocalDateTime.now() + " " + i);
        }
    }

    @Test
    public void MixedProducerTest() {
        for (int i = 0; i < 10; ++i) {
            jmsTemplate.convertAndSend(new ActiveMQQueue("testQueue"), "testMsg " + LocalDateTime.now() + " " + i, message -> {
                message.setStringProperty("priority", "high");
                return message;
            });
            jmsTemplate.convertAndSend(new ActiveMQTopic("testQueue"), "testMsg " + LocalDateTime.now() + " " + i, message -> {
                message.setStringProperty("priority", "high");
                return message;
            });
        }
        System.out.println(jmsTemplate.getConnectionFactory().getClass().getTypeName());
    }

    @Test
    @Transactional
    public void TransactionalProducerTest() {
        QueueProducerTest();
        int i = 4 / 0;
        QueueProducerTest();
    }

    @Test
    public void AsyncProducerTest() {
//        System.out.println(asyncJmsTemplate.getConnectionFactory().hashCode());
//        System.out.println(jmsTemplate.getConnectionFactory().hashCode());

        ConnectionFactory connectionFactory = asyncJmsTemplate.getConnectionFactory();
        try (Connection connection = connectionFactory.createConnection()) {
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue("testQueue");
            JmsPoolMessageProducer producer = (JmsPoolMessageProducer) session.createProducer(queue);

            ActiveMQMessageProducer mqMessageProducer = (ActiveMQMessageProducer) producer.getMessageProducer();
            TextMessage msg = session.createTextMessage("testAsyncMsg");
            msg.setStringProperty("priority", "high");
            msg.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, 10 * 1000L);
            mqMessageProducer.send(queue, msg, new AsyncCallback() {
                @Override
                public void onSuccess() {
                    System.out.println("async done");
                }

                @Override
                public void onException(JMSException exception) {
                    System.out.println("async err");
                }
            });
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
