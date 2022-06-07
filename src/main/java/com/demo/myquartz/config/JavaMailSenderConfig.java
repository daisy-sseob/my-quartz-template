package com.demo.myquartz.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * JavaMailSender config
 * 
 * @author shs
 */
@Configuration
public class JavaMailSenderConfig {
	
	/* mail */
	@Value("${mail.host}")
	private String host;
	@Value("${mail.port}")
	private Integer port;
	// username, password는 개발환경에서 gmail 테스트시 사용합니다.
	@Value("${mail.username}")
	private String username;
	@Value("${mail.password}")
	private String password;
	
	@Bean
	public JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(host);
		mailSender.setPort(port);
		mailSender.setDefaultEncoding("UTF-8");
		
		Properties props = mailSender.getJavaMailProperties();
//		props.put("mail.debug", "true");
//		props.put("mail.smtp.host", host);
//		props.put("mail.smtp.port", port);
//		props.put("mail.smtp.auth", "false");
//		props.put("mail.smtp.ssl.enable", "false");
//		props.put("mail.smtp.ssl.trust", host);
//		props.put("mail.smtp.socketFactory.fallback", "true");
//		props.put("mail.mime.charset", "UTF-8");
		
		// gmail 개발환경 mail test시에는 아래 option으로 테스트할 것
		mailSender.setUsername(username);
		mailSender.setPassword(password);
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", "true");
		props.put("mail.mime.charset", "UTF-8");
		
		return mailSender;
	}
}
