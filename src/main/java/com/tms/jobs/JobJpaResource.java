package com.tms.jobs;
import org.springframework.web.bind.annotation.RestController;

import com.tms.service.ExcelFileService;

import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@CrossOrigin(origins="http://localhost:4200")
public class JobJpaResource {
	
	@Autowired
	private JobService jobService;
	
	@RequestMapping(value="/jobs/{username}/timesheet",method=RequestMethod.POST)
	public void executeTimesheetJob(@PathVariable String username){
		try {
			System.out.println("executeTimesheetJob called");
			this.jobService.executeDailyTimesheetReportJob();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//return ResponseEntity.noContent().build();
	}

}
