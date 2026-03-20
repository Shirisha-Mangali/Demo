package com.cognine.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.cognine.model.ClientsFilter;
import com.cognine.model.FilterReports;
import com.cognine.model.FilteredProjectDetails;
import com.cognine.model.FutureProjectFilters;
import com.cognine.model.PbiFilter;
import com.cognine.model.ProjectsFilter;
import com.cognine.model.ResourceFilter;
import com.cognine.model.SprintsFilter;

@Mapper
public interface CountMapper {

	String getRolesCount = "select count(*) from roles";
	String getFilteredClientsCount = "select count(*) from clients c1 " + "join country c2 on c2.id = c1.countryid "
			+ "join regions r on r.id = c1.regionid "
			+ " left join client_billing_type cbt on cbt.id = c1.clientbillingtypeid "
			+ "where (c1.clientname ILIKE '%${searchClientName}%' and r.regionname ILIKE '%${searchRegion}%' and "
			+ "c1.address ILIKE '%${searchAddress}%' and c1.city ILIKE '%${searchCity}%' and c1.state ILIKE '%${searchState}%' "
			+ "and c2.country ILIKE '%${searchCountry}%' and c1.active::text ilike '%${searchActive}%')";
	String getProjectsCount = "SELECT count(*) FROM PROJECTS";

	String getAllFilteredClientsCount = "select count(*) from clients c1 " + "join country c2 on c2.id = c1.countryid "
			+ "join regions r on r.id = c1.regionid "
			+ " left join client_billing_type cbt on cbt.id = c1.clientbillingtypeid "
			+ "where (c1.clientname ILIKE '%${searchClientName}%' and r.regionname ILIKE '%${searchRegion}%' and "
			+ "c1.address ILIKE '%${searchAddress}%' and c1.city ILIKE '%${searchCity}%' and c1.state ILIKE '%${searchState}%' "
			+ "and c2.country ILIKE '%${searchCountry}%')";
	String getProjectResourcesByProjectIdCount = "select count(*) from project_resources pr  "
			+ " left join  employee e  on pr.employeeid = e.id  " + " left join projects p on p.id = pr.projectid   "
			+ " left join roles r on r.id = pr.roleid where projectid = #{projectId} and e.employeename ilike '%${resourceName}%' ";
	String getSprintsCount = "select count(*) from sprints  "
			+ "where isactive = true and (sprintname ilike '%${sprintName}%' and startdate::text ilike '%${sprintStartDate}%' "
			+ "	   and enddate::text ilike '%${sprintEndDate}%') " + "and projectid = #{projectId}";
	String getFilteredPBIsCount = "select count(*) from pbi p " + "join pbi_type pt on pt.id = p.pbitypeid "
			+ "where (p.pbiId ilike '%${searcPbiId}%'  " + "	   and p.pbiName ilike '%${searchPbiName}%'  "
			+ "	   and pt.pbiType ilike '%${searchPbiType}%'  "
			+ "	   and p.addedAt::text ilike '%${searchAddedAt}%') and sprintid = #{sprintId} and isactive = true ";
	String getFilteredProjectsCount =

			"with projectsList as(select p.id,p.projectname,p.description,p.startdate as startDate,p.enddate as endDate, "
					+ "p.notes,p.projectaliasemail as projectAliasEmail,e.employeename as employeeName  , "
					+ "		 c.clientname,c.id as clientId,c.active,pp.status from projects p LEFT JOIN CLIENTS c ON c.id = p.clientid  "
					+ "		 left join project_pmos pp on pp.projectid = p.id "
					+ "		 left join employee e on e.id = pp.employeeid "
					+ "		 where (c.clientname ilike '%${searchClientName}%' and p.projectname ilike '%${searchProjectName}%'   "
					+ "		  and p.description ilike '%${searchDescription}%' and to_char(p.startdate::date,'yyyy-mm-dd') ilike '%${searchStartDate}%'  "
					+ "		 and (case when p.enddate is null then '' else to_char(p.enddate::date,'yyyy-mm-dd') end ilike '%${searchEndDate}%') ) "
					+ "	), "
					+ "	projectsWithNoPmo as (select distinct id,status from projectsList where(employeename ilike '%${searchPmoName}%') and status = true "
					+ "			), "
					+ "	projectsPmoMapping as (select cte1Alias.* from projectsList as cte1Alias join projectsWithNoPmo cte2Alias on cte1Alias.id = cte2Alias.id) "
					+ "	select count(distinct id) from projectsPmoMapping ";
	String getFilteredProjectsCountWhenSearchPmoNull = " with projectsList as(select p.id,p.projectname,p.description,p.startdate as startDate,p.enddate as endDate,   "
			+ "			   p.notes,p.projectaliasemail as projectAliasEmail,p.createdat,e.employeename ,   "
			+ "			   		 c.clientname,c.id as clientId,c.active,pp.status from projects p LEFT JOIN CLIENTS c ON c.id = p.clientid    "
			+ "			   		 left join project_pmos pp on pp.projectid = p.id   "

