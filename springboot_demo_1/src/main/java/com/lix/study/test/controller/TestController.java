package com.lix.study.test.controller;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
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
import com.lix.study.login.dao.LoginUserDao;
import com.lix.study.login.dto.LoginUserEntity;
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
	
	@Autowired
	private LoginUserDao loginUserDao;
	
	@PostMapping("/execsql")
	public ResultDTO<List<Map<String, Object>>> execSqlData(@RequestBody String sql){
		return new ResultDTO<>(dBCommonQueryService.execSqlData(sql));
	}
	/**
	 * 测试redis注解以及自定义配置注入
	 * @param param
	 * @return
	 */
	@GetMapping("/testRedis")
	@InputOutputLog
	@RedisCacheble(key = "testRedis")
	public ResultDTO<String> testRedis(@RequestParam String param){
		return new ResultDTO<>(String.format("传入参数:%s,数据库配置:%s,静态文件读取:%s", param, value, user.getName()));
	}
	/**
	 * 测试异步任务执行
	 * @return
	 */
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
	/**
	 * 测试webservice调用
	 * @param name
	 * @return
	 */
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
	/**
	 * 测试loginUserDao
	 * @param userName
	 * @return
	 */
	@InputOutputLog
	@GetMapping("/find")
	@RedisCacheble(key = "findUserByName")
	public ResultDTO<LoginUserEntity> findUserByName(@RequestParam String userName) {
		return new ResultDTO<>(loginUserDao.findUserByName(userName));
	}
	/**
	 * 测试shiro登录认证
	 * @param userName
	 * @param password
	 * @return
	 */
	@PostMapping("/login")
	public ResultDTO<String> testLogin(@RequestParam String userName, @RequestParam String password) {
		UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userName, password);
		Subject subject = SecurityUtils.getSubject();
		try {
			// 登录
			subject.login(usernamePasswordToken);
			LoginUserEntity user = (LoginUserEntity) subject.getPrincipal();
			subject.getSession().setAttribute("user", user);
			return new ResultDTO<>("欢迎" + user.getUserName() + "登陆成功。。。。。");
		} catch (Exception e) {
			return new ResultDTO<>("登陆失败的处理逻辑。。。。。");
		}
	}
	
}
