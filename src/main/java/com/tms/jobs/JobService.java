package com.tms.jobs;
import org.springframework.stereotype.Service;
import com.tms.scheduletasks.ScheduleManager;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class JobService {
	
	@Autowired
	private ScheduleManager scheduleManager;
	
	public void executeDailyTimesheetReportJob() throws FileNotFoundException, IOException {
		this.scheduleManager.generateDailyTimesheetReport();
	}

}
