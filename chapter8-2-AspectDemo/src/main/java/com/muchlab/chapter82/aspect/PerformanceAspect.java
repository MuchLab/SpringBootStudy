package com.muchlab.chapter82.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class PerformanceAspect {
    @Around("repositoryOps()")
    public Object repoLogPerformance(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();
        String name = "-";
        String result = "Y";
        try {
            name = pjp.getSignature().toShortString();
            return pjp.proceed();
        }catch (Throwable t){
            result = "N";
            throw t;
        }finally {
            long endTime = System.currentTimeMillis();
            log.info("{};{};{}ms", name, result, endTime - startTime);
        }
    }
    @After("serviceOps()")
    public void serviceLogPerformance(JoinPoint jp){
        log.info("================================================");
    }
    @Pointcut("execution(* com.muchlab.chapter82.repository.CoffeeRepository.findOne(..))")
    public void repositoryOps(){

    }
    @Pointcut("execution(* com.muchlab.chapter82.service.*.*(..))")
    public void serviceOps(){

    }
}
