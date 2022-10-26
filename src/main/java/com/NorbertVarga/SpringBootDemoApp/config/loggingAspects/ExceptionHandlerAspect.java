package com.NorbertVarga.SpringBootDemoApp.config.loggingAspects;

import com.NorbertVarga.SpringBootDemoApp.entity.userAccount.UserPrincipal;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Parameter;

@Aspect
@Component
public class ExceptionHandlerAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger("errorLog");

    @Around("execution(* com.NorbertVarga.SpringBootDemoApp.errorHandling.GlobalExceptionHandler..*(..))")
    public Object LogAroundException(ProceedingJoinPoint proceedingJoinPoint) throws Throwable
    {
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Exception ex = (Exception) proceedingJoinPoint.getArgs()[0];

        LOGGER.error("** EXCEPTION CATCH BY GLOBAL EXCEPTION HANDLER **", ex);
        return proceedingJoinPoint.proceed();
    }
}
