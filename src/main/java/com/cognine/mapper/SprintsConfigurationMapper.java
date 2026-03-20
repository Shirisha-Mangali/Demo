package com.cognine.mapper;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.cognine.model.PBIType;
import com.cognine.model.Pbi;
import com.cognine.model.PbiFilter;
import com.cognine.model.SprintDeleteResponse;
import com.cognine.model.RoleBasedSprints;
import com.cognine.model.SprintDetails;
import com.cognine.model.SprintResourcesData;
import com.cognine.model.SprintsConfiguration;
import com.cognine.model.SprintsFilter;
import com.cognine.model.TeamSize;

@Mapper
public interface SprintsConfigurationMapper {

	String isSprintExist = "select count(1) from sprints s join projects p on p.id = s.projectid "
			+ "where s.isactive = true and s.projectid = #{projectId}  and lower(s.sprintname) = lower(#{sprintName})";

	String isSprintDatesExist = "select s.id,s.sprintname,s.projectid,s.startdate,s.enddate,e1.employeename"
			+ " as createdby,e2.employeename as updatedby from sprints s " + "join projects p on p.id = s.projectid "
			+ "join clients c on c.id = p.clientid " + "join employee e1 on e1.id = s.createdby "
			+ "join employee e2 on e2.id = s.updatedby where  " + "(s.startdate between #{startDate} and #{endDate} or "
			+ "s.enddate between #{startDate} and #{endDate} or "
			+ "#{startDate}  between s.startdate and s.enddate or "
			+ "#{endDate}  between s.startdate and s.enddate ) and s.projectId = #{projectId} and s.isactive = true "
			+ "and (s.id = 0 or s.id != #{sprintId})";

	String addSprint = "INSERT INTO sprints( "
			+ " sprintname, description, projectid, createdby, updatedby, startdate, enddate, leaves, holidays,sprintdays,teamsize) "
			+ "	VALUES (#{sprintName}, #{description}, #{projectId}, #{createdBy}, #{updatedBy}, #{startDate}, #{endDate}, #{leaves}, "
			+ " #{holidays},#{sprintDays},#{teamSize})  returning id";

	String editSprint = "update sprints set sprintname = #{sprintName},description= #{description},startdate = #{startDate}, "
			+ "	enddate = #{endDate},leaves = #{leaves},holidays = #{holidays},updatedby=#{updatedBy}, "
			+ "sprintdays = #{sprintDays}, teamsize = #{teamSize} where id = #{id} ";

	String getTeamSize = "with teamSize as (select distinct pr.employeeid from project_resources pr  where pr.billableallocation !=0 and "
			+ "  ( #{startDate} <= pr.enddate  or pr.enddate is null) "
			+ " and ( #{endDate} >= pr.startdate ) and "
			+ "pr.projectid = #{projectId} ) "
			+ "select count(*) as resourceCount from teamSize";

	String getSprints = "select * from sprints  "
			+ "where (sprintname ilike '%${sprintName}%' and startdate::text ilike '%${sprintStartDate}%' "
			+ "	   and enddate::text ilike '%${sprintEndDate}%') "
			+ "and projectid = #{projectId} and isactive = true order by startdate desc Offset (#{pageNum} - 1) * #{pageSize} limit #{pageSize}";

	String isPBIExists = "select count(1) from pbi p join sprints s on s.id = p.sprintid "
			+ "where lower(pbiid) = lower(#{pbiId}) and  s.id = #{sprintId} and p.isactive = true";

	String addPBI = "INSERT INTO pbi(pbiid, pbiname, pbitypeid, sprintid, addedat, "
			+ "createdat, updatedat,priority) VALUES (#{pbiId},#{pbiName},#{pbiTypeId},#{sprintId},#{addedAt}, now(), now(),#{priority})";

	String getPBITypes = "select * from pbi_type order by pbitype asc";

	String editPBIWithSourceAndDestination = "update pbi set  " + " priority = CASE  "
			+ "        WHEN #{source}>#{destination} and priority!=#{source} " + "            THEN priority+1  "
			+ "        when #{source}>#{destination} and priority = #{source}  " + "            THEN #{destination}  "
			+ "        WHEN  #{source}<#{destination} and priority!=#{source}  " + "            THEN priority-1  "
			+ "        when #{source}<#{destination} and priority = #{source}  " + "            THEN #{destination} "
			+ "			when #{source}=#{destination} and (priority = #{source} or priority = #{destination}) "
			+ "				THEN #{source} " + "				END "
			+ "WHERE (priority between #{source} and #{destination} or  priority between #{destination} and #{source})  "
			+ "and isactive = true and sprintid = #{sprintId}";

