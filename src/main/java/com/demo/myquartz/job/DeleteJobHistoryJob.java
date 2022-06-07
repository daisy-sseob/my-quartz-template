package com.demo.myquartz.job;

import com.demo.myquartz.common.JobLoggingUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.LocalDate;

/**
 * 
 * Job history log file들을 삭제하는 Job 입니다.
 * 
 * 3달전 log file들을 삭제합니다.
 * 
 * @author shs
 */
@Component
@DisallowConcurrentExecution
public class DeleteJobHistoryJob implements Job {

	@Autowired
	private JobLoggingUtils jobLoggingUtils;
	
	@Value("${job.log.max.history}")
	private int maxHistory;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		final LocalDate targetDate = LocalDate.now().minusMonths(maxHistory);
		final File logFileOfDate = jobLoggingUtils.getJobLogFileOfDate(targetDate);
		if (logFileOfDate.exists()) {
			logFileOfDate.delete();
		}
		
		jobLoggingUtils.clearRootDirectory(logFileOfDate, targetDate);
	}
}
