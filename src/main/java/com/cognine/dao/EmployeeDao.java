package com.cognine.dao;

import com.cognine.model.Employee;
import com.cognine.model.Roles;
import java.util.List;

public interface EmployeeDao {
    Employee getEmployeeByEmail(String email) ;

	List<Employee> searchEmployee();

	Employee getEmployeeRolesByEmail(String email);

	List<Roles> getProjectRoles(int id);
	
    Roles getProjectTechLeadRoles(int employeeId);
	
	Roles getProjectManagerRoles(int employeeId);
}
