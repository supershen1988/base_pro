package com.supershen.example.utils;

import java.text.DecimalFormat;
import java.util.regex.Pattern;

/**
 * 字符串处理工具类.
 */
public class GtStringUtils {
	
	private GtStringUtils() {
		
	}
	
	/**
	 * 数字前导字符.
	 * <pre>
	 * 例:
	 * 数字23前导2位0，则ldap(23, 4, '0');
	 * </pre>
	 * @param src	原数字
	 * @param len	总长度
	 * @param ch	前导字符
	 */
	public static String ldap(int src, int len, char ch) {
		String s = String.valueOf(src);
		int num = len - s.length();

		char[] dest = new char[len];
		int idx = 0;
		for (; idx < num; idx++) {
			dest[idx] = ch;
		}

		for (int i = 0; idx < len; i++, idx++) {
			dest[idx] = s.charAt(i);
		}

		return new String(dest);
	}

	/**
	 * 查看此字符串第一个数字是从第几位开始的.
	 * 如果字符串中没有数字，则返回零.
	 */
	public static int numIndexOf(String str) {
		int idx = -1;

		char ch;
		for (int i = 0, len = str.length(); i < len; i++) {
			ch = str.charAt(i);
			if (ch >= '0' && ch <= '9') {
				idx = i;
				break;
			}
		}

		return idx;
	}

	/**
	 * 判断字符串是否全部由数字组成. 
	 */
	public static boolean isNumber(String s) {
		if (s == null || s.equals("")) {
			return false;
		}
		
		return Pattern.compile("[0-9]*").matcher(s).matches();
	}

	/**
	 * 判断字符是否可以转换成整数.
	 * @param s
	 */
	public static boolean isInt(String s) {
		try {
			Integer.parseInt(s);
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	/**
	 * 判断字符是否可以转换成整数.
	 * @param s
	 */
	public static boolean isDouble(String s) {
		try {
			Double.parseDouble(s);
		} catch (Exception e) {
			return false;
		}

		return true;
	}
	
	/**
	 * 将浮点数转换成字符串，保留两位小数
	 */
	public static String formatDouble(double d) {
		return new DecimalFormat("##0.00").format(d);
	}
}
