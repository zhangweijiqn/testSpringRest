package com.zwj.utils;

import org.apache.log4j.Logger;


/**
 * html工具类
 * 
 * @author liyonghui
 */
public class HtmlUtils {
	private static final Logger LOG = Logger.getLogger(HtmlUtils.class);
	
	private HtmlUtils() {
	}
	
	/**
	 * 按HTML的规则对字符串进行反向转义，不对空格对应的html编码："&amp;nbsp;"进行反转义，
	 * 请参考：{@link com.jd.common.util.StringEscapeUtils.unescapeHtml(String)}
	 * @param str
	 * @return
	 */
	public static String unescapeHtml(String str) {
		if ( str == null || str.length() == 0 ) {
			return str;
		}
		
//		//如果包含&nbsp;则转义前替换成日文、拉丁文、特殊符号、中文组成的字符串（该组合可以较大程度的避免与原字符串冲突），
//		//转义后再反向替换
//		if ( str.indexOf("&nbsp;") != -1 ) {
//			str = str.replace("&nbsp;", "あβ~#中"); 
//			str = StringEscapeUtils.unescapeHtml(str);//TODO:有待优化，unescapeHtml性能较差
//			str = str.replace("あβ~#中", "&nbsp;");
//		} else {
//			str = StringEscapeUtils.unescapeHtml(str);
//		}
		
		return str.replace("&amp;", "&").replace("&lt;", "<").replace("&gt;", ">").replace("&quot;", "\"").replace("&#39;", "'");
	}
	
}
