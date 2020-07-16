package com.muchlab.chapter44.service;

import com.muchlab.chapter44.entity.Coffee;
import com.muchlab.chapter44.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

@Service
@Slf4j
@Transactional
public class CoffeeService {
    @Autowired
    private CoffeeRepository coffeeRepository;

    public Optional<Coffee> findOneCoffee(String name){
        //matcher表示查询的规则，exact表示需完全匹配，ignoreCase表示忽略大小写
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("name", exact().ignoreCase());
        Optional<Coffee> coffee = coffeeRepository.findOne(Example.of(
                Coffee.builder().name(name).build(),matcher));
        log.info("Coffee Found:{}", coffee);
        return coffee;
    }
}
