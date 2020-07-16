package com.chapter21.demo.service;

import com.chapter21.demo.domain.User;

import java.util.List;

public interface UserService {
    int create(User user);
    List<User> getByName(String name);
    int deleteByName(String name);
    List<User> getAllUsers();
    int deleteAllUsers();
}
