package com.Files.Order.Logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

 import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Aspect
@Component
public class LoggerFile {

    private static final Logger log = LoggerFactory.getLogger(LoggerFile.class);

    // Define a pointcut for all methods in specified packages
    @Pointcut(value = "execution(* com.Files.Order.*.*.*(..))")
    public void myPointCut() {
        // Pointcut for all methods in CartModel sub-packages
    }

    @Around("myPointCut()")
    public Object applicationLogger(ProceedingJoinPoint pjp) throws Throwable {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerModule(new Jdk8Module()); // To handle Java 8 Optional and other types

        String methodName = pjp.getSignature().getName();
        String className = pjp.getTarget().getClass().getSimpleName();
        Object[] arguments = pjp.getArgs();

        String serializedArguments;
        try {
            serializedArguments = objectMapper.writeValueAsString(arguments);
        } catch (Exception e) {
            serializedArguments = "Unable to serialize arguments: " + e.getMessage();
        }

        log.info("Method invoked: {}.{}() with arguments: {}", className, methodName, serializedArguments);

        long startTime = System.currentTimeMillis();
        Object response;
        try {
            // Proceed to the actual method
            response = pjp.proceed();
        } catch (Throwable throwable) {
            log.error("Exception in {}.{}(): {}", className, methodName, throwable.getMessage(), throwable);
            throw throwable;
        }
        long elapsedTime = System.currentTimeMillis() - startTime;

        String serializedResponse;
        try {
            serializedResponse = objectMapper.writeValueAsString(response);
        } catch (Exception e) {
            serializedResponse = "Unable to serialize response: " + e.getMessage();
        }

        log.info("Response from {}.{}(): {} | Execution time: {} ms", className, methodName, serializedResponse, elapsedTime);

        return response;
    }
}
