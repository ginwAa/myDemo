package com.demo.consumer.listener;

import org.apache.activemq.Message;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TestListener {
    /**
     * queue监听1
     * @param msg
     */
    @JmsListener(destination = "testQueue", containerFactory = "topicListenerFactory", selector = "priority = 'high'")
    public void receiveTopic(Message msg) {
        System.out.println(Thread.currentThread() + ": " + LocalDateTime.now() + " -> topicReceiver1 from topic:" + msg);
    }

    /**
     * queue监听2
     * @param msg
     */
    @JmsListener(destination = "testQueue", containerFactory = "topicListenerFactory", selector = "priority = 'high'")
    public void receiveTopic2(Message msg) {
        System.out.println(Thread.currentThread() + ": " + LocalDateTime.now() + " -> topicReceive2 from topic:" + msg);
    }

    /**
     * topic监听1
     * @param msg
     */
    @JmsListener(destination = "testQueue", containerFactory = "queueListenerFactory", concurrency = "3-10", selector = "priority = 'high'")
    public void receiveQueue(Message msg) {
        System.out.println(Thread.currentThread() + ": " + LocalDateTime.now() + " -> queueReceiver1 from queue:" + msg);
    }

    /**
     * topic监听2
     * @param msg
     */
    @JmsListener(destination = "testQueue", containerFactory = "queueListenerFactory", concurrency = "3-10", selector = "priority = 'high'")
    public void receiveQueue2(Message msg) {
        System.out.println(Thread.currentThread() + ": " + LocalDateTime.now() + " -> queueReceive2 from queue:" + msg);
    }

//    @JmsListener(destination = "testQueue", containerFactory = "queueListenerFactory", concurrency = "3-10")
//    public void receiveQueueTransactional(Message msg, Session session) {
//        try {
//            System.out.println(Thread.currentThread() + ": " + LocalDateTime.now() + " -> Receive from queue:" + msg);
//            int i = 4/0;
//            session.commit();
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            try {
//                session.rollback();
//            } catch (JMSException ee) {
//                ee.printStackTrace();
//            }
//        }
//    }
//
//    @JmsListener(destination = "testQueue", containerFactory = "topicListenerFactory")
//    public void receiveTopicTransactional(Message msg, Session session) {
//        try {
//            System.out.println(Thread.currentThread() + ": " + LocalDateTime.now() + " -> Receive from topic:" + msg);
//            int i = 4/0;
//            session.commit();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            try {
//                session.rollback();
//            } catch (JMSException ee) {
//                ee.printStackTrace();
//            }
//        }
//    }
}
