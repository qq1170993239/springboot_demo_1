package com.lix.study.common.conts;

import java.nio.charset.Charset;

/**
 * 项目常量类
 * @author lix
 */
public class DemoConts {
	
	private DemoConts() {}
	/**
	 * 密码加盐
	 */
	public static final String SECRET_SALT = "随便写点GF;.51[hd78//=-0&$%h。，fdjkj就可以";
	/**
	 * 加密算法——MD5
	 */
	public static final String ALGORITHM_MD5 = "md5";
	
	public static final String VALUE = "value";
	public static final String CODE = "code";
	public static final String TYPE = "type";
	
	public static final String UTF_8 = "utf-8";
	
	public static final Charset CHARSET_UTF8 = Charset.forName(UTF_8);

}
