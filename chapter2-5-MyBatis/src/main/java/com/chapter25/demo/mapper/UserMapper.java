package com.chapter25.demo.mapper;

import com.chapter25.demo.domain.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    User findByName(@Param("name") String name);

    int insert(@Param("name") String name, @Param("age") Integer age);
}
