package com.demo.myquartz.common.controller;

import com.demo.myquartz.common.annotation.ShutdownCheck;
import com.demo.myquartz.common.dto.QuartzMsg;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Scheduler의 생명주기를 manage하는 controller입니다.
 */
@Slf4j
@Controller
@RequestMapping(value = "/schedule", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ManageController {
	
	private final Scheduler scheduler;
	private final String schedulerName;
	private final String shutdownMsg;
	private final String standbyMsg;
	private final String startMsg;
	
	@Autowired
	public ManageController(SchedulerFactoryBean schedulerFactory,
	                        String schedulerName,
	                        String shutdownMsg,
	                        String standbyMsg,
	                        String startMsg) {
		this.scheduler = schedulerFactory.getScheduler();
		this.schedulerName = schedulerName;
		this.shutdownMsg = shutdownMsg;
		this.standbyMsg = standbyMsg;
		this.startMsg = startMsg;
	}

	@GetMapping("")
	public String manage(Model model) throws SchedulerException {
		model.addAttribute("schedulerName", schedulerName);
		model.addAttribute("isShutdown", scheduler.isShutdown());
		model.addAttribute("isStarted", scheduler.isStarted());
		model.addAttribute("isInStandbyMode", scheduler.isInStandbyMode());
		return "/manage";
	}
	
	/**
	 * Scheduler를 실질적으로 start 시킵니다.
	 */
	@ShutdownCheck
	@PostMapping("/start")
	public ResponseEntity<QuartzMsg> start() throws SchedulerException {

		// standby 상태이면 start
		if (this.scheduler.isInStandbyMode()) {
			this.scheduler.start();
			log.info("\n### ======================= "+ schedulerName +" =======================" + this.startMsg);
			return ResponseEntity.ok().body(new QuartzMsg.Builder().msg("[standby -> start] "+ this.schedulerName + " successful start !!!" + this.startMsg).build());
		} else {
			String body = this.schedulerName + "[start fail] because already started. ";
			log.info(body);
			return ResponseEntity.ok().body(new QuartzMsg.Builder().msg(body).build());
		}
	}
	
	/**
	 * Scheduler를 일시적으로 중지 시킵니다.
	 */
	@ShutdownCheck
	@PostMapping(value = "/standby")
	public ResponseEntity<QuartzMsg> standby() throws SchedulerException {
		
		// standby 상태이면 standby 거절
		if (this.scheduler.isInStandbyMode()) {
			String body = "[standby fail] because " + this.schedulerName + " is not working.";
			log.info(body);
			return ResponseEntity.ok().body(new QuartzMsg.Builder().msg(body).build());
		} else {
			this.scheduler.standby();
			log.info("\n### ======================= "+ schedulerName +" =======================" + this.standbyMsg);
			return ResponseEntity.ok().body(new QuartzMsg.Builder().msg(this.schedulerName + " successful [standby] !!!" + this.standbyMsg).build());
		}
	}
	
	/**
	 * Scheduler를 Shutdown 시킵니다.
	 */
	@ShutdownCheck
	@PostMapping("/shutdown")
	public ResponseEntity<QuartzMsg> shutdown() throws SchedulerException{
		
		// shutdown은 시작된적이 있으면 해당 스케줄러를 파괴시킨다. standby상태이든 말든 상관없음.
		if (this.scheduler.isStarted()) {
			scheduler.shutdown(true);
			log.info("\n### ======================= "+ schedulerName +" =======================" + this.shutdownMsg);
			return ResponseEntity.ok().body(new QuartzMsg.Builder().msg(this.schedulerName + " [successful shutdown] !!!" + this.shutdownMsg).build());
		} else {
			String body = "[shutdown fail] because " + this.schedulerName + " is not working. ";
			log.info(body);
			return ResponseEntity.ok().body(new QuartzMsg.Builder().msg(body).build());
		}
	}
}
