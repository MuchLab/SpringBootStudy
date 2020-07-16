package com.chapter42.demo.mapper;

import com.chapter42.demo.Coffee;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface CoffeeMapper {
    @Insert("insert into t_coffee(name, price, create_time, update_time)" +
            "values(#{name}, #{price}, now(), now())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int save(Coffee coffee);

    @Select("select * from t_coffee where id=#{id}")
    @Results(id="coffeeResult", value = {
            @Result(column = "id", property = "id", id = true),
            @Result(column = "create_time", property = "createTime")
    })
    Coffee findById(@Param("id") Long id);
}
