package com.cognine.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cognine.dao.EmployeeDao;
import com.cognine.mapper.EmployeeMapper;
import com.cognine.model.Employee;
import com.cognine.model.Roles;

@Repository
public class EmployeeDaoImpl implements EmployeeDao {
    @Autowired
	private EmployeeMapper employeeMapper;

	@Override
	public Employee getEmployeeByEmail(String email) {
		Employee employeeData = null;
		try {
			employeeData = employeeMapper.getEmployeeRoles(email);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return employeeData;
	}

	@Override
	public List<Employee> searchEmployee() {
		return employeeMapper.searchEmployee();
	}

	@Override
	public Employee getEmployeeRolesByEmail(String email) {
		Employee employeeData = null;
		try {
			employeeData = employeeMapper.getEmployeeRolesByEmail(email);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return employeeData;
	}

	@Override
	public List<Roles> getProjectRoles(int id) {
		List<Roles> employeeRoles = null;
		try {
			employeeRoles = employeeMapper.getProjectRoles(id);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return employeeRoles;
	}
	@Override
	public Roles getProjectTechLeadRoles(int employeeId) {
		return employeeMapper.getProjectTechLeadRoles(employeeId);
	}

	@Override
	public Roles getProjectManagerRoles(int employeeId) {
		return employeeMapper.getProjectManagerRoles(employeeId);
	}

	
}
