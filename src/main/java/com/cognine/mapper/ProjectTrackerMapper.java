package com.cognine.mapper;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.cognine.model.AccomplishmentData;
import com.cognine.model.EditableFields;
import com.cognine.model.HolidaysData;
import com.cognine.model.PbiTimeline;
import com.cognine.model.ProjectResource;
import com.cognine.model.ProjectTrackerColorCode;
import com.cognine.model.ProjectTrackerPBIFields;
import com.cognine.model.ProjectTrackerPbi;
import com.cognine.model.SprintDetails;

@Mapper
public interface ProjectTrackerMapper {

	String getSprintDataById = " WITH sprint_info AS (  "
			+ "    SELECT s.id, s.startdate, s.enddate, p.hoursperday,p.actualstorypoints, "
			+ "        (SELECT COUNT(*)  "
			+ "         FROM generate_series(s.startdate::date, s.enddate::date, '1 day'::interval) day  "
			+ "         WHERE EXTRACT(DOW FROM day::date) NOT IN (0, 6)  "
			+ "           AND day::date NOT IN (SELECT holidaydate FROM holidaymaster)) AS sprintdays,  "
			+ "        s.leaves, s.teamsize, p.projectname,  "
			+ "        (SELECT COUNT(*) FROM holidaymaster WHERE holidaydate BETWEEN s.startdate AND s.enddate) AS holidays,  "
			+ "        CASE  " + "            WHEN current_date::date >= s.enddate THEN s.enddate  "
			+ "            ELSE current_date::date - INTERVAL '1 day'  " + "        END AS asOn  "
			+ "    FROM sprints s  " + "    INNER JOIN projects p ON p.id = s.projectid  "
			+ "    WHERE s.id = #{sprintId}  " + "),  " + "sprint_info_with_daysLeft AS (  "
			+ "    SELECT id, startdate, enddate, sprintdays, leaves, teamsize, projectname, holidays,hoursperday,actualstorypoints,  "
			+ "        CASE  " + "            WHEN current_date::date >= enddate THEN 0  " + "            ELSE   "
			+ "                (SELECT COUNT(*)  "
			+ "                 FROM generate_series(current_date::date, enddate::date, '1 day'::interval) day  "
			+ "                 WHERE EXTRACT(DOW FROM day::date) NOT IN (0, 6)  "
			+ "                   AND day::date NOT IN (SELECT holidaydate FROM holidaymaster))  "
			+ "        END AS daysLeft,  " + "        TO_CHAR(asOn, 'YYYY-MM-DD') AS asOn  " + "    FROM sprint_info  "
			+ "),  " + " sprint_info_with_daysElapsed AS (  "
			+ "    SELECT id, startdate, enddate, sprintdays, leaves, teamsize, projectname, holidays, asOn, daysLeft,hoursperday,actualstorypoints,  "
			+ "        CASE  " + "            WHEN current_date::date > enddate THEN sprintdays  "
			+ "            ELSE sprintdays - daysLeft  " + "        END AS daysElapsed  "
			+ "    FROM sprint_info_with_daysLeft  " + " )  " + " SELECT * FROM sprint_info_with_daysElapsed;  ";

