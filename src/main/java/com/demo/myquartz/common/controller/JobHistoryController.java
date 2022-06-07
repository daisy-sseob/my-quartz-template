package com.demo.myquartz.common.controller;

import com.demo.myquartz.common.JobLoggingUtils;
import com.demo.myquartz.common.dto.JobHistory;
import com.demo.myquartz.common.service.JobHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequestMapping("/job")
@Controller
public class JobHistoryController {
	
	private final JobLoggingUtils jobLoggingUtils;
	private final JobHistoryService jobHistoryService;
	
	@Autowired
	public JobHistoryController(JobLoggingUtils jobLoggingUtils, JobHistoryService jobHistoryService) {
		this.jobLoggingUtils = jobLoggingUtils;
		this.jobHistoryService = jobHistoryService;
	}
	
	@GetMapping("/history")
	public String history(){
		return "jobHistory";
	}
	
	// get job date history
	@GetMapping("/history/{date}")
	public @ResponseBody List<JobHistory> histories(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date){
		List<JobHistory> jobHistories = new ArrayList<>();
		final File logFileOfDate = jobLoggingUtils.getJobLogFileOfDate(date);
		jobHistoryService.addJobHistories(jobHistories, logFileOfDate);
		return jobHistories;
	}
	
	// get job month of history 조회 
	@GetMapping("/history/month/{date}")
	public @ResponseBody List<JobHistory> monthOfHistories(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date){
		final List<JobHistory> jobHistories = new ArrayList<>();
		final Optional<File[]> filesOfMonth = jobLoggingUtils.getJobLogFilesOfMonth(date);
		filesOfMonth.ifPresent((files) -> {
			for (File file : files) {
				jobHistoryService.addJobHistories(jobHistories, file);
			}
		});
		return jobHistories;
	}
}
