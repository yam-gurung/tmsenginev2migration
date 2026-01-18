package com.tms.service;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.tms.model.Timesheet;
import com.tms.model.TimesheetDTO;

public interface TimesheetService {
	List<Timesheet> getAllTimesheets();
	Page<Timesheet> getAllTimesheets(String username,PageRequest pageRequest);
	Page<Timesheet> getAllTimesheets(PageRequest pageRequest);
	Page<Timesheet> getAllTimesheets(String username,Pageable pageable);
	Page<Timesheet> getAllTimesheets(String username,String startDate
			,String endDate,String project,Pageable pageable);
	Page<Timesheet> getAllEmployeesTimesheets(String username,String startDate
			,String endDate,String project,Pageable pageable);
	Timesheet getTimesheet(Long id);
	Timesheet createNew(TimesheetDTO timesheetDTO);
	Timesheet updateTimesheet(Long timesheetId,String username,
			String project,Date loginDate,LocalTime fromTime,LocalTime toTime,double loggedHr);
	void deleteTimesheet(Long id);
}
