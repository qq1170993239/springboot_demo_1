package com.lix.study.common.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 拦截器的注册
 * @author lix
 */
@Configuration
public class TestWebAppConfigurer extends WebMvcConfigurerAdapter {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 多个拦截器组成一个拦截器链
		
		// addPathPatterns 用于添加拦截规则
		registry.addInterceptor(new MyFirthInterceptor()).addPathPatterns("/private").addPathPatterns("/**");
		// excludePathPatterns 用户排除拦截
		// registry.addInterceptor(new MyFirthInterceptor()).excludePathPatterns("/test");
		super.addInterceptors(registry);
	}

}
