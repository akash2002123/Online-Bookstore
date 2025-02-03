package com.example.demo.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Aspect for logging execution of service methods in the OrderService. Uses
 * Spring AOP.
 */
@Aspect
@Component
public class LoggingAspect {

	private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

	/**
	 * Pointcut that matches all methods in the OrderServiceImpl class.
	 */
	@Pointcut("execution(* com.cts.teams.service.OrderServiceImpl.*(..))")
	public void serviceMethods() {
		// Pointcut for methods in OrderServiceImpl
	}

	/**
	 * Advice that logs methods before their execution.
	 * 
	 * @param joinPoint join point for advice
	 */
	@Before("serviceMethods()")
	public void logBefore(JoinPoint joinPoint) {
		if (logger.isInfoEnabled()) {
			logger.info("A method in OrderService is about to be executed. Method: {}, Arguments: {}",
					joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
		}
	}

	/**
	 * Advice that logs methods after their successful execution.
	 * 
	 * @param joinPoint join point for advice
	 * @param result    the result of the method execution
	 */
	@AfterReturning(pointcut = "serviceMethods()", returning = "result")
	public void logAfterReturning(JoinPoint joinPoint, Object result) {
		logger.info("A method in OrderService has executed successfully. Method: {}, Result: {}",
				joinPoint.getSignature().getName(), result);
	}

	/**
	 * Advice that logs methods after an exception is thrown.
	 * 
	 * @param joinPoint join point for advice
	 * @param error     the exception thrown
	 */
	@AfterThrowing(pointcut = "serviceMethods()", throwing = "error")
	public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
		logger.error("An error occurred in OrderService. Method: {}, Error: {}", joinPoint.getSignature().getName(),
				error.getMessage());
	}
}