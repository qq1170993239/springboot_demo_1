package com.lix.study.config.redis.service;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lix.study.sdk.common.utils.StringUtils;

/**
 * RedisCacheble 实现类
 * @author lix
 */
@Aspect
@Component
public class RedisCachebleAdapter {

	private static final Logger logger = LoggerFactory.getLogger(RedisCachebleAdapter.class);

	@Autowired
	private RedisHelper redisCache;

	@Around("@annotation(RedisCacheble)")
	public Object log(ProceedingJoinPoint joinPoint) {
		// 先获取目标方法参数
		Signature s = joinPoint.getSignature();
		if (!(s instanceof MethodSignature)) {
			// 异常信息： 该注解只能用于方法
			throw new IllegalArgumentException("This annotation can only be used for methods");
		}
		RedisCacheble rc = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(RedisCacheble.class);

		long id = 0;
		Object[] args = joinPoint.getArgs();
		if (args != null && args.length > 0) {
			String applId = StringUtils.getJSONStr(args[0]);
			id = applId == null ? -1 : applId.hashCode();
		}
		// redis 缓存key的生成策略
		String redisKey = null;
		redisKey = rc.key() + ":" + id;
		if (StringUtils.isEmpty(rc.key())) {
			// 获取目标方法所在类
			String className = joinPoint.getTarget().toString().split("@")[0];
			// 获取目标方法的方法名称
			String methodName = joinPoint.getSignature().getName();
			// redis中key格式： applId:方法名称
			redisKey = className + "." + methodName + ":" + id;
		}

		// 前置：从Redis里获取缓存
		Object obj = redisCache.get(redisKey);

		// 如果从缓存中获取到了数据，就将其直接返回
		if (obj != null) {
			logger.info("**********key[{}]数据获取成功!!!**********", redisKey);
			return obj;
		}
		try {
			// 后置：从方法本身获取数据
			obj = joinPoint.proceed();
		} catch (Throwable e) {
			logger.error(e.getMessage());
		}
		// 后置：将获取到的数据保存到Redis
		if (redisCache.set(redisKey, obj, rc.expireTime())) {
			logger.info("**********数据成功保存到Redis缓存!!!**********");
		}
		return obj;
	}
}
