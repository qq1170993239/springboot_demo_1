package com.lix.study;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringbootDemoApplication.class) // 指定spring-boot的启动类
public class SpringbootDemoApplicationTests {

	// 测试方法期望得到的异常类，如果方法执行没有抛出指定的异常，则测试失败
	@Test(expected = Exception.class)
	public void contextLoads() {
	}

}
