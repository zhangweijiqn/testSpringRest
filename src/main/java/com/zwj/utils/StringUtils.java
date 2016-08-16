package com.zwj.utils;


import org.apache.log4j.Logger;

import java.util.regex.Pattern;

/**
 * 字符串工具类
 * 
 * @author liyonghui
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {
	private static final Logger LOG = Logger.getLogger(StringUtils.class);
	
	private StringUtils() {
	}
	
	/**
	 * 替换正则表达式匹配的所有内容，相对于String.replaceAll方法：用户可以控制是否忽略大小写
	 * @param source
	 * @param regex
	 * @param replacement
	 * @param ignoreCase 是否忽略大小写, true：忽略大小写
	 * @return
	 */
	public static String replaceAll(String source, String regex, String replacement, boolean ignoreCase) {
		if ( source == null || source.length() == 0 ) {
			return source;
		}
		
		if ( !ignoreCase ) {
			return source.replaceAll(regex, replacement);
		}

		return Pattern.compile(regex, Pattern.CASE_INSENSITIVE).matcher(source).replaceAll(replacement); 
	}
	
	/**
	 * 返回超链接的html字符串，比如：<a href="{url}">{name}</a>
	 * @param url
	 * @param name
	 * @return
	 */
	public static String getAHTML(String url, String name) {
		return getAHTML(url, name, null);
	}
	
	/**
	 * 返回超链接的html字符串，比如：<a href="{url}" {attributeHTML}>{name}</a>
	 * @param url
	 * @param name
	 * @param otherAttrHTML，超链接的其它属性，比如：style="color:red;"
	 * @return
	 */
	public static String getAHTML(String url, String name, String otherAttrHTML) {
		if ( otherAttrHTML == null ) {
			otherAttrHTML = "";
		}
		
		return "<a href=\"" +url+ "\" " +otherAttrHTML+ ">" + name + "</a>";
	}
	
}

