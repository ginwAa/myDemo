package com.demo.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.service.GenericService;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.String;



@Slf4j

public class MyGenericServiceImpl implements GenericService {
    @Override
    public Object $invoke(String method, String[] parameterTypes, Object[] args) {
        log.info("LogHost {}", RpcContext.getServerContext().getRemoteHost());

        // 用反射获取方法参数类型
        Class<?>[] paramTypes = new Class[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; ++i) {
            try {
                paramTypes[i] = Class.forName(parameterTypes[i]);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        // 获取自己及对应方法
        Class<?> selfClass = this.getClass();
        Method selfMethod = null;
        try {
            selfMethod = selfClass.getMethod(method, paramTypes);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        //调用方法
        Object res = null;
        if (selfMethod != null) {
            try {
                res = selfMethod.invoke(this, args);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        return res;
    }

    public String getY(String args) {
        return args + " Y confirmed";
    }
}
