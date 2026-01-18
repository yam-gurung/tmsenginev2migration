package com.tms.employee;

import org.springframework.stereotype.Service;

import com.tms.user.Role;
import com.tms.user.RoleRepository;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	private EmployeeJpaRepository employeeJpaRepository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public Employee saveEmployee(String username,String password,String roleName,String firstName,String lastName,
			String city,String department,int salary,int managerId,int employeeId) {
		String encodedPassword=this.passwordEncoder.encode(password);
		Optional<Role> role=this.roleRepository.findByRoleName(roleName);
		Employee newEmployee=new Employee(
				username,encodedPassword,role.get(),firstName,lastName,department,salary,managerId,city,employeeId
				);
		
		return this.employeeJpaRepository.save(newEmployee);
	}

	@Override
	public Employee updateEmployee(Employee employee) {
		return this.employeeJpaRepository.save(employee);
	}

	@Override
	public Employee getEmployeeById(Long employeeId) {
		return this.employeeJpaRepository.findById(employeeId).get();
	}

	@Override
	public void deleteEmployee(Long id) {
		this.employeeJpaRepository.deleteById(id);
	}

	@Override
	public List<Employee> getEmployees() {
		return this.employeeJpaRepository.findAll();
	}

	@Override
	public Employee getEmployeeByEmployeeId(int employeeId) {
		return this.employeeJpaRepository.findByEmployeeId(employeeId);
	}
}
