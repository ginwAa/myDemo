package com.demo.service.impl;

import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.service.GenericException;
import org.apache.dubbo.rpc.service.GenericService;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

//@DubboService
public class YServiceImpl implements GenericService {
    @Override
    public Object $invoke(String method, String[] parameterTypes, Object[] args) throws GenericException {
        if (method.equals("getY")) {
            return "Y confirmed";
        }
        return null;
    }

    @Override
    public CompletableFuture<Object> $invokeAsync(String method, String[] parameterTypes, Object[] args) throws GenericException {
        return GenericService.super.$invokeAsync(method, parameterTypes, args);
    }
}
