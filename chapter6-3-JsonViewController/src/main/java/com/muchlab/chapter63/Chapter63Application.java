package com.muchlab.chapter63;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class Chapter63Application {

    public static void main(String[] args) {
        SpringApplication.run(Chapter63Application.class, args);
    }
    @Bean
    public Hibernate5Module hibernate5Module(){
        return new Hibernate5Module();
    }
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer(){
        //将json数据进行缩进处理
        return builder->builder.indentOutput(true);
    }
}
