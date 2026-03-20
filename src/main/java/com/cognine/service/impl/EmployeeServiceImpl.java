package com.cognine.service.impl;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.cognine.dao.EmployeeDao;
import com.cognine.model.Employee;
import com.cognine.model.Roles;
import com.cognine.service.EmployeeService;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@Component
@Repository
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeDao employeeDao;

    @Override
	public Employee getEmployeeByEmail(String email) {
		Employee employeeData = employeeDao.getEmployeeByEmail(email);
		if (employeeData.getEmployeeRoles().isEmpty()) {
			List<Roles> employeeRoles = employeeDao.getProjectRoles(employeeData.getId());
			employeeData.setEmployeeRoles(employeeRoles);
		}
		return employeeData;
	}

	@Override
	public List<Employee> searchEmployee() {
		return employeeDao.searchEmployee();
	}

	@Override
	public Employee getEmployeeRolesByEmail(String email) {
		Employee employee = employeeDao.getEmployeeRolesByEmail(email);
		if (employee.getEmployeeRoles().isEmpty()) {
			List<Roles> employeeRoles = new ArrayList<>();
			Roles projectManagerRole = employeeDao.getProjectManagerRoles(employee.getId());
			Roles projectTechLeadRole = employeeDao.getProjectTechLeadRoles(employee.getId());
			if(projectManagerRole != null) employeeRoles.add(projectManagerRole);
			if(projectTechLeadRole != null) employeeRoles.add(projectTechLeadRole);
			employeeRoles.addAll(employeeDao.getProjectRoles(employee.getId()));
			employeeRoles =  employeeRoles.stream()
            .collect(Collectors.toMap(Roles::getId, role -> role, (existing, replacement) -> existing))
            .values()
            .stream()
            .collect(Collectors.toList());
			employee.setEmployeeRoles(employeeRoles);

		}
		return employee;
	}



}