	String getPbisDataBySprintId = "WITH timeline_data AS (  "
			+ "    SELECT t.id as dayId,p.sprintid,p.priority,p.pbiid as givenPbiId,p.id as pbiId,p.pbiname as pbiTitle,  "
			+ "     pt.pbitype,p.storypoints,COALESCE(ptwf.actualstorypoints, p.storypoints) as actualstorypoints, p.expectedhours,p.actualhours,ts.id as status,ts.status as statusName,  "
			+ "     t.resourceid,e.employeename as resourceName,t.date,t.hours,COALESCE(ptwf.remarks, '') as remarks  "
			+ "    FROM   " + "        pbi p  "
			+ "        LEFT JOIN timeline t ON p.id = t.pbiid AND (t.isactive = true OR t.isactive IS NULL)  "
			+ "        LEFT JOIN pbi_type pt ON pt.id = p.pbitypeid OR p.pbitypeid IS NULL  "
			+ "        LEFT JOIN timelinestatus ts ON p.timelinestatusid = ts.id OR p.timelinestatusid IS NULL  "
			+ "        LEFT JOIN employee e ON e.id = t.resourceid OR t.resourceid IS NULL  "
			+ " LEFT JOIN project_tracker_workitem_fields ptwf ON ptwf.sprintid = p.sprintid AND ptwf.pbiid = p.id"
			+ "    WHERE   " + "        p.sprintid = #{sprintId} "
			+ "        AND (t.isactive = true OR t.isactive IS NULL)  " + "        AND p.isactive = true  "
			+ "        AND (t.hours ~ '^[0-9]+(\\.[0-9]+)?$' OR t.hours is null)  " + " ),  " + " pbi_dates AS ( "
			+ "    SELECT  " + "        pbiId, " + "        MIN(CASE  " + "            WHEN t.hours IS NULL THEN NULL "
			+ "            WHEN t.hours:: numeric = 0 THEN NULL " + "            WHEN t.hours::numeric > 0 THEN date "
			+ "            ELSE NULL " + "        END) AS actualStartDate, " + "        MAX(CASE  "
			+ "            WHEN t.hours IS NULL THEN NULL " + "            WHEN t.hours::numeric = 0 THEN NULL "
			+ "            WHEN t.hours::numeric > 0 AND t.statusName IN ('Done', 'Removed') THEN date "
			+ "            ELSE NULL " + "        END) AS actualFinishDate " + "    FROM  " + "        timeline_data t "
			+ "    GROUP BY  " + "        pbiId " + "), " + " pbi_summary AS (  "
			+ "    SELECT t.pbiId,t.pbiTitle,t.priority,t.pbitype,t.storypoints,t.actualstorypoints,t.expectedhours,t.actualhours,  "
			+ "      ts.id as status,ts.status as statusName,pd.actualStartDate,pd.actualFinishDate,t.givenpbiid,COALESCE(t.remarks, '') as remarks, "
			+ " CASE " + "            WHEN t.statusName = 'Done' THEN 100 "
			+ "            WHEN t.expectedhours IS NOT NULL AND t.expectedhours > 0 THEN LEAST(100, (t.actualhours / t.expectedhours) * 100) "
			+ "            ELSE 0 " + "        END AS completionPercentage" + " FROM timeline_data t  "
			+ "   left JOIN pbi_dates pd ON t.pbiId = pd.pbiId  "
			+ "    JOIN timelinestatus ts ON t.status = ts.id OR t.status IS NULL  " + "    GROUP BY  "
			+ "     t.givenpbiid, t.pbiId, t.pbiTitle, t.priority, t.pbitype, t.storypoints, t.actualstorypoints,  t.expectedhours, t.actualhours, ts.id, ts.status, pd.actualStartDate, pd.actualFinishDate ,t.remarks,  completionPercentage  "
			+ ")  " + " SELECT * FROM pbi_summary ORDER BY priority;  ";

	String getEditableFields = "select * from project_tracker_fields where sprintid = #{sprintId}";

	String updateSprintAccomplishments = "INSERT INTO project_tracker_fields "
			+ "(sprintid, accomplishmentsintheweek, planfornextweek, challengesorrisks, defectcount,createdat, updatedat,createdby,updatedby) "
			+ "VALUES (#{sprintId}, #{data.accomplishments}, #{data.plans}, #{data.challenges}, #{data.defectCount},now(),now(),#{data.createdBy},#{data.updatedBy}) "
			+ "ON CONFLICT (sprintid) " + "DO UPDATE SET "
			+ "accomplishmentsintheweek = COALESCE(#{data.accomplishments}, project_tracker_fields.accomplishmentsintheweek), "
			+ "planfornextweek = COALESCE(#{data.plans}, project_tracker_fields.planfornextweek), "
			+ "challengesorrisks = COALESCE(#{data.challenges}, project_tracker_fields.challengesorrisks), "
			+ "defectcount = COALESCE(#{data.defectCount}, project_tracker_fields.defectcount),"
			+ "updatedby=#{data.updatedBy}," + "updatedat = now();";

