package com.muchlab.chapter62.service;


import com.muchlab.chapter62.model.Coffee;
import com.muchlab.chapter62.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

@Service
@Slf4j
@CacheConfig(cacheNames = "CoffeeCache")
public class CoffeeService {
    @Autowired
    private CoffeeRepository coffeeRepository;

//    public Optional<Coffee> findOneCoffee(String name){
//        //matcher表示查询的规则，exact表示需完全匹配，ignoreCase表示忽略大小写
//        ExampleMatcher matcher = ExampleMatcher.matching()
//                .withMatcher("name", exact().ignoreCase());
//        Optional<Coffee> coffee = coffeeRepository.findOne(Example.of(
//                Coffee.builder().name(name).build(),matcher));
//        log.info("Coffee Found:{}", coffee);
//        return coffee;
//    }
    public List<Coffee> getAllCoffee(){return coffeeRepository.findAll(Sort.by("id"));}

    public Coffee getCoffee(Long id){return coffeeRepository.getOne(id);}

    public List<Coffee> getCoffeeByName(List<String> names){return coffeeRepository.findByNameInOrderById(names);}

    public Coffee getCoffee(String name) {
        return coffeeRepository.findByName(name);
    }

    public Coffee saveCoffee(String name, Money price){
        return coffeeRepository.save(Coffee.builder().name(name).price(price).build());
    }
}
