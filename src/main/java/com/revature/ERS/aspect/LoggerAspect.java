package com.revature.ERS.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggerAspect.class);

    @Before(value = "execution(* com.revature.ERS.*.*.*(..))")
    public void beforeAdvice(JoinPoint joinPoint) {
        logger.info("Before method: {}", joinPoint.getSignature());
    }

    @After(value = "execution(* com.revature.ERS.*.*.*(..))")
    public void afterAdvice(JoinPoint joinPoint) {
        logger.info("After method: {}", joinPoint.getSignature());
    }

    @AfterThrowing(value = "execution(* com.revature.ERS.*.*.*(..))", throwing = "e")
    public void exceptionAdvice(JoinPoint joinPoint, Throwable e) {
        logger.warn("Exception: {}", e.getMessage());
    }

}
