package com.cognine.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.cognine.model.AllocatedProjectResource;
import com.cognine.model.AvailableResource;
import com.cognine.model.Employee;
import com.cognine.model.Pmos;
import com.cognine.model.ProjectAttachments;
import com.cognine.model.ProjectConfiguration;
import com.cognine.model.ProjectResource;
import com.cognine.model.ProjectResourceDetails;
import com.cognine.model.ProjectRole;
import com.cognine.model.ProjectsFilter;
import com.cognine.model.ResourceFilter;
import com.cognine.model.RoleBasedProjects;
import com.cognine.model.Roles;
import com.cognine.model.TechStack;

@Mapper
public interface ProjectsMapper {

	public String isProjectNameExist = "select count(1) from projects where projectname=#{projectName} and clientid = #{clientId}";
	public String addProjectConfiguration = "insert into projects (projectname,description,startdate,enddate,createdat,updatedat,notes,clientid,projectaliasemail,hoursperday,actualstorypoints) values (#{projectName},#{description},#{startDate},#{endDate},now(),now(),#{notes},#{clientId},#{projectAliasEmail},#{hoursPerDay},#{actualStoryPoints}) returning id";
	public String addPMOtoProject = "insert into project_pmos(createdat,updatedat,projectid,employeeid) values (now(),now(),#{projectId},#{employeeRolesId})";
	public String getPmos = "select  employeeName,er.employeeid as employeeId from employee_roles er join roles r on r.id = er.roleid join employee e on e.id = er.employeeid where r.rolename = 'ROLE_PMO' and e.isactive = true and er.status = true order by employeeName asc";
	public String editProject = "update projects set description = #{description},startdate = #{startDate},enddate = #{endDate},updatedat = now(),notes = #{notes},projectaliasemail = #{projectAliasEmail}, hoursperday=#{hoursPerDay}, actualstorypoints=#{actualStoryPoints} where clientid = #{clientId} and projectname = #{projectName}";
	public String editProjectPmos = "update project_pmos set updatedat = now(),employeeid = #{employeeRolesId} where projectid = #{projectId}";
	public String updatePmos = "update project_pmos set status = false where projectid =#{projectId} and employeeid = #{employeeRolesId}";
	public String getFilteredPmos = "with test as (select distinct projectid from project_pmos pp "
			+ "left join employee_roles er on er.id = pp.employeeid " + "left join employee e on e.id = er.employeeid  "
			+ "where (employeeName ilike '%${searchPmoName}%')) "
			+ "select pmo.employeeid,  employeeName from project_pmos pmo  "
			+ "left join employee_roles er on er.id = pmo.employeeid "
			+ "left join employee e on e.id = er.employeeid  "
			+ "left join test t on t.projectid = pmo.projectid where t.projectid = #{projectId} and pmo.status = true";

	public String getAdminProjects = " select p.id,p.projectname,c.clientname,c.id as clientId, "
			+ " case when (current_date between p.startdate and p.enddate) or (p.enddate is null and p.startdate <= current_date) then true else false END as active "
			+ " from projects p LEFT JOIN CLIENTS c ON c.id = p.clientid where c.id = #{clientId} order by p.projectname asc";

	public String getPmosProjects = "select distinct p.id,p.projectname,c.clientname,  "
			+ "case when (current_date between p.startdate and p.enddate) or (p.enddate is null and p.startdate <= current_date)  "
			+ "then true else false END as active from employee e inner join project_pmos pp on pp.employeeid = e.id  "
			+ "inner join projects p on p.id = pp.projectid inner join clients c on c.id = p.clientid where pp.status = true "
			+ "and e.id = #{employeeId} and c.id = #{clientId} order by p.projectname asc    ";

	public String getPmosByProjectId = "select pp.employeeid,p.projectname,p.description,p.startdate,p.enddate, "
			+ " employeeName from project_pmos pp "
			+ "left join projects p on p.id = pp.projectid left join clients c on c.id=p.clientid  "
			+ "left join employee e on e.id = pp.employeeid "
			+ "where projectid = #{projectId} and pp.status = true or pp.status is null order by employeeName asc";
	public String getProjectDetails = "select p.id,p.projectname,p.description,p.startdate as startDate,p.enddate as endDate, "
			+ "p.notes,c.clientname,c.id as clientId  " + "from projects p "
			+ "LEFT JOIN CLIENTS c ON c.id = p.clientid where c.id = #{clientId} and p.id = #{projectId} order by p.createdat desc";

	public String getProjectRoles = "select * from roles where projectrole = true order by id";
	public String addResource = "insert into project_resources(employeeid,roleid,projectid,startdate,enddate,projectallocation,billableallocation,createdat,updatedat) values(#{employeeId},#{roleId},#{projectId},#{startDate},#{endDate},#{projectAllocation},#{billableAllocation},now(),now()) ";

