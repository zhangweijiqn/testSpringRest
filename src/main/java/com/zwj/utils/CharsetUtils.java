package com.zwj.utils;


import org.apache.commons.lang3.CharUtils;
import org.apache.log4j.Logger;

/**
 * 字符集工具类
 * 
 * @author liyonghui
 */
public class CharsetUtils {
	private static final Logger LOG = Logger.getLogger(CharsetUtils.class);
	
	private CharsetUtils() {
	}
	
	/**
	 * unicode编码
	 * @param num
	 * @param format
	 * @return
	 */
	public static String toUnicode(String str) {
		if ( str == null || str.length() == 0 ) {
			return str;
		}
		
		StringBuilder sb = new StringBuilder();
		char[] cArr = str.toCharArray();
		for (char c : cArr) {
			sb.append(CharUtils.unicodeEscaped(c));
		}
		
		return sb.toString();
	}
	
}