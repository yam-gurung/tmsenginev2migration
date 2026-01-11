package com.tms.timesheet;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PagedModel;
import org.springframework.format.annotation.DateTimeFormat;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonFormat;
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
	public PagedModel<?> getAllTimesheets(@PathVariable String username,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "0") int size) {
		Sort sort = Sort.by(Direction.DESC, "loginDate");
		PageRequest pageRequest = PageRequest.of(page, size, sort);
		Page<Timesheet> pageResult = this.timesheetService.getAllTimesheets(username, pageRequest);
		return new PagedModel<>(pageResult);

	}*/

	@GetMapping("/jpa/users/{username}/timesheets")
	public PagedModel<?> getAllTimesheetsBySearach(@PathVariable String username,
			@RequestParam(value="startDate",required=false) @DateTimeFormat(pattern="yyyy-MM-dd")    Date startDate,
	        @RequestParam(value="endDate",required=false) @DateTimeFormat(pattern="yyyy-MM-dd")   Date endDate,
			@RequestParam(name="page",defaultValue = "0") int page,
			@RequestParam(name="size",defaultValue = "0") int size)
	{
		System.out.println("start Date: "+startDate);
		System.out.println("end Date: "+endDate);
		System.out.println("page: "+page);
		System.out.println("size: "+size);
		
		Sort sort=Sort.by(Direction.DESC, "loginDate");
		PageRequest pageRequest = PageRequest.of(page, size, sort);
		Page<Timesheet> pageResult = this.timesheetService
				.getAllTimesheets(username,startDate,endDate,pageRequest);
		return new PagedModel<>(pageResult);
		
	}

	@GetMapping("/jpa/users/{username}/timesheets/{id}")
	public TimesheetDTO getTimesheet(@PathVariable String username, @PathVariable Long id) {
		TimesheetDTO timesheetDTO = new TimesheetDTO();
		Timesheet timesheet = this.timesheetService.getTimesheet(id);
		timesheetDTO.setId(timesheet.getId());
		timesheetDTO.setProject(timesheet.getProject());
		timesheetDTO.setLoginDate(timesheet.getLoginDate());
		timesheetDTO.setLoggedHr(timesheet.getLoggedHr());
		timesheetDTO.setUsername(timesheet.getUsername());
		return timesheetDTO;
	}

	@DeleteMapping("/jpa/users/{username}/timesheets/{id}")
	public ResponseEntity<Void> deleteTimesheet(@PathVariable String username, @PathVariable("id") Long id) {
		this.timesheetService.deleteTimesheet(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/jpa/users/{username}/timesheets/{id}")
	public ResponseEntity<Timesheet> updateTimesheet(@PathVariable String username, @PathVariable long id,
			@RequestBody TimesheetDTO timesheetDTO) {

		Timesheet updatedTimesheet = this.timesheetService.updateTimesheet(id, username, timesheetDTO.getProject(),
				timesheetDTO.getLoginDate(), timesheetDTO.getLoggedHr());

		return new ResponseEntity<Timesheet>(updatedTimesheet, HttpStatus.OK);
	}

	@PostMapping("/jpa/users/{username}/timesheets")
	public ResponseEntity<Void> createTimesheet(@PathVariable String username, @RequestBody TimesheetDTO timesheetDTO) {

		timesheetDTO.setUsername(username);
		Timesheet createdTimesheet = this.timesheetService.createNew(timesheetDTO);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(createdTimesheet.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}

}
