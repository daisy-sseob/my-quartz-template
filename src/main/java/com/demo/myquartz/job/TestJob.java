package com.demo.myquartz.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 테스트용 Class입니다.
 */
@Slf4j
public class TestJob implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		log.info("### ====================================================");
		log.info("### Test Job 테스트 Job 입니다. quartz 정상 동작 여부 확인용.");
		log.info("### ====================================================");
	}
}
