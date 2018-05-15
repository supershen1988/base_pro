package com.supershen.example;

/**
 * 应用程序配置类.
 */
public class AppConf {
	private static AppConf sp;

	public static AppConf getInstantce() {
		if (null == sp) {
			sp = new AppConf();
		}

		return sp;
	}

	private AppConf() {
		super();
	}

	

}