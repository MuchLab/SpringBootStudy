package com.muchlab.chapter64.controller;

import com.muchlab.chapter64.controller.exception.FormValidationException;
import com.muchlab.chapter64.controller.request.NewCoffeeRequest;
import com.muchlab.chapter64.model.Coffee;
import com.muchlab.chapter64.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/coffee")
@Slf4j
public class CoffeeController {
    @Autowired
    private CoffeeService coffeeService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Coffee> addJsonCoffee(@RequestBody @Valid NewCoffeeRequest newCoffee, BindingResult result){
        if (result.hasErrors()){
            log.warn("errors:{}", result);
            throw new ValidationException(result.toString());
        }
        Coffee coffee = coffeeService.saveCoffee(newCoffee.getName(), newCoffee.getPrice());
        return new ResponseEntity<Coffee>(coffee, HttpStatus.CREATED);
    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Coffee addCoffee(@Valid NewCoffeeRequest newCoffee, BindingResult result){
        if (result.hasErrors()){
            log.warn("errors:{}", result);
            throw new FormValidationException(result);
        }
        return coffeeService.saveCoffee(newCoffee.getName(), newCoffee.getPrice());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public List<Coffee> batchAddCoffee(
            @RequestParam("file")MultipartFile file){
        List<Coffee> coffees = new ArrayList<>();
        if (!file.isEmpty()){
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(
                        new InputStreamReader(file.getInputStream()));
                String str;
                while((str = reader.readLine())!=null){
                    String[] arr = StringUtils.split(str, " ");
                    if (arr !=null && arr.length==2){
                        coffees.add(coffeeService.saveCoffee(arr[0],
                                Money.of(CurrencyUnit.of("CNY"),
                                        NumberUtils.createBigDecimal(arr[1]))));
                    }
                }
            } catch (IOException e) {
                log.error("exception", e);
            } finally {
                IOUtils.closeQuietly(reader);
            }
        }
        return coffees;
    }

    @GetMapping(params = "!name")
    @ResponseBody
    public List<Coffee> getAll(){
        return coffeeService.getAllCoffee();
    }

    @GetMapping(path = "/{id}")
    @ResponseBody
    public ResponseEntity<Coffee> getById(@PathVariable Long id){
        Coffee coffee = coffeeService.getCoffee(id);
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(coffee);
    }

    @GetMapping(params = {"name"})
    @ResponseBody
    public Coffee getByName(@RequestParam String name){
        return coffeeService.getCoffee(name);
    }
}
