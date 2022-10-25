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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Aspect
@Component
public class ControllerAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerAspect.class);

    //AOP expression for which methods shall be intercepted
    @Around("execution(* com.NorbertVarga.SpringBootDemoApp.controller..*(..))")
    public Object profileAllMethods(ProceedingJoinPoint proceedingJoinPoint) throws Throwable
    {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        String endpoint = request.getRequestURI();
        String ipAddress = request.getRemoteAddr();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail;
        String userFullName;

        if (auth.getPrincipal() != "anonymousUser") {
            UserPrincipal principal = (UserPrincipal) auth.getPrincipal();
            userEmail = principal.getEmail();
            userFullName = principal.getFirstName() + " " + principal.getLastName();
            LOGGER.info("** Controller called: " + endpoint + " FROM: " + userEmail + " " + userFullName + " " + ipAddress);
        } else {
            LOGGER.info("** Controller called by guest: " + endpoint + " FROM: " + ipAddress);
        }


        return proceedingJoinPoint.proceed();
    }
}