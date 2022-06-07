package com.demo.myquartz.common.dto;

public class QuartzMsg {
	private final String msg;
	
	private QuartzMsg(Builder builder) {
		this.msg = builder.msg;
	}

	public String getMsg() {
		return msg;
	}

	public static class Builder {
		private String msg;
		
		public Builder msg(String msg) {
			this.msg = msg;
			return this;
		}
		
		public QuartzMsg build() {
			return new QuartzMsg(this);
		}
	}
}
