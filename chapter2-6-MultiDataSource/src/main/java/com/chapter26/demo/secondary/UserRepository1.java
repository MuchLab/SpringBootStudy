package com.chapter26.demo.secondary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository1 extends JpaRepository<User1, Long> {

    User1 findByName(String name);

    User1 findByNameAndAge(String name, Integer age);

    @Query("from User1 u where u.name=:name")
    User1 findUser(@Param("name") String name);
}
