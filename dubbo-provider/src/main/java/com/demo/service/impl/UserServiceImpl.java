package com.demo.service.impl;

import com.demo.entity.User;
import com.demo.service.UserService;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class UserServiceImpl implements UserService {

    public String getUser() {
        return new String("23177777");
    }

    public User editUserName(User user, String name) {
        user.setName(name);
        return user;
    }

}
