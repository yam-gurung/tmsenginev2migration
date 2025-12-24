package com.tms.timesheet;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tms.model.Timesheet;

@Repository
public interface TimesheetJpaRepository extends JpaRepository<Timesheet, Long> {
	List<Timesheet> findByUsername(String username);
	
	Page<Timesheet> findByUsername(String username,Pageable pageable);
}
