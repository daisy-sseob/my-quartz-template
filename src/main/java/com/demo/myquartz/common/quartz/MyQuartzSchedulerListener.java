package com.demo.myquartz.common.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Trigger;
import org.quartz.listeners.SchedulerListenerSupport;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Slf4j
public class MyQuartzSchedulerListener extends SchedulerListenerSupport{

	private final String name;
	
	public String getName(){
		return this.name;
	}
	
	public MyQuartzSchedulerListener(String name) {
		this.name = name;
	}
	
	@Override
	public void schedulerStarted() {
		log.debug("### " + this.name + " Started.");
	}
	
	@Override
	public void schedulerShutdown() {
		log.debug("### " + this.name + " Shutdown.");
	}
	
	@Override
	public void jobScheduled(Trigger trigger) {
		log.debug("### " + this.name + "jobScheduled");
		log.debug("### " + this.name + "End time = {}", trigger.getEndTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
		log.debug("### " + this.name + "Final fire time = {}", trigger.getFinalFireTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
		log.debug("### " + this.name + "Next fire time = {}", trigger.getNextFireTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
	}
}
