package com.supershen.example.config.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;

/**
 * mybatis配置
 * 
 * @author guoshen
 * 
 */
@Configuration
// 扫描dao或者是Mapper接口
@MapperScan("com.supershen.example.dao")
public class MybatisPlusConfig {
	/**
	 * mybatis-plus 分页插件
	 */
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		PaginationInterceptor page = new PaginationInterceptor();
		page.setDialectType("mysql");
		return page;
	}
}
