package com.cognine.dao;

import java.util.Date;
import java.util.List;

import com.cognine.model.HolidaysData;
import com.cognine.model.Indicators;
import com.cognine.model.PbiDays;
import com.cognine.model.PbiTimeline;
import com.cognine.model.ProjectResource;
import com.cognine.model.ProjectResourceAllocation;
import com.cognine.model.RemoveResource;
import com.cognine.model.ResourceHours;
import com.cognine.model.SprintsConfiguration;
import com.cognine.model.TimelineExcelData;
import com.cognine.model.TimelineNotes;
import com.cognine.model.TimelineStatus;

public interface TimelineDao {

	public List<TimelineStatus> getTimelineStatus();

	public List<Indicators> getIndicators();

	public int UpdatePbi(PbiTimeline pbiTimeline);

	public int addPbiTimeline(int pbiId, int resourceId, PbiDays day);
	
	public int updatePbiTimeline(int pbiId, int resourceId, PbiDays day);

	public List<PbiTimeline> getAllPbiTimelineData(int sprintId);

	public String getInProgressColor(String statusValue);

	public String getCurrentDateColor(String pressentDay);

	public String getLeaveColor(String leave);

	public int addHoliday(Date holidayDate, int sprintId);

	public int deleteHoliday(Date holidayDate, boolean flag, int sprintId);

	public List<HolidaysData> getHolidaysList(int sprintId);

	public String getHeaderColor(String headerIndicator);

	public List<HolidaysData> getHolidaysList();

	public List<ProjectResource> getBillableResourcesByProjectId(int projectId);
	
	public List<PbiDays> getDateWiseResourceTotal(int sprintId);
	
	public List<ResourceHours> getResourceSummaryData(int sprintId, int projectId);

	public int deleteResourceHours(RemoveResource deleteResource);
	
	public TimelineNotes getTimelineNotes(int pbiId);
	
	public int saveTimelineNotes(TimelineNotes timelineNotes);

	public TimelineExcelData getLastInsertedTimelineDataByProjectId(int projectId);

	public List<Integer> addOrRemoveTimelineHoursWhensprintChange(SprintsConfiguration SprintsConfiguration);

	public int isResourceWithPBIExistInTimeline(int pbiId, int resourceId);
	
    public int updateTimelineNotes(TimelineNotes timelineNotes);
	
	public int isPbiHasTimelineNotes(TimelineNotes timelineNotes);

	//new
	List<ProjectResourceAllocation> getResourceAllocations(int resourceId,int sprintId);

}
