package com.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ConsumerService {
    @Autowired
    private RestTemplate restTemplate;
    public String consume() {
        String providerMsg = restTemplate.getForObject("http://provider-service/provider/test", String.class);
        return "consume: " + providerMsg;
    }
}
