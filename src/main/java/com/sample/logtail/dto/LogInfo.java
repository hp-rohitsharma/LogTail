package com.sample.logtail.dto;

public class LogInfo {

	private String logdata;

	public LogInfo(String logdata) {
		super();
		this.logdata = logdata;
	}

	public String getLogdata() {
		return logdata;
	}

	public void setLogdata(String logdata) {
		this.logdata = logdata;
	}
}
