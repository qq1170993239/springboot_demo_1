package com.lix.study.sdk.common.utils;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * UUID号码生成工具
 * @author lix
 * @Date 2018-01-17
 */
public class UuidUtils {

	/** 包含字母大小写和数字的二维数组 */
	private static final char[][] WORDS = new char[][] {
			{ 'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x',
					'c', 'v', 'b', 'n', 'm' },
			{ 'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P', 'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'Z', 'X',
					'C', 'V', 'B', 'N', 'M' },
			{ '1', '2', '3', '4', '5', '6', '7', '9', '8', '0' } };
	/** 生成随机小写组成的字符串 */
	public static final int LOWER = 0;
	/** 生成随机大写组成的字符串 */
	public static final int UPPER = 1;
	/** 生成随机数字组成的字符串 */
	public static final int NUMBER = 2;
	/** 生成随机大小写混合的字符串 */
	public static final int ALL = 3;
	/** 用于获取随机值 */
	public static final ThreadLocalRandom random = ThreadLocalRandom.current();

	private UuidUtils() {

	}

	/**
	 * 获取指定类型及长度的uuid串
	 * @param length
	 * @param type LOWER,UPPER,NUMBER,ALL
	 * @return 返回指定类型的uuid字符串
	 */
	public static String getUuid(int length, int type) {
		if (length < 1) {
			return null;
		}
		char[] chars = null;
		switch (type) {
		case LOWER:
			chars = getChars(WORDS[LOWER], length);
			break;
		case UPPER:
			chars = getChars(WORDS[UPPER], length);
			break;
		case NUMBER:
			chars = getChars(WORDS[NUMBER], length);
			break;
		case ALL:
			chars = getChars(WORDS, length);
			break;
		default:
			return "";
		}
		return new String(chars);

	}

	/** 获取jdk自带的uuid */
	public static String getUuid() {
		return UUID.randomUUID().toString();
	}
	
	/** 获取不带-的uud */
	public static String getUuidNotLine() {
		return getUuid().replace("-", "");
	}

	/** 获取和时间相关的uuid */
	public static String getTimeUuid(int length) {
		String timeStr = Long.toString(System.nanoTime());
		int difference = timeStr.length() - length;
		if (difference < 0) {
			timeStr = getUuid(Math.abs(difference), NUMBER) + timeStr;
		} else if (difference > 0) {
			timeStr = timeStr.substring(difference);
		}
		return timeStr;
	}

	/** 获取随机指定长度的数字uuid */
	public static String getRandomUuid(int length) {
		long ran = random.nextLong();
		if (0 > ran) {
			ran = Math.abs(ran);
		}
		String result = Long.toString(ran);
		int difference = result.length() - length;
		if (difference < 0) {
			result = getUuid(difference, NUMBER) + result;
		} else if (difference > 0) {
			result = result.substring(difference);
		}
		return result;
	}

	/** 随机获取数组中的i个字符 */
	private static char[] getChars(char[] c, int length) {
		char[] temp = new char[length];
		for (int j = 0; j < length; j++) {
			temp[j] = c[getRandomVlue(c.length)];
		}
		return temp;
	}

	/** 随机获取数组中的i个字符 */
	private static char[] getChars(char[][] c, int length) {
		char[] temp = new char[length];
		for (int j = 0; j < length; j++) {
			char[] word = c[getRandomVlue(c.length)];
			for (int i = 0; i < word.length; i++) {
				temp[j] = word[getRandomVlue(word.length)];
			}
		}
		return temp;
	}

	/** 获取0~n的随机数 */
	private static int getRandomVlue(int n) {
		return random.nextInt(n);
	}

}
