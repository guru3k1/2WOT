package com.cgasystems.BCP.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ControllerAspect {
    private static final Logger logger = LoggerFactory.getLogger(ControllerAspect.class);

    @Around("@annotation(ControllerLogger)")
    public Object controllerLogger(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        logger.info("Executing method {} with parameters name {} values {}", joinPoint.getSignature().getName(), signature.getParameterNames(), joinPoint.getArgs());
        Object executionResult = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - startTime;

        if(executionTime > 2000){
            logger.warn("Execution time of {} exceeded threshold with {} ms",joinPoint.getSignature().getName(),executionTime);
        }
        return executionResult;
    }

}
