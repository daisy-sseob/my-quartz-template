package com.demo.myquartz.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;
import java.util.EnumSet;

public class WebInitializer implements WebApplicationInitializer {
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.setDisplayName("MyQuartz");
		context.setServletContext(servletContext);
		context.register(ServerConfig.class);
		context.refresh();

		servletContext.addListener(new SessionListener());

		DispatcherServlet dispatcher = new DispatcherServlet(context); // 메인 Thread 하위 child Thread에서도 RequestContextHolder의 Request 객체를 사용할 수 있다.
		dispatcher.setThreadContextInheritable(true);
		
		Dynamic servlet = servletContext.addServlet("dispatcher", dispatcher);
		servlet.addMapping("/");
		servlet.setLoadOnStartup(1);
		
		servletContext
				.addFilter("encodingFilter", new CharacterEncodingFilter("UTF-8",true))
				.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
	}
}
