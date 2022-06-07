package com.demo.myquartz.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.quartz.Trigger;

@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class JobHistory {
	private String jobKey;
	private long jobRunTime;
	private String fireTime;
	private String nextFireTime;
	private boolean isSuccess;
	private String jobExceptionMessage;
	private Trigger.TriggerState triggerState;

	private JobHistory(Builder builder) {
		this.jobKey = builder.jobKey;
		this.jobRunTime = builder.jobRunTime;
		this.fireTime = builder.fireTime;
		this.nextFireTime = builder.nextFireTime;
		this.isSuccess = builder.isSuccess;
		this.jobExceptionMessage = builder.jobExceptionMessage;
		this.triggerState = builder.triggerState;
	}

	public static class Builder {

		private String jobKey;
		private long jobRunTime;
		private String fireTime;
		private String nextFireTime;
		private boolean isSuccess;
		private String jobExceptionMessage;
		private Trigger.TriggerState triggerState;

		public Builder jobKey(String jobKey) {
			this.jobKey = jobKey;
			return this;
		}
		
		public Builder jobRunTime(long jobRunTime) {
			this.jobRunTime = jobRunTime;
			return this;
		}
		public Builder fireTime(String fireTime) {
			this.fireTime = fireTime;
			return this;
		}
		public Builder nextFireTime(String nextFireTime) {
			this.nextFireTime = nextFireTime;
			return this;
		}
		public Builder isSuccess(boolean isSuccess) {
			this.isSuccess = isSuccess;
			return this;
		}
		public Builder jobExceptionMessage(String jobExceptionMessage) {
			this.jobExceptionMessage = jobExceptionMessage;
			return this;
		}
		
		public Builder triggerState(Trigger.TriggerState triggerState) {
			this.triggerState = triggerState;
			return this;
		}
		
		public JobHistory build() {
			return new JobHistory(this);
		}
	}

	@Override
	public String toString() {
		return "JobHistory{" +
				"jobKey=" + jobKey +
				", jobRunTime=" + jobRunTime +
				", fireTime=" + fireTime +
				", nextFireTime=" + nextFireTime +
				", isSuccess=" + isSuccess +
				", jobExceptionMessage='" + jobExceptionMessage + '\'' +
				", triggerState='" + triggerState + '\'' +
				'}';
	}
}
