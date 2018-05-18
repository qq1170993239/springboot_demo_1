package com.lix.study.webservice.cxf;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * SayHiServices 接口
 * @author lix
 * @Date 2018-05-19
 */
// 命名空间,一般是接口的包名倒序
@WebService(targetNamespace = "http://webservice.study.lix.com")
public interface SayHiServices {

	@WebMethod
	String sayHello(@WebParam(name = "name") String name);
}
