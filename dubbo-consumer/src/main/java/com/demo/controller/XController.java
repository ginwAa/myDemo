package com.demo.controller;

import com.demo.entity.User;
import com.demo.result.Result;
import com.demo.service.UserService;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.rpc.service.GenericService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试相关接口
 */
@RestController
@RequestMapping("/X")
public class XController {
    /**
     * 标准服务
     */
    @DubboReference(group = "testGroup")
    private UserService userService;
    /**
     * 泛化服务
     */
//    @DubboReference(interfaceName = "com.demo.service.MyGenericService", version = "1.0")
//    private GenericService myGenericService;

    /**
     * 测试泛化调用修改功能
     * @param name
     * @return
     */
    @GetMapping("/Y/{name}")
    public Result<User> invokeUser(@PathVariable String name) {
//        ReferenceConfig<GenericService> referenceConfig = new ReferenceConfig<>();
//        referenceConfig.setInterface("com.demo.service.UserService");
//        referenceConfig.setGeneric(true);
//        GenericService userService = referenceConfig.get();

//        Map map = new HashMap<>();
//        map.put("id", 1L);
//        map.put("name", "NAME");
//        map.put("employeeId", 2L);
//        map.put("address", "ADDR");
//        map.put("phone", "13822222222");
//        map = (Map) userService.$invoke("editUserName", new String[]{User.class.getName(), String.class.getName()}, new Object[]{map, name});
        User user = userService.editUserName(new User(1L, "oldname", 2L, "ADDR", "13822222222"), name);
        //        RpcContext.getServerContext().setAttachment("key", "value");

//        User user = new User((Long) map.get("id"), (String) map.get("name"), (Long) map.get("employeeId"), (String) map.get("address"), (String) map.get("phone"));
        return Result.success(user);
    }

    /**
     * 测试泛化调用读取功能
     * @return
     */
    @GetMapping("/Y")
    public Result<String> getY() {
        ReferenceConfig<GenericService> referenceConfig = new ReferenceConfig<GenericService>();
        referenceConfig.setInterface("com.demo.service.MyGenericService");
        referenceConfig.setVersion("1.0");
        referenceConfig.setGeneric(true);
        GenericService myGenericService = referenceConfig.get();
//        String name = (String) userService.$invoke("getUserName", new String[]{}, new Object[]{});
        String name = userService.getUserName();
        Object result = myGenericService.$invoke("getY", new String[]{name.getClass().getTypeName()}, new Object[] {name});

        return Result.success(result.toString());
    }
}
