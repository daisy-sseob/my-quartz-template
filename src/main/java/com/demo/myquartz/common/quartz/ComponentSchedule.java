package com.demo.myquartz.common.quartz;

import com.demo.myquartz.common.controller.ManageController;
import com.demo.myquartz.job.DeleteJobHistoryJob;
import com.demo.myquartz.job.SendMailJob;
import org.quartz.ListenerManager;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.matchers.EverythingMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ComponentSchedule {

	private final QuartzCreater quartzCreater;
	private final Scheduler scheduler;
	private final String schedulerName;
	private final MyQuartzJobListener myQuartzJobListener;
	private final ManageController manageController;

	@Autowired
	public ComponentSchedule(SchedulerFactoryBean schedulerFactory,
	                         QuartzCreater quartzCreater,
	                         String schedulerName,
	                         MyQuartzJobListener myQuartzJobListener,
	                         ManageController manageController) {
		this.scheduler = schedulerFactory.getScheduler();
		this.quartzCreater = quartzCreater;
		this.schedulerName = schedulerName;
		this.myQuartzJobListener = myQuartzJobListener;
		this.manageController = manageController;
	}

	@PostConstruct
	public void start() throws SchedulerException {
//		quartzCreater.create(scheduler, TestJob.class, SimpleScheduleBuilder.repeatSecondlyForever(5)); // 테스트용
//		quartzCreater.create(scheduler, TestJob2.class, SimpleScheduleBuilder.repeatSecondlyForever(10)); // 테스트용
		quartzCreater.create(this.scheduler, DeleteJobHistoryJob.class, "0 30 0 * * ?"); // (오늘 - max history (month)) 날짜에 해당하는 job log file 삭제 
		quartzCreater.create(this.scheduler, SendMailJob.class, "0 0/5 * * * ?"); // mail, sms 발송 job
		
		setListener(this.scheduler); //listener 등록
//		manageController.start();
	}
	
	/**
	 * scheduler, job, trigger listener등록
	 */
	public void setListener( Scheduler scheduler ) throws SchedulerException{
		ListenerManager listenerManager = scheduler.getListenerManager();
		listenerManager.addSchedulerListener(new MyQuartzSchedulerListener(this.schedulerName + "Listener"));
		listenerManager.addJobListener(this.myQuartzJobListener, EverythingMatcher.allJobs());
		listenerManager.addTriggerListener(new MyQuartzTriggerListener("MyQuartzTriggerListener", this.schedulerName),EverythingMatcher.allTriggers());
	}
}
