package com.demo.service.impl;

import com.demo.service.UserService;
import com.demo.service.XService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;


@Service
public class XServiceImpl implements XService {

    @DubboReference
    private UserService userService;
    @Override
    public String getX() {
        String username = userService.getUser();
        return username + " X confirmed";
    }


}
