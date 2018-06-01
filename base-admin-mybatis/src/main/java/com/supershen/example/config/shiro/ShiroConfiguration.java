package com.supershen.example.config.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

@Configuration
public class ShiroConfiguration {

	private static Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();

	@Bean(name = "shiroEhcacheManager")
	public EhCacheManager getEhCacheManager() {
		EhCacheManager em = new EhCacheManager();
		em.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
		return em;
	}

	@Bean(name = "shiroRealm")
	public ShiroDbRealm shiroRealm(EhCacheManager shiroCacheManager) {
		ShiroDbRealm realm = new ShiroDbRealm();
		realm.setCacheManager(shiroCacheManager);
		return realm;
	}

	@Bean
	public AuthenticationFilter authenticationFilter() {
		return new FormAuthenticationFilter();
	}

	@Bean(name = "securityManager")
	public DefaultWebSecurityManager securityManager(ShiroDbRealm shiroRealm, EhCacheManager shiroCacheManager) {
		DefaultWebSecurityManager dwsm = new DefaultWebSecurityManager();
		dwsm.setRealm(shiroRealm);
		dwsm.setCacheManager(shiroCacheManager);
		return dwsm;
	}

	@Bean(name = "lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	@Bean
	public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();

		daap.setProxyTargetClass(true);

		return daap;
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(
			DefaultWebSecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();

		aasa.setSecurityManager(securityManager);

		return aasa;
	}

	/**
	 * 注册DelegatingFilterProxy（Shiro）
	 */
	@Bean
	public FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean filterRegistration = new FilterRegistrationBean();

		filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
		// 该值缺省为false,表示生命周期由SpringApplicationContext管理,设置为true则表示由ServletContainer管理
		filterRegistration.addInitParameter("targetFilterLifecycle", "true");
		filterRegistration.setEnabled(true);
		filterRegistration.addUrlPatterns("/*");

		return filterRegistration;
	}

	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager securityManager,
			AuthenticationFilter authenticationFilter) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

		// shiroFilterFactoryBean.getFilters().put("perms", new URLPermissionsFilter());
		
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		
		shiroFilterFactoryBean.setLoginUrl("/login");
		shiroFilterFactoryBean.setSuccessUrl("/");

		filterChainDefinitionMap.put("/login", "authc");
		filterChainDefinitionMap.put("/logout", "logout");
		filterChainDefinitionMap.put("/static/**", "anon");

		filterChainDefinitionMap.put("/", "authc");
		filterChainDefinitionMap.put("/**", "authc");
		
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

		return shiroFilterFactoryBean;
	}
}
