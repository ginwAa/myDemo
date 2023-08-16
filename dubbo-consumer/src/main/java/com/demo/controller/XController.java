package com.demo.controller;

import com.demo.result.Result;
import com.demo.service.XService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.ref.Reference;

@RestController
@RequestMapping("/X")
public class XController {
    @Autowired
    private XService xService;

    @GetMapping("/X")
    public Result getX() {
        return Result.success(xService.getX());
    }

    @GetMapping("/Y")
    public Result getY() {
        ReferenceConfig<GenericService> referenceConfig = new ReferenceConfig<GenericService>();
        referenceConfig.setInterface("com.demo.service.YService");
        referenceConfig.setVersion("1.0");
        referenceConfig.setGeneric(true);
        GenericService yService = referenceConfig.get();

        Object result = yService.$invoke("getY", new String[]{}, new Object[] {});

        return Result.success();
    }
}
