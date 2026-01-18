package com.tms.model;

import java.time.LocalDateTime;
import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PreRemove;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.SQLRestriction;
import com.tms.user.SecurityUser;
import java.time.LocalTime;

@Entity
@SQLDelete(sql = "update timesheet set is_deleted=true where id=?")
@SQLRestriction("is_deleted = false")
public class Timesheet{
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String username;
	
	private String project;
	
	private Date loginDate;
	
	private LocalTime fromTime;
	
	private LocalTime toTime;
	
	private double loggedHr;
	
	@ManyToOne
	@JoinColumn(name="security_user_id")
	private SecurityUser securityUser;
	
	@UpdateTimestamp
	private LocalDateTime lastUpdateDate;
	
	@CreationTimestamp
	private LocalDateTime createdDate;
	
	private boolean isDeleted;
	
	@PreRemove
	private void PreRemove() {
		this.isDeleted = true;
	}

	public Timesheet() {
		super();
	}
	

	public Timesheet(String username, String project, Date loginDate,LocalTime fromTime, 
			LocalTime toTime,double loggedHr) {
		super();
		this.username = username;
		this.project = project;
		this.loginDate = loginDate;
		this.fromTime = fromTime;
		this.toTime = toTime;
		this.loggedHr = loggedHr;
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

	public SecurityUser getSecurityUser() {
		return securityUser;
	}

	public void setSecurityUser(SecurityUser securityUser) {
		this.securityUser = securityUser;
	}


	public LocalDateTime getLastUpdateDate() {
		return lastUpdateDate;
	}


	public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Timesheet other = (Timesheet) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
