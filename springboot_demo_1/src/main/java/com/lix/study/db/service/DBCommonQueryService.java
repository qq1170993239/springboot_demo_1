package com.lix.study.db.service;

import java.util.List;
import java.util.Map;

/**
 * 公用数据库操作service
 * @author Administrator
 */
public interface DBCommonQueryService {
	/**
	 * 获取数据，执行异常返回null
	 * @param sql 原生sql语句
	 * @return
	 */
	List<Map<String, Object>> execSqlData(String sql);
	
	/**
	 * 根据条件查询单表
	 * @param tableName 表名
	 * @param conditions 条件
	 * @return
	 */
	List<Map<String, Object>> queryConditionsData(String tableName, List<String> conditions);

	boolean insertOneData(String tableName, Map<String, String> conditions);
}
