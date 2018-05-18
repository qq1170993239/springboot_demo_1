package com.lix.study.config.redis.service;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.lix.study.sdk.common.utils.DateUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
/**
 * 切面实现缓存控制
 * @author lix
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedisCacheble {

	/**
	 * 缓存的key,可以不填
	 * @return
	 */
	String key() default "";
	/**
	 * 缓存的有效时长（以秒为单位）,默认2小时
	 * @return
	 */
	long expireTime() default DateUtils.HOUR * 2 / 1000;
	
}
