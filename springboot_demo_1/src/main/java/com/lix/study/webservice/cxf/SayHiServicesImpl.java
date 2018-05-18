package com.lix.study.webservice.cxf;

import javax.jws.WebService;

import org.springframework.stereotype.Component;

/**
 * 网吧web services 接口实现
 * 
 * @author xiaojf 2017/7/24 21:38
 */
/* http://localhost:8002/services/webservice/sayhi?wsdl*/
@WebService(serviceName = "SayHiServices"// 服务名
		, targetNamespace = "http://webservice.study.lix.com"// 包名倒叙，并且和接口定义保持一致
		, endpointInterface = "com.lix.study.webservice.cxf.SayHiServices") // 包名
@Component
public class SayHiServicesImpl implements SayHiServices {

	@Override
	public String sayHello(String name) {
		return "hello , my name is "+ name;
	}

}
