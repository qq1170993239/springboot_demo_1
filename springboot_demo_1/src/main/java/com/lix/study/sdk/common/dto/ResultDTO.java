package com.lix.study.sdk.common.dto;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 封装数据公共类
 * 
 * @author lix
 * @Date 2018-03-18
 */
public class ResultDTO<T> implements Serializable {

	private static final long serialVersionUID = 2681687922807081507L;
	/** 成功状态码 */
	public static final int SUCCESS = 1;
	/** 失败状态码(未查到数据等) */
	public static final int FAIL = 0;
	/** 异常状态码(运行时异常等) */
	public static final int EXCEPTION = 2;

	/** 成功信息 */
	public static final String OK = "OK";
	/** 异常信息 */
	public static final String ERROR = "ERROR";

	public static final String DATESTRING = "data";
	public static final String MGSSTING = "msg";
	public static final String CODESTRING = "code";
	/** 信息 */
	private String msg;
	/** 异常详细信息 */
	private String exceptionMsg;
	/** 数据 */
	private T data;
	/** 执行状态码 */
	private int code;
	
	public ResultDTO() {
		
	}
	
	/**
	 * 没有数据，但有提示信息一类的
	 * @param msg
	 * @param code
	 */
	public ResultDTO(String msg, int code) {
		this(msg, null, null, code);
	}

	/**
	 * 有异常信息返回
	 * @param e
	 */
	public ResultDTO(Throwable e) {
		this(ERROR, e.toString(), null, EXCEPTION);
	}

	/**
	 * 有成功数据返回
	 * @param data
	 */
	public ResultDTO(T data) {
		this(OK, null, data, SUCCESS);
	}

	private ResultDTO(String msg, String exception, T data, int code) {
		this.msg = msg;
		this.exceptionMsg = exception;
		this.data = data;
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	/**
	 * 判断执行结果
	 * 
	 * @return 请求成功返回true
	 */
	public boolean itsok() {
		return code == SUCCESS;
	}

	/**
	 * 获取该对象的json类型
	 * 
	 * @return json String
	 */
	public String toJSONStr() {
		return JSON.toJSONString(this);
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getException() {
		return exceptionMsg;
	}

	public void setException(String exception) {
		this.exceptionMsg = exception;
	}

	@Override
	public String toString() {
		return "ResultDTO [msg=" + msg + ", exception=" + exceptionMsg + ", data=" + data + ", code=" + code + "]";
	}


}
