package com.tms.employee;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeJpaRepository extends JpaRepository<Employee, Long> {
	Employee findByEmployeeId(int employeeId);
}
