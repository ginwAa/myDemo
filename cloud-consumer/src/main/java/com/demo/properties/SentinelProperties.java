package com.demo.properties;

import com.demo.config.SentinelConfiguration;
import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "spring.cloud.sentinel")
@Component
@Data
public class SentinelProperties {
    public Long qpsLimitation;

}
