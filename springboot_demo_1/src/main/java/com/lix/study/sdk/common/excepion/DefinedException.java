package com.lix.study.sdk.common.excepion;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义异常封装类
 * @author Administrator
 */
public class DefinedException extends RuntimeException {

	private static final long serialVersionUID = 77870657776492012L;
	
	private List<String> errorMsg;
	
	public List<String> getErrorMsg() {
		if(errorMsg == null) {
			errorMsg = new ArrayList<>();
		}
		return errorMsg;
	}

	public void setErrorMsg(List<String> errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	public void addErrorMsg(String message) {
		this.getErrorMsg().add(message);
	}

	public DefinedException() {
		super();
	}

	public DefinedException(String message, Throwable cause) {
		super(message, cause);
		this.getErrorMsg().add(message);
	}

	public DefinedException(String message) {
		this.getErrorMsg().add(message);
	}
	
	public DefinedException(List<String> message) {
		this.errorMsg = message;
	}

	public DefinedException(Throwable cause) {
		super(cause);
	}

}
