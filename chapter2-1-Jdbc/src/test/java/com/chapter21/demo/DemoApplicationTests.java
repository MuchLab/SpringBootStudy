package com.chapter21.demo;

import com.chapter21.demo.domain.User;
import com.chapter21.demo.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class DemoApplicationTests {
    @Autowired
    private UserService userService;

    @Before
    public void setUp(){
        userService.deleteAllUsers();
    }
    @Test
    public void test(){
        userService.create(new User("Tom", 10));
        userService.create(new User("Mike", 11));
        userService.create(new User("Didispace", 30));
        userService.create(new User("Oscar", 21));
        userService.create(new User("Linda", 17));
        // 查询名为Oscar的用户，判断年龄是否匹配
        List<User> userList = userService.getByName("Oscar");
        Assert.assertEquals(21, userList.get(0).getAge().intValue());

        // 查数据库，应该有5个用户
        Assert.assertEquals(5, userService.getAllUsers());

        // 删除两个用户
        userService.deleteByName("Tom");
        userService.deleteByName("Mike");

        // 查数据库，应该有5个用户
        Assert.assertEquals(3, userService.getAllUsers());
    }
}
