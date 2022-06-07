package com.demo.myquartz.common;

import com.demo.myquartz.common.dto.ErrorLog;
import com.demo.myquartz.repository.MyQuartzRepository;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 
 * error log를 남기기위한 aspect 입니다.
 * 
 * @author shs
 */
@Slf4j
@Aspect
@Component
public class LoggingExceptionAspect {
	
	private final String nonScheduleName;
	private final MyQuartzRepository myQuartzRepository;
	
	@Autowired
	public LoggingExceptionAspect(String nonScheduleName, MyQuartzRepository myQuartzRepository) {
		this.nonScheduleName = nonScheduleName;
		this.myQuartzRepository = myQuartzRepository;
	}

	/**
	 * Exception을 logging 한다. exception 발생 후 동작함. 
	 * @param joinPoint handler method
	 * @param e CustomException
	 */
	@AfterThrowing(value = "@annotation(com.demo.myquartz.common.annotation.LoggingException)", throwing = "e")
	public void logging(JoinPoint joinPoint, CustomException e){
		
		ErrorLog errorLog = ErrorLog
				.builder()
				.excpNm(e.toString())
				.excpCn(e.getCause().getMessage())
				.excpTraceCn(e.getStackTraceString())
				.dvlprDc(e.getDeveloperDc() + "\n" + e.getContextInfo())
				.vriablCn(e.getParam())
				.lastChangerId(Optional.ofNullable(e.getSchedulerName()).orElseGet(() -> this.nonScheduleName))
				.build();
		
		// 예외 로그 저장
		int result = myQuartzRepository.insErrorLog(errorLog);
	}
}
