package com.lix.study.sdk.common.excepion;
/**
 * 自定义异常封装类
 * @author Administrator
 */
public class DefException extends RuntimeException {

	private static final long serialVersionUID = 77870657776492012L;

	public DefException() {
		super();
	}

	public DefException(String message, Throwable cause) {
		super(message, cause);
	}

	public DefException(String message) {
		super(message);
	}

	public DefException(Throwable cause) {
		super(cause);
	}
	
	

}
