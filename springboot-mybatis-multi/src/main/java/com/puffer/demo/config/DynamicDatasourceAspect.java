package com.puffer.demo.config;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DynamicDatasourceAspect {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Around("execution(* com.puffer.demo.mapper..*.*(..))")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		Signature signature = pjp.getSignature();
		MethodSignature methodSignature = (MethodSignature) signature;
		Method method = methodSignature.getMethod();
		boolean methodAnnotation = method.isAnnotationPresent(TargetDataSource.class);

		TargetDataSource targetDataSource = null;
		if (methodAnnotation) {
			targetDataSource = method.getAnnotation(TargetDataSource.class);
		} else {
			Class clazz[] = pjp.getTarget().getClass().getInterfaces();
			targetDataSource = (TargetDataSource) clazz[0].getAnnotation(TargetDataSource.class);
		}
		if (targetDataSource != null) {
			DynamicDataSource.setDataSource(targetDataSource.value());
			logger.debug("mybatis接口: " + (method.getDeclaringClass() + "." + method.getName()) + " 设置数据源 key is " + targetDataSource.value());
		}
		Object result = pjp.proceed();// 执行方法

		DynamicDataSource.clear();

		return result;
	}
}
