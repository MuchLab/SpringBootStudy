package com.muchlab.chapter63.controller;

import com.muchlab.chapter63.controller.request.NewCoffeeRequest;
import com.muchlab.chapter63.controller.request.NewOrderRequest;
import com.muchlab.chapter63.model.Coffee;
import com.muchlab.chapter63.model.CoffeeOrder;
import com.muchlab.chapter63.service.CoffeeOrderService;
import com.muchlab.chapter63.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/order")
@Slf4j
public class CoffeeOrderController {
    @Autowired
    private CoffeeOrderService orderService;
    @Autowired
    private CoffeeService coffeeService;

    @GetMapping("/{id}")
    public CoffeeOrder getOrder(@PathVariable Long id){
        return orderService.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CoffeeOrder create(@RequestBody NewOrderRequest newOrder){
        Coffee[] coffees = coffeeService.getCoffeeByName(newOrder.getItems()).toArray(new Coffee[]{});

        return orderService.createOrder(newOrder.getCustomer(),coffees);

    }
}
