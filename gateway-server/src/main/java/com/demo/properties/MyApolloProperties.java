package com.demo.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@Data
@ConfigurationProperties(
        prefix = "apollo"
)
public class MyApolloProperties {
    String metaServerAddress;
    String appId;
    String cacheDir;
    String env;
    String cluster;
    String accessToken;

    public MyApolloProperties() {
        metaServerAddress = "http://localhost:8080";
        appId = "defaultApp";
        env = "dev";
        cluster = "default";
    }

    @PostConstruct
    public void configure() {
        System.setProperty("app.id", appId);
        System.setProperty("apollo.meta", metaServerAddress);
        if (cacheDir != null) {
            System.setProperty("apollo.cache-dir", cacheDir);
        }
        System.setProperty("env", env);
        System.setProperty("apollo.cluster", cluster);
        if (accessToken != null) {
            System.setProperty("apollo.access-key.secret", accessToken);
        }
    }

}
