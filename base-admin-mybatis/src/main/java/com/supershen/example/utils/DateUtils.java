package com.supershen.example.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期操作工具类.
 */
public class DateUtils {

	public static final String formatDate = "yyyy-MM-dd";

	public static final String formatDateChinese = "yyyy年M月d日";

	public static final String formatDateTime = "yyyy-MM-dd HH:mm:ss";

	/** 每天的毫秒数 */
	private static final int HAO_MIAO_SHU = 86400000;

	private DateUtils() {

	}

	/**
	 * 将指定日期转换成中文大写的字符串
	 * 
	 * @param d 日期对象
	 * @return 转换后的日期字符
	 */
	public static String getCapsDate(java.util.Date d) {
		return getCapsDate(DateUtils.toString(d));
	}

	/**
	 * 将字串形式的日期转换成中文大写的形式.
	 * 
	 * @param date 日期字符串(yyyy-MM-dd)
	 * 
	 * @return 转换后的日期字符
	 */
	public static String getCapsDate(String date) {
		// String str = "2008-12-31";
		String result = null;

		int nub = 0;
		String year = "";

		for (int i = 0; i < 4; i++) {
			nub = Integer.parseInt(date.substring(i, i + 1));
			year = year + getDateStr(nub);
		}

		String month = "";
		nub = Integer.parseInt(date.substring(5, 7));

		if (nub < 10) {
			month = getDateStrZero(nub);
		} else {
			if (nub == 10) {
				month = "十";
			} else {
				month = "十";
				nub = Integer.parseInt(date.substring(6, 7));
				month = month + getDateStr(nub);
			}
		}

		String day = "";
		nub = Integer.parseInt(date.substring(8, 10));

		if (nub < 10) {
			day = getDateStrZero(nub);
		} else {
			if ((nub == 10) || (nub == 20) || (nub == 30)) {
				if (nub == 10) {
					day = "十";
				} else {
					nub = Integer.parseInt(date.substring(8, 9));
					day = getDateStr(nub) + "十";
				}

			} else {
				if ((nub < 20) && (nub > 10)) {
					day = "十";
					nub = Integer.parseInt(date.substring(9, 10));
					day = day + getDateStr(nub);
				} else {
					nub = Integer.parseInt(date.substring(8, 9));
					day = getDateStr(nub) + "十";
					nub = Integer.parseInt(date.substring(9, 10));
					day = day + getDateStr(nub);
				}

			}
		}

		result = year + "年" + month + "月" + day + "日";

		return result;
	}

	/**
	 * 得到当前的日期时间.
	 * 
	 * @return 当期日期对象
	 */
	public static java.util.Date getCurrentDate() {
		return new java.util.Date();
	}

	private static String getDateStr(int nub) {
		String str = "";
		switch (nub) {
		case 0:
			str = "O";
			break;
		case 1:
			str = "一";
			break;
		case 2:
			str = "二";
			break;
		case 3:
			str = "三";
			break;
		case 4:
			str = "四";
			break;
		case 5:
			str = "五";
			break;
		case 6:
			str = "六";
			break;
		case 7:
			str = "七";
			break;
		case 8:
			str = "八";
			break;
		case 9:
			str = "九";
			break;
		default:
			break;
		}

		return str;
	}

	private static String getDateStrZero(int nub) {
		String str = "";
		switch (nub) {
		case 0:
			str = "十";
			break;
		case 1:
			str = "一";
			break;
		case 2:
			str = "二";
			break;
		case 3:
			str = "三";
			break;
		case 4:
			str = "四";
			break;
		case 5:
			str = "五";
			break;
		case 6:
			str = "六";
			break;
		case 7:
			str = "七";
			break;
		case 8:
			str = "八";
			break;
		case 9:
			str = "九";
			break;
		default:
			break;
		}

		return str;
	}

	/**
	 * 得到时间置零util.Date的日期时间.
	 * 
	 * @return 时间置零的一个日期对象
	 */
	public static java.util.Date getSimpleCurrentDate() {
		java.util.Date dd = DateUtils.getCurrentDate();
		return DateUtils.toDate(DateUtils.toString(dd));

	}

	/**
	 * 按默认格式(yyyy-MM-dd)将日期字符串转换成日期类型的对象.
	 * 
	 * @param strDate 日期字符串（yyyy-MM-dd）
	 * 
	 * @return 转换后的一个日期类型对象
	 */
	public static java.util.Date toDate(String strDate) {
		return DateUtils.toDate(DateUtils.formatDate, strDate);
	}

