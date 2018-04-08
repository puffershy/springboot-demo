package com.puffer.demo.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据源枚举注解
 *
 * @author buyi
 * @date 2018年4月5日下午12:11:09
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface TargetDataSource {
	DataSourceType value() default DataSourceType.MASTER;
}
