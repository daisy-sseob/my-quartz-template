package com.demo.myquartz.job;

import com.demo.myquartz.common.mail.EmailService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * warning 메일 대상 list를 조회하여 mail 발송
 */
@Component
@DisallowConcurrentExecution
public class SendMailJob implements Job {
    
    @Autowired
    private EmailService emailService;
    
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
//        emailService.sendSimpleMailMessage(); // 메일 발송
    }
}