package com.chapter42.demo;

import com.chapter42.demo.mapper.CoffeeMapper;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.chapter42.demo.mapper")
@Slf4j
@SpringBootApplication
public class DemoApplication implements CommandLineRunner {
    @Autowired
    private CoffeeMapper coffeeMapper;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Coffee c1 = Coffee.builder().name("coffee1")
                .price(Money.of(CurrencyUnit.of("CNY"), 20.0))
                .build();
        int count = coffeeMapper.save(c1);
        log.info("save {} Coffee:{}", count, c1);

        Coffee c2 = Coffee.builder().name("coffee2")
                .price(Money.of(CurrencyUnit.of("CNY"), 25.0))
                .build();
        count = coffeeMapper.save(c2);
        log.info("save {} Coffee:{}", count, c2);

        Coffee c3 = coffeeMapper.findById(c1.getId());
        log.info("Find Coffee:{}", c3);
    }
}
