package com.tms.timesheet;

import java.net.URI;
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
import com.tms.model.Timesheet;
import com.tms.model.TimesheetDTO;
import java.text.SimpleDateFormat;
import com.tms.service.TimesheetService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TimesheetJpaResource {

	private TimesheetService timesheetService;

	@Autowired
	public TimesheetJpaResource(TimesheetService timesheetService) {
		this.timesheetService = timesheetService;
	}

	@GetMapping("/jpa/timesheets")
	public PagedModel<?> getAllEmployeesTimesheetsBySearch(
			@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
			@RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
			@RequestParam(value = "project", required = false) String project,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size) {

		// String username = null;
		String formattedStartDate = null;
		String formattedEndDate = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		if (startDate != null && endDate != null) {
			formattedStartDate = formatter.format(startDate);
			formattedEndDate = formatter.format(endDate);

			System.out.println("formatted start date " + formattedStartDate);
			System.out.println("formatted end date " + formattedEndDate);

		}

		Sort sort = Sort.by(Direction.DESC, "login_date");
		PageRequest pageRequest = PageRequest.of(page, size, sort);
		Page<Timesheet> pageResult = this.timesheetService.getAllEmployeesTimesheets(username, formattedStartDate,
				formattedEndDate, project, pageRequest);

		return new PagedModel<>(pageResult);
	}

	@GetMapping("/jpa/users/{username}/timesheets")
	public PagedModel<?> getAllTimesheetsBySearch(@PathVariable(required = false) String username,
			@RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
			@RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
			@RequestParam(value = "project", required = false) String project,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size) {

		String formattedStartDate = null;
		String formattedEndDate = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		if (startDate != null && endDate != null) {
			formattedStartDate = formatter.format(startDate);
			formattedEndDate = formatter.format(endDate);
		}

		Sort sort = Sort.by(Direction.DESC, "login_date");
		PageRequest pageRequest = PageRequest.of(page, size, sort);
		Page<Timesheet> pageResult = this.timesheetService.getAllTimesheets(username, formattedStartDate,
				formattedEndDate, project, pageRequest);
		return new PagedModel<>(pageResult);

	}

	@GetMapping("/jpa/users/{username}/timesheets/{id}")
	public TimesheetDTO getTimesheet(@PathVariable String username, @PathVariable Long id) {
		TimesheetDTO timesheetDTO = new TimesheetDTO();
		Timesheet timesheet = this.timesheetService.getTimesheet(id);
		timesheetDTO.setId(timesheet.getId());
		timesheetDTO.setProject(timesheet.getProject());
		timesheetDTO.setLoginDate(timesheet.getLoginDate());
		timesheetDTO.setFromTime(timesheet.getFromTime());
		timesheetDTO.setToTime(timesheet.getToTime());
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
				timesheetDTO.getLoginDate(), timesheetDTO.getFromTime(), timesheetDTO.getToTime(),
				timesheetDTO.getLoggedHr());

		return new ResponseEntity<Timesheet>(updatedTimesheet, HttpStatus.OK);
	}

	@PostMapping("/jpa/users/{username}/timesheets")
	public ResponseEntity<Void> createTimesheet(@PathVariable String username, @RequestBody TimesheetDTO timesheetDTO) {

		System.out.println("from Time " + timesheetDTO.getFromTime());
		System.out.println("to Time " + timesheetDTO.getToTime());

		timesheetDTO.setUsername(username);
		Timesheet createdTimesheet = this.timesheetService.createNew(timesheetDTO);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(createdTimesheet.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}

}