	public String getResourceDetails = "select p.projectname,c.id as clientId,c.clientname,pr.*,employeeName as currentreportinghead from project_resources pr  "
			+ "left join employee e1 on e1.id = pr.currentreportingheadid "
			+ "left join employee e2 on e2.id = pr.newreportingheadid " + "left join projects p on p.id = pr.projectid "
			+ "left join clients c on c.id = p.clientid where pr.id = #{resourceId}";
	public String isProjectResourceExist = "select count(1) from project_resources "
			+ "where projectid = #{projectId} and employeeid = #{employeeId} and isactive = #{isActive}";
	public String updateResourceActiveStatus = "update project_resources set isactive = false where projectid = #{projectId} and employeeid = #{employeeId} and startdate = #{start_date} and enddate = #{end_date}";
	public String getProjectResourcesByProjectId = "With projectResources AS( (select pr.id,pr.employeeId, "
			+ "  e.reportsto as currentReportingHead , e.newreportinghead, p.projectname,pr.projectid,  "
			+ "  pr.roleid,r.displayrolename, r.rolename ,r.priorityorder , pr.projectallocation, "
			+ "  pr.billableallocation, e.employeeName , pr.startdate,pr.enddate from project_resources pr  "
			+ "  left join  employee e  on pr.employeeid = e.id  left join projects p on p.id = pr.projectid  "
			+ "  left join roles r on r.id = pr.roleid where projectid = #{projectId} and pr.enddate is null  and e.employeename ilike '%${resourceName}%' "
			+ "   )	" + "union all "
			+ "(select pr.id,pr.employeeId, e.reportsto as currentReportingHead , e.newreportinghead, "
			+ " p.projectname,pr.projectid,  " + " pr.roleid, r.displayrolename , r.rolename,r.priorityorder , "
			+ " pr.projectallocation, "
			+ " pr.billableallocation,  e.employeeName ,  pr.startdate,pr.enddate from project_resources pr  "
			+ " left join  employee e  on pr.employeeid = e.id " + " left join projects p on p.id = pr.projectid  "
			+ " left join roles r on r.id = pr.roleid where pr.projectid = #{projectId} and pr.enddate is not null and  e.employeename ilike '%${resourceName}%' "
			+ "	 ) order by enddate desc, priorityorder, employeename) "
			+ " select * from projectResources Offset (#{pageNum} - 1) * #{pageSize} limit #{pageSize}";

	public String getProjectInfo = "select p.id,p.projectname,p.description,p.startdate as startDate,p.enddate as endDate, "
			+ "p.notes,c.clientname,c.id as clientId  " + "from projects p "
			+ "LEFT JOIN CLIENTS c ON c.id = p.clientid where c.id = #{clientId} and p.id = #{projectId} order by p.createdat desc";

	public String getProjectDetailsByResource = "select pr.id as projectResourceId,pr.startdate,pr.enddate,pr.projectallocation, pr.projectid,"

			+ "p.projectname,c.clientname " + "from project_resources pr "
			+ "inner join projects p on p.id = pr.projectid " + "inner join clients c on c.id = p.clientid "
			+ "where (pr.startdate >= #{start_date} or pr.enddate >= #{start_date} or pr.enddate isnull) and (pr.startdate <= #{end_date} or pr.enddate <= #{end_date} or pr.enddate isnull) "
			+ "and pr.employeeid = #{employeeId} order by pr.startdate asc";
	public String isExists = "select count(1) from project_resources where employeeid = #{employeeId} "
			+ "and projectid = #{projectId} and startdate = #{startDate} and enddate is null";
	public String getProjectDetailsByResourceWithoutEndDate = "select pr.id as projectResourceId,pr.startdate,pr.enddate,pr.projectallocation, pr.projectid,"

			+ " p.projectname,c.clientname  " + "			 from project_resources pr  "
			+ "			 inner join projects p on p.id = pr.projectid " + "inner join clients c on c.id = p.clientid "
			+ "			 where (pr.startdate > #{start_date} or pr.enddate > #{start_date} or pr.enddate isnull)  "
			+ "and pr.employeeid = #{employeeId} order by pr.startdate asc";

	public String addConfirmedResourceAllocation = "insert into project_resources "
			+ "(employeeid,roleid,projectid,startdate,enddate,projectallocation,billableallocation, "
			+ "createdat,updatedat) " + "values(#{employeeId},#{roleId},#{projectId},#{availableResource.startDate}, "
			+ "#{availableResource.endDate},#{availableResource.allocatePercentage}, "
			+ "#{billableAllocation},now(),now()) ";

			
	public String isResourceAllocationExists = "select count(1) from project_resources where employeeid = #{employeeId} "
			+ "and projectid = #{projectId} and startdate = #{startDate} and (enddate = #{endDate} or enddate is null) ";
	public String updateResourceAllocation = "UPDATE project_resources "
			+ "	SET roleid=#{roleId},startdate = #{startDate}, enddate=#{endDate},billableallocation=#{billableAllocation}, "
			+ "updatedat=now() WHERE employeeid = #{employeeId} and projectid = #{projectId} and id = #{id}";