	String deletePBI = "with cte as(update pbi set isactive = 'false',updatedat=now() where id = #{id} returning priority, sprintId) "
			+ "update pbi set priority = priority-1,updatedat=now() where priority > (select priority from cte) and sprintid = (select sprintId from cte)";

	String getFilteredPBIs = "select p.*,pt.pbitype as pbiTypeName from pbi p "
			+ "join pbi_type pt on pt.id = p.pbitypeid where (p.pbiId ilike '%${searcPbiId}%'  "
			+ "	   and p.pbiName ilike '%${searchPbiName}%'  " + "	   and pt.pbiType ilike '%${searchPbiType}%'  "
			+ "	   and p.addedAt::text ilike '%${searchAddedAt}%') and sprintid = #{sprintId} and isactive = true order by priority"
			+ "	   Offset (#{pageNum} - 1) * #{pageSize} limit #{pageSize} ";

	String sprintClose = "with sprintClose as(select p.id,p.enddate,p.startdate from projects p  "
			+ "                    where p.enddate <= now()::date - INTERVAL '1 day' ), "
			+ "countVar as(update sprints set startdate = case "
			+ "            when startdate>=now()::date - INTERVAL '1 day'    "
			+ "                       then now()::date - INTERVAL '1 day'  else startdate end,   "
			+ "                 enddate =  case    "
			+ "                       when enddate >= now()::date - INTERVAL '1 day' or enddate is null   "
			+ "                       then now()::date - INTERVAL '1 day'  "
			+ "                       else enddate end  "
			+ "                 where projectid in (select id from sprintClose)  "
			+ "            and (enddate > now()-INTERVAL '1 day' or enddate is null) returning 1) "
			+ "           select count(*) countVar";

	String getLastPbiSequence = "select case when priority is null then 0 else priority end from pbi "
			+ "where sprintId = #{sprintId}  and isactive = true order by priority desc limit 1 ";

	String editPBI = "update pbi set pbiname = #{pbiName},pbitypeid = #{pbiTypeId}, "
			+ " addedat = #{addedAt},updatedat=now() where id=#{id}";

	String getPbiDataByPriority = "select id,pbiid,pbiname,priority from pbi where sprintid = #{sprintId} and priority = #{priority} and isactive = true";

	
	String getPbiHoursCountByPbiId = "select count(id) > 0 from timeline where pbiid = #{id} and isactive = true";
	
	String isActiveSprintExist = "SELECT EXISTS (SELECT 1 FROM sprints WHERE isactive = true and projectid = #{projectId} AND current_date BETWEEN startdate AND enddate) AS result";

	String isResourcesExistInTimeline = " select 0 < COUNT(DISTINCT t.resourceid) AS result from employee e inner join timeline t on t.resourceid = e.id "
			+ " inner join pbi p on p.id = t.pbiid where p.sprintid = #{id} and t.isactive = true and p.isactive = true and e.isactive = true " ;
	
	
	String deleteSprintBySprintId = " WITH updated AS (  "
			+ " UPDATE pbi AS p SET isactive = false FROM sprints AS s WHERE p.sprintid = s.id  "
			+ " AND p.sprintid =  #{sprintId} AND p.isactive = true  "
			+ " RETURNING p.id  "
			+ " ),  "
			+ " timeline_updated AS (  "
			+ " UPDATE timeline AS t SET isactive = false FROM updated WHERE t.pbiid = updated.id  "
			+ " AND t.isactive = true RETURNING t.id  "
			+ " ),  "
			+ " sprints_updated AS ( "
			+ " UPDATE sprints AS s SET isactive = false WHERE s.id = #{sprintId} AND s.isactive = true  "
			+ "	returning s.id  "
			+ " ),  "
			+ " timelinenotes_updated AS ( "
			+ " UPDATE timelinenotes AS tn SET isactive = false WHERE tn.sprintid = #{sprintId} AND tn.isactive = true "
			+ "	returning tn.id "
			+ " ) "
			+ " SELECT  "
			+ "    (SELECT COUNT(*) FROM updated) AS pbiUpdatedCount, "
			+ "    (SELECT COUNT(*) FROM timeline_updated) AS timelineUpdatedCount, "
			+ "    (SELECT COUNT(*) FROM sprints_updated) AS sprintsUpdatedCount; ";
	
	
	String getOnboardedAndreservedResourceData = "SELECT COUNT(DISTINCT CASE WHEN pr.billableallocation > 0 THEN pr.employeeid END) AS onBoardedResourcesCount, "
			+ " COUNT(DISTINCT CASE WHEN pr.billableallocation = 0 THEN pr.employeeid END) AS reservedResourcesCount FROM project_resources pr "
			+ "INNER JOIN projects p ON p.id = pr.projectid INNER JOIN sprints s ON s.projectid = p.id  "
			+ "WHERE s.projectid = #{projectId} AND s.id = #{sprintId} and s.isactive = true AND ((pr.startdate between s.startdate and s.enddate)  "
			+ " or ( (pr.enddate >= s.enddate or (pr.enddate is null)) and pr.startdate <= s.startdate ) or (pr.enddate between s.startdate and s.enddate) );";
	
