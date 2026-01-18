package com.tms.jobs;

public interface JobService {
	void executeDailyTimesheetReportJob();
	void executeReadRolesFileFromInboxFolderJob();
	void executeDailUserReportJob();
}