	public String getAllocatedProjectResourceDetailsByResource = "with dates as ( select unnest(array[ "
			+ "pr.startdate, pr.enddate "
			+ "]) as date, pr.projectallocation, pr.id , pr.projectid,case when #{startDate} > #{endDate} "
			+ "			  then #{startDate}::date else #{endDate}::date end as reqMax from project_resources pr where "
			+ " pr.employeeid = #{employeeId} and  "
			+ "			               (pr.startdate >= #{startDate} or pr.enddate >= #{startDate} or pr.enddate isnull)   "
			+ "			 and (pr.startdate <= #{endDate} or pr.enddate <= #{endDate} or pr.enddate isnull)  "
			+ "order by date asc), "
			+ "maxDateTable as(select case when dates.date is null then (select case when max(date) > reqMax then max(date) else reqMax end from dates group by reqMax) else date end  "
			+ ",dates.projectallocation, dates.id, dates.projectid from dates), " + "maxmin as (select "
			+ "		   case when min(date) <= #{startDate} then min(date) else #{startDate} end as mindate, "
			+ "		   case when max(date) >= #{endDate} then max(date) else #{endDate} end "
			+ "		   as maxdate from maxDateTable), "
			+ "maxmintable as (select generate_series( mindate ,maxdate, '1 day')::date as generatedDates from maxmin), "
			+ "generateDateSeriesBetweenMinAndMax as (select generate_series( min(maxDateTable.date) , max(maxDateTable.date), '1 day')::date as generatedDates "
			+ ",maxDateTable.projectallocation, maxDateTable.id " + "from (select * from maxDateTable) as maxDateTable "
			+ "group by maxDateTable.projectallocation, maxDateTable.id, maxDateTable.projectid order by generatedDates asc), "
			+ "sumProjectResourceAllocation as ( " + "select  "
			+ "sum(gn.projectallocation) over (partition by mnt.generatedDates) as alloc, "
			+ "rank() over (partition by mnt.generatedDates order by gn.id) " + ", gn.id "
			+ ", mnt.generatedDates as datesList " + "from generateDateSeriesBetweenMinAndMax gn  "
			+ "right join maxmintable mnt on mnt.generatedDates = gn.generatedDates " + "group by  "
			+ "gn.projectallocation,gn.id " + ", mnt.generatedDates order by datesList) "
			+ "select case when alloc is null then 0 else alloc end as projectAllocation,rank,id,dateslist as generatedDates "
			+ "from sumProjectResourceAllocation where rank = 1 and dateslist <= #{endDate} and dateslist >= #{startDate} ";

	public String getAllocatedProjectResourceDetailsByResourceWithouEndDate = "with dates as ( select unnest(array[ "
			+ "pr.startdate, pr.enddate "
			+ "]) as date, pr.projectallocation, pr.id , pr.projectid from project_resources pr where "
			+ "pr.employeeid = #{employeeId} and "
			+ " (pr.startdate >= #{startDate} or pr.enddate >= #{startDate} or pr.enddate isnull)  "
			+ "order by date asc), "
			+ "maxDateTable as(select case when dates.date is null then (select case when max(date) >= #{startDate} then max(date) else #{startDate} end  from dates) else date end  "
			+ ",dates.projectallocation, dates.id, dates.projectid from dates), " + "maxmin as (select   "
			+ "		   case when min(date) <= #{startDate} then min(date) else #{startDate} end as mindate, "
			+ "		   max(date) " + "		   as maxdate from maxDateTable), "
			+ "maxmintable as (select generate_series( mindate ,maxdate, '1 day')::date as generatedDates from maxmin), "
			+ "generateDateSeriesBetweenMinAndMax as (select generate_series( min(maxDateTable.date) , max(maxDateTable.date), '1 day')::date as generatedDates "
			+ ",maxDateTable.projectallocation, maxDateTable.id " + "from (select * from maxDateTable) as maxDateTable "
			+ "group by maxDateTable.projectallocation, maxDateTable.id, maxDateTable.projectid order by generatedDates asc), "
			+ "sumProjectResourceAllocation as ( " + "select  "
			+ "sum(gn.projectallocation) over (partition by mnt.generatedDates) as alloc, "
			+ "rank() over (partition by mnt.generatedDates order by gn.id), gn.id, mnt.generatedDates as datesList "
			+ "from generateDateSeriesBetweenMinAndMax gn  "
			+ "right join maxmintable mnt on mnt.generatedDates = gn.generatedDates "
			+ "group by gn.projectallocation,gn.id, mnt.generatedDates order by datesList) "
			+ "select case when alloc is null then 0 else alloc end as projectAllocation,rank,id,dateslist as generatedDates "
			+ "from sumProjectResourceAllocation where rank = 1 " + "and dateslist >= #{startDate} ";
	public String isResourceAllocationExistsWithoutEndDate = "select count(1) from project_resources where employeeid = #{employeeId} "
			+ "and projectid = #{projectId} and startdate = #{startDate} and enddate is null";

