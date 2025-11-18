package com.example.skilltracker.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Pointcut("execution(* com.example.skilltracker.service..*(..))")
    public void anyServiceMethod() {}

    @Pointcut("execution(* com.example.skilltracker.service.log.ErrorLogCleanupService.cleanupOldLogs(..))")
    public void errorLogCleanupMethod() {}

    @Before("anyServiceMethod()")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println(
                "➡️ Calling: " +
                        joinPoint.getSignature().toShortString() +
                        " args=" + java.util.Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(pointcut = "anyServiceMethod()", returning = "result")
    public void logAfter(JoinPoint joinPoint, Object result) {
        System.out.println(
                "✅ Finished: " +
                        joinPoint.getSignature().toShortString() +
                        " return=" + result);
    }

    @AfterThrowing(pointcut = "anyServiceMethod()", throwing = "ex")
    public void logException(JoinPoint joinPoint, Throwable ex) {
        System.out.println(
                "Exception in: " +
                        joinPoint.getSignature().toShortString() +
                        " message=" + ex.getMessage());
    }

    @Around("anyServiceMethod()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        try {
            return joinPoint.proceed();
        } finally {
            long ms = System.currentTimeMillis() - start;
            System.out.println("⏱ " +
                    joinPoint.getSignature().toShortString() +
                    " took " + ms + "ms");
        }
    }

    @Before("errorLogCleanupMethod()")
    public void logBeforeCleanup(JoinPoint joinPoint) {
        System.out.println("Starting logs clean-up...");
    }

    @After("errorLogCleanupMethod()")
    public void logAfterCleanup(JoinPoint joinPoint) {
        System.out.println("Log clean-up is finished!");
    }
}
