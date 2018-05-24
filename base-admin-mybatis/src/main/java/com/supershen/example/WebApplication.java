package com.supershen.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
//SpringBoot 应用标识
@SpringBootApplication
@MapperScan("com.supershen.example.dao")
public class WebApplication 
{
	/**
	 * 启动嵌入式的Tomcat并初始化Spring环境
	 */
	public static void main(String[] args) throws Exception {
		SpringApplication.run(WebApplication.class, args);
	}
}
