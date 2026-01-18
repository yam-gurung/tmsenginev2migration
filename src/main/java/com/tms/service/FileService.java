package com.tms.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import com.tms.user.SecurityUser;
import com.tms.model.Timesheet;

public interface FileService {
	void generateAndSaveTimesheetReportExcelFile(String targetLocation, List<Timesheet> timesheets)
			throws FileNotFoundException, IOException;

	void generateAndSaveUserReportExcelFile(String targetLocation, List<SecurityUser> users)
			throws FileNotFoundException, IOException;
}
