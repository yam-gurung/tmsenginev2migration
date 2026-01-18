package com.tms.model;
import java.time.LocalTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TimesheetDTO {
	
	private Long id;
	private String project;
	private Date loginDate;
	@JsonFormat(pattern="HH:mm")
	private LocalTime fromTime;
	@JsonFormat(pattern="HH:mm")
	private LocalTime toTime;
	private double loggedHr;
	private String username;

	public TimesheetDTO(Long id, String project, Date loginDate,LocalTime fromTime, LocalTime toTime,
			double loggedHr, String username) {
		super();
		this.id = id;
		this.project = project;
		this.loginDate = loginDate;
		this.fromTime = fromTime;
		this.toTime = toTime;
		this.loggedHr = loggedHr;
		this.username = username;
	}

	public TimesheetDTO() {
		super();
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
	
	public LocalTime getFromTime() {
		return fromTime;
	}

	public void setFromTime(LocalTime fromTime) {
		this.fromTime = fromTime;
	}

	public LocalTime getToTime() {
		return toTime;
	}

	public void setToTime(LocalTime toTime) {
		this.toTime = toTime;
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
