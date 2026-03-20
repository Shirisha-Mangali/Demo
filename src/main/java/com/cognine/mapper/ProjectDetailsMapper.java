package com.cognine.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.cognine.model.FilteredProjectDetails;
import com.cognine.model.Pmos;
import com.cognine.model.ProjectDetails;

@Mapper
public interface ProjectDetailsMapper {

	public String GetProjectDetails = "<script> "
			+ "WITH partialSearch AS ( "
			+ "  SELECT  "
			+ "   c.clientname,  "
			+ "   c.id AS clientid,  "
			+ "   p.id AS projectid, "
			+ "   p.projectname,  "
			+ "   p.startdate AS projectStartDate,  "
			+ "   p.enddate AS projectEndDate,  "
			+ "   pr.id AS projectResourceUniqueId,  "
			+ "   pr.employeeid AS resourceId,  "
			+ "   pr.projectallocation,  "
			+ "   pr.billableallocation,  "
			+ "   pr.startdate AS resourceStartDate,  "
			+ "   pr.enddate AS resourceEndDate,  "
			+ "   e.employeename AS resourceName,  "
			+ "   e.isactive::text AS employeeActiveStatus,  "
			+ "   e.reportsto AS currentReportingHead,  "
			+ "   r.displayrolename AS displayRoleName,  "
			+ "   r.priorityorder,  "
			+ "   pp.id AS pmoId,  "
			+ "   epmo.id AS pmoEmployeeId,  "
			+ "   epmo.employeename AS pmoEmployeeName "
			+ "  FROM clients c "
			+ "  INNER JOIN projects p ON p.clientid = c.id "
			+ "  LEFT JOIN project_resources pr ON pr.projectid = p.id AND pr.active = TRUE "
			+ "  LEFT JOIN employee e ON e.id = pr.employeeid "
			+ "  LEFT JOIN roles r ON r.id = pr.roleid "
			+ "  LEFT JOIN project_pmos pp ON pp.projectid = p.id AND pp.status = TRUE "
			+ "  LEFT JOIN employee epmo ON epmo.id = pp.employeeid "
			+ "  WHERE c.clientname ILIKE '%${searchClientName}%' "
			+ "  <if test='searchProjectName.length > 0'> "
			+ "   AND p.projectname ILIKE '%${searchProjectName}%' "
			+ "  </if> "
			+ "  <if test = 'isActiveClientsAndProjectsCheck'> and c.active = true and ((current_date between p.startdate and p.enddate) or (p.enddate is null and current_date >= p.startdate ))</if>"
			+ "), "
			+ "historical_reporting AS ( "
			+ "  SELECT  "
			+ "   eh.employeeid AS resourceId,  "
			+ "   pr.projectid,  "
			+ "   eh.reportsto,  "
			+ "   eh.startdate AS reportingHeadStartDate,  "
			+ "   eh.enddate AS reportingHeadEndDate "
			+ "  FROM employee_history eh "
			+ "  JOIN project_resources pr ON pr.employeeid = eh.employeeid AND pr.active = TRUE "
			+ "  WHERE  "
			+ "   (eh.startdate &lt;= COALESCE(pr.enddate, CURRENT_DATE))  "
			+ "   AND (eh.enddate IS NULL OR eh.enddate &gt;= pr.startdate)) "
			+ "SELECT  "
			+ "  ps.clientname,  "
			+ "  ps.clientid,  "
			+ "  ps.projectid,  "
			+ "  ps.projectname,  "
			+ "  ps.projectStartDate,  "
			+ "  ps.projectEndDate,  "
			+ "  ps.projectResourceUniqueId,  "
			+ "  ps.resourceId,  "
			+ "  ps.resourceName,  "
			+ "  ps.employeeActiveStatus,  "
			+ "  ps.displayRoleName,  "
			+ "  ps.priorityorder,  "
			+ "  ps.resourceStartDate,  "
			+ "  ps.resourceEndDate,  "
			+ "  ps.projectallocation,  "
			+ "  ps.billableallocation,  "
			+ "  ps.currentReportingHead AS reportingHead,  "
			+ "  hr.reportsto AS reportsTo,  "
			+ "  hr.reportingHeadStartDate,  "
			+ "  hr.reportingHeadEndDate,  "
			+ "  ps.pmoId,  "
			+ "  ps.pmoEmployeeId,  "
			+ "  ps.pmoEmployeeName "
			+ "FROM partialSearch ps "
			+ "LEFT JOIN historical_reporting hr ON hr.resourceId = ps.resourceId  "
			+ "<where> "
			+ "  <if test='searchGlobalEmployeeName.length > 0'> "
			+ "   (ps.resourceName ILIKE '%${searchGlobalEmployeeName}%' "
			+ "    OR hr.reportsto ILIKE '%${searchGlobalEmployeeName}%' "
			+ "    OR ps.pmoEmployeeName ILIKE '%${searchGlobalEmployeeName}%') "
			+ "  </if> "
			+ "</where> "
			+ "ORDER BY  "
			+ "  ps.resourceName IS NOT NULL,  "
			+ "  ps.projectid DESC NULLS LAST,  "
			+ "  ps.resourceId,  "
			+ "  hr.reportingHeadStartDate "
			+ "</script>";

	public String getProjectPmosByProjectId = "select pp.id, e.employeeName, e.id as employeeId from project_pmos  pp inner join employee e on pp.employeeid = e.id where projectid = #{projectId} and status = true";

	public String getResourcePoolData = "select id as projectid, clientid from projects where upper(projectname) = 'RESOURCE POOL' limit 1";
	
	@Select(getProjectPmosByProjectId)
	List<Pmos> getProjectPmos(int projectId);

	@Select(GetProjectDetails)
	List<ProjectDetails> getProjectDetails(FilteredProjectDetails filteredProjectDetails);

	@Select(getResourcePoolData)
	ProjectDetails getResourcePoolData();

}
