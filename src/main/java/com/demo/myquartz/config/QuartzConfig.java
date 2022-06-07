package com.demo.myquartz.config;

import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * Quartz는 spring의 Application context 생성시점과는 별개로
 * 동작하기 때문에 Scheduler를 생성시켜 ApplicationContext에 넣어준다.
 * 
 * Scheduler name과 Scheduler를 사용하지 않았을 때의 name을 Bean으로 생성함.
 * 
 * @author shs
 */
@Configuration
public class QuartzConfig {
	
	private final ApplicationContext context;
	
	@Autowired
	public QuartzConfig(ApplicationContext context) {
		this.context = context;
	}

	@Bean
	public JobFactory jobFactory() {
		AutoWiringSpringBeanJobFactory factory = new AutoWiringSpringBeanJobFactory();
		factory.setApplicationContext(context);
		return factory;
	}
	
	@Bean
	public SchedulerFactoryBean schedulerFactoryBean() {
		SchedulerFactoryBean factory = new SchedulerFactoryBean();
		factory.setAutoStartup(false); //초기화 이후 자동시작여부
		factory.setSchedulerName(this.schedulerName());
		factory.setOverwriteExistingJobs(true); // 기존 작업을 덮어쓸지 여부
		factory.setJobFactory(jobFactory());
		return factory;
	}
	
	@Bean
	public String schedulerName() {
		return "MyQuartzScheduler";
	}
	@Bean
	public String nonScheduleName(){
		return "HTTP_REQUEST";
	}
	@Bean String startMsg() {
		return "\n███████╗████████╗ █████╗ ██████╗ ████████╗\n██╔════╝╚══██╔══╝██╔══██╗██╔══██╗╚══██╔══╝\n███████╗   ██║   ███████║██████╔╝   ██║   \n╚════██║   ██║   ██╔══██║██╔══██╗   ██║   \n███████║   ██║   ██║  ██║██║  ██║   ██║";
	}
	@Bean String shutdownMsg() {
		return "\n███████╗██╗  ██╗██╗   ██╗████████╗██████╗  ██████╗ ██╗    ██╗███╗   ██╗\n██╔════╝██║  ██║██║   ██║╚══██╔══╝██╔══██╗██╔═══██╗██║    ██║████╗  ██║\n███████╗███████║██║   ██║   ██║   ██║  ██║██║   ██║██║ █╗ ██║██╔██╗ ██║\n╚════██║██╔══██║██║   ██║   ██║   ██║  ██║██║   ██║██║███╗██║██║╚██╗██║\n███████║██║  ██║╚██████╔╝   ██║   ██████╔╝╚██████╔╝╚███╔███╔╝██║ ╚████║\n╚══════╝╚═╝  ╚═╝ ╚═════╝    ╚═╝   ╚═════╝  ╚═════╝  ╚══╝╚══╝ ╚═╝  ╚═══╝";
	}
	@Bean String standbyMsg() {
		return "\n███████╗████████╗ █████╗ ███╗   ██╗██████╗ ██████╗ ██╗   ██╗\n██╔════╝╚══██╔══╝██╔══██╗████╗  ██║██╔══██╗██╔══██╗╚██╗ ██╔╝\n███████╗   ██║   ███████║██╔██╗ ██║██║  ██║██████╔╝ ╚████╔╝\n╚════██║   ██║   ██╔══██║██║╚██╗██║██║  ██║██╔══██╗  ╚██╔╝\n███████║   ██║   ██║  ██║██║ ╚████║██████╔╝██████╔╝   ██║";
	}
	@Bean String alreadyShutdownMsg() {
		return "\n █████╗ ██╗     ██████╗ ███████╗ █████╗ ██████╗ ██╗   ██╗    ███████╗██╗  ██╗██╗   ██╗████████╗██████╗  ██████╗ ██╗    ██╗███╗   ██╗\n██╔══██╗██║     ██╔══██╗██╔════╝██╔══██╗██╔══██╗╚██╗ ██╔╝    ██╔════╝██║  ██║██║   ██║╚══██╔══╝██╔══██╗██╔═══██╗██║    ██║████╗  ██║\n███████║██║     ██████╔╝█████╗  ███████║██║  ██║ ╚████╔╝     ███████╗███████║██║   ██║   ██║   ██║  ██║██║   ██║██║ █╗ ██║██╔██╗ ██║\n██╔══██║██║     ██╔══██╗██╔══╝  ██╔══██║██║  ██║  ╚██╔╝      ╚════██║██╔══██║██║   ██║   ██║   ██║  ██║██║   ██║██║███╗██║██║╚██╗██║\n██║  ██║███████╗██║  ██║███████╗██║  ██║██████╔╝   ██║       ███████║██║  ██║╚██████╔╝   ██║   ██████╔╝╚██████╔╝╚███╔███╔╝██║ ╚████║\n╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝╚═════╝    ╚═╝       ╚══════╝╚═╝  ╚═╝ ╚═════╝    ╚═╝   ╚═════╝  ╚═════╝  ╚══╝╚══╝ ╚═╝  ╚═══╝";
	}
}
