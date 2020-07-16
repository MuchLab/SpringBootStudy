package com.muchlab.chapter64.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ValidationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public Map<String, String> validationExceptionHandler(ValidationException exception){
        Map<String, String > map = new HashMap<>();
        map.put("message", exception.getMessage());
        System.out.println(exception.getMessage());
        return map;
    }
}
