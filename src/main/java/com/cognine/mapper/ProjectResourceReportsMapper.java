package com.cognine.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.cognine.model.FilterReports;
import com.cognine.model.ProjectResourceReports;

@Mapper
public interface ProjectResourceReportsMapper {

	String getFilteredProjectResourceReports = "<script> select distinct e.employeename, pr.projectid,pr.startdate as resourceStartDate,pr.enddate resourceEndDate, "
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
			+ "<if test = 'searchIsAllocated == \"Not Allocated\"'>  and ((pr.projectallocation is null and e.isactive = true) "
			+ " and (fpr.status is null or fpr.status = false) or p.projectname = 'RESOURCE POOL')</if> "
			+ "</if> "
			+ "order by e.employeename, pr.startdate desc, pr.enddate desc <if test = 'isFilterData == \"true\"'>Offset (#{pageNum} - 1) * #{pageSize} limit #{pageSize} </if>"
			+ " </script>";

	@Select(getFilteredProjectResourceReports)
	List<ProjectResourceReports> getFilteredProjectResourceReports(FilterReports filterReports);

}
