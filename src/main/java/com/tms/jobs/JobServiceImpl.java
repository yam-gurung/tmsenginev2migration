package com.tms.jobs;
import org.springframework.stereotype.Service;
import com.tms.scheduletasks.ScheduleManager;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class JobServiceImpl implements JobService {
	
	@Autowired
	private ScheduleManager scheduleManager;
	
	public void executeDailyTimesheetReportJob() {
		try {
			this.scheduleManager.generateDailyTimesheetReport();
		} catch (FileNotFoundException e) {
			throw new CustomFileNotFoundException("File destination location not found  ",e);
		} catch (IOException e) {
			throw new FileStorageException("Failed to create and save a file",e);
		}
	}

	@Override
	public void executeReadRolesFileFromInboxFolderJob() {
		this.scheduleManager.readRolesFileFromInboxFolder();
	}

	@Override
	public void executeDailUserReportJob() {
		try {
			this.scheduleManager.generateDailyUserReport();
		} catch(FileNotFoundException e) {
			throw new CustomFileNotFoundException("File destination location not found ",e);
		}
		catch (IOException e) {
			throw new FileStorageException("Failed to crete and save a file",e);
		}
	}

}
