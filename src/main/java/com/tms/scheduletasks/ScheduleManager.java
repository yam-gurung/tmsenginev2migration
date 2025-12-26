package com.tms.scheduletasks;
import org.springframework.stereotype.Component;

import com.tms.model.Timesheet;
import com.tms.service.ExcelFileService;
import com.tms.timesheet.TimesheetService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;

@Component
public class ScheduleManager {
	
	private TimesheetService timesheetService;
	private ExcelFileService excelFileService;
	
	@Value("${report.target.path}")
	private String targetFileLocation;
	
	@Autowired
	public ScheduleManager(TimesheetService timesheetService,
			ExcelFileService excelFileService) {
		this.timesheetService = timesheetService;
		this.excelFileService = excelFileService;
	}
	
	@Scheduled(cron="${timesheet.time.reminder.cron}")// runs every 10 second
	public void sendTimesheetEmailRemainder() {
		System.out.println("Dear User, its general remainder to fill up your timesheet "
				+ "for today");
	}
	
	@Scheduled(cron="${report.time.cron}")
	public void generateDailyTimesheetReport() throws FileNotFoundException, IOException {
		List<Timesheet> timesheets=this.timesheetService.getAllTimesheets();
		this.excelFileService.
		createAndSaveTimesheetReportExcelFile(targetFileLocation, timesheets);
	}

}
