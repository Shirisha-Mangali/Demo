package com.cognine.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Many;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.cognine.model.HolidaysData;
import com.cognine.model.Indicators;
import com.cognine.model.PbiDays;
import com.cognine.model.PbiResource;
import com.cognine.model.PbiTimeline;
import com.cognine.model.ProjectResource;
import com.cognine.model.RemoveResource;
import com.cognine.model.ResourceHours;
import com.cognine.model.SprintsConfiguration;
import com.cognine.model.TimelineExcelData;
import com.cognine.model.TimelineNotes;
import com.cognine.model.TimelineStatus;

@Mapper
public interface TimelineMapper {

	public String getTimelineStatus = "select * from timelinestatus";
	public String getIndicators = "select * from timelineindicators ORDER BY ID";
	public String addHoliday = "insert into holidays (sprintid,holidaydate) values(#{sprintId},#{holidayDate})";
	// public String addPbiTimeline = "insert into timeline(pbiid,resourceid,date,hours) values(#{pbiId},#{resourceId},#{day.date},#{day.hours})";
	public String addPbiTimeline = "INSERT INTO timeline (pbiid, resourceid, date, hours) " +
                               "SELECT #{pbiId}, #{resourceId}, #{day.date}, #{day.hours} " +
                               "WHERE NOT EXISTS (SELECT 1 FROM timeline " +
                               "WHERE pbiid = #{pbiId} AND resourceid = #{resourceId} AND date = #{day.date} and isactive = true)";
	public String UpdatePbi = "update pbi set storypoints=#{storyPoints},expectedhours=#{expectedHours},actualhours=#{actualHours},timelinestatusid=#{status}::int where id=#{pbiId} and sprintid=#{sprintId}";
	public String getAllPbiTimelineData = "select t.id as dayId, p.sprintid, p.priority, p.pbiid as givenPbiId, p.id as pbiId,p.pbiname as pbiTitle,pt.pbitype, "
			+ "p.storypoints,p.expectedhours,p.actualhours,ts.id as status, ts.status as statusName ,t.resourceid,e.employeename as resourceName,t.date,t.hours "
			+ "from pbi p left join timeline t on p.id = t.pbiid and (t.isactive = true OR t.isactive IS NULL)  "
			+ " left join pbi_type pt on pt.id = p.pbitypeid  or p.pbitypeid = null "
			+ " left join timelinestatus ts on p.timelinestatusid=ts.id  or p.timelinestatusid = null "
			+ " LEFT join employee e on e.id = t.resourceid or t.resourceid = null "
			+ " where p.sprintid=#{sprintId} and (t.isactive = true or t.isactive is null) "

			+ "and (p.isactive = true) " + " order by p.priority ";

	// public String findResourceById = "SELECT e.id AS resourceId, "
			
	// 		// for replacing the resource name with Proxy resource name			
	// 		+ " CASE "
	// 		+ "            WHEN EXISTS ( "
	// 		+ "                SELECT pr.projectemployeename "
	// 		+ "                FROM proxy_resources pr  "
	// 		+ "                INNER JOIN projects p ON p.id = pr.projectid  "
	// 		+ "                INNER JOIN sprints s ON s.projectid = p.id   "
	// 		+ "                WHERE pr.employeeid = #{resourceid} AND s.id = #{sprintid} AND pr.isactive = true "
	// 		+ "                LIMIT 1 "
	// 		+ "            ) THEN "
	// 		+ "                ( "
	// 		+ "                    SELECT pr.projectemployeename "
	// 		+ "                    FROM proxy_resources pr  "
	// 		+ "                    INNER JOIN projects p ON p.id = pr.projectid  "
	// 		+ "                    INNER JOIN sprints s ON s.projectid = p.id   "
	// 		+ "                    WHERE pr.employeeid = #{resourceid} AND s.id = #{sprintid} AND pr.isactive = true "
	// 		+ "                    LIMIT 1 "
	// 		+ "                ) "
	// 		+ "            ELSE employeename "
	// 		+ "        END "
	// 		+ "   AS resourceName,  "
	// 		+ " ((pr.enddate is null and pr.startdate <= current_date) or "
	// 		+ " current_date between pr.startdate and pr.enddate) AS isBillable, r.rolename as resourceRole "
	// 		+ " FROM project_resources pr JOIN employee e ON e.id = pr.employeeid JOIN projects p ON p.id = pr.projectid "
	// 		+ "  JOIN sprints s ON s.projectid = p.id inner join roles r on r.id = pr.roleid "
	// 		+ " WHERE pr.employeeid = #{resourceid} AND s.id = #{sprintid} AND pr.billableallocation > 0 and pr.active = true "
	// 		+ " ORDER BY pr.createdat DESC LIMIT 1 ";

