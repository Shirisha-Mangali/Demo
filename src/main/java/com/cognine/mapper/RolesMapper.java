package com.cognine.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.cognine.model.Roles;

@Mapper
public interface RolesMapper {

	String getRoles = "select * from roles where globalrole = true order by priorityorder";
	String assignRolesToEmployee = "insert into employee_roles(roleid, employeeid, isprimary, createdat, updatedat) "
			+ " values (#{role.id},#{employeeId},#{role.primary},now(),now())";
	String isEmployeeRoleExist = "select count(1) from employee_roles where roleid = #{roleId} and employeeid = #{employeeId} and status = true";
	String deleteEmployeeRoles = "update employee_roles set status = false where roleid = #{role.id} and employeeid = #{employeeId}";
	String editEmployeeRoles = "update employee_roles set isprimary = #{primary} where employeeid = #{employeeId} and roleid = #{roleId}";
	String updatePrimaryRoles = "update employee_roles set isprimary = false where employeeid = #{employeeId}";

	@Select(getRoles)
	List<Roles> getRoles();

	@Insert(assignRolesToEmployee)
	int assignRolesToEmployee(int employeeId, Roles role);

	@Select(isEmployeeRoleExist)
	int isEmployeeRoleExist(int employeeId, int roleId);

	@Update(deleteEmployeeRoles)
	int deleteEmployeeRoles(Roles role, int employeeId);

	@Update(editEmployeeRoles)
	int editEmployeeRoles(int employeeId, boolean primary, int roleId);

	@Update(updatePrimaryRoles)
	int updatePrimaryRoles(int employeeId);

}
