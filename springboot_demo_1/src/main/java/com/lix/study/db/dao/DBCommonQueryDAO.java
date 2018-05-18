package com.lix.study.db.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 公用数据库操作
 * @author Administrator
 * @Date 2018-03-31
 */
@Mapper
public interface DBCommonQueryDAO {
	/**
	 * 直接执行sql。该方法请慎用
	 * @param sql 原生sql语句
	 * @return 数据集合
	 */
	List<Map<String, Object>> execSqlData(@Param("sql") String sql);
	/**
	 * 获取指定表名下对应的条件数据
	 * @param tableName
	 * @param conditions
	 * @return
	 */
	List<Map<String, Object>> queryConditionsData(@Param("tableName") String tableName, @Param("conditions") List<String> conditions);
	
	boolean insertOneData(@Param("tableName") String tableName, @Param("colName") List<String> colName, @Param("colValue") List<String> colValue);

}
