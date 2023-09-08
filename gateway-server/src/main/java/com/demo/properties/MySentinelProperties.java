package com.demo.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Configuration
@Data
@ConfigurationProperties(
        prefix = "csp.sentinel"
)
public class MySentinelProperties {

    Boolean eager = false;
    Log log;
    Transport transport;
    String gatewayName = "gateway";
    public MySentinelProperties() {
        log = new Log();
        transport = new Transport();
    }

    @PostConstruct
    public void configure() {
        System.setProperty("csp.sentinel.dashboard.server", transport.dashboard);
        System.setProperty("csp.sentinel.log.dir", log.dir);
        System.setProperty("csp.sentinel.app.type", "1");
        System.setProperty("csp.sentinel.heartbeat.interval.ms", transport.heartbeatIntervalMs);
        System.setProperty("project.name", gatewayName);
        System.setProperty("spring.cloud.sentinel.eager", eager.toString());
    }

    @Data
    public static class Log {
        private String dir;
    }

    @Data
    public static class Transport {
        private String dashboard = "localhost:8199";
        private String heartbeatIntervalMs = "10000";
    }
}


