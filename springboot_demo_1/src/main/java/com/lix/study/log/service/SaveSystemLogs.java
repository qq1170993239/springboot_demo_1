package com.lix.study.log.service;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lix.study.db.service.DBCommonQueryService;
import com.lix.study.sdk.common.utils.StringUtils;

@Component
public class SaveSystemLogs {
	
	private static final  Logger logger = LoggerFactory.getLogger(SaveSystemLogs.class);

	@Autowired
	private DBCommonQueryService dBCommonQueryService;

	public void saveLogs(Map<String, String> data) {
		dBCommonQueryService.execSqlData(this.getSql(data));
	}


	private String getSql(Map<String, String> data) {
		String caller = data.get("caller");
		if(StringUtils.isNotEmpty(caller) && caller.length() > 200) {
			caller = caller.substring(0, 100);
		}
		String args = data.get("args");
		if(StringUtils.isNotEmpty(args) && args.length() > 500) {
			args = args.substring(0, 500);
		}
		String response = data.get("response");
		if(StringUtils.isNotEmpty(response) && response.length() > 500) {
			response = response.substring(0, 500);
		}
		String exception = data.get("exception");
		if(StringUtils.isNotEmpty(exception) &&  exception.length() > 100) {
			exception = exception.substring(0, 100);
		}
		if (StringUtils.isEmpty(exception)) {
			return "insert into lix_transfer_log(method_name,args,costs,data) values('" + caller + "','"
					+ args + "','" + data.get("cost") + "','" + response + "')";
		}
		return "insert into lix_exception_log(method_name,args,costs,exception) values('" + caller + "','"
				+ args + "','" + data.get("cost") + "','" + exception + "')";
	}
	// 在不方便使用此注解时考虑实现ApplicationListener接口
	@PostConstruct
	public void init() {
		logger.info("在服务器加载Servlet的时候运行，并且只会被服务器调用一次，类似于Servlet的inti()方法");
	}
	
}
