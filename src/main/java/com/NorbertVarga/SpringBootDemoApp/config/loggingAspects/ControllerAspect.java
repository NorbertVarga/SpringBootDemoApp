package com.NorbertVarga.SpringBootDemoApp.config.loggingAspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class ControllerAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerAspect.class);

    //AOP expression for which methods shall be intercepted
    @Around("execution(* com.NorbertVarga.SpringBootDemoApp.controller..*(..))")
    public Object profileAllMethods(ProceedingJoinPoint proceedingJoinPoint) throws Throwable
    {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        String endpoint = request.getRequestURI();
        //Get intercepted method details
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();

        Object result = proceedingJoinPoint.proceed();

        //Log method execution time
        LOGGER.info("Controller called: " + endpoint + " method:" + methodName);

        return result;
    }
}