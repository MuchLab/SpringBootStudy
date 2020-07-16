package com.muchlab.chapter64.controller;

import com.muchlab.chapter64.controller.request.NewOrderRequest;
import com.muchlab.chapter64.model.Coffee;
import com.muchlab.chapter64.model.CoffeeOrder;
import com.muchlab.chapter64.service.CoffeeOrderService;
import com.muchlab.chapter64.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.awt.*;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/order")
public class CoffeeOrderController {
    @Autowired
    private CoffeeOrderService orderService;
    @Autowired
    private CoffeeService coffeeService;

    @ModelAttribute
    public List<Coffee> coffeeList(){
        return coffeeService.getAllCoffee();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public CoffeeOrder getOrder(@PathVariable("id") Long id){
        return orderService.get(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public CoffeeOrder create(@RequestBody NewOrderRequest newOrder){
        log.info("Receive new Order{}", newOrder);
        Coffee[] coffees = coffeeService.getCoffeeByName(newOrder.getItems())
                .toArray(new Coffee[]{});
        return orderService.createOrder(newOrder.getCustomer(), coffees);
    }

    @GetMapping
    public ModelAndView showCreateForm(){
        return new ModelAndView("create-order-form");
    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String createOrder(@Valid NewOrderRequest newOrder,
                              BindingResult result, ModelMap map){
        if (result.hasErrors()){
            log.warn("Binding Result:{}", result);
            map.addAttribute("message", result.toString());
            return "create-order-form";
        }
        log.info("Receive new Order:{}", newOrder);
        Coffee[] coffees = coffeeService.getCoffeeByName(newOrder.getItems())
                .toArray(new Coffee[]{});
        CoffeeOrder order = orderService.createOrder(newOrder.getCustomer(), coffees);
        return "redirect:/order/"+order.getId();
    }

}
