package com.dreamsjewelrystudio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import com.dreamsjewelrystudio.interceptors.AdminSessionInterceptor;
import com.dreamsjewelrystudio.interceptors.UserSessionInterceptor;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.dreamsjewelrystudio.controllers")
public class WebConfig implements WebMvcConfigurer{
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("/static/");
		registry.addResourceHandler("/WEB-INF/**").addResourceLocations("/WEB-INF/");
	}
	
	@Bean 
	public FreeMarkerViewResolver freemarkerViewResolver() { 
	    FreeMarkerViewResolver resolver = new FreeMarkerViewResolver(); 
	    resolver.setPrefix(""); 
	    resolver.setSuffix(".ftl"); 
	    return resolver; 
	}
	
	@Bean 
	public FreeMarkerConfigurer freemarkerConfig() { 
	    FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer(); 
	    freeMarkerConfigurer.setTemplateLoaderPath("/WEB-INF/templates/");
	    return freeMarkerConfigurer; 
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(getAdminInterceptor())
			.addPathPatterns("/admin","/admin/*")
			.excludePathPatterns("/admin/alogin");
		
		registry.addInterceptor(getUserSessionInterceptor())
		.addPathPatterns("/deleteItemFromCart", "/changeAmount", "/addToCart");
	}
	
	@Bean
	public AdminSessionInterceptor getAdminInterceptor() {
		return new AdminSessionInterceptor();
	}
	
	@Bean
	public UserSessionInterceptor getUserSessionInterceptor() {
		return new UserSessionInterceptor();
	}
}