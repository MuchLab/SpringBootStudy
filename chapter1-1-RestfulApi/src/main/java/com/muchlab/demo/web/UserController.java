package com.muchlab.demo.web;

import com.muchlab.demo.domain.User;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    static Map<Long, User> users = Collections.synchronizedMap(new HashMap<>());
    @GetMapping("/")
    public List<User> getUserList(){
        ArrayList<User> r = new ArrayList<>(UserController.users.values());
        return r;
    }
    @PostMapping("/")
    public String postUser(@RequestBody User user){
        users.put(user.getId(), user);
        return "success";
    }
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id){
        return users.get(id);
    }
    @PutMapping("/{id}")
    public String putUser(@PathVariable Long id, @RequestBody User user){
        User u = users.get(id);
        u.setName(user.getName());
        u.setAge(user.getAge());
        users.put(id, u);
        return "success";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id){
        users.remove(id);
        return "success";
    }
}
