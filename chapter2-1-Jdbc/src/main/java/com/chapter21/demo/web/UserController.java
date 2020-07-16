package com.chapter21.demo.web;

import com.chapter21.demo.domain.User;
import com.chapter21.demo.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public List<User> index(){
        List<User> allUsers = userService.getAllUsers();
        return allUsers;
    }
    @PostMapping("")
    public void createUser(@RequestBody User user){
        userService.create(user);
    }
    @GetMapping("/{name}")
    public List<User> getByName(@PathVariable String name){
        List<User> users = userService.getByName(name);
        return users;
    }
    @DeleteMapping("/{name}")
    public void deleteUser(@PathVariable String name){
        userService.deleteByName(name);
    }
}
