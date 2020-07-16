package com.chapter22.demo.service;

import com.chapter22.demo.domain.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private JdbcTemplate jdbcTemplate;

    public UserServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> getAll() {
        List<User> users = jdbcTemplate.query("select NAME, AGE from USER", (resultSet, i) -> {
            User user = new User();
            user.setName(resultSet.getString("NAME"));
            user.setAge(resultSet.getInt("AGE"));
            return user;
        });
        return users;
    }

    @Override
    public List<User> getByName(String name) {
        String sql = "select name, age from user";
        RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
        List<User> users = jdbcTemplate.query(sql, rowMapper);
        return users;
    }

    @Override
    public void createUser(User user) {
        String sql = "insert into user(name, age) values(?,?)";
        jdbcTemplate.update(sql, new Object[]{user.getName(), user.getAge()});
    }

    @Override
    public void deleteUser(String name) {
        String sql = "delete from user where name=?";
        jdbcTemplate.update(sql, name);
    }

    @Override
    public void updateUser(String name, User user) {
        String sql = "update user set name=?, age=? where name=?";
        jdbcTemplate.update(sql, new Object[]{user.getName(), user.getAge(), name});
    }
}
