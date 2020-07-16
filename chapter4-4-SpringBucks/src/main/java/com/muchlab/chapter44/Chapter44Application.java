package com.muchlab.chapter44;

import com.muchlab.chapter44.entity.Coffee;
import com.muchlab.chapter44.entity.CoffeeOrder;
import com.muchlab.chapter44.entity.OrderState;
import com.muchlab.chapter44.repository.CoffeeRepository;
import com.muchlab.chapter44.service.CoffeeOrderService;
import com.muchlab.chapter44.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Optional;

@EnableJpaRepositories
@Slf4j
@EnableTransactionManagement
@SpringBootApplication
public class Chapter44Application implements ApplicationRunner {
    @Autowired
    private CoffeeRepository coffeeRepository;
    @Autowired
    private CoffeeService coffeeService;
    @Autowired
    private CoffeeOrderService orderService;

    public static void main(String[] args) {
        SpringApplication.run(Chapter44Application.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("All Coffee:{}", coffeeRepository.findAll());

        Optional<Coffee> latte = coffeeService.findOneCoffee("LAtte");
        if (latte.isPresent()) {
            CoffeeOrder order = orderService.createOrder("Li Lei", latte.get());
            log.info("Update INIT to PAID: {}", orderService.updateState(order, OrderState.PAID));
            log.info("Update PAID to INIT: {}", orderService.updateState(order, OrderState.INIT));
        }
    }
}
