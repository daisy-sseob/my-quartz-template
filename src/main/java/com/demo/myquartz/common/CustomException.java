package com.demo.myquartz.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;

import java.text.SimpleDateFormat;

/**
 * 
 * Quatz에서는 어떤 job에서 에러가 발생했는지 알아야 하기 때문에 JobExecutionContext객체를 추가로 받는다. <br>
 * Exception 발생시 Request parameter와 개발자 임의의 메세지를 Custom하기위한 Exception Class <br>
 * Exception의 원형을 유지하기위해 모든 생성자는 Throwable Type으로 parameter를 받는다.
 *
 * @author shs
 */
@Slf4j
@Getter @Setter
public class CustomException extends RuntimeException {

	private static final long serialVersionUID = 7543978644480786663L;

	private String developerDc; // 개발자 임의로 추가하고싶은 메세지
	private final String stackTraceString; // stackTrace -> toString
	private JobExecutionContext context;
	private StringBuilder contextInfo;
	private String schedulerName;
	private String param;
	
	public CustomException(Throwable cause, JobExecutionContext context) {
		this(cause,context,null,"");
	}
	
	public CustomException(Throwable cause,JobExecutionContext context, String developerMessage ) {
		this(cause,context,null,developerMessage);
	}
	
	public CustomException(Throwable cause,JobExecutionContext context,Log logVo, String developerMessage ) {
		super(cause);
		this.stackTraceString = toString(cause);
		this.context = context;
		
		if(context != null){
			this.contextInfo = toString(context);
		}
		
		if(logVo != null){
			try {
				ObjectMapper mapper = new ObjectMapper();
				this.param = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(logVo);
			} catch (JsonProcessingException e) {
				this.param = logVo.toString();
				log.error(e.getMessage(), e);
			}
		}
		
		this.developerDc = developerMessage;
		
		log.error(cause.getMessage(),cause);
	}

	/**
	 * stackTrace를 String으로 return
	 * @param cause 원인
	 * @return String
	 */
	private String toString(Throwable cause){
		
		StackTraceElement[] ste = cause.getStackTrace();
		
		StringBuilder stacktrace = new StringBuilder();
		
		for (StackTraceElement stackTraceElement : ste) {
			stacktrace.append(stackTraceElement.toString()).append("\n");
		}
		return stacktrace.toString();
	}
	
	private StringBuilder toString( JobExecutionContext context ){
		
		StringBuilder sb = new StringBuilder();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		try {
			this.schedulerName = context.getScheduler().getSchedulerName();
			String previousFireTime = (context.getPreviousFireTime() != null) ? format.format(context.getPreviousFireTime()) : "0";
			
			sb.append("### Custom Exception Job info ###")
			.append("\njob key                    => ").append(context.getJobDetail().getKey())
			.append("\njob Detail                 => ").append(context.getJobDetail().toString())
			.append("\njob getPreviousFireTime    => ").append(previousFireTime)
			.append("\njob getFireTime            => ").append(format.format(context.getFireTime()))
			.append("\njob getJobRunTime          => ").append(context.getJobRunTime())
			.append("\njob getNextFireTime        => ").append(format.format(context.getNextFireTime()));
			
		} catch (SchedulerException e) {
			log.error(e.getMessage(), e);
			sb.append("SchedulerException.. JobExecutionContext info do not try toString");
		}
		return sb;
	}
}