	String getSprintsByProjectId= "select id ,sprintname,startdate,enddate, CASE WHEN current_date between startdate and enddate THEN TRUE "
			+ " ELSE FALSE END AS active from sprints where projectid = #{projectId} and isactive = true order by id desc ";
	
	@Select(addSprint)
	int addSprint(SprintsConfiguration sprintsConfiguration);

	@Select(isSprintExist)
	int isSprintExist(int projectId, String sprintName);

	@Select(isSprintDatesExist)
	List<SprintDetails> isSprintDatesExist(int projectId, Date startDate, Date endDate, int sprintId);

	@Update(editSprint)
	int editSprint(SprintsConfiguration sprintsConfiguration);

	@Select(getTeamSize)
	TeamSize getTeamSize(int projectId, Date startDate, Date endDate);

	@Select(getSprints)
	@Results({
	    @Result(property = "id", column = "id"),
	    @Result(property = "hasTimelineResources", column = "id", one = @One(select = "isResourcesExistInTimeline"))
	})
	List<SprintDetails> getSprints(SprintsFilter sprintsFilter);
	

	@Select(isPBIExists)
	int isPBIExists(String pbiId, int sprintId);

	@Insert(addPBI)
	int addPBI(Pbi pbi);

	@Select(getPBITypes)
	List<PBIType> getPBITypes();

	@Update(deletePBI)
	int deletePBI(int id);

//	@Select(getFilteredPBIs)
//	List<Pbi> getFilteredPBIs(PbiFilter pbiFilter);

	@Select(sprintClose)
	int sprintClose();

	@Select(getLastPbiSequence)
	Pbi getLastPbiSequence(int sprintId);

	@Update(editPBI)
	int editPBI(Pbi pbi);

	@Update(editPBIWithSourceAndDestination)
	int editPBIWithSourceAndDestination(int source, int destination, int sprintId);

	@Select(getPbiDataByPriority)
	Pbi getPbiDataByPriority(Pbi pbi);
	
	@Select(getPbiHoursCountByPbiId)
	boolean getPbiHoursCountByPbiId(int id);
	
	@Select(getFilteredPBIs)
	@Results({
	    @Result(property = "id", column = "id"),
	    @Result(property = "hasTimelineHours", column = "id", one = @One(select = "getPbiHoursCountByPbiId"))
	})
	List<Pbi> getFilteredPBIs(PbiFilter pbiFilter);
	
	@Select(isActiveSprintExist)
	boolean IsActiveSprintExist(int projectId);
	
	@Select(isResourcesExistInTimeline)
	boolean isResourcesExistInTimeline(int id);
	
	@Select(deleteSprintBySprintId)
	SprintDeleteResponse deleteSprint(int sprintId);
	
	
	@Select(getOnboardedAndreservedResourceData)
	SprintResourcesData getOnboardedAndreservedResourceData(int sprintId, int projectId);
	
	@Select(getSprintsByProjectId)
	List<SprintDetails> getSprintsByProjectId(RoleBasedSprints roleBasedSprints);
	
	
}
