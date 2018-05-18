package com.lix.study.service.async.testservice;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Future;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import com.lix.study.sdk.common.utils.DateUtils;
/**
 * spring boot 异步执行任务类
 * <P>单线程执行总时长2+6+4，异步处理时耗时为单任务最大值，即6秒</P>
 * @author lix
 */
@Component
public class TestAsync {

	@Async
	public Future<Boolean> test1() {
		boolean flag = true;
		try {
			Thread.sleep(DateUtils.SECOND * 2);
			flag = true;
		} catch (InterruptedException e) {
			flag = false;
		}
		return new AsyncResult<>(flag);
	}
	@Async
	public Future<Boolean> test2() {
		boolean flag = true;
		try {
			Thread.sleep(DateUtils.SECOND * 6);
			flag = true;
		} catch (InterruptedException e) {
			flag = false;
		}
		return new AsyncResult<>(flag);
	}
	@Async
	public Future<Boolean> test3() {
		boolean flag = true;
		try {
			Thread.sleep(DateUtils.SECOND * 4);
			flag = true;
		} catch (InterruptedException e) {
			flag = false;
		}
		return new AsyncResult<>(flag);
	}
	
}
