package com.muchlab.chapter61.foo;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;

@Aspect
@Slf4j
public class FooAspect {
    /**
     * 以testBean前缀的Bean返回后调用
     */
    @AfterReturning("bean(testBean*)")
    public void printAfter(){
        log.info("after hello()");
    }
}
