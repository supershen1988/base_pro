package com.supershen.example.core;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.shiro.SecurityUtils;

import com.supershen.example.config.shiro.ShiroDbRealm.ShiroUser;

public class UserHelper {
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static String day(){
		
		return dateFormat.format(new Date());
	}
	
	/**
	 * 获取当前登录用户信息.
	 */
	public static ShiroUser getCurrentUser() {
		return (ShiroUser) SecurityUtils.getSubject().getPrincipal();
	}

	/**
	 * 取出Shiro中的当前用户Id.
	 */
	public static Integer getCurrentUserId() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();

		if (null != user) {
			return user.id;
		}

		return null;
	}
}
