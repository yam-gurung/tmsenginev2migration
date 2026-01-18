package com.tms.scheduletasks;
import org.springframework.stereotype.Component;
import com.tms.model.RoleDTO;
import com.tms.model.Timesheet;
import com.tms.roles.RoleService;
import com.tms.service.FileService;
import com.tms.timesheet.TimesheetServiceImpl;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.FileReader;
import java.io.BufferedReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import com.tms.service.UserService;
import com.tms.user.SecurityUser;

@Component
public class ScheduleManager {
	
	private TimesheetServiceImpl timesheetService;
	private FileService excelFileService;
	private RoleService roleService;
	private UserService userService;
	
	@Value("${report.target.path}")
	private String targetFileLocation;
	
	@Value("${role.filepath}")
	private String sourceRoleFileLocation;
	
	@Autowired
	public ScheduleManager(TimesheetServiceImpl timesheetService,
			FileService excelFileService,
			RoleService roleService,
			UserService userService) {
		this.timesheetService = timesheetService;
		this.excelFileService = excelFileService;
		this.roleService = roleService;
		this.userService = userService;
	}
	
	@Scheduled(cron="${timesheet.time.reminder.cron}")
	public void sendTimesheetEmailRemainder() {
		System.out.println("Dear User, its general remainder to fill up your timesheet "
				+ "for today");
	}
	
	@Scheduled(cron="${timesheetreport.time.cron}")
	public void generateDailyTimesheetReport() throws FileNotFoundException, IOException {
		List<Timesheet> timesheets=this.timesheetService.getAllTimesheets();
		this.excelFileService.
		generateAndSaveTimesheetReportExcelFile(targetFileLocation, timesheets);
	}
	
	public void generateDailyUserReport() throws FileNotFoundException,IOException {
		List<SecurityUser> users=this.userService.getAllUsers();
		this.excelFileService.generateAndSaveUserReportExcelFile(targetFileLocation, users);
	}
	
	@Scheduled(cron="${rolesfile.read.time.cron}")
	public void readRolesFileFromInboxFolder() {
		String filePath=sourceRoleFileLocation+"/role.txt";
		String line;
		String delimiter=",";
		
		try(BufferedReader br=new BufferedReader(new FileReader(filePath))){
			String headerLine = br.readLine();
			if(headerLine!=null) {
				String[] headers=headerLine.split(delimiter);
				System.out.println("headers: "+Arrays.toString(headers));
			}
			
			List<RoleDTO> listRoles=new ArrayList<>();
			while((line=br.readLine())!=null) {
				String[] dataRow=line.split(delimiter);
				RoleDTO roleDTO=new RoleDTO();
					roleDTO.setRoleName(dataRow[0]);
					roleDTO.setDescription(dataRow[1]);
				listRoles.add(roleDTO);
				System.out.println("Data Row: "+Arrays.toString(dataRow));
			}
			
			roleService.saveAllRoles(listRoles);
			System.out.println("role file saved successfully");
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

}
