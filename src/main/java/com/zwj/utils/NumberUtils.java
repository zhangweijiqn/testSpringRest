package com.zwj.utils;


import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.StringTokenizer;


/**
 * number工具类
 * 
 * @author liyonghui
 */
public class NumberUtils {
	private static final Logger LOG = Logger.getLogger(NumberUtils.class);
	
	private NumberUtils() {
	}
	
	/**
	 * 相当于format(num, format, true)
	 * @param num
	 * @param format
	 * @return
	 */
	public static String format(double num, Object format) {
		return format(num, format, true);
	}
	
	/**
	 * 格式化数字
	 * @param num
	 * @param format 格式，format格式如下：<br/>
	 * <ul>
	 * 	<li>int：小数精度，例如：format(1000.1234, 2, true)，输出结果：1000.12</li>
	 * 	<li>char/String：千分位分隔符，例如：foramt(1000.1234, ',', true)，输出结果：1000<br/>
	 *     <font color=red>注意：该格式下：1、自动舍弃小数；2、参数mustHasDecimal无效</font></li>
	 * 	<li>String：小数精度#千分位分隔符，例如：foramt(1000.1234, "2#,", true)，输出结果：1,000.12<br/>
	 *     <font color=red>注意：该格式必须指定小数精度</font></li>
	 * </ul>
	 * @param mustHasDecimal 是否必须有小数，在只指定“千分位分隔符”的格式该参数无效，值：<br/>
	 *     &nbsp;&nbsp;&nbsp;&nbsp;true：num为整数或者在精度范围内的小数均为0时，格式化后的数字也会带有指定精度的小数，例如：foramt(1000, 2, true)，输出结果：1000.00；<br/>
	 * 	    &nbsp;&nbsp;&nbsp;&nbsp;false：如果num为整数或者在精度范围内的小数均为0时，格式化后的数字不带小数，例如：foramt(1000.001, 2, false)，输出结果：1000。
	 * @return
	 */
	public static String format(double num, Object format, boolean mustHasDecimal) {
		if ( format == null ) {
			return num+"";
		}
		
		Object[] fArr = parseFormat(format);
		int digit = (Integer)fArr[0];
		char separator = (Character)fArr[1];
		
		DecimalFormat f = new DecimalFormat();
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		if (!(separator == '\0')) {
			symbols.setGroupingSeparator(separator);
		}
		f.setDecimalFormatSymbols(symbols);

		String pattern = "";
		String pUnit = "0";
		if ( !mustHasDecimal ) {
			pUnit = "#";
		}
		for (int i = 0; i < digit; i++) {
			pattern = pUnit + pattern;
		}
		
		if (digit > 0) {
			if (!(separator == '\0')) {
				pattern = "#,##0." + pattern;
			} else {
				pattern = "###0." + pattern;
			}
		} else {
			if (!(separator == '\0')) {
				pattern = "#,##0";
			} else {
				pattern = "###0";
			}
		}
		
		f.applyPattern(pattern);
		return f.format(num);
	}
	
	/**
	 * 解析出小数精度和千分位分隔符
	 * @param format
	 * @return
	 */
	private static Object[] parseFormat(Object format) {
		Object[] rv = new Object[2];
		rv[0] = 0; //小数精度
		rv[1] = '\0'; //千分位分隔符
		
		if( format instanceof Integer ) {
			rv[0] = format;
		} else if ( format instanceof Character ) {
			rv[1] = format;
		} else if ( format instanceof String && ((String) format).length() == 1 ) {
			rv[1] = ((String)format).charAt(0);
		} else if ( !(format instanceof String) || ((String)format).indexOf("#") == -1 ) {
			throw new IllegalArgumentException("参数format格式错误，format可以是只表示小数精度的Integer，或者只表示千分为分隔符的字符（比如：“,”），" +
					"或者两者均表示的字符串，两者均表示的格式为：小数精度#千分为分隔符，比如：“2#,”");
		} else {
			String f = (String)format;
			StringTokenizer st = new StringTokenizer(f, "#");
			try {
				String x = st.nextToken();
				rv[0] = Integer.parseInt(x);
			} catch ( Exception e ) {
				throw new IllegalArgumentException("参数format格式错误，表示小数精度的必须是正整数, format=" + f);
			}
			
			if (st.hasMoreTokens()) {
				rv[1] = st.nextToken().charAt(0);
			}
		}
		
		return rv;
	}
	
}
