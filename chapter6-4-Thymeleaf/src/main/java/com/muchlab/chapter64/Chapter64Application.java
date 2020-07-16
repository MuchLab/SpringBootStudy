package com.muchlab.chapter64;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.validation.constraints.Digits;

@SpringBootApplication
@EnableJpaRepositories
public class Chapter64Application {

    public static void main(String[] args) {
        SpringApplication.run(Chapter64Application.class, args);
    }

}
