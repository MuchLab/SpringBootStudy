package com.muchlab.chapter62.controller;

import com.muchlab.chapter62.controller.request.NewCoffeeRequest;
import com.muchlab.chapter62.model.Coffee;
import com.muchlab.chapter62.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/coffee")
public class CoffeeController {
    @Autowired
    private CoffeeService coffeeService;

    @GetMapping(params = "!name")
    @ResponseBody
    public List<Coffee> getAll(){
        return coffeeService.getAllCoffee();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Coffee getById(@PathVariable Long id){
        Coffee coffee = coffeeService.getCoffee(id);
        return coffee;
    }

    @GetMapping(params = "name")
    @ResponseBody
    public Coffee getByName(@RequestParam String name){return coffeeService.getCoffee(name);}

    /**
     * 创建Coffee
     * @param newCoffee
     * @param result
     * @return
     */
    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Coffee addCoffee(@Valid NewCoffeeRequest newCoffee,
                            BindingResult result){
        if (result.hasErrors()){
            log.warn("Binding Errors:{}", result);
            return null;
        }
        return coffeeService.saveCoffee(newCoffee.getName(), newCoffee.getPrice());
    }

//    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @ResponseBody
//    @ResponseStatus(HttpStatus.CREATED)
//    public List<Coffee> batchAddCoffee(){
//
//    }
}
