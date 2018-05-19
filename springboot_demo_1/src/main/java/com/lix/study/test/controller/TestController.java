package com.lix.study.test.controller;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lix.study.config.dto.User;
import com.lix.study.config.redis.service.RedisCacheble;
import com.lix.study.db.service.DBCommonQueryService;
import com.lix.study.ioc.ConfigValue;
import com.lix.study.ioc.log.InputOutputLog;
import com.lix.study.sdk.common.dto.ResultDTO;
import com.lix.study.service.async.testservice.TestAsync;

/**
 * 提供的测试接口
 * @author lix
 */
@RestController
@RequestMapping("/test")
public class TestController {
	
	private static final  Logger logger = LoggerFactory.getLogger(TestController.class);
	
	// 接受类型为String时即唯一值，使用type+code确定其唯一性
	@ConfigValue(type = "test", code = "test")
	private String value;
	
	// 接受类型为Map时即对type模糊查询，type不能为空
	@ConfigValue(type = "test")
	private Map<String, String> valueMap;
	
	// 接受类型为List时即对type模糊查询，type不能为空
	@ConfigValue(type = "test")
	private List<String> valueList;
	
	
	@Autowired
	private DBCommonQueryService dBCommonQueryService;
	
	@Autowired
	private TestAsync testAsync;
	
	@Autowired
	private User user;
	
	@PostMapping("/execsql")
	public ResultDTO<List<Map<String, Object>>> execSqlData(@RequestBody String sql){
		return new ResultDTO<>(dBCommonQueryService.execSqlData(sql));
	}
	
	@GetMapping("/testRedis")
	@InputOutputLog
	@RedisCacheble(key = "testRedis")
	public ResultDTO<String> testRedis(@RequestParam String param){
		return new ResultDTO<>(String.format("传入参数:%s,数据库配置:%s,静态文件读取:%s", param, value, user.getName()));
	}
	
	@InputOutputLog
	@GetMapping("/testAsync")
	public ResultDTO<String> testAsync() {
		long start = System.currentTimeMillis();
		Future<Boolean> fval1 = testAsync.test1();
		Future<Boolean> fval2 = testAsync.test2();
		Future<Boolean> fval3 = testAsync.test3();
		try {
			fval1.get();
			fval2.get();
			fval3.get();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return new ResultDTO<>("执行总耗时 ：" + (System.currentTimeMillis() - start) + "ms");
	}
	
	@InputOutputLog
	@GetMapping("/testWebserivce")
	@RedisCacheble(key = "testWebserivce")
	public ResultDTO<String> testWebserivce(@RequestParam String name){
		JaxWsDynamicClientFactory clientFactory = JaxWsDynamicClientFactory.newInstance();  
		Client client = clientFactory.createClient("http://localhost:8002/services/webservice/sayhi?wsdl");  
		Object[] result = null;
		try {
			// 方法名，参数
			result = client.invoke("sayHello", name);
			return new ResultDTO<>(result[0].toString()); 
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return new ResultDTO<>(null);
	}
	
}
