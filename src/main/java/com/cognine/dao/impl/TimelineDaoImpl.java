package com.cognine.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cognine.dao.TimelineDao;
import com.cognine.mapper.TimelineMapper;
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

@Repository
public class TimelineDaoImpl implements TimelineDao {
	@Autowired
	private TimelineMapper timelineMapper;

	@Override
	public List<TimelineStatus> getTimelineStatus() {
		return timelineMapper.getTimelineStatus();
	}

	@Override
	public List<Indicators> getIndicators() {
		return timelineMapper.getIndicators();
	}

//	@Override
//	public int addHoliday(InsertHoliday insertHoliday) {
//		int returnValue = 0;
//		try {
//			returnValue = timelineMapper.addHoliday(insertHoliday);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return returnValue;
//	}

	@Override
	public int UpdatePbi(PbiTimeline pbiTimeline) {
		int returnValue = 0;
		try {
			returnValue = timelineMapper.UpdatePbi(pbiTimeline);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnValue;
	}

	@Override
	public int addPbiTimeline(int pbiId, int resourceId, PbiDays day) {
		int returnValue = 0;
		try {
			returnValue = timelineMapper.addPbiTimeline(pbiId, resourceId, day);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnValue;
	}

	@Override
	public List<PbiTimeline> getAllPbiTimelineData(int sprintId) {
		return timelineMapper.getAllPbiTimelineData(sprintId);
	}

	@Override
	public int addHoliday(Date holidayDate, int sprintId) {
		int returnValue = 0;
		try {
			returnValue = timelineMapper.addHoliday(holidayDate,sprintId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnValue;
	}

	@Override
	public int deleteHoliday(Date holidayDate, boolean flag,int sprintId) {
		return timelineMapper.deleteHoliday(holidayDate,flag,sprintId);
	}

	@Override
	public List<HolidaysData> getHolidaysList(int sprintId) {
		return timelineMapper.getHolidaysList(sprintId);
	}

	@Override
	public String getInProgressColor(String statusValue) {
		return timelineMapper.getInProgressColor(statusValue);
	}

	@Override
	public String getCurrentDateColor(String pressentDay) {
		return timelineMapper.getCurrentDateColor(pressentDay);

	}

	@Override
	public String getLeaveColor(String leave) {
		return timelineMapper.getLeaveColor(leave);
	}

	@Override
	public String getHeaderColor(String headerIndicator) {
		return timelineMapper.getHeaderColor(headerIndicator);
	}

	@Override
	public int updatePbiTimeline(int pbiId, int resourceId, PbiDays day) {
		int returnValue = 0;
		try {
			returnValue = timelineMapper.updatePbiTimeline(pbiId, resourceId, day);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnValue;

	}

	@Override
	public List<HolidaysData> getHolidaysList() {
		return timelineMapper.getHolidayasList();
	}

	@Override
	public List<ProjectResource> getBillableResourcesByProjectId(int projectId) {
		return timelineMapper.getBillableResourcesByProjectId(projectId);
	}

	@Override
	public List<PbiDays> getDateWiseResourceTotal(int sprintId) {
		return timelineMapper.getDateWiseResourceTotal(sprintId);
	}

	@Override
	public List<ResourceHours> getResourceSummaryData(int sprintId, int projectId) {
		return timelineMapper.getProjectResourcesForSummary(sprintId,projectId);
	}

	@Override
	public int deleteResourceHours(RemoveResource deleteResource) {
		return timelineMapper.deleteResourceHours(deleteResource);
	}

	@Override
	public TimelineNotes getTimelineNotes(int pbiId) {
		return timelineMapper.getTimelineNotes(pbiId);
	}

	@Override
	public int saveTimelineNotes(TimelineNotes timelineNotes) {
		return timelineMapper.saveTimelineNotes(timelineNotes);
	}

	@Override
	public TimelineExcelData getLastInsertedTimelineDataByProjectId(int projectId) {
		return timelineMapper.getLastInsertedTimelineDataByProjectId(projectId);
	}

	@Override
	public List<Integer> addOrRemoveTimelineHoursWhensprintChange(SprintsConfiguration SprintsConfiguration) {
		return timelineMapper.addOrRemoveTimelineHoursWhensprintChange(SprintsConfiguration);
	}

	@Override
	public int isResourceWithPBIExistInTimeline(int pbiId, int resourceId) {
		return timelineMapper.isResourceWithPBIExistInTimeline(pbiId, resourceId);
	}

	@Override
	public int updateTimelineNotes(TimelineNotes timelineNotes) {
	   return timelineMapper.updateTimelineNotes(timelineNotes);
	}

	@Override
	public int isPbiHasTimelineNotes(TimelineNotes timelineNotes) {
		return timelineMapper.isPbiHasTimelineNotes(timelineNotes);
	}

	//new
	@Override
public List<ProjectResourceAllocation> getResourceAllocations(int resourceId, int sprintId) {
    return timelineMapper.getResourceAllocations(resourceId, sprintId);
}

	
}