	public String findResourceById = "SELECT e.id AS resourceId, "
        + " CASE "
        + "            WHEN EXISTS ( "
        + "                SELECT pr.projectemployeename "
        + "                FROM proxy_resources pr  "
        + "                INNER JOIN projects p ON p.id = pr.projectid  "
        + "                INNER JOIN sprints s ON s.projectid = p.id   "
        + "                WHERE pr.employeeid = #{resourceid} AND s.id = #{sprintid} AND pr.isactive = true "
        + "                LIMIT 1 "
        + "            ) THEN "
        + "                ( "
        + "                    SELECT pr.projectemployeename "
        + "                    FROM proxy_resources pr  "
        + "                    INNER JOIN projects p ON p.id = pr.projectid  "
        + "                    INNER JOIN sprints s ON s.projectid = p.id   "
        + "                    WHERE pr.employeeid = #{resourceid} AND s.id = #{sprintid} AND pr.isactive = true "
        + "                    LIMIT 1 "
        + "                ) "
        + "            ELSE employeename "
        + "        END "
        + "   AS resourceName,  "
        + " pr.startdate AS startDate, "  // ← ADD
        + " pr.enddate AS endDate, "      // ← ADD
        + " ((pr.enddate is null and pr.startdate <= current_date) or "
        + " current_date between pr.startdate and pr.enddate) AS isBillable, r.rolename as resourceRole "
        + " FROM project_resources pr JOIN employee e ON e.id = pr.employeeid JOIN projects p ON p.id = pr.projectid "
        + "  JOIN sprints s ON s.projectid = p.id inner join roles r on r.id = pr.roleid "
        + " WHERE pr.employeeid = #{resourceid} AND s.id = #{sprintid} AND pr.billableallocation > 0 and pr.active = true "
        + " ORDER BY pr.createdat DESC LIMIT 1 ";

	public String findResourceDatesId = "select date from timeline where resourceid = #{resourceid} and t.isactive = true";

	public String getInProgressColor = "select color from timelinestatus where LOWER(status)=LOWER(#{statusValue})";
	public String getCurrentDateColor = "select color from timelineindicators where LOWER(indicator)=LOWER(#{pressentDay}) ";
	public String getLeaveColor = "select color from timelineindicators where LOWER(indicator)=LOWER(#{leave}) ";

	public String deleteHoliday = "update holidays set isactive=#{flag} where holidaydate=#{holidayDate} and sprintid=#{sprintId} ";
	public String getHolidaysList = "select holidaydate from holidays where sprintid=#{sprintId} and isactive=true ";
	public String getHeaderColor = "select color from timelineindicators where LOWER(indicator)=LOWER(#{headerIndicator})";
	public String updatePbiTimeline = "update timeline set date = #{day.date}, hours = #{day.hours} where pbiid = #{pbiId} and id= #{day.id} and resourceid = #{resourceId} ";
	public String getHolidayasList = "select * from holidaymaster";
	public String getBillableResourcesByProjectId = "SELECT * FROM project_resources pr inner join employee e on e.id=pr.employeeid "
			+ " inner join roles r on r.id = pr.roleid "
			+ "WHERE pr.projectid = #{projectId} and ((pr.enddate is null and pr.startdate <= current_date) or "
			+ "current_date between pr.startdate and pr.enddate) and pr.billableallocation > 0 and pr.active = true";
			

	public String getDateWiseResourceTotal = "SELECT t.date, SUM(CASE WHEN t.hours = 'L' OR t.hours = ''  THEN 0 ELSE CAST(t.hours AS NUMERIC) END) as hours "
			+ "FROM timeline t INNER JOIN pbi p ON t.pbiid = p.id "
			+ "WHERE p.sprintid = #{sprintId} and t.isactive = true  GROUP BY t.date order by t.date ";

