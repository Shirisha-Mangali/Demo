package com.cognine.service;

import java.util.List;

import com.cognine.model.Employee;

public interface EmployeeService {

	Employee getEmployeeByEmail(String email) throws Exception;

	List<Employee> searchEmployee();

	Employee getEmployeeRolesByEmail(String email);

}