	public String updateProjectResourceEndDateWithProjectEndDate = "with updateProjectResourceEndDate as "
			+ " (select p.id,p.enddate,p.startdate from projects p "
			+ "  where p.enddate <= now()::date - INTERVAL '1 day' ),  " + " countVar as( update project_resources "
			+ "  " + "  set startdate = case  "
			+ "				   			when startdate >= now()::date - INTERVAL '1 day'  "
			+ "				  			then now()::date - INTERVAL '1 day'  "
			+ "							else startdate end, " + "	  enddate =  case  "
			+ "				   			when enddate > now()::date - INTERVAL '1 day' or enddate is null "
			+ "				  			then now()::date - INTERVAL '1 day'  "
			+ "				  				else enddate end   "
			+ "  where projectid in (select id from updateProjectResourceEndDate) "
			+ "  and (enddate > now()- INTERVAL '1 day' or enddate is null) returning 1) "
			+ " select count(*) from countVar";
	public String updateProjectResourceEndDateWhenEmployeeFalse = "WITH EMPLOYEEJOB AS( "
			+ "select pr.id,pr.startdate,pr.enddate,e.id as employeeid,e.isactive from employee e  "
			+ "	inner join project_resources pr on pr.employeeid = e.id where e.isactive = false ), "
			+ "countVar as(	update project_resources set startdate = case  "
			+ "											when startdate >= now()::date - INTERVAL '1 day'  "
			+ "											then now()::date - INTERVAL '1 day'  "
			+ "											else  startdate "
			+ "											end, " + "						enddate = case  "
			+ "										when  enddate >= now()::date - INTERVAL '1 day' or enddate is null "
			+ "											then now()::date - INTERVAL '1 day' "
			+ "										else  enddate " + "										end "
			+ "	 " + "  where employeeid in (select employeeid from EMPLOYEEJOB)     "
			+ "  and (enddate > now()- INTERVAL '1 day'  or enddate is null) returning 1) "
			+ "  select count(*) from countVar";
	public String updateInActiveClientsProjects = "with updateProjectEndDateJob as(select p.clientid,p.id,p.enddate,p.startdate from projects p   "
			+ "     where p.clientid = #{clientId}), " + "    countVar as (update projects set  "
			+ "                startdate = case "
			+ "                 when startdate>=now()::date - INTERVAL '1 day'    "
			+ "                       then now()::date - INTERVAL '1 day'  else startdate end,   "
			+ "                 enddate =  case    "
			+ "                       when enddate >= now()::date - INTERVAL '1 day' or enddate is null   "
			+ "                       then now()::date - INTERVAL '1 day' else enddate end  "
			+ "                 where clientid in (select clientid from updateProjectEndDateJob) returning 1 "
			+ "                )    select count(*) countVar";
	public String getFilteredProjects = "with projectsList as(select p.id,p.projectname,p.description,p.startdate as startDate,p.enddate as endDate, "
			+ "p.notes,p.projectaliasemail as projectAliasEmail,p.hoursperday,p.actualstorypoints,p.createdat,e.employeename  ,e.id as employeeId,  "
			+ "		 c.clientname,c.id as clientId,c.active,pp.status from projects p LEFT JOIN CLIENTS c ON c.id = p.clientid  "
			+ "		 left join project_pmos pp on pp.projectid = p.id "
			+ "		 left join employee e on e.id = pp.employeeid "
			+ "		 where (c.clientname ilike '%${searchClientName}%' and p.projectname ilike '%${searchProjectName}%'  "
			+ "	  and p.description ilike '%${searchDescription}%' and to_char(p.startdate::date,'yyyy-mm-dd') ilike '%${searchStartDate}%'  "
			+ "	 and (case when p.enddate is null then '' else to_char(p.enddate::date,'yyyy-mm-dd') "
			+ "end ilike '%${searchEndDate}%'))), "
			+ "filteredPmos as (select distinct id,projectname,clientname,hoursperday,actualstorypoints,startdate,enddate,createdat,status from projectsList where (employeename ilike '%${searchPmoName}%') and status = true), "
			+ "filteredDistinctProjects as (select distinct id,projectname,clientname,startdate,enddate,createdat from filteredPmos order by ${sortColumn} ${sortType} Offset (#{pageNum} - 1) * #{pageSize} limit #{pageSize}), "
			+ "			    projectPmos as (select pl.* from projectsList as pl  "
			+ "			   			   join filteredDistinctProjects fdp on pl.id = fdp.id )  "
			+ "			   			   select distinct * from projectPmos order by ${sortColumn} ${sortType}";

