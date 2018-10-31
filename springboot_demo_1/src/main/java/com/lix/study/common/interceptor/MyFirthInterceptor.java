package com.lix.study.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class MyFirthInterceptor implements HandlerInterceptor {
	
	private static final  Logger logger = LoggerFactory.getLogger(MyFirthInterceptor.class);

	/**
	 * 在请求处理之前进行调用,Controller方法调用之前
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.info("preHandle>>>>>>>>在请求处理之前进行调用,Controller方法调用之前");
//		return false;表示拦截
		return true;// 表示放行
	}

	/**
	 * 请求处理之后进行调用，但是在视图被渲染之前,Controller方法调用之后
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		logger.info("postHandle>>>>>>>>请求处理之后进行调用，但是在视图被渲染之前,Controller方法调用之后");
	}

	/**
	 * 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）")
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		logger.info("afterCompletion>>>>>>>>在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）\")");
	}

}
