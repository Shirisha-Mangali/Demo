package com.cognine.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.cognine.model.Employee;
import com.cognine.model.Roles;

@Mapper
public interface EmployeeMapper {

	String getEmployeeRoles = "select e.id,e.email from employee e where lower(e.email)=lower(#{email}) and isactive = true";
	String findRolesById = "select er.isprimary as primary,r.id,r.rolename,r.displayrolename,r.description,r.active from employee_roles er "
			+ "join roles r on er.roleid=r.id where er.employeeid = #{id} and er.status = true";
	String searchEmployee = "select id, employeeName, email from employee where isactive=true order by id asc";
	String getEmployeeRolesByEmail = "select e.id,e.email, employeeName from employee e where e.email=#{email} and isactive = true";
	String getRolesByEmail = "select er.isprimary as primary,r.id,r.rolename,r.displayrolename from employee_roles er "
			+ "join roles r on er.roleid=r.id where er.employeeid = #{id} and er.status = true";
	String getProjectRoles = "select distinct pr.roleid as id,r.rolename,r.displayRoleName from project_resources pr "
			+ "join roles r on r.id = pr.roleid " + "where employeeid = #{id} and pr.active = true";
    
    public String getProjectTechLeadRoles = " SELECT distinct (SELECT id FROM roles WHERE rolename = 'ROLE_TECH.LEAD') AS id,  "
			+ "(SELECT displayRoleName FROM roles WHERE rolename = 'ROLE_TECH.LEAD') AS displayRoleName,  "
			+ "	'ROLE_TECH.LEAD' AS rolename  "
			+ "FROM project_tech_leads ptl   "
			+ "INNER JOIN employee e ON e.id = ptl.employeeid  "
			+ "WHERE ptl.employeeid = #{employeeId} AND e.isactive = true AND ptl.status = true  ";
    
    public String getProjectManagerRoles = " SELECT distinct   (SELECT id FROM roles WHERE rolename = 'ROLE_PROJECT_MANAGER') AS id,  "
			+ "    (SELECT displayRoleName FROM roles WHERE rolename = 'ROLE_PROJECT_MANAGER') AS displayRoleName,  "
			+ "	'ROLE_PROJECT_MANAGER' as rolename "
			+ " FROM project_managers pm   "
			+ " INNER JOIN employee e ON e.id = pm.employeeid  "
			+ " WHERE pm.employeeid = #{employeeId} AND e.isactive = true AND pm.status = true ";

    
	public String getAllResources = "select id, employeeName, "
			+ " reportsto as currentReportingHead from employee where isactive = true order by employeeName asc";

	@Select(getEmployeeRoles)
	@Results({ @Result(property = "id", column = "id"),
			@Result(property = "employeeRoles", column = "id", one = @One(select = "findRolesById")) })
	Employee getEmployeeRoles(@Param("email") String email);

	@Select(findRolesById)
	@Results({ @Result(property = "id", column = "id"), @Result(property = "roleName", column = "rolename"),
			@Result(property = "displayRoleName", column = "displayrolename"),
			@Result(property = "primary", column = "primary") })
	List<Roles> findRolesById(@Param("id") int id);

	@Select(searchEmployee)
	List<Employee> searchEmployee();

	@Select(getEmployeeRolesByEmail)
	@Results({ @Result(property = "id", column = "id"),
			@Result(property = "employeeRoles", column = "id", one = @One(select = "getRolesByEmail")) })
	Employee getEmployeeRolesByEmail(String email);

	@Select(getRolesByEmail)
	@Results({ @Result(property = "id", column = "id"), @Result(property = "roleName", column = "rolename"),
			@Result(property = "displayRoleName", column = "displayrolename"),
			@Result(property = "primary", column = "primary") })
	List<Roles> getRolesByEmail(@Param("id") int id);

	@Select(getProjectRoles)
	List<Roles> getProjectRoles(int id);

	List<Roles> getProjectRoles();

	@Select(getAllResources)
	List<Employee> getAllResources();
	
	@Select(getProjectTechLeadRoles)
	Roles getProjectTechLeadRoles(int employeeId);
	
	@Select(getProjectManagerRoles)
	Roles getProjectManagerRoles(int employeeId);
}