	public String addNewReportingHead = "update employee set newreportinghead = #{newReportingHead},updatedat = now() where id = #{employeeId}";
	public String getChangedReportingHeadDetails = "select reportsto as currentReportingHead,newreportinghead,id,employeeName from employee where newreportinghead is not null and reportsto != newreportinghead";
	public String getFilteredProjectsWhenSearchPmoNull = "with projectsList as(select p.id,p.projectname,p.description,p.startdate as startDate,p.enddate as endDate,   "
			+ "			   p.notes,p.projectaliasemail as projectAliasEmail,p.hoursperday,p.actualstorypoints,p.createdat,e.employeename  , e.id as employeeId  ,     "
			+ "			   		 c.clientname,c.id as clientId,c.active,pp.status from projects p LEFT JOIN CLIENTS c ON c.id = p.clientid    "
			+ "			   		 left join project_pmos pp on pp.projectid = p.id   "
			+ "			   		 left join employee e on e.id = pp.employeeid   "
			+ "			   		 where (c.clientname ilike '%${searchClientName}%' and p.projectname ilike '%${searchProjectName}%'    "
			+ "			   	  and p.description ilike '%${searchDescription}%' and to_char(p.startdate::date,'yyyy-mm-dd') ilike '%${searchStartDate}%'    "
			+ "			   	 and (case when p.enddate is null then '' else to_char(p.enddate::date,'yyyy-mm-dd')   "
			+ "			   end ilike '%${searchEndDate}%'))),   "
			+ "			   filteredPmos as (select distinct id,projectname,hoursperday,actualstorypoints,clientname,startdate,enddate,createdat  "
			+ "								from projectsList where (employeename ilike '%%' or employeename is null)) "
			+ "								,   "
			+ "			   filteredDistinctProjects as (select distinct id,projectname,clientname,startdate,enddate,createdat "
			+ "											from filteredPmos order by ${sortColumn} ${sortType} Offset (#{pageNum} - 1) * #{pageSize} limit #{pageSize}),   "
			+ "			   			    projectPmos as (select pl.* from projectsList as pl    "
			+ "			   			   			   join filteredDistinctProjects fdp on pl.id = fdp.id )    "
			+ "			   			   			   select distinct * from projectPmos order by ${sortColumn} ${sortType}";
	public String removeResourceAllocation = "delete from project_resources where id = #{id}";

	public String getOtherRolesProjects = " select distinct c.clientname,p.projectname, p.id, pr.active FROM employee e join project_resources pr on pr.employeeid =  e.id   "
			+ " join projects p on p.id = pr.projectid join clients c on c.id = p.clientid where  pr.active = true and   "
			+ " ((current_date BETWEEN p.startdate AND p.enddate) OR (p.enddate IS NULL and p.startdate <= current_date)) AND ((current_date BETWEEN pr.startdate AND pr.enddate) OR (pr.enddate IS NULL and pr.startdate <= current_date)) and  "
			+ " e.id = #{employeeId} and c.id = #{clientId} order by p.projectname asc ";

	public String updateNewReportingHeadWithNull = " update employee set newreportinghead = null,updatedat = now() where id = #{employeeId} ";

	public String getTechStackDropdown = "select \"SkillSetName\" as techstackname, \"SkillSetId\" as id, \"IsActive\" as isactive from \"LND\".\"SkillSet\" where \"IsActive\" = true order by \"SkillSetName\"";


	public String addTechStackToProject = "<script> <foreach collection='projectTechstacks' item='item' separator=';'>"
			+ " INSERT INTO public.project_techstack_mapping(projectid, techstackid) VALUES (#{projectId} , #{item.id}) ; "
			+ "</foreach> </script> ";

	public String deleteProjectTechStack = "<script> <foreach collection='projectTechstacks' item='item' separator=';'>"
			+ " UPDATE project_techstack_mapping   SET isactive = false  WHERE id = #{item.projectTechStackMappingId}"
			+ "</foreach> </script>";

	public String addProjectAttachment = "insert into attachments(attachment,createdat,updatedat,filename,filetype) values(#{attachment},now(),now(),#{fileName},#{fileType}) returning id";

	public String addProjectAttachmentMappingData = "INSERT INTO project_attachments_mapping(attachmentid, projectid) VALUES (#{attachmentId} , #{projectId}) ";

	public String getProjectAttachments = "select a.id as attachmentId,a.attachment,a.filename, a.filetype, pam.id as projectAttachmentMappingId  from attachments "
			+ " a inner join project_attachments_mapping pam on a.id = pam.attachmentid where pam.projectid = #{projectId} and a.isactive = true and pam.isactive = true ";

	public String deleteProjectAttachments = "<script> <foreach collection='projectAttachments' item='item' separator=';'>"
			+ " UPDATE attachments   SET isactive = false  WHERE id = #{item.attachmentId} ; "
			+ " UPDATE project_attachments_mapping  SET isactive = false  WHERE id = #{item.projectAttachmentMappingId}"
			+ "</foreach> </script>";

	public String getProjectTechStack = "SELECT ts.\"SkillSetId\" AS id, ptm.id AS projectTechStackMappingId, ptm.projectid, ts.\"SkillSetName\" AS techstackname,"
			+ "  ptm.isactive FROM \"LND\".\"SkillSet\" ts INNER JOIN project_techstack_mapping ptm ON ts.\"SkillSetId\" = ptm.techstackid "
			+ "WHERE ptm.projectid = #{projectId} AND ptm.isactive = true AND ts.\"IsActive\" = true";

