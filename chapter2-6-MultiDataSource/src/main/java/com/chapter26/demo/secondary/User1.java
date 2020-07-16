package com.chapter26.demo.secondary;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

//标识了User类是一个持久化的实体
@Entity
@Data
@NoArgsConstructor
public class User1 {
    //标识User对应对应数据库表中的主键
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private  Integer age;

    public User1(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
}
