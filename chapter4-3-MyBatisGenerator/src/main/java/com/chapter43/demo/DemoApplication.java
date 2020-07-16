package com.chapter43.demo;

import com.chapter43.demo.entity.Coffee;
import com.chapter43.demo.entity.CoffeeExample;
import com.chapter43.demo.mapper.CoffeeMapper;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;
import java.util.List;

@SpringBootApplication
@Slf4j
@MapperScan("com.chapter43.demo.mapper")
public class DemoApplication implements CommandLineRunner {
    @Autowired
    private CoffeeMapper coffeeMapper;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        playWithArtifacts();
    }

    private void playWithArtifacts() {
        Coffee coffee1 = new Coffee()
                .withName("coffee1")
                .withPrice(Money.of(CurrencyUnit.of("CNY"), 20.0))
                .withCreateTime(new Date())
                .withUpdateTime(new Date());
        coffeeMapper.insert(coffee1);

        Coffee coffee2 = new Coffee()
                .withName("coffee2")
                .withPrice(Money.of(CurrencyUnit.of("CNY"), 30.0))
                .withCreateTime(new Date())
                .withUpdateTime(new Date());
        coffeeMapper.insert(coffee2);

        CoffeeExample example = new CoffeeExample();
        example.createCriteria().andNameEqualTo("coffee2");
        List<Coffee> list = coffeeMapper.selectByExample(example);
        list.forEach(e->log.info("selectByExample:{}", e));
    }
}
