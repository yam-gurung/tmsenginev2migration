package com.tms.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TimesheetDTO {
	private Long id;
	public TimesheetDTO(Long id, String project, Date loginDate, double loggedHr, String username) {
		super();
		this.id = id;
		this.project = project;
		this.loginDate = loginDate;
		this.loggedHr = loggedHr;
		this.username = username;
	}
	private String project;
	
	private Date loginDate;
	
	private double loggedHr;
	private String username;
	public TimesheetDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public Date getLoginDate() {
		return loginDate;
	}
	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}
	public double getLoggedHr() {
		return loggedHr;
	}
	public void setLoggedHr(double loggedHr) {
		this.loggedHr = loggedHr;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