	String updateSprintWorkItems = "<script>" + "<foreach collection='pbiData' item='item'>"
			+ "INSERT INTO project_tracker_workitem_fields (sprintid,pbiid,actualstorypoints,remarks,createdat,updatedat,createdby,updatedby) VALUES"
			+ "(#{sprintId},#{item.pbiId},#{item.actualStoryPoints},#{item.remarks},now(),now(),#{createdBy},#{updatedBy})"
			+ "ON CONFLICT (sprintid,pbiid) DO UPDATE SET "
			+ "actualstorypoints = COALESCE(#{item.actualStoryPoints},project_tracker_workitem_fields.actualstorypoints), "
			+ "remarks = COALESCE(#{item.remarks},project_tracker_workitem_fields.remarks), "
			+ "updatedby = #{updatedBy}, " + "updatedat = now();" + "</foreach>" + "</script>";

	String getProjectTrackerColorCodes = "SELECT * FROM project_tracker_color_codes";

	String getHolidayDates = "SELECT holidaydate,holidayname from holidaymaster"
			+ " WHERE holidaydate BETWEEN #{startDate} AND #{endDate}";

	String getBurnedHours = "    SELECT p.id as pbiId, " + "           SUM(CASE  "
			+ "                   WHEN t.hours ~ '^\\d+$' THEN t.hours::integer  " + "                   ELSE 0  "
			+ "               END) AS actualHours " + "    FROM pbi p " + "    LEFT JOIN timeline t ON t.pbiid = p.id "
			+ "    WHERE p.sprintid = #{sprintId} " + "      AND p.isactive = true " + "      AND t.isactive = true "
			+ "      AND t.date <= current_date - 1 " + "    GROUP BY p.id ";

	String getResourcesDataBySprintId = "WITH relevant_timesheets AS ("
			+ "SELECT t.id AS timesheetid, p.id as projectid, t.selecteddate, p.clientid " + "FROM public.timesheets t "
			+ "JOIN public.projects p ON t.clientid = p.clientid " + "JOIN public.sprints s ON p.id = s.projectid "
			+ "WHERE s.id = #{sprintId} " + "AND t.selecteddate::DATE >= DATE_TRUNC('month', s.startdate::DATE) "
			+ "AND t.selecteddate::DATE <= s.enddate::DATE " + "AND s.isactive = true " + "AND t.isactive = true), " +

			"leave_days AS (" + "SELECT trdm.projectresourceid, rt.timesheetid, rt.projectid, "
			+ "t.selecteddate::DATE + (seq - 1) * INTERVAL '1 day' AS leavedate "
			+ "FROM public.timesheets_resource_day_mapping trdm "
			+ "JOIN relevant_timesheets rt ON trdm.timesheetid = rt.timesheetid "
			+ "JOIN generate_series(1, 31) AS seq ON TRUE " + "JOIN timesheets t ON trdm.timesheetid = t.id "
			+ "WHERE (seq = 1 AND trdm.day1 = 'L') OR " + "(seq = 2 AND trdm.day2 = 'L') OR "
			+ "(seq = 3 AND trdm.day3 = 'L') OR " + "(seq = 4 AND trdm.day4 = 'L') OR "
			+ "(seq = 5 AND trdm.day5 = 'L') OR " + "(seq = 6 AND trdm.day6 = 'L') OR "
			+ "(seq = 7 AND trdm.day7 = 'L') OR " + "(seq = 8 AND trdm.day8 = 'L') OR "
			+ "(seq = 9 AND trdm.day9 = 'L') OR " + "(seq = 10 AND trdm.day10 = 'L') OR "
			+ "(seq = 11 AND trdm.day11 = 'L') OR " + "(seq = 12 AND trdm.day12 = 'L') OR "
			+ "(seq = 13 AND trdm.day13 = 'L') OR " + "(seq = 14 AND trdm.day14 = 'L') OR "
			+ "(seq = 15 AND trdm.day15 = 'L') OR " + "(seq = 16 AND trdm.day16 = 'L') OR "
			+ "(seq = 17 AND trdm.day17 = 'L') OR " + "(seq = 18 AND trdm.day18 = 'L') OR "
			+ "(seq = 19 AND trdm.day19 = 'L') OR " + "(seq = 20 AND trdm.day20 = 'L') OR "
			+ "(seq = 21 AND trdm.day21 = 'L') OR " + "(seq = 22 AND trdm.day22 = 'L') OR "
			+ "(seq = 23 AND trdm.day23 = 'L') OR " + "(seq = 24 AND trdm.day24 = 'L') OR "
			+ "(seq = 25 AND trdm.day25 = 'L') OR " + "(seq = 26 AND trdm.day26 = 'L') OR "
			+ "(seq = 27 AND trdm.day27 = 'L') OR " + "(seq = 28 AND trdm.day28 = 'L') OR "
			+ "(seq = 29 AND trdm.day29 = 'L') OR " + "(seq = 30 AND trdm.day30 = 'L') OR "
			+ "(seq = 31 AND trdm.day31 = 'L')), " +

