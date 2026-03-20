package com.cognine.contr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cognine.model.Employee;
import com.cognine.service.EmployeeService;
import java.util.List;

@RestController
public class EmployeeController {
    @Autowired
	private EmployeeService employeeService;

	@GetMapping("/searchemployee")
	@Secured({"ROLE_ADMIN","ROLE_PMO"})
	public List<Employee> searchEmployee() {
		return employeeService.searchEmployee();

	}
    @GetMapping("/getassignedrolesofemployee/{email}")
	@Secured("ROLE_ADMIN")
	public Employee getAssignedEmployeeRoles(@PathVariable("email") String email) throws Exception {
		return employeeService.getEmployeeByEmail(email);

	}
	
	@GetMapping("getemployeeroles/{email}")
   // @Secured("ROLE_ADMIN")
	public Employee getEmployeeRoles(@PathVariable("email") String email) throws Exception {
		return employeeService.getEmployeeRolesByEmail(email);
	}
	

}
