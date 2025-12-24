package com.tms.timesheet;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableArgumentResolver;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.tms.model.Timesheet;
import com.tms.model.TimesheetDTO;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TimesheetJpaResource {
	
	private TimesheetService timesheetService;
	
	@Autowired
	public TimesheetJpaResource(TimesheetService timesheetService) {
		this.timesheetService = timesheetService;
	}
	
	/*@GetMapping("/jpa/users/{username}/timesheets")
	public List<Timesheet> getAllTimesheets(@PathVariable String username){
		return this.timesheetService.getAllTimesheets(username);
	}*/
	
	/*@GetMapping("/jpa/users/{username}/timesheets")
	public Page<Timesheet> getAllTimesheets(@RequestParam(name="page",defaultValue = "0") int page,
											@RequestParam(name="size",defaultValue = "0") int size
											){
		PageRequest pageRequest = PageRequest.of(page, size);
		Page<Timesheet> pageResult = this.timesheetService.getAllTimesheets(pageRequest);
		
		List<Timesheet> timesheets = pageResult.stream()
				.collect(Collectors.toList());
		return new PageImpl<>(timesheets, pageRequest, pageResult.getTotalElements());
	}*/
	
	@GetMapping("/jpa/users/{username}/timesheets")
	public PagedModel<?> getAllTimesheets(@PathVariable String username,
			@RequestParam(name="page",defaultValue = "0") int page,
			@RequestParam(name="size",defaultValue = "0") int size)
	{
		//Sort sort = Sort.by(Direction.DESC,"loginDate");
		Sort sort=Sort.by(Direction.DESC, "loginDate");
		PageRequest pageRequest = PageRequest.of(page, size, sort);
		Page<Timesheet> pageResult = this.timesheetService.getAllTimesheets(username,pageRequest);
		return new PagedModel<>(pageResult);
		//List<Timesheet> timesheets = pageResult.stream()
		//		.collect(Collectors.toList());
		//return new PageImpl<>(timesheets, pageRequest, pageResult.getTotalElements());
	}
	
	@GetMapping("/jpa/users/{username}/timesheets/{id}")
	public TimesheetDTO getTimesheet(@PathVariable String username,@PathVariable Long id){
		TimesheetDTO timesheetDTO=new TimesheetDTO();
		Timesheet timesheet=this.timesheetService.getTimesheet(id);
		timesheetDTO.setId(timesheet.getId());
		timesheetDTO.setProject(timesheet.getProject());
		timesheetDTO.setLoginDate(timesheet.getLoginDate());
		timesheetDTO.setLoggedHr(timesheet.getLoggedHr());
		timesheetDTO.setUsername(timesheet.getUsername());
		return timesheetDTO;
	}
	
	@DeleteMapping("/jpa/users/{username}/timesheets/{id}")
	public ResponseEntity<Void> deleteTimesheet(@PathVariable String username,@PathVariable("id") Long id){
		this.timesheetService.deleteTimesheet(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/jpa/users/{username}/timesheets/{id}")
	public ResponseEntity<Timesheet> updateTimesheet(@PathVariable String username,@PathVariable long id,@RequestBody TimesheetDTO timesheetDTO) {
		
		//Timesheet updatedTimesheet = this.timesheetService.updateTimesheet(id,timesheet.getUsername(),timesheet.getProject(),timesheet.getLoginDate(),timesheet.getLoggedHr());
		Timesheet updatedTimesheet = this.timesheetService.updateTimesheet(id,username,timesheetDTO.getProject(),timesheetDTO.getLoginDate(),timesheetDTO.getLoggedHr());
		
		return new ResponseEntity<Timesheet>(updatedTimesheet,HttpStatus.OK);		
	}
	
	@PostMapping("/jpa/users/{username}/timesheets")
	public ResponseEntity<Void> createTimesheet(@PathVariable String username,@RequestBody TimesheetDTO timesheetDTO){
		
		
		timesheetDTO.setUsername(username);
		Timesheet createdTimesheet =  this.timesheetService.createNew(timesheetDTO);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(createdTimesheet.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
}
