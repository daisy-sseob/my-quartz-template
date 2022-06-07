package com.demo.myquartz.common.quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.stereotype.Component;

/**
 * Quartz 
 * 
 * Job, Trigger Create and schedule 등록
 * 
 * @author shs
 */
@Component
public class QuartzCreater {

	/**
	 * job, trigger를 생성하고 Schedule에 등록시킨다.
	 * 
	 * @param scheduler Job을 등록시킬 Scheduler
	 * @param clazz Job을 구현시킨 JobDetail Class
	 * @param cronExpression 크론 표현식. 
	 * ref. http://www.cronmaker.com/?0 여기서 크론표현식 검증하고 돌리세요 진짜 큰일남
	 * 
	 */
	public void create(Scheduler scheduler, Class<? extends Job> clazz, String cronExpression) throws SchedulerException {
		JobDetail job = JobBuilder
							.newJob(clazz)
							.withIdentity(clazz.getName(), "myQuartz")
							.build();
		
		Trigger trigger = TriggerBuilder
							.newTrigger()
							.withIdentity(clazz.getName(), "myQuartz")
							.withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
							.build();
		
		scheduler.scheduleJob(job, trigger);
	}
	
	/**
	 * job, trigger를 생성하고 
	 * ScheduleBulder를 상속받은 객체로 Schdule 등록
	 * 
	 * @param scheduler Job을 등록시킬 Scheduler
	 * @param clazz Job을 구현시킨 JobDetail Class
	 * @param simpleSchedule SimpleScheduleBuilder로 작성한 스케줄.
	 */
	public void create(Scheduler scheduler, Class<? extends Job> clazz, SimpleScheduleBuilder simpleSchedule) throws SchedulerException {
		JobDetail job = JobBuilder
				.newJob(clazz)
				.withIdentity(clazz.getName(), "myQuartz")
				.build();
		
		Trigger trigger = TriggerBuilder
				.newTrigger()
				.withIdentity(clazz.getName(), "myQuartz")
				.withSchedule(simpleSchedule)
				.build();
		
		scheduler.scheduleJob(job, trigger);
	}
}
