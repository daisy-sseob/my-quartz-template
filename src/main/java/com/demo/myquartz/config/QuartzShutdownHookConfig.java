package com.demo.myquartz.config;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * Spring context가 파괴되며 Bean이 destroy되기 직전
 * Quartz Schedule을 종료하기위한 config임.
 * 종료하기전에 job목록 logging
 * 
 * @author shs
 */
@Slf4j
@Component
public class QuartzShutdownHookConfig {

	private final Scheduler scheduler;
	private final String schedulerName;
	private final String shutdownMsg;
	private final String alreadyShutdownMsg;
	
	@Autowired 
	public QuartzShutdownHookConfig(SchedulerFactoryBean schedulerFactory,
	                                String schedulerName,
	                                String shutdownMsg,
	                                String alreadyShutdownMsg) {
		this.scheduler = schedulerFactory.getScheduler();
		this.schedulerName = schedulerName;
		this.shutdownMsg = shutdownMsg;
		this.alreadyShutdownMsg = alreadyShutdownMsg;
	}

	@PreDestroy
	public void destroy() throws SchedulerException{
		Boolean isShutDown = scheduler.isShutdown();
		log.info("### Scheduler isShutdown() = {}", isShutDown);
		
		if (!isShutDown) {
			
			for(String group: scheduler.getJobGroupNames()) {
				for( JobKey jobKey : scheduler.getJobKeys(GroupMatcher.groupEquals(group)) ) {
					log.info("### Found job identified by = {}", jobKey);
				}
			}
			
			scheduler.shutdown(true);
			log.info("\n### ======================= "+ schedulerName +" =======================" + shutdownMsg);
		} else {
			log.info("\n### ======================= "+ schedulerName +" =======================" + alreadyShutdownMsg);
		}
	}
}
