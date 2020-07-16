package com.chapter22.demo.web;

import com.chapter22.demo.domain.User;
import com.chapter22.demo.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private UserService userService;
    @GetMapping("")
    public List<User> index(){
        List<User> users = userService.getAll();
        return users;
    }
    @GetMapping("{name}")
    public List<User> getByName(@PathVariable String name){
        List<User> users = userService.getByName(name);
        return users;
    }
    @PostMapping("")
    public void createUser(@RequestBody User user){
        userService.createUser(user);
    }
    @PutMapping("{name}")
    public void putUser(@PathVariable String name, @RequestBody User user){
        userService.updateUser(name, user);
    }
    @DeleteMapping("{name}")
    public void deleteUser(@PathVariable String name){
        userService.deleteUser(name);
    }
}
