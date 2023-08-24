package com.demo.producer.config;

import com.sun.jndi.ldap.pool.PooledConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.messaginghub.pooled.jms.JmsPoolConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.JmsPoolConnectionFactoryFactory;
import org.springframework.boot.autoconfigure.jms.JmsPoolConnectionFactoryProperties;
import org.springframework.boot.autoconfigure.jms.JmsProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.*;
import java.time.Duration;


@Configuration
@EnableJms
public class JmsConfiguration {
    @Value("${spring.activemq.pool.max-sessions-per-connection}")
    private Integer MAX_SESSIONS_PER_CONNECTION;

    @Value("${spring.activemq.pool.max-connections}")
    private Integer MAX_CONNECTIONS;

    @Value("${spring.activemq.pool.idle-timeout}")
    private Integer IDLE_TIMEOUT_SECOND;

    @Value("${spring.activemq.pool.block-if-full}")
    private Boolean BLOCK_IF_FULL;
    @Value("${spring.activemq.pool.block-if-full-timeout}")
    private Integer BLOCK_IF_FULL_TIMEOUT;
    @Bean(name = "asyncJmsMessagingTemplate")
    public JmsMessagingTemplate asyncJmsMessagingTemplate(@Qualifier("asyncJmsTemplate") JmsTemplate asyncJmsTemplate) {
        return new JmsMessagingTemplate(asyncJmsTemplate);
    }

    @Bean(name = "asyncJmsTemplate")
    public JmsTemplate asyncJmsTemplate(@Qualifier("asyncPoolConnectionFactory") JmsPoolConnectionFactory poolConnectionFactory) {
        JmsTemplate template = new JmsTemplate(poolConnectionFactory);
        template.setExplicitQosEnabled(true);
        return template;
    }

    @Bean(name = "syncJmsTemplate")
    public JmsTemplate syncJmsTemplate(@Qualifier("syncPoolConnectionFactory") JmsPoolConnectionFactory poolConnectionFactory) {
        JmsTemplate template = new JmsTemplate(poolConnectionFactory);
        return template;
    }

    // TODO prefetch
    @Bean(name = "syncPoolConnectionFactory")
    public JmsPoolConnectionFactory syncPoolConnectionFactory(JmsPoolConnectionFactoryFactory poolConnectionFactoryFactory) {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://172.27.23.85:61616");
        connectionFactory.setUseAsyncSend(false);
        connectionFactory.setAlwaysSyncSend(false);


        return poolConnectionFactoryFactory.createPooledConnectionFactory(connectionFactory);
    }

    @Bean(name = "asyncPoolConnectionFactory")
    public JmsPoolConnectionFactory asyncPoolConnectionFactory(JmsPoolConnectionFactoryFactory poolConnectionFactoryFactory) {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://172.27.23.85:61616");
        connectionFactory.setUseAsyncSend(true);
        connectionFactory.setAlwaysSyncSend(true);
        return poolConnectionFactoryFactory.createPooledConnectionFactory(connectionFactory);
    }
    @Bean
    public JmsPoolConnectionFactoryFactory poolConnectionFactoryFactory() {
        JmsPoolConnectionFactoryProperties properties = new JmsPoolConnectionFactoryProperties();
        properties.setEnabled(true);
        properties.setBlockIfFull(BLOCK_IF_FULL);
        properties.setBlockIfFullTimeout(Duration.ofSeconds(BLOCK_IF_FULL_TIMEOUT));
        properties.setMaxConnections(MAX_CONNECTIONS);
        properties.setIdleTimeout(Duration.ofSeconds(IDLE_TIMEOUT_SECOND));
        properties.setMaxSessionsPerConnection(MAX_SESSIONS_PER_CONNECTION);
        return new JmsPoolConnectionFactoryFactory(properties);
    }

}
