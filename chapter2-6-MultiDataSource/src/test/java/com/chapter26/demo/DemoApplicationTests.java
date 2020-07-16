package com.chapter26.demo;

import com.chapter26.demo.primary.User;
import com.chapter26.demo.primary.UserRepository;
import com.chapter26.demo.secondary.User1;
import com.chapter26.demo.secondary.UserRepository1;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class DemoApplicationTests {
    @Autowired
    private JdbcTemplate primaryJdbcTemplate;
    @Autowired
    private JdbcTemplate secondaryJdbcTemplate;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRepository1 userRepository1;

    @Test
    @Rollback
    public void test1() {
        primaryJdbcTemplate.update("insert into user (name, age) values (?,?)", "aaa", 20);
        primaryJdbcTemplate.update("insert into user (name, age) values (?,?)", "bbb", 30);
        // 往第二个数据源中插入 1 条数据，若插入的是第一个数据源，则会主键冲突报错
        secondaryJdbcTemplate.update("insert into user(name,age) values(?, ?)", "ccc", 20);

        // 查一下第一个数据源中是否有 2 条数据，验证插入是否成功
        Assert.assertEquals("2", primaryJdbcTemplate.queryForObject("select count(1) from user", String.class));

        // 查一下第一个数据源中是否有 1 条数据，验证插入是否成功
        Assert.assertEquals("1", secondaryJdbcTemplate.queryForObject("select count(1) from user", String.class));
    }
    @Test
    @Rollback
    public void test2(){
        userRepository.save(new User("aaa", 10));
        userRepository.save(new User("bbb", 20));
        userRepository.save(new User("ccc", 30));
        userRepository.save(new User("ddd", 40));
        userRepository.save(new User("eee", 50));

        Assert.assertEquals(5, userRepository.findAll().size());

        userRepository1.save(new User1("ccc", 30));
        userRepository1.save(new User1("ddd", 40));
        userRepository1.save(new User1("eee", 50));

        Assert.assertEquals(3, userRepository1.findAll().size());
    }
}