	public String getProjectResourcesForSummary = " WITH projectResourcesWithProxy AS ( WITH RankedRecords AS (  "
			+ "    SELECT   "
			+ "        e.employeename,   "
			+ "	     pb.sprintid,  "
			+ "	     pr.employeeid,  "
			+ "        r.rolename,   "
			+ "        pr.startdate,  "
			+ "        ROW_NUMBER() OVER (PARTITION BY pr.employeeid ORDER BY pr.startdate DESC) AS RowNum  "
			+ "    FROM   "
			+ "        project_resources pr  "
			+ "        INNER JOIN projects p ON pr.projectid = p.id   "
			+ "        INNER JOIN employee e ON e.id = pr.employeeid   "
			+ "        INNER JOIN roles r ON r.id = pr.roleid    "
			+ "        INNER JOIN timeline t ON pr.employeeId = t.resourceid   "
			+ "        INNER JOIN pbi pb ON pb.id = t.pbiid  "
			+ "    WHERE   "
			+ "        pb.sprintid = #{sprintId}    "
			+ "        AND p.id = #{projectId}   "
			+ "        AND t.isactive = true and pb.isactive = true  "
			+ ")  "
			+ "SELECT   "
			+ "    employeename,   "
			+ "    rolename,   "
			+ "    startdate,  "
			+ "	sprintid,  "
			+ "	employeeid  "
			+ "FROM   "
			+ "    RankedRecords  "
			+ "WHERE   "
			+ "    RowNum = 1 ) "
			+ "select "
			
// for replacing the resource name with Proxy resource name			
+ " CASE "
+ "            WHEN EXISTS ( "
+ "                SELECT pr.projectemployeename "
+ "                FROM proxy_resources pr  "
+ "                INNER JOIN projects p ON p.id = pr.projectid  "
+ "                INNER JOIN sprints s ON s.projectid = p.id   "
+ "                WHERE pr.employeeid = prw.employeeid AND s.id = #{sprintId} AND pr.isactive = true "
+ "                LIMIT 1 "
+ "            ) THEN "
+ "                ( "
+ "                    SELECT pr.projectemployeename "
+ "                    FROM proxy_resources pr  "
+ "                    INNER JOIN projects p ON p.id = pr.projectid  "
+ "                    INNER JOIN sprints s ON s.projectid = p.id   "
+ "                    WHERE pr.employeeid = prw.employeeid AND s.id = #{sprintId} AND pr.isactive = true "
+ "                    LIMIT 1 "
+ "                ) "
+ "            ELSE prw.employeename "
+ "        END "
+ "   AS employeename, prw.employeeid, prw.sprintid, prw.rolename from projectResourcesWithProxy as prw  ";

	public String getResourceSummaryData = "WITH DateSumCTE AS" + " ( SELECT t.date, e.employeename, p.id, "
			+ "    CASE WHEN t.hours = 'L' THEN 'L' ELSE t.hours END as hours FROM timeline t "
			+ "    INNER JOIN project_resources pr ON pr.employeeid = t.resourceid "
			+ "    INNER JOIN employee e ON e.id = pr.employeeid   " + "	   INNER JOIN pbi p ON p.id = t.pbiid "
			+ "    WHERE p.sprintid = #{sprintId} and pr.employeeid = #{employeeId} and t.isactive = true "
			+ "    GROUP BY t.date, e.employeename, t.hours, p.id  ) " + " SELECT date, employeename, "
			+ " CASE WHEN MAX(hours) = 'L' THEN 'L'" + " ELSE CAST(SUM(CASE WHEN hours = 'L' OR hours = '' THEN 0"
			+ " ELSE CAST(hours AS NUMERIC) END) AS VARCHAR(255)) END as hours "
			+ " FROM DateSumCTE  GROUP BY date, employeename order by date ";

	public String deleteResourceHours = "update timeline set isactive = false where pbiid=#{pbiId} and resourceid=#{resourceId}";

	public String getTimelineNotes = " select * from  timelineNotes where pbiid = #{pbiid} and isactive = true ";

	public String saveTimelineNotes = " INSERT INTO timelinenotes (pbiid, sprintid, notes, createdat, updatedat) VALUES (#{pbiId}, #{sprintId}, #{notes}, now(), now()) ";
			
    public String updateTimelineNotes =  " UPDATE timelinenotes SET notes = #{notes}, updatedat = now() WHERE pbiid = #{pbiId} and isactive = true ";

    public String isPbiHasTimelineNotes  = " select count(id) from timelinenotes where pbiid = #{pbiId} and isactive = true ";
    
	public String getLastInsertedTimelineDataByProjectId = "select t.timelineattachment, t.id, st.sprintid, t.sprintpresentdayid as sprintAttachmentMappingId, "
			+ " st.istimelinefinalized, st.timelineDate  from sprinttimelinemapping st inner join timelineattachment t  "
			+ " on t.sprintpresentdayid = st.id inner join sprints s on s.id = st.sprintid where s.projectid = #{projectId} "
			+ " Order By st.timelineDate desc limit 1 ";

	public String addOrRemoveTimelineHoursWhensprintChange = " <script> <if test= 'endDate > previousEndDate'> "

