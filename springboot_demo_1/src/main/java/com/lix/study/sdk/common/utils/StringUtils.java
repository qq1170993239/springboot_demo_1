package com.lix.study.sdk.common.utils;

import com.alibaba.fastjson.JSON;

/**
 * 字符串工具类
 * @author lix
 */
public class StringUtils {
	
	private StringUtils() {}
	/**
	 * 字符是否为空
	 * @param value
	 * @return 为空返回true
	 */
	public static boolean isEmpty(String value) {
		return value == null || value.trim().length() == 0;
    }
	
	public static boolean isNotEmpty(String value) {
		return !isEmpty(value);
    }
	/**
	 * 获取任意对象的json串
	 * @param obj
	 * @return
	 */
	public static String getJSONStr(Object obj) {
		if(obj instanceof String) {
			return (String)obj;
		}
		return JSON.toJSONString(obj);
    }
	
}
