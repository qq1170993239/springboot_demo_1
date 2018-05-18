package com.lix.study.ioc.log;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.lix.study.log.service.SaveSystemLogs;


@Aspect
@Component
public class InputOutputLogAspect {

	private static final  Logger logger = LoggerFactory.getLogger(InputOutputLogAspect.class);
	
	@Autowired
	private SaveSystemLogs saveSystemLogs;
	
    @Autowired
    private ThreadPoolExecutor executor;

    @Around("@annotation(InputOutputLog)")
    public Object log(ProceedingJoinPoint point) throws Throwable {
		Map<String, String> data = new HashMap<>();
		// 调用者 包名.类名.方法名
		data.put("caller",  point.getSignature().toLongString());

		// 传入参数
		data.put("args", getArgsString(point));

		// 耗时
		long start = System.currentTimeMillis();
		try {
			Object obj = point.proceed();
			data.put("cost", String.valueOf(System.currentTimeMillis() - start));
			// 返回结果
			data.put("response", getObjectString(obj));
			logger.info(getObjectString(data));
			return obj;
		} catch (Throwable e) {
			data.put("cost", String.valueOf(System.currentTimeMillis() - start));
			data.put("exception", e.toString());
			logger.error(getObjectString(data));
			// 原样抛出异常
			throw e;
		} finally {
		    try {
		    	executor.execute(() -> {
		    		saveSystemLogs.saveLogs(data);
		    	}); 
            } catch (Exception e2) {
                logger.warn("Exception happened when logging to dblog server: {}", e2.getMessage());
            }
		}
    }
    
    private String getArgsString(ProceedingJoinPoint point) {
        StringBuilder sb = new StringBuilder();
        Object[] args = point.getArgs();
        for (int i = 0; i < args.length; i++) {
            sb.append(getObjectString(args[i]));
            if (i < args.length - 1) {
                sb.append(',');
            }
        }
        return sb.toString();
    }

    private String getObjectString(Object obj) {
        if (obj instanceof String) {
            // String 经过JSON处理会变成加前后引号，不太好，因此单独处理
            return (String) obj;
        }
        return JSON.toJSONString(obj);
    }
}
