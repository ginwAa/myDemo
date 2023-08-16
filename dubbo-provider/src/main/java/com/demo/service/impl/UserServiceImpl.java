package com.demo.service.impl;

import com.demo.service.UserService;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class UserServiceImpl implements UserService {

    public String getUser() {
        return new String("23177777");
    }


}
