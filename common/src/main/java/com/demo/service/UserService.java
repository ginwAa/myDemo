package com.demo.service;

import com.demo.entity.User;

public interface UserService {
    String getUser();
    User editUserName(User user, String name);
}
