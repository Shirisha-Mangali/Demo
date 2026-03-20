package com.cognine.dao;

import java.util.List;

import com.cognine.model.Roles;

public interface RolesDao {

	List<Roles> getRoles();
	int assignRolesToEmployee(int employeeId, Roles role);

	int deleteEmployeeRoles(Roles role, int employeeId);

	int editEmployeeRoles(int employeeId, Roles editRole);

	int updatePrimaryRoles(int employeeId);
}
