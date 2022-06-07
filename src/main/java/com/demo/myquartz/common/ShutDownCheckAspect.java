package com.demo.myquartz.common;

import com.demo.myquartz.common.dto.QuartzMsg;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

/**
 * Scheduler가 Shutdown되었으면 target method를 실행시키지 않고 return합니다.
 * 
 * @author shs
 */
@Slf4j
@Component
@Aspect
public class ShutDownCheckAspect {

	private final Scheduler scheduler;
	private final String schedulerName;
	private final String alreadyShutdownMsg;
	
	@Autowired
	public ShutDownCheckAspect(SchedulerFactoryBean schedulerFactory, String schedulerName, String alreadyShutdownMsg) {
		this.scheduler = schedulerFactory.getScheduler();
		this.schedulerName = schedulerName;
		this.alreadyShutdownMsg = alreadyShutdownMsg;
	}
	
	@Around("@annotation(com.demo.myquartz.common.annotation.ShutdownCheck)")
	public Object shutdownCheck(ProceedingJoinPoint joinPoint) throws Throwable {
		if (scheduler.isShutdown()) {
			String body = "[shutdown detector] " + this.schedulerName + "는 이미 shutdown 되었습니다. shutdown된 scheduler는 다시 사용할 수 없습니다. Application을 다시 시작해야합니다." + this.alreadyShutdownMsg;
			log.info(body);
			return ResponseEntity.ok().body(new QuartzMsg.Builder().msg(body).build());
		} else {
			return joinPoint.proceed();
		} 
	}
}
