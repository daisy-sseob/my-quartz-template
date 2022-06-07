package com.demo.myquartz.common.quartz;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.demo.myquartz.common.JobLoggingUtils;
import com.demo.myquartz.common.dto.JobHistory;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Slf4j
@Component
public class MyQuartzJobListener implements JobListener{
	
	private final String name;
	private final String schedulerName;
	private final ObjectMapper objectMapper;
	private final JobLoggingUtils jobLoggingUtils;
	
	public MyQuartzJobListener(String schedulerName, ObjectMapper objectMapper, JobLoggingUtils jobLoggingUtils) {
		this.name = "Job Listener";
		this.schedulerName = schedulerName;
		this.objectMapper = objectMapper;
		this.jobLoggingUtils = jobLoggingUtils;
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	/**
	 * job 실행전
	 */
	@Override
	public void jobToBeExecuted(JobExecutionContext context){
		log.debug("### "+ schedulerName +" = job 실행 전 {}", context.getJobDetail().getKey());
	}

	/**
	 * job 실행 완료
	 */
	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {
		log.debug("### "+ schedulerName +" job 실행 중지 = {}", context.getJobDetail().getKey());
	}

	/**
	 * job 실행후 history logging
	 */
	@Override
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		
		loggingJobHistory(new JobHistory
				.Builder()
				.jobKey(context.getJobDetail().getKey().toString())
				.jobRunTime(context.getJobRunTime())
				.fireTime( context.getFireTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)))
				.nextFireTime(context.getNextFireTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)))
				.isSuccess(jobException == null)
				.jobExceptionMessage(jobException == null ? null : jobException.getMessage())
				.build());
		
		log.info("### "+ schedulerName +" job was executed. logging success = {}", context.getJobDetail().getKey());
	}

	// job history logging to log file
	private void loggingJobHistory(JobHistory jobHistory) {
		
		String jobHistoryStr = "";
		try {
			jobHistoryStr = objectMapper.writeValueAsString(jobHistory);
		} catch (JsonProcessingException e) {
			jobHistoryStr = jobHistory.toString();
		}
		
		final File jobLogPath = this.jobLoggingUtils.getJobLoggingDirectory();
		if (!jobLogPath.exists()) {
			jobLogPath.mkdirs();
		}
		
		final File todayLoggingFile = this.jobLoggingUtils.getJobLogFileOfDate(LocalDate.now());
		try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(todayLoggingFile, todayLoggingFile.exists()))){
			bufferedWriter.write(jobHistoryStr + System.lineSeparator());
		} catch (Exception e) {
			log.error("Job was executed logging fail", e);
		}
	}
}
