package com.chapter25.demo;

import com.chapter25.demo.domain.User;
import com.chapter25.demo.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class DemoApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    @Rollback
    public void test(){
        userMapper.insert("AAA", 20);
        User user = userMapper.findByName("AAA");
        Assert.assertEquals(20, user.getAge().intValue());
    }

}
