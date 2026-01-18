package com.tms.timesheet;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import com.tms.model.Timesheet;
import com.tms.model.TimesheetDTO;
import com.tms.user.SecurityUser;
import com.tms.user.UserRepository;
import com.tms.service.TimesheetService;
import org.springframework.security.access.prepost.PreAuthorize;

@Service
public class TimesheetServiceImpl implements TimesheetService {

	private TimesheetJpaRepository timesheetJpaRepository;
	@Autowired
	private UserRepository userRepository;

	@Autowired
	public TimesheetServiceImpl(TimesheetJpaRepository timesheetJpaRepository) {
		this.timesheetJpaRepository = timesheetJpaRepository;
	}

	public List<Timesheet> getAllTimesheets() {
		Sort sort = Sort.by(Direction.DESC, "loginDate");
		return this.timesheetJpaRepository.findAll(sort);
	}

	public Page<Timesheet> getAllTimesheets(String username, PageRequest pageRequest) {
		return this.timesheetJpaRepository.findByUsername(username, pageRequest);
	}

	public Page<Timesheet> getAllTimesheets(PageRequest pageRequest) {
		return this.timesheetJpaRepository.findAll(pageRequest);
	}

	public Page<Timesheet> getAllTimesheets(String username, Pageable pageable) {
		return this.timesheetJpaRepository.findByUsername(username, pageable);
	}

	public Page<Timesheet> getAllTimesheets(String username, String startDate, String endDate, String project, Pageable pageable) {
		return this.timesheetJpaRepository.findByUsernameAndloginDateBetween(username, startDate, endDate,project, pageable);
	}

	public Timesheet getTimesheet(Long id) {
		return this.timesheetJpaRepository.findById(id).get();
	}

	public Timesheet createNew(TimesheetDTO timesheetDTO) {
		SecurityUser securityUser = this.userRepository.findByUsername(timesheetDTO.getUsername()).get();
		Timesheet newTimesheet = new Timesheet();
		newTimesheet.setProject(timesheetDTO.getProject());
		newTimesheet.setLoginDate(timesheetDTO.getLoginDate());
		newTimesheet.setFromTime(timesheetDTO.getFromTime());
		newTimesheet.setToTime(timesheetDTO.getToTime());
		newTimesheet.setLoggedHr(timesheetDTO.getLoggedHr());
		newTimesheet.setUsername(securityUser.getUsername());
		newTimesheet.setSecurityUser(securityUser);
		
		System.out.println("from Time entity timesheet "+newTimesheet.getFromTime());
		System.out.println("to Time entity timesheet "+newTimesheet.getToTime());
		

		return this.timesheetJpaRepository.save(newTimesheet);
	}

	public Timesheet updateTimesheet(Long timesheetId, String username, String project, Date loginDate,
			LocalTime fromTime, LocalTime toTime,double loggedHr) {
		Timesheet updateTimesheet = this.timesheetJpaRepository.findById(timesheetId).get();
		updateTimesheet.setProject(project);
		updateTimesheet.setLoginDate(loginDate);
		updateTimesheet.setFromTime(fromTime);
		updateTimesheet.setToTime(toTime);
		updateTimesheet.setLoggedHr(loggedHr);

		return this.timesheetJpaRepository.save(updateTimesheet);
	}

	public void deleteTimesheet(Long id) {
		this.timesheetJpaRepository.deleteById(id);
	}

	@Override
	@PreAuthorize("hasRole('ADMIN')")
	public Page<Timesheet> getAllEmployeesTimesheets(String username, String startDate,String endDate, String project,
			Pageable pageable) {
		System.out.println("username: "+username);
		System.out.println("startDate: "+startDate);
		System.out.println("endDate: "+endDate);
		System.out.println("project: "+project);
		return this.timesheetJpaRepository.findByUsernameAndloginDateBetween(username, startDate, endDate, project, pageable);
	}

}