			+ " INSERT INTO timeline (pbiid, date, isactive, hours, resourceid) " + " WITH ResourceIds AS ("
			+ "    SELECT p.id as pbid,t.resourceid FROM pbi p INNER JOIN timeline t ON p.id = t.pbiid AND t.isactive = true "
			+ "    WHERE p.sprintid = #{id} AND t.date = #{previousEndDate} ::date AND p.isactive = true) "
			+ " SELECT  pbid, date_range.date,true as isactive,'0' as hours, "
			+ "	ri.resourceid as resourceid FROM ResourceIds ri CROSS JOIN generate_series(#{previousEndDate}::date, #{endDate}::date, interval '1 day') "
			+ "	date_range(date) where date_range.date > #{previousEndDate}::date and 6 > EXTRACT(ISODOW FROM date_range.date)   order by date, ri.pbid returning id "
			+ " </if> "
			+ "<if test= 'previousEndDate > endDate'>  "
			+ "UPDATE timeline t SET isactive = false FROM pbi p WHERE p.id = t.pbiid AND p.sprintid = #{id} "
			+ "AND t.date > #{endDate}::date AND  #{previousEndDate}::date >= t.date  and t.isactive = true returning t.id  </if>"
			+ "  </script>  ";

	public String isResourceWithPBIExistInTimeline = "select count(id) from timeline where resourceid = #{resourceId} and "
			+ " pbiid = #{pbiId} and isactive = true ";
	
	@Select(addOrRemoveTimelineHoursWhensprintChange)
	public List<Integer> addOrRemoveTimelineHoursWhensprintChange(SprintsConfiguration SprintsConfiguration);

	@Select(getLastInsertedTimelineDataByProjectId)
	public TimelineExcelData getLastInsertedTimelineDataByProjectId(int projectId);

	@Insert(saveTimelineNotes)
	public int saveTimelineNotes(TimelineNotes timelineNotes);

	@Update(updateTimelineNotes)
	public int updateTimelineNotes(TimelineNotes timelineNotes);
	
	@Select(isPbiHasTimelineNotes)
	public int isPbiHasTimelineNotes(TimelineNotes timelineNotes);
	
	@Select(getTimelineNotes)
	public TimelineNotes getTimelineNotes(int pbiid);

	@Update(deleteResourceHours)
	public int deleteResourceHours(RemoveResource deleteResource);

	@Select(getHolidayasList)
	public List<HolidaysData> getHolidayasList();

	@Select(getTimelineStatus)
	public List<TimelineStatus> getTimelineStatus();

	@Select(getIndicators)
	public List<Indicators> getIndicators();

	@Update(UpdatePbi)
	public int UpdatePbi(PbiTimeline pbiTimeline);

	@Insert(addPbiTimeline)
	public int addPbiTimeline(int pbiId, int resourceId, PbiDays day);

	@Update(updatePbiTimeline)
	public int updatePbiTimeline(int pbiId, int resourceId, PbiDays day);

	@Select(getAllPbiTimelineData)
	@Results({
			@Result(property = "pbiResource", column = "{resourceid=resourceid, sprintid=sprintid}", one = @One(select = "findResourceById")), })

	public List<PbiTimeline> getAllPbiTimelineData(int sprintId);

	@Select(findResourceById)
	PbiResource findResourceById(int resourceid, int sprintid);

	@Select(findResourceDatesId)
	List<PbiDays> findResourceDatesId(int resourceid);

	@Select(getInProgressColor)
	public String getInProgressColor(String statusValue);

	@Select(getCurrentDateColor)
	public String getCurrentDateColor(String pressentDay);

	@Select(getLeaveColor)
	public String getLeaveColor(String leave);

	@Insert(addHoliday)
	public int addHoliday(Date holidayDate, int sprintId);

	@Update(deleteHoliday)
	public int deleteHoliday(Date holidayDate, boolean flag, int sprintId);

	@Select(getHolidaysList)
	public List<HolidaysData> getHolidaysList(int sprintId);

	@Select(getHeaderColor)
	public String getHeaderColor(String headerIndicator);

	@Select(getBillableResourcesByProjectId)
	public List<ProjectResource> getBillableResourcesByProjectId(int projectId);

	@Select(getDateWiseResourceTotal)
	public List<PbiDays> getDateWiseResourceTotal(int sprintId);

	@Select(getProjectResourcesForSummary)
	@Results({
			@Result(property = "totalHoursList", column = "{employeeId=employeeId, sprintId=sprintId}", javaType = List.class, many = @Many(select = "getResourceSummaryData")) })
	public List<ResourceHours> getProjectResourcesForSummary(@Param("sprintId") int sprintId, @Param("projectId") int projectId);

	@Select(getResourceSummaryData)
	public List<PbiDays> getResourceSummaryData(int employeeId, int sprintId);
	
	@Select(isResourceWithPBIExistInTimeline)
	public int isResourceWithPBIExistInTimeline(int pbiId, int resourceId);


}