			+ "			   		 left join employee e on e.id = pp.employeeid   "
			+ "			   		 where (c.clientname ilike '%${searchClientName}%' and p.projectname ilike '%${searchProjectName}%'    "
			+ "			   	  and p.description ilike '%${searchDescription}%' and to_char(p.startdate::date,'yyyy-mm-dd') ilike '%${searchStartDate}%'    "
			+ "			   	 and (case when p.enddate is null then '' else to_char(p.enddate::date,'yyyy-mm-dd')   "
			+ "			   end ilike '%${searchEndDate}%'))),   "
			+ "			   filteredPmos as (select distinct id,projectname,clientname,startdate,enddate,createdat from projectsList "
			+ "							  where (employeename ilike '%%' or employeename is null)),   "
			+ "			   filteredDistinctProjects as (select distinct id,projectname,clientname,startdate,enddate,createdat  "
			+ "											from filteredPmos order by projectname asc ),   "
			+ "			   			    projectPmos as (select pl.* from projectsList as pl    "
			+ "			   			   			   join filteredDistinctProjects fdp on pl.id = fdp.id )    "
			+ "			   			   			   select count(distinct id)  from projectPmos";
	String getProjectDetailsCount = "with globalSearch as (select pp.id as pmoId, ee.employeeName  as pmoEmployeeName, ee.id as pmoEmployeeId , c.clientname as clientName,  "
			+ "p.id as projectId , pr.employeeid as employeeId, pr.startdate as resourceStartDate, pr.enddate as resourceEndDate, "
			+ "e.employeename as resourceName,e.reportsto as reportsTo,e.newreportinghead as newReportingHead,e.jobtitle as jobTitle,    "
			+ "p.projectname as projectName, p.startdate as projectStartDate , p.enddate as projectEndDate,   "
			+ "pr.projectallocation as projectAllocation,pr.billableallocation as billableAllocation,pr.active as projectActiveStatus,   "
			+ "e.isactive as employeeActiveStatus from project_resources pr   "
			+ "join employee e on e.id = pr.employeeid   " + "join projects p on p.id = pr.projectid    "
			+ "join project_pmos pp on pp.projectid= p.id   " + "join employee ee on pp.employeeid = ee.id   "
			+ "join clients c  on c.id = p.clientid where  pp.status = true and c.clientname ilike '%${searchClientName}%' and p.projectname ilike '%${searchProjectName}%'  "
			+ "and (ee.employeename ilike '%${searchPmoEmployeeName}%' or e.employeename ilike '%${searchResourceName}%' or e.reportsto ilike '%${searchReportsTo}%' or e.newreportinghead ilike '%${searchNewReportingHead}%') "
			+ ")  " + ",individualFieldSearch as (select * from globalSearch  "
			+ "						   where pmoemployeename ilike '%${searchPmoEmployeeName}%' and employeename ilike '%${searchResourceName}%' "
			+ "						and reportsto ilike '%${searchReportsTo}%' and (newreportinghead ilike '%${searchNewReportingHead}%' or newreportinghead isnull) "
			+ "						   and resourcestartdate::text ilike '%${searchResourceStartDate}%'  "
			+ "						and resourceenddate::text ilike '%${searchResourceEndDate}%'   "
			+ "						   and jobtitle ilike '%${searchJobTitle}%'  "
			+ "						   and projectstartdate::text ilike '%${searchProjectStartDate}%'  "
			+ "						and projectenddate::text ilike '%${searchProjectEndDate}%'  "
			+ " 						   and projectallocation = #{searchProjectAllocation} and billableallocation = #{searchBillableAllocation} "
			+ "						and projectactivestatus = true and employeeactivestatus = true "
			+ "						  ) select count(*) from individualFieldSearch ";
	String getFilteredFutureProjectsCount =