	public String updateProjectResource = "UPDATE project_resources "
			+ "	SET startdate = #{startDate}, enddate=#{endDate},billableallocation=#{billableAllocation}, "
			+ "updatedat=now() WHERE employeeid = #{employeeId} and projectid = #{projectId} and id = #{id} ";

	public String addTechLeadToProject = "insert into project_tech_leads(createdat,updatedat,projectid,employeeid,startdate) values (now(),now(),#{projectId},#{employeeId}, TO_CHAR(CURRENT_DATE, 'YYYY-MM-DD')::date )";

	public String updateTechLeadForProject = "update project_tech_leads set status = false , enddate = TO_CHAR(CURRENT_DATE, 'YYYY-MM-DD')::date , updatedat = now() where projectid = #{projectId} and employeeid = #{employeeId} and id = #{id}";

	public String getProjectTechLeads = "select ptl.*, e.employeename from project_tech_leads ptl inner join projects p on ptl.projectid = p.id "
			+ " inner join employee e on e.id = ptl.employeeid where ptl.projectid = #{projectId} and ptl.status = true and e.isactive = true ";

	public String addManagerToProject = "insert into project_managers(createdat,updatedat,projectid,employeeid,startdate) values (now(),now(),#{projectId},#{employeeId}, TO_CHAR(CURRENT_DATE, 'YYYY-MM-DD')::date )";

	public String updateManagerForProject = "update project_managers set status = false , enddate = TO_CHAR(CURRENT_DATE, 'YYYY-MM-DD')::date , updatedat = now() where projectid = #{projectId} and employeeid = #{employeeId} and id = #{id}";

	public String getProjectManagers = "select pm.*, e.employeename from project_managers pm inner join projects p on pm.projectid = p.id "
			+ " inner join employee e on e.id = pm.employeeid where pm.projectid = #{projectId} and pm.status = true and e.isactive = true ";

	public String getEmployees = "select id as employeeId, employeeName, email from employee where isactive=true order by employeeName asc";

	public String getManageRoleProjects = " select distinct p.id,p.projectname,c.clientname,  "
			+ "case when (current_date between p.startdate and p.enddate) or (p.enddate is null and p.startdate <= current_date)  "
			+ "then true else false END as active from employee e inner join project_managers pm on pm.employeeid = e.id  "
			+ "inner join projects p on p.id = pm.projectid inner join clients c on c.id = p.clientid where pm.status = true "
			+ "and e.id = #{employeeId} and c.id = #{clientId} and ((current_date BETWEEN p.startdate AND p.enddate) OR (p.enddate IS NULL and p.startdate <= current_date)) order by p.projectname asc ";

	public String getTechLeadRoleProjects = " select distinct p.id,p.projectname,c.clientname,  "
			+ "case when (current_date between p.startdate and p.enddate) or (p.enddate is null and p.startdate <= current_date)  "
			+ "then true else false END as active from employee e inner join project_tech_leads ptl on ptl.employeeid = e.id  "
			+ "inner join projects p on p.id = ptl.projectid inner join clients c on c.id = p.clientid where ptl.status = true "
			+ "and e.id = #{employeeId} and c.id = #{clientId} and ((current_date BETWEEN p.startdate AND p.enddate) OR (p.enddate IS NULL and p.startdate <= current_date)) order by p.projectname asc";

	public String getOnlyTechLeadOrManagerProjects = "select distinct c.clientname,p.projectname, p.id, pr.active FROM employee e join project_resources pr on pr.employeeid =  e.id        "
			+ "join projects p on p.id = pr.projectid join clients c on c.id = p.clientid  "
			+ "join roles r on r.id = pr.roleid  where  pr.active = true and (r.rolename = 'ROLE_PROJECT_MANAGER' OR r.rolename = 'ROLE_TECH.LEAD') and "
			+ "((current_date BETWEEN p.startdate AND p.enddate) OR (p.enddate IS NULL and p.startdate <= current_date)) AND ((current_date BETWEEN pr.startdate AND pr.enddate) OR (pr.enddate IS NULL and pr.startdate <= current_date)) and "
			+ "e.id = #{employeeId} and c.id = #{clientId} order by p.projectname asc";

