package com.muchlab.chapter61;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class TestBean {
    private String context;

    public void hello(){
        log.info("hello "+context);
    }
}
