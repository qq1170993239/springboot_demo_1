package com.lix.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
/**
 * 开启异步处理 
 * 开启任务调度
 * 开启缓存
 * @author lix
 */
@SpringBootApplication
@EnableScheduling
@EnableAsync
@ComponentScan("com.lix.study")
public class SpringbootDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootDemoApplication.class, args);
//		或者 new SpringApplicationBuilder(SpringbootDemoApplication.class).run(args);
	}
	
}
