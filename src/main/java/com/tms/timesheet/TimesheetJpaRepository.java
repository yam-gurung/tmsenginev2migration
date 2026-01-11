package com.tms.timesheet;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tms.model.Timesheet;

@Repository
public interface TimesheetJpaRepository extends JpaRepository<Timesheet, Long> {
	List<Timesheet> findByUsername(String username);
	
	Page<Timesheet> findByUsername(String username,Pageable pageable);
	
	@Query("select t from Timesheet t where (CAST(:startDate AS date) is NULL OR t.loginDate>=:startDate) AND (CAST(:endDate AS date) is NULL OR t.loginDate<=:endDate) and t.username=:username")
	Page<Timesheet> findByUsernameAndloginDateBetween(
			@Param("username") String username,
			@Param("startDate") Date startDate,
			@Param("endDate") Date endDate,
			Pageable pageable);
}
