package com.cgasystems.BCP.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Objects;

@Aspect
@Component
public class AuthorizationAspect {
    private static final Logger logger = LoggerFactory.getLogger(AuthorizationAspect.class);


    @Around("@annotation(Authorize)")
    public Object authorize(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        Collections.list(request.getHeaderNames()).forEach(header -> logger.info("Header: {}, Value: {}",header,request.getHeader(header)));
        return joinPoint.proceed();
    }
}
