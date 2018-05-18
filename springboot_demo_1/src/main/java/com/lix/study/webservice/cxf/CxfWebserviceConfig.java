package com.lix.study.webservice.cxf;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 发布服务
 * @author Administrator
 */
@Configuration
public class CxfWebserviceConfig {

	
	@Autowired
    private Bus bus;
	
    @Autowired
    private SayHiServices sayHiServices;
    
    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus,sayHiServices);
        // 接口发布在 /webservice/sayhi 目录下
        endpoint.publish("/webservice/sayhi");
        return endpoint;
    }
}
