package com.demo.myquartz.config;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.stereotype.Component;

/**
 * org.quartz.Job Interface를 상속받은 class에서 autowired를 사용하기위함.
 * 
 * Spring과 Quartz의 Bean을 통합? 한다.
 * 
 * @author shs
 * @ref bealdung.
 */
@Component
public final class AutoWiringSpringBeanJobFactory extends SpringBeanJobFactory implements ApplicationContextAware{

	private static ApplicationContext context;
	
	private transient AutowireCapableBeanFactory beanFactory;
	
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.beanFactory = applicationContext.getAutowireCapableBeanFactory();
		context = applicationContext;
	}
	
	@Override
	protected Object createJobInstance(final TriggerFiredBundle bundle) throws Exception {
		final Object job = super.createJobInstance(bundle);
		beanFactory.autowireBean(job);
		return job;
	}
}
