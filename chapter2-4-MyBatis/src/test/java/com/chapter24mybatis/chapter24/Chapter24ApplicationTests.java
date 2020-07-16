package com.chapter24mybatis.chapter24;

import com.chapter24mybatis.chapter24.domain.User;
import com.chapter24mybatis.chapter24.domain.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
class Chapter24ApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    @Rollback
    public void test(){
        userMapper.insert("AAA", 20);
        User user = userMapper.findByName("AAA");
        Assert.assertEquals(20, user.getAge().intValue());
        // update一条数据，并select出来验证
        user.setAge(30);
        userMapper.update(user);
        user= userMapper.findByName("AAA");
        Assert.assertEquals(30, user.getAge().intValue());
        // 删除这条数据，并select验证
        userMapper.delete(user.getId());
        user = userMapper.findByName("AAA");
        Assert.assertEquals(null, user);
        List<User> users = userMapper.findAll();
        log.info(users.toString());
        for (User u :
                users) {
            Assert.assertEquals(null, u.getId());
            Assert.assertNotEquals(null, u.getName());
        }
    }

}
