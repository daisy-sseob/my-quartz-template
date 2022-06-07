package com.demo.myquartz.common.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.Trigger.CompletedExecutionInstruction;
import org.quartz.TriggerListener;

import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Slf4j
public class MyQuartzTriggerListener implements TriggerListener{
	
	private final String name;
	private final String schedulerName;

	public MyQuartzTriggerListener(String name, String schedulerName) {
		this.name = name;
		this.schedulerName = schedulerName;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void triggerFired(Trigger trigger, JobExecutionContext context) {
		log.info("###" + schedulerName + " Trigger triggerFired. fire time = {}, job runtime = {}, job key = {}",
				context.getFireTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)),
				context.getJobRunTime(),
				trigger.getJobKey());
	}

	@Override
	public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
		return false;
	}

	@Override
	public void triggerMisfired(Trigger trigger) {
		log.info("### "+ schedulerName + "job = {} Trigger triggerMisfired", trigger.getJobKey());
	}

	@Override
	public void triggerComplete(Trigger trigger, JobExecutionContext context, CompletedExecutionInstruction triggerInstructionCode) {
		log.info("### "+ schedulerName + "job = {} Trigger triggerComplete", trigger.getJobKey());
	}
}
