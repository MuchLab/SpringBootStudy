package com.chapter22.demo.service;

import com.chapter22.demo.domain.User;

import java.util.List;

public interface UserService {
    List<User> getAll();
    List<User> getByName(String name);
    void createUser(User user);
    void deleteUser(String name);
    void updateUser(String name, User user);
}