			"aggregated_leaves AS (" + "SELECT projectresourceid, "
			+ "ARRAY_AGG(leavedate::DATE ORDER BY leavedate::DATE) AS leavedates " + "FROM leave_days "
			+ "WHERE leavedate IS NOT NULL " + "GROUP BY projectresourceid), " +

			"filtered_leaves AS (" + "SELECT al.projectresourceid, "
			+ "ARRAY_AGG(ld::DATE ORDER BY ld::DATE) AS filtered_leavedates "
			+ "FROM aggregated_leaves al, UNNEST(al.leavedates) ld "
			+ "WHERE ld BETWEEN (SELECT startdate FROM sprints WHERE id = #{sprintId}) "
			+ "AND (SELECT enddate FROM sprints WHERE id = #{sprintId}) " + "GROUP BY al.projectresourceid) " +

			"SELECT e.employeeName, pr.id, e.id AS employeeid, r.rolename, "
			+ "pr.billableallocation, pr.startdate, pr.enddate, "
			+ "al.filtered_leavedates as resourceleaves, p.id as projectid " + "FROM project_resources pr "
			+ "INNER JOIN employee e ON pr.employeeid = e.id " + "INNER JOIN roles r ON r.id = pr.roleid "
			+ "INNER JOIN projects p ON pr.projectid = p.id " + "INNER JOIN sprints s ON s.projectid = p.id "
			+ "LEFT JOIN filtered_leaves al ON pr.id = al.projectresourceid WHERE s.id = #{sprintId} "
			+ "  AND pr.startdate <= s.enddate " + "  AND (pr.enddate IS NULL OR pr.enddate >= s.startdate) "
			+ "ORDER BY r.priorityorder ASC, e.employeeName;";

	String GET_SPRINT_DEFECT_COUNT = "select count(*) from defects where sprintid = #{sprintId}";

	@Select(getSprintDataById)
	SprintDetails getSprintDataById(int sprintId);

	@Select(getResourcesDataBySprintId)
	List<ProjectResource> getResourcesDataBySprintId(int sprintId);

	@Select(getPbisDataBySprintId)
	public List<ProjectTrackerPbi> getPbisDataBySprintId(int sprintId);

	@Select(getEditableFields)
	EditableFields getEditableFields(int sprintId);

	@Update(updateSprintAccomplishments)
	int updateSprintAccomplishments(AccomplishmentData data, int sprintId);

	@Update(updateSprintWorkItems)
	int updateSprintWorkItems(int sprintId, int createdBy, int updatedBy, List<ProjectTrackerPBIFields> pbiData);

	@Select(getProjectTrackerColorCodes)
	List<ProjectTrackerColorCode> getProjectTrackerColorCodes();

	@Select(getHolidayDates)
	List<HolidaysData> getHolidayDates(Date startDate, Date endDate);

	@Select(getBurnedHours)
	List<PbiTimeline> getBurnedHours(int sprintId);

	@Select(GET_SPRINT_DEFECT_COUNT)
	int getSprintDefectCount(int sprintId);

}
