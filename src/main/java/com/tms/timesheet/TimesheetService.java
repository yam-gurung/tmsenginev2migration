package com.tms.timesheet;

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

@Service
public class TimesheetService {
	
	private TimesheetJpaRepository timesheetJpaRepository; 
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	public TimesheetService(TimesheetJpaRepository timesheetJpaRepository) {
		this.timesheetJpaRepository = timesheetJpaRepository;
	}

	public List<Timesheet> getAllTimesheets(){
		Sort sort=Sort.by(Direction.DESC,"loginDate");
		return this.timesheetJpaRepository.findAll(sort);
	}
	
	public Page<Timesheet> getAllTimesheets(String username,PageRequest pageRequest){
		return this.timesheetJpaRepository.findByUsername(username,pageRequest);
	}
	
	public Page<Timesheet> getAllTimesheets(PageRequest pageRequest){
		return this.timesheetJpaRepository.findAll(pageRequest);
	}
	
	public Page<Timesheet> getAllTimesheets(String username,Pageable pageable){
		return this.timesheetJpaRepository.findByUsername(username, pageable);
	}
	
	public Page<Timesheet> getAllTimesheets(String username,Date startDate
			,Date endDate,Pageable pageable){
		return this.timesheetJpaRepository
				.findByUsernameAndloginDateBetween(username, startDate, endDate, pageable);	}
	
	public Timesheet getTimesheet(Long id) {
		return this.timesheetJpaRepository.findById(id).get();
	}
	
	public Timesheet createNew(TimesheetDTO timesheetDTO) {
		SecurityUser securityUser = this.userRepository.findByUsername(timesheetDTO.getUsername()).get();
		Timesheet newTimesheet=new Timesheet();
		newTimesheet.setProject(timesheetDTO.getProject());
		newTimesheet.setLoginDate(timesheetDTO.getLoginDate());
		newTimesheet.setLoggedHr(timesheetDTO.getLoggedHr());
		newTimesheet.setUsername(securityUser.getUsername());
		newTimesheet.setSecurityUser(securityUser);
		
		return this.timesheetJpaRepository.save(newTimesheet);
	}
	
	/*
	 * public Timesheet updateTimesheet(Timesheet timesheet) { return
	 * this.timesheetJpaRepository.save(timesheet); }
	 */
	
	public Timesheet updateTimesheet(Long timesheetId,String username,String project,Date loginDate,double loggedHr) {
		Timesheet updateTimesheet = this.timesheetJpaRepository.findById(timesheetId).get();
		//updateTimesheet.setUsername(username);
		updateTimesheet.setProject(project);
		updateTimesheet.setLoginDate(loginDate);
		updateTimesheet.setLoggedHr(loggedHr);
		
		return this.timesheetJpaRepository.save(updateTimesheet);
	}
	
	public void deleteTimesheet(Long id) {
		this.timesheetJpaRepository.deleteById(id);
	}

}
