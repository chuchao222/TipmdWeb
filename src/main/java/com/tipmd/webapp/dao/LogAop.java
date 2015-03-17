package com.tipmd.webapp.dao;

import java.text.DecimalFormat;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class LogAop {
	protected static final Logger log = Logger.getLogger(LogAop.class);
	@Around("execution(* com.tipmd.webapp.dao.impl.*.*(..))")
	public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable{
		log.debug("enter " + joinPoint.getSignature().getName());
		long startTime = System.nanoTime();

		Object retVal = joinPoint.proceed();
		long consumingTime = System.nanoTime() - startTime;
		
		if (consumingTime > 100000)
			log.debug(" (uses "+ new DecimalFormat(",###").format(consumingTime/1000000)+" ms)");
		else
			log.debug(" (uses "+ new DecimalFormat(",###").format(consumingTime)+" ns)");

		log.debug("Leave "+ joinPoint.getSignature().getName());
		return retVal;
	}
}
