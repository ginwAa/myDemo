package com.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
public class ConsumerService {
    @Autowired
    private RestTemplate restTemplate;

//    public static String blockHandlerForConsume(LocalDateTime time, BlockException e) {
//        log.info(time + " consumerService Blocked " + e.getMessage());
//        return "BLOCK WARNING!";
//    }
//
//    public static String exceptionHandlerForConsume(LocalDateTime time, Exception e) {
//        log.info(time + " consumerService Error " + e.getMessage());
//        return "UNKNOWN ERROR WARNING!";
//    }

    public String consume(LocalDateTime time) {
        String providerMsg = restTemplate.getForObject("http://provider-service/provider/test", String.class);
        return "consume: " + providerMsg;
    }


}
