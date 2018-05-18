package com.lix.study.sdk.common.excepion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lix.study.sdk.common.dto.ResultDTO;
/**
 * 异常统一处理类
 * @author lix
 * Date 2018-03-18
 */
@ControllerAdvice
public class HandleExceptions {
	
	private static final  Logger logger = LoggerFactory.getLogger(HandleExceptions.class);
	
	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public ResultDTO<Exception> errorHandler(HttpServletRequest req, HttpServletResponse resp, Exception e) {
		logger.error("请求路径：{}，异常信息：【{}】", req.getRequestURL(), e.getMessage());
		return new ResultDTO<>(e);
	}

}
