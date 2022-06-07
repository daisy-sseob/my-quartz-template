package com.demo.myquartz.common.mail;


import com.demo.myquartz.common.dto.MailSms;

public interface EmailService {
	void sendSimpleMailMessage(MailSms mailSms) throws Exception;
}
