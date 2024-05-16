package com.cookbook.cookbook.aop;

import lombok.Synchronized;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import com.cookbook.cookbook.service.CounterService;

@Aspect
@Component
@Log4j2
public class AspectLogger {
    @Before("execution(* com.cookbook.cookbook.service.CategoryService.*(..))"
            + " || execution(* com.cookbook.cookbook.service.IngredientService.*(..))"
            + " || execution(* com.cookbook.cookbook.service.RecipeService.*(..))")
    public final void logBeforeServiceCommand(final JoinPoint joinPoint) {
        CounterService requestCounter = null;
        log.info(() -> String.format("Running method %s with args %s; Current count of requests: %d", joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs()), requestCounter.increment()));
    }

    @AfterReturning(pointcut = "execution(* com.cookbook.cookbook.service.CategoryService.*(..))"
            + " || execution(* com.cookbook.cookbook.service.IngredientService.*(..))"
            + " || execution(* com.cookbook.cookbook.service.RecipeService.*(..))")
    public final void logAfterServiceCommand(final JoinPoint joinPoint) {
        log.info(() -> String.format("Result of %s: success ", joinPoint.getSignature().getName()));
    }

    @AfterThrowing(pointcut = "execution(* com.cookbook.cookbook.service.*.*(..))", throwing = "exception")
    public final void logAfterError(final JoinPoint joinPoint, final Exception exception) {
        log.error(
                () -> String.format("Error while running %s with args %s: %s", joinPoint.getSignature().getName(),
                        Arrays.toString(joinPoint.getArgs()), exception.getMessage()));
    }
}
