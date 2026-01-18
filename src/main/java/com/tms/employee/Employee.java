package com.tms.employee;

import com.tms.user.Role;
import com.tms.user.SecurityUser;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "employee")
@PrimaryKeyJoinColumn(name="user_id")
public class Employee extends SecurityUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String department;
	private int salary;
	private int managerId;
	private String city;
	private int employeeId;

	public Employee(String username, String password, Role role, String firstName, String lastName, String department,
			int salary, int managerId, String city, int employeeId) {
		super(username, password, role, firstName, lastName);

		this.department = department;
		this.salary = salary;
		this.managerId = managerId;
		this.city = city;
		this.employeeId = employeeId;
	}

	protected Employee() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public int getManagerId() {
		return managerId;
	}

	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
}
