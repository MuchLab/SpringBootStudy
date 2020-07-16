package com.chapter41.demo;

import com.chapter41.demo.entities.Coffee;
import com.chapter41.demo.entities.CoffeeOrder;
import com.chapter41.demo.entities.OrderState;
import com.chapter41.demo.repositories.CoffeeOrderRepository;
import com.chapter41.demo.repositories.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@EnableJpaRepositories
@Slf4j
@SpringBootApplication
public class DemoApplication implements CommandLineRunner {
    @Autowired
    private CoffeeRepository coffeeRepository;
    @Autowired
    private CoffeeOrderRepository coffeeOrderRepository;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);

    }

    @Override
    //开启事务
    @Transactional
    public void run(String... args) throws Exception {
        initOrders();
        findOrders();
    }

    private void findOrders() {
        //使用Sort根据Id进行降序排序
        coffeeOrderRepository.findAll(Sort.by(Sort.Direction.DESC, "id"))
                .forEach(c->log.info("Loading:{}", c));
        List<CoffeeOrder> list = coffeeOrderRepository.findAllOrOrderById();
        log.info("findAllOrOrderById:{}", getJoinedOrderId(list));

        list = coffeeOrderRepository.findByCustomerOrderById("Li Lei");
        log.info("findByCustomerOrderById:{}", getJoinedOrderId(list));

        list.forEach(o->{
            log.info("Order:{}", o.getId());
            o.getItems().forEach(i->log.info("item{}", i));
        });
        list = coffeeOrderRepository.findByItems_Name("coffee2");
        log.info("findByItems_Name:{}", getJoinedOrderId(list));


    }

    private String getJoinedOrderId(List<CoffeeOrder> list) {
        return list.stream().map(o->o.getId().toString())
                .collect(Collectors.joining(","));
    }

    private void initOrders() {
        Coffee coffee1 = Coffee.builder().name("coffee1")
                .price(Money.of(CurrencyUnit.of("CNY"), 30.0))
                .build();
        coffeeRepository.save(coffee1);
        log.info("Coffee:{}", coffee1);
        Coffee coffee2 = Coffee.builder().name("coffee2")
                .price(Money.of(CurrencyUnit.of("CNY"), 20.0))
                .build();
        coffeeRepository.save(coffee2);
        log.info("Coffee:{}", coffee2);

        CoffeeOrder order = CoffeeOrder.builder()
                .customer("Li Lei")
                .items(Collections.singletonList(coffee1))
                .state(OrderState.INIT)
                .build();
        coffeeOrderRepository.save(order);
        log.info("Order:{}", order);

        order = CoffeeOrder.builder()
                .customer("Li Lei")
                .items(Arrays.asList(coffee1, coffee2))
                .state(OrderState.INIT)
                .build();
        coffeeOrderRepository.save(order);
        log.info("Order:{}", order);
    }
}
