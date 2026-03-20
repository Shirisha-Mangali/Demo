package com.cognine.dao.impl;
import com.cognine.dao.RolesDao;
import com.cognine.mapper.RolesMapper;
import com.cognine.model.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class RolesDaoImpl implements RolesDao {
	@Autowired
	private RolesMapper rolesMapper;

	@Override
	public List<Roles> getRoles() {
		return rolesMapper.getRoles();
	}

	@Override
	public int assignRolesToEmployee(int employeeId, Roles role) {
		int returnValue = 0;
		try {
			returnValue = rolesMapper.assignRolesToEmployee(employeeId, role);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnValue;
	}

	@Override
	public int deleteEmployeeRoles(Roles role, int employeeId) {
		int returnValue = 0;
		try {
			returnValue = rolesMapper.deleteEmployeeRoles(role, employeeId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnValue;
	}

	@Override
	public int editEmployeeRoles(int employeeId, Roles editRole) {
		int returnValue = 0;
		try {
			returnValue = rolesMapper.editEmployeeRoles(employeeId, editRole.isPrimary(), editRole.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnValue;
	}

	@Override
	public int updatePrimaryRoles(int employeeId) {
		int returnValue = 0;
		try {
			returnValue = rolesMapper.updatePrimaryRoles(employeeId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnValue;
	}
}
