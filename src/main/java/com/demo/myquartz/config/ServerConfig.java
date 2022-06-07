package com.demo.myquartz.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@ComponentScan(basePackages={"com"})
@EnableWebMvc
@PropertySource("classpath:property/data.properties")
public class ServerConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
	    registry.addResourceHandler("/js/**").addResourceLocations("/js/");
	    registry.addResourceHandler("/css/**").addResourceLocations("/css/");
	    registry.addResourceHandler("/assets/**").addResourceLocations("/assets/");
	}
	
	/**
	 * 	<bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
	 *	<property name="maxUploadSize" value="52428800"></property>
	 *	<property name="maxInMemorySize" value="52428800"></property>
	 *   </bean>
	 */
//	@Bean
//	public MultipartResolver multipartResolver() {
//	    return new CommonsMultipartResolver();
//	}
	
	@Bean
	public InternalResourceViewResolver  setInternalResourceViewResolver() {
		InternalResourceViewResolver  resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/view/");
		resolver.setSuffix(".jsp");
		resolver.setOrder(2);
		resolver.setViewClass(JstlView.class);
		return resolver;
	}
	
	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}
