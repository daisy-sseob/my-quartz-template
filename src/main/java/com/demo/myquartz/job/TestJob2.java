package com.demo.myquartz.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 테스트용 Class입니다.
 */
@Slf4j
public class TestJob2 implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		log.info("### ====================================================");
		log.info("### Test Job2 테스트2 Job 입니다. quartz 정상 동작 여부 확인용.");
		log.info("### ====================================================");
	}
}
