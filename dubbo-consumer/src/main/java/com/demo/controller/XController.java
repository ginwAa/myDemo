package com.demo.controller;

import com.demo.entity.User;
import com.demo.result.Result;
import com.demo.service.XService;
import org.apache.dubbo.common.utils.JsonUtils;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.ref.Reference;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/X")
public class XController {
    @Autowired
    private XService xService;

    @GetMapping("/X")
    public Result getX() {
        return Result.success(xService.getX());
    }

    @GetMapping("/x")
    public Result invokeX() {
        ReferenceConfig<GenericService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setInterface("com.demo.service.UserService");
        referenceConfig.setGeneric(true);
        GenericService userService = referenceConfig.get();
        Object user = userService.$invoke("getUser", new String[]{}, new Object[]{});
        return Result.success(user);
    }

    @GetMapping("/x/{name}")
    public Result<User> invokeUser(@PathVariable String name) {
        ReferenceConfig<GenericService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setInterface("com.demo.service.UserService");
        referenceConfig.setGeneric(true);
        GenericService userService = referenceConfig.get();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", 1L);
        map.put("name", "NAME");
        map.put("employeeId", 2L);
        map.put("address", "ADDR");
        map.put("phone", "13822222222");
        map = (Map) userService.$invoke("editUserName", new String[]{User.class.getName(), String.class.getName()}, new Object[]{map, name});
        User user = new User((Long) map.get("id"), (String) map.get("name"), (Long) map.get("employeeId"), (String) map.get("address"), (String) map.get("phone"));
        return Result.success(user);
    }

    @GetMapping("/Y")
    public Result getY() {
        ReferenceConfig<GenericService> referenceConfig = new ReferenceConfig<GenericService>();
        referenceConfig.setInterface("com.demo.service.YService");
        referenceConfig.setVersion("1.0");
        referenceConfig.setGeneric(true);
        GenericService yService = referenceConfig.get();
        Object result = yService.$invoke("getY", new String[]{}, new Object[] {});

        return Result.success(result.toString());
    }
}
