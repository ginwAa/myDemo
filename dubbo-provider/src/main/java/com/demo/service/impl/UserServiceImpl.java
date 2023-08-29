package com.demo.service.impl;

import com.demo.entity.User;
import com.demo.service.UserService;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.service.GenericException;
import org.apache.dubbo.rpc.service.GenericService;
import org.springframework.stereotype.Service;

@DubboService(group = "testGroup")
public class UserServiceImpl implements UserService {
    public String getUserName() {
        return "23177777";
    }

    public User editUserName(User user, String name) {
        user.setName(name);
        return user;
    }

}
