package com.lix.study.db.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.lix.study.db.dao.DBCommonQueryDAO;

@Service
public class DBCommonQueryServiceImpl implements DBCommonQueryService {
	
	private static final  Logger logger = LoggerFactory.getLogger(DBCommonQueryServiceImpl.class);

	@Autowired
	private DBCommonQueryDAO dBCommonQueryDAO;
	
	@Override
	public List<Map<String, Object>> execSqlData(String sql) {
		if(StringUtils.isEmpty(sql)) {
			return new ArrayList<>();
		}
		// 超过50条，只返回前五十条
		if(dBCommonQueryDAO.execSqlData(sql).size() > 50) {
			return dBCommonQueryDAO.execSqlData(sql).stream().limit(50).collect(Collectors.toList());
		}
		return dBCommonQueryDAO.execSqlData(sql);
	}

	@Override
	public List<Map<String, Object>> queryConditionsData(String tableName, List<String> conditions) {
		if(StringUtils.isEmpty(tableName)) {
			return new ArrayList<>();
		}
		return dBCommonQueryDAO.queryConditionsData(tableName, conditions);
	}

	@Override
	public boolean insertOneData(String tableName, Map<String, String> conditions) {
		if(StringUtils.isEmpty(tableName) || conditions == null || conditions.isEmpty()) {
			return false;
		}
		Set<Entry<String, String>> entrys = conditions.entrySet();
		List<String> colName = new ArrayList<>();
		List<String> colValue = new ArrayList<>();
		for (Entry<String, String> entry : entrys) {
			colName.add(entry.getKey());
			colValue.add("'" + entry.getValue() + "'");
		}
		try {
			dBCommonQueryDAO.insertOneData(tableName, colName, colValue);
		}catch(Exception e) {
			logger.error(e.getMessage());
			return false;
		}
		return true;
	}

}