			"<script>with cte as(select fpr.resourceid,fp.id as projectId, "
					+ "fp.projectname,fps.id as futureProjectStatusid,fp.clientname,fp,notes,fpr.status as projectResourceStatus,"
					+ " e.employeename  as resourceName from future_projects fp "
					+ "left join future_projects_resource_mapping fpr on fp.id = fpr.future_projectid "
					+ "left join employee e on e.id = fpr.resourceid "
					+ "left join future_project_status fps on fp.future_project_statusid = fps.id "
					+ "where <if test = 'searchProjectId != 0'>fp.id = #{searchProjectId} and</if> fps.id in  "
					+ "<foreach item='item' index='index' collection='searchStatusIds' "
					+ " open='(' separator=',' close=')'> #{item} </foreach> " + "order by fpr.resourceid), "
					+ "cte2 as (select distinct resourceid,projectId from cte <if test = 'searchResourceId != 0'> "
					+ "where resourceid = #{searchResourceId}</if>), " + "projectResources as (select c1.* from cte c1 "
					+ "left join cte2 c2 on c2.projectId = c1.projectId <if test = 'searchResourceId != 0'> "
					+ "	where c1.resourceid = #{searchResourceId} and c1.projectResourceStatus = true</if>order by c1.projectId) "
					+ "select count(distinct projectId) from projectResources </script>";
	String getFilteredProjectResourceReportsCount =  "<script>with cte as(select distinct e.employeename, pr.projectid,pr.startdate as resourceStartDate,pr.enddate resourceEndDate, "
			+ "pr.projectallocation, p.projectname,p.startdate as projectStartDate,p.enddate as projectEndDate ,e.id as employeeId,e.isactive employeeactive, "
			+ "fpr.status as futureResource " + "from employee e "
			+ "	left join project_resources pr on e.id = pr.employeeid "
			+ "left  join projects p on   p.id = pr.projectid "
			+ "	left  join future_projects_resource_mapping fpr on fpr.resourceid = e.id and fpr.status = true "
			+ "left join future_projects fp on fp.id = fpr.future_projectid "
			+ "left join future_project_status fps on fps.id = fp.future_project_statusid " + "and fps.name = 'Future' "
			+ "<if test = 'searchToDate == null'> where (pr.enddate >= #{searchFromDate} or pr.enddate is null) "
			+ "<if test = 'searchIsAllocated == \"All\"'> and (pr.projectallocation is not null and p.projectname != 'RESOURCE POOL') "
			+ " or ((pr.projectallocation is null and e.isactive = true) or p.projectname = 'RESOURCE POOL') </if>"
			+ "<if test = 'searchIsAllocated == \"Allocated\"'> and pr.projectallocation is not null and p.projectname != 'RESOURCE POOL'</if> "
			+ "<if test = 'searchIsAllocated == \"Not Allocated\"'> and (pr.projectallocation is null and e.isactive = true) or p.projectname = 'RESOURCE POOL'</if> "
			+ "</if> "
			+ "<if test = 'searchToDate != null'>where ((pr.startdate is null or #{searchFromDate} >= pr.startdate  "
			+ "				and (pr.enddate is null or pr.enddate >= #{searchFromDate} or pr.enddate is null)) "
			+ "			  or (pr.startdate is null or #{searchToDate} >= pr.startdate "
			+ "				  and (pr.enddate is null or pr.enddate >= #{searchToDate} or pr.enddate is null)) "
			+ "			   or ((pr.startdate is null or pr.startdate >= #{searchFromDate}) "
			+ "				   and ((pr.enddate is null and #{searchToDate}>=pr.startdate) or #{searchToDate}>=pr.enddate ))) "
			+ "<if test = 'searchIsAllocated == \"All\"'> and (pr.projectallocation is not null and p.projectname != 'RESOURCE POOL') "
			+ " or ((pr.projectallocation is null and e.isactive = true) and (fpr.status is null or fpr.status = false) or p.projectname = 'RESOURCE POOL') </if> "
			+ "<if test = 'searchIsAllocated == \"Allocated\"'> and pr.projectallocation is not null and p.projectname != 'RESOURCE POOL'</if> "
			+ "<if test = 'searchIsAllocated == \"Not Allocated\"'>  and ((pr.projectallocation is null and e.isactive = true) and (fpr.status is null or fpr.status = false) "
			+ "or p.projectname = 'RESOURCE POOL')</if> "
			+ "</if>) select count(*) from cte </script>";


	@Select(getRolesCount)
	int getRolesCount();

	@Select(getProjectsCount)
	int getProjectsCount();

	@Select(getFilteredClientsCount)
	int getFilteredClientsCount(ClientsFilter clientsFilter);

	@Select(getFilteredProjectsCount)
	int getFilteredProjectsCount(ProjectsFilter projectsFilter);

	@Select(getAllFilteredClientsCount)
	int getAllFilteredClientsCount(ClientsFilter clientsFilter);

	@Select(getProjectResourcesByProjectIdCount)
	int getProjectResourcesByProjectIdCount(ResourceFilter resourceFilter);

	@Select(getSprintsCount)
	int getSprintsCount(SprintsFilter sprintsFilter);

	@Select(getFilteredPBIsCount)
	int getFilteredPBIsCount(PbiFilter pbiFilter);

	@Select(getFilteredProjectsCountWhenSearchPmoNull)
	int getFilteredProjectsCountWhenSearchPmoNull(ProjectsFilter projectsFilter);

	@Select(getProjectDetailsCount)
	int getProjectDetailsCount(FilteredProjectDetails filteredProjectDetails);

	@Select(getFilteredFutureProjectsCount)
	int getFilteredFutureProjectsCount(FutureProjectFilters futureProjectFilters);

	@Select(getFilteredProjectResourceReportsCount)
	int getFilteredProjectResourceReportsCount(FilterReports filterReports);
    
}