	public String getResourcesForResourcePool = "SET TIME ZONE 'Asia/Kolkata';" + "With balanceallocation as ( "
			+ "WITH allocation AS ( " + "  SELECT  " + "    employeeid, " + "    startdate, " + "    enddate, "
			+ "    roleid, " + "    projectallocation AS project_allocation, " + "    CASE  "
			+ "      WHEN startdate <= CURRENT_DATE AND (enddate IS NULL OR enddate >= CURRENT_DATE) THEN 2 "
			+ "      WHEN startdate > CURRENT_DATE THEN 1  "
			+ "      WHEN startdate < CURRENT_DATE AND enddate < CURRENT_DATE THEN 0  " + "    END AS project_status "
			+ "  FROM  " + "    project_resources " + "), " + "max_dates AS ( " + "  SELECT  " + "    employeeid, "
			+ "    MAX(startdate) AS max_startdate " + "  FROM allocation " + "  GROUP BY employeeid " + ") "
			+ "SELECT  " + "  a.employeeid,  " + "  MAX(a.project_status) AS status, " + "  CASE "
			+ "    WHEN MAX(md.max_startdate) >= CURRENT_DATE THEN  " + "      (SELECT MIN(startdate)  "
			+ "       FROM allocation  " + "       WHERE employeeid = a.employeeid  "
			+ "       AND startdate >= CURRENT_DATE) " + "    ELSE  " + "      MAX(a.startdate) "
			+ "  END AS startdate, " + "  CASE " + "    WHEN MAX(md.max_startdate) > CURRENT_DATE THEN  "
			+ "      (SELECT MAX(enddate)  " + "       FROM allocation  " + "       WHERE employeeid = a.employeeid  "
			+ "       AND enddate < CURRENT_DATE) " + "    ELSE  " + "      MAX(a.enddate) " + "  END AS enddate, "
			+ "  Max(roleid) as roleid, "
			+ "  Sum(CASE WHEN a.project_status = 2 THEN a.project_allocation ELSE 0 END) AS allocation "
			+ "FROM allocation a " + "INNER JOIN max_dates md ON a.employeeid = md.employeeid " + "GROUP BY  "
			+ "  a.employeeid " + "ORDER BY  " + "  a.employeeid " + " )  " + " SELECT employeeid,  " + " CASE  "
			+ "      WHEN status =2 THEN 'Active' " + "      WHEN status =1 THEN 'Future' "
			+ "      WHEN status =0 THEN 'Completed' "
			+ "    END AS resourceprojectstatus, startdate, enddate, allocation as projectAllocation, roleid "
			+ "FROM balanceallocation b  " + "INNER JOIN Employee e ON e.id = b.employeeid "
			+ "WHERE e.isactive = true  "
			+ "AND (status = 0 or status = 1 OR (status = 2 AND allocation < 100) ) order by employeeid";

	public String FREE_UP_RESOURCE_CONFIRMATION = "update project_resources set enddate = #{projectResourceChangedEndDate} where id = #{id}";

	@Select(getResourcesForResourcePool)
	List<ProjectResource> getResourcesForResourcePool();

	@Select(addProjectAttachment)
	int addProjectAttachment(ProjectAttachments projectAttachment);

	@Select(getEmployees)
	List<ProjectRole> getEmployees();

	@Insert(addProjectAttachmentMappingData)
	int addProjectAttachmentMappingData(int projectId, int attachmentId);

	@Select(isProjectNameExist)
	int isProjectNameExist(String projectName, int clientId);

	@Select(addProjectConfiguration)
	int addProjectConfiguration(ProjectConfiguration projectConfiguration);

	@Insert(addPMOtoProject)
	int addPMOtoProject(int employeeRolesId, int projectId);

	@Select(getPmos)
	List<Pmos> getPmos();

	@Update(editProject)
	int editProject(ProjectConfiguration projectConfiguration);

	@Update(editProjectPmos)
	int editProjectPmos(int employeeRolesId, int projectId);

	@Update(updatePmos)
	int updatePmos(int employeeRolesId, int projectId);

	@Select(getFilteredProjects)
	// @Results(
	// {
	//// @Result(property = "id", column = "id"),
	// @Result(property = "projectAttachments", column = "id", javaType =
	// List.class, many = @Many(select = "getProjectAttachments")) })
	List<ProjectConfiguration> getFilteredProjects(ProjectsFilter projectsFilter);

	@Select(getFilteredPmos)
	List<Pmos> getFilteredPmos(int projectId, String searchPmoName);

	@Select(getAdminProjects)
	List<ProjectConfiguration> getProjectsByClientId(RoleBasedProjects roleBasedProjects);

	@Select(getPmosByProjectId)
	List<Pmos> getPmosByProjectId(int projectId);

	@Select(getProjectDetails)
	List<ProjectConfiguration> getProjectDetails(int clientId, int projectId);

	@Select(getProjectRoles)
	List<Roles> getProjectRoles();

	@Insert(addResource)
	@Options(keyColumn = "id", keyProperty = "id", useGeneratedKeys = true)
	int addResource(ProjectResource projectResource);

	@Select(getResourceDetails)
	ProjectResource getResourceDetails(int resourceId);

	@Select(isProjectResourceExist)
	int isProjectResourceExist(int projectId, int employeeId, boolean isActive);

	@Update(updateResourceActiveStatus)
	int updateResourceActiveStatus(int projectId, int employeeId, java.util.Date start_date, java.util.Date end_date);

