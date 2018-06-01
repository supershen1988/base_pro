package com.supershen.example;

import javax.servlet.Filter;
import javax.validation.Validator;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.supershen.example.config.MySiteMeshFilter;

/**
 * Hello world!
 *
 */
//SpringBoot 应用标识
@SpringBootApplication
//扫描dao或者是Mapper接口
@MapperScan("com.supershen.example.dao")
public class WebApplication extends SpringBootServletInitializer {
	/**
	 * 启动嵌入式的Tomcat并初始化Spring环境
	 */
	public static void main(String[] args) throws Exception {
		SpringApplication.run(WebApplication.class, args);
	}
	
	/**
	 * 容器内启动支撑方法.
	 */
	@Override    
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {    
		return application.sources(WebApplication.class);    
	}  
	
	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer() {
		return new EmbeddedServletContainerCustomizer() {
			@Override
			public void customize(ConfigurableEmbeddedServletContainer container) {
				ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/WEB-INF/views/error/401.jsp");
				ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/WEB-INF/views/error/404.jsp");
				ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR,
						"/WEB-INF/views/error/500.jsp");

				container.addErrorPages(error401Page, error404Page, error500Page);
			}
		};
	}
	
	@Bean
	// JSR303 Validator定义
	public Validator localValidatorFactoryBean() {
		return new LocalValidatorFactoryBean();
	}
	
	//siteMesh支持
//	@Bean
//	public Filter sitemeshFilter() {
//		return new MySiteMeshFilter();
//	}
}


