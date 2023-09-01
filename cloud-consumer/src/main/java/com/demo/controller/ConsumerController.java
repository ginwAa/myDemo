package com.demo.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.context.ContextUtil;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.demo.service.ConsumerService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/consumer")
public class ConsumerController {
    @Resource
    private ConsumerService consumerService;

    @RequestMapping("/test")
    @SentinelResource(value = "consumer/consume", blockHandler = "blockHandlerForConsume", blockHandlerClass = ConsumerService.class, fallback = "exceptionHandlerForConsume", fallbackClass = ConsumerService.class)
    public String test() {
        return consumerService.consume(LocalDateTime.now());
    }

    @RequestMapping("/test1")
    public String test1() {
        ContextUtil.enter("consumer/consume", "T");
        Entry entry = null;
        String res = null;
        try {
            entry = SphU.entry("consumer/consume");
            // TODO ...
            res = consumerService.consume(LocalDateTime.now());
            System.out.printf("Passed for resource %s, origin is %s%n", "consumer/consume", "T");
        } catch (BlockException ex) {
            System.err.printf("Blocked for resource %s, origin is %s%n", "consumer/consume", "T");
        } finally {
            if (entry != null) {
                entry.exit();
            }
            ContextUtil.exit();
        }
        return res;

    }
}