	@Select(getProjectResourcesByProjectId)
	List<ProjectResource> getProjectResourcesByProjectId(ResourceFilter resourceFilter);

	@Select(getProjectInfo)
	ProjectConfiguration getProjectInfo(int clientId, int projectId);

	@Select(getProjectDetailsByResource)
	List<ProjectResourceDetails> getProjectDetailsByResource(int employeeId, Date start_date, Date end_date);

	@Select(isExists)
	int isExists(int employeeId, int projectId, Date startDate, Date endDate);

	@Select(getProjectDetailsByResourceWithoutEndDate)
	List<ProjectResourceDetails> getProjectDetailsByResourceWithoutEndDate(int employeeId, Date start_date);

	@Insert(addConfirmedResourceAllocation)
	int addConfirmedResourceAllocation(int employeeId, int roleId, int projectId, int billableAllocation,
			AvailableResource availableResource);

	@Select(isResourceAllocationExists)
	int isResourceAllocationExists(int employeeId, int projectId, Date startDate, Date endDate);

	@Update(updateResourceAllocation)
	int updateResourceAllocation(ProjectResource projectResource);

	@Select(getAllocatedProjectResourceDetailsByResource)
	List<AllocatedProjectResource> getAllocatedProjectResourceDetailsByResource(int employeeId, Date startDate,
			Date endDate);

	@Select(getAllocatedProjectResourceDetailsByResourceWithouEndDate)
	List<AllocatedProjectResource> getAllocatedProjectResourceDetailsByResourceWithouEndDate(int employeeId,
			Date startDate);

	@Select(isResourceAllocationExistsWithoutEndDate)
	int isResourceAllocationExistsWithoutEndDate(int employeeId, int projectId, Date startDate);

	@Select(updateProjectResourceEndDateWithProjectEndDate)
	int updateProjectResourceEndDateWithProjectEndDate();

	@Select(updateProjectResourceEndDateWhenEmployeeFalse)
	int updateProjectResourceEndDateWhenEmployeeFalse();

	@Select(getPmosProjects)
	List<ProjectConfiguration> getPmosProjects(RoleBasedProjects roleBasedProjects);

	@Select(updateInActiveClientsProjects)
	int updateInActiveClientsProjects(int clientId);

	@Update(addNewReportingHead)
	int addNewReportingHead(String newReportingHead, int employeeId);

	@Select(getChangedReportingHeadDetails)
	List<Employee> getChangedReportingHeadDetails();

	@Select(getFilteredProjectsWhenSearchPmoNull)
	List<ProjectConfiguration> getFilteredProjectsWhenSearchPmoNull(ProjectsFilter projectsFilter);

	@Update(removeResourceAllocation)
	void removeResourceAllocation(int id);

	@Select(getOtherRolesProjects)
	List<ProjectConfiguration> getOtherRolesProjects(RoleBasedProjects roleBasedProjects);

	@Update(updateNewReportingHeadWithNull)
	int updateNewReportingHeadWithNull(int employeeId);

	@Select(getTechStackDropdown)
	List<TechStack> getTechStackDropdown();

	@Insert(addTechStackToProject)
	int addTechStackToProject(List<TechStack> projectTechstacks, int projectId);

	@Update(deleteProjectTechStack)
	int deleteProjectTechStack(List<TechStack> projectTechstacks);

	@Select(getProjectAttachments)
	List<ProjectAttachments> getProjectAttachments(int projectId);

	@Update(deleteProjectAttachments)
	int deleteProjectAttachments(List<ProjectAttachments> projectAttachments);

	@Select(getProjectTechStack)
	List<TechStack> getProjectTechStack(int projectId);

	@Update(updateProjectResource)
	int updateProjectResource(ProjectResource projectResource);

	@Insert(addTechLeadToProject)
	int addTechLeadToProject(ProjectRole projectRole);

	@Update(updateTechLeadForProject)
	int updateTechLeadForProject(ProjectRole projectRole);

	@Select(getProjectTechLeads)
	List<ProjectRole> getProjectTechLeads(int projectId);

	@Insert(addManagerToProject)
	int addManagerToProject(ProjectRole projectRole);

	@Update(updateManagerForProject)
	int updateManagerForProject(ProjectRole projectRole);

	@Select(getProjectManagers)
	List<ProjectRole> getProjectManagers(int projectId);

	@Select(getManageRoleProjects)
	List<ProjectConfiguration> getManageRoleProjects(RoleBasedProjects roleBasedProjects);

	@Select(getTechLeadRoleProjects)
	List<ProjectConfiguration> getTechLeadRoleProjects(RoleBasedProjects roleBasedProjects);

	@Select(getOnlyTechLeadOrManagerProjects)
	List<ProjectConfiguration> getOnlyTechLeadOrManagerProjects(RoleBasedProjects roleBasedProjects);

	@Update(FREE_UP_RESOURCE_CONFIRMATION)
	int freeUpResourceConfirmation(ProjectResource requestedResourceData);

}
