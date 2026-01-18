package com.tms.jobs;
import org.springframework.web.bind.annotation.RestController;

import com.tms.exception.ErrorResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@RestController
@CrossOrigin(origins="http://localhost:4200")
public class JobJpaResource {
	
	@Autowired
	private JobService jobService;
	
	@RequestMapping(value="/jobs/{username}/timesheet",method=RequestMethod.POST)
	public void executeTimesheetJob(@PathVariable String username)
	throws FileStorageException,CustomFileNotFoundException
	{
			System.out.println("executeTimesheetJob called");
			this.jobService.executeDailyTimesheetReportJob();
	}
	
	@RequestMapping(value="/jobs/{username}/role",method=RequestMethod.POST)
	public void executeLoadRoleDataJob() {
		System.out.println("executeLoadRoleDataJob called");
		this.jobService.executeReadRolesFileFromInboxFolderJob();
	}
	
	@RequestMapping(value="/jobs/{username}/user",method=RequestMethod.POST)
	public void executeUserJob(@PathVariable String username) {
		System.out.println("executeUserJob called");
		this.jobService.executeDailUserReportJob();
	}
	
	@ExceptionHandler(CustomFileNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleFileNotFoundException(CustomFileNotFoundException e,
			WebRequest request){
		ErrorResponse errorDetails=new ErrorResponse(
				new Date(),e.getMessage(),
				request.getDescription(false),
				HttpStatus.INTERNAL_SERVER_ERROR.value()
				);
		return new ResponseEntity<>(errorDetails,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(FileStorageException.class)
	public ResponseEntity<ErrorResponse> hanldeFileStorageException(FileStorageException e,
			WebRequest request){
		ErrorResponse errorDetails = new ErrorResponse(
				new Date(),"An I/O error occured: "+e.getMessage(),request.getDescription(false),
				HttpStatus.INTERNAL_SERVER_ERROR.value());
				
		return new ResponseEntity<>(errorDetails,HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
