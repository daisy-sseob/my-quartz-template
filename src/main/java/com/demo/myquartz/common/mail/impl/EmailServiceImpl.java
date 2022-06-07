package com.demo.myquartz.common.mail.impl;

import com.demo.myquartz.common.dto.MailSms;
import com.demo.myquartz.common.mail.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

/**
 * mail service 제공
 * 
 * @author shs
 */
@Service
public class EmailServiceImpl implements EmailService {
	
	private final JavaMailSender javaMailSender;
	
	/* mail */
	@Value("${mail.from}")
	private String from;

	@Autowired
	public EmailServiceImpl(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	//메일 발송 method호출
	@Override
	public void sendSimpleMailMessage(MailSms mail) throws Exception {
		sendSimpleMailMessage(mail.getEmailArray(), (mail.getSj() != null) ? mail.getSj() : "", mail.getCn());
	}

	public void sendSimpleMailMessage(String[] to, String subject, String content) throws Exception {

		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setFrom(from);
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(content, true);
		
		javaMailSender.send(message);
	}
}
