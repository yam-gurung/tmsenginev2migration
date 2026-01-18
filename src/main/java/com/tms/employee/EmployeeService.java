package com.tms.employee;
import java.util.List;

public interface EmployeeService {
	
	Employee saveEmployee(String username,String password,String roleName,String firstName,String lastName,
			String city,String department,int salary,int managerId,int employeeId);
	
	Employee updateEmployee(Employee employee);
	
	Employee getEmployeeByEmployeeId(int employeeId);
	
	Employee getEmployeeById(Long id);
	
	void deleteEmployee(Long id);
	
	List<Employee> getEmployees();
	
}
