package com.tms.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.tms.model.Timesheet;

public interface FileService {
	void createAndSaveTimesheetReportExcelFile(
			String targetLocation,List<Timesheet> timesheets) throws FileNotFoundException, IOException;
}