	/**
	 * 将指定格式的日期字符串转换成日期类型.
	 * 
	 * @param pattern 日期格式
	 * @param strDate 日期字符串
	 * 
	 * @return 转换后的一个日期类型对象
	 */
	public static java.util.Date toDate(String pattern, String strDate) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			return sdf.parse(strDate);
		} catch (ParseException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public static String toOraDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("d-M月-yyyy");
		return sdf.format(date);
	}

	public static String toOraDate(String date) {
		Date d = DateUtils.toDate(date);
		SimpleDateFormat sdf = new SimpleDateFormat("d-M月-yyyy");
		return sdf.format(d);
	}

	/**
	 * 将日期对象转换成指定格式的字符串
	 * 
	 * @param d 日期对象
	 * @param f 格式字符串
	 * 
	 * @return 转换后的一个日期字符串
	 */
	public static String toString(java.sql.Date d, String f) {
		String result = "";
		SimpleDateFormat sdf = new SimpleDateFormat(f);

		if (d != null) {
			result = sdf.format(d);
		}

		return result;
	}

	/**
	 * 按默认格式(yyyy-MM-dd)将指定日期对象转换成字符串
	 * 
	 * @param d 日期对象(yyyy-MM-dd)
	 * @return 转换后的一个日期字符串
	 */
	public static String toString(java.util.Date date) {
		return DateUtils.toString(date, DateUtils.formatDate);
	}

	/**
	 * 将指定日期对象转换成字符串.
	 * 
	 * @param d 日期对象
	 * @param f 格式字符串
	 * @return 转换后的一个日期字符串
	 */
	public static String toString(java.util.Date d, String f) {
		String result = "";
		SimpleDateFormat sdf = new SimpleDateFormat(f);

		if (d != null) {
			result = sdf.format(d);
		}

		return result;
	}

	/**
	 * 按中文格式(yyyy年M月d日)将指定日期对象转换成字符串
	 * 
	 * @param d 日期对象
	 * @return 转换后的一个日期字符串
	 */
	public static String toChineseString(java.util.Date d) {
		return DateUtils.toString(d, DateUtils.formatDateChinese);
	}

	/**
	 * 检查日期格式是否合法.
	 * 
	 * @param date 日期字符串(yyyy-MM-dd)
	 */
	public static boolean isLegalDate(String date) {
		int[][] month = { { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 },
				{ 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 } };

		int yyyy = 0;
		int mm = 0;
		int dd = 0;
		int uruu = 0;

		if ((date == null) || (date.length() != 10)) {
			return false;
		}

		String[] gt = date.split("-");

		if (gt.length != 3) {
			return false;
		}

		try {
			yyyy = Integer.parseInt(gt[0]);
			mm = Integer.parseInt(gt[1]);
			dd = Integer.parseInt(gt[2]);

		} catch (Exception ex) {
			return false;
		}

		uruu = 0;
		if ((yyyy % 4) == 0) {
			uruu = 1;
			if ((yyyy % 100) == 0) {
				uruu = 0;
				if ((yyyy % 400) == 0) {
					uruu = 1;
				}
			}
		}

		if ((mm < 1) || (mm > 12)) {
			return false;
		}

		if ((dd < 1) || (dd > month[uruu][mm - 1])) {
			return false;
		}

		return true;
	}

	/**
	 * 给两个日期做减法.
	 */
	public static int subtract(Date s, Date t) {
		String ss = DateUtils.toString(s);
		String tt = DateUtils.toString(t);

		Date sss = DateUtils.toDate(ss);
		Date ttt = DateUtils.toDate(tt);

		return (int) ((sss.getTime() - ttt.getTime()) / HAO_MIAO_SHU);
	}

	/**
	 * 给指定的日期加指定的天数.
	 */
	public static Date addDate(Date d, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.DAY_OF_MONTH, days);
		return cal.getTime();
	}

	/**
	 * 日期格式转换
	 * 
	 * @param dateStr 21-三月-2015
	 * @return 2015-3-21
	 */
	public static Date dateStrChange(String dateStr) {
		String[] date = dateStr.split("-");
		String year = date[2];
		String month = monthChange(date[1]);
		String day = date[0];

		return DateUtils.toDate(year + "-" + month + "-" + day);
	}

	/**
	 * 月份小写转大写
	 */
	private static String monthChange(String month) {
		if ("十二月".equals(month)) {
			return "12";
		} else if ("十一月".equals(month)) {
			return "11";
		} else if ("十月".equals(month)) {
			return "10";
		} else if ("九月".equals(month)) {
			return "9";
		} else if ("八月".equals(month)) {
			return "8";
		} else if ("七月".equals(month)) {
			return "7";
		} else if ("六月".equals(month)) {
			return "6";
		} else if ("五月".equals(month)) {
			return "5";
		} else if ("四月".equals(month)) {
			return "4";
		} else if ("三月".equals(month)) {
			return "3";
		} else if ("二月".equals(month)) {
			return "2";
		} else {
			return "1";
		}

	}
}
