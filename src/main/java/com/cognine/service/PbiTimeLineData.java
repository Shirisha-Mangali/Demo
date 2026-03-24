package com.cognine.model;

import java.sql.Date;
import java.util.List;

import com.cognine.utils.Status;
import com.fasterxml.jackson.annotation.JsonInclude;

public class PbiTimeLineData {
	private int sprintId;
	private int projectId;
	private List<PbiTimeline> pbiTimeLine;
	private List<HolidaysData> holidaysData;
	private Status status;
	private List<PbiDays> dateWiseResourceTotalList;
    private String timelineGenerateDate; 
    private boolean isTimelineforYesterDay;
    private Date lastTimelineGeneratedAt;
    
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private boolean isTimelineFinalized;

    

	public int getSprintId() {
		return sprintId;
	}

	public void setSprintId(int sprintId) {
		this.sprintId = sprintId;
	}

	public List<PbiTimeline> getPbiTimeLine() {
		return pbiTimeLine;
	}

	public void setPbiTimeLine(List<PbiTimeline> pbiTimeLine) {
		this.pbiTimeLine = pbiTimeLine;
	}

	public List<HolidaysData> getHolidaysData() {
		return holidaysData;
	}

	public void setHolidaysData(List<HolidaysData> holidaysData) {
		this.holidaysData = holidaysData;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public List<PbiDays> getDateWiseResourceTotalList() {
		return dateWiseResourceTotalList;
	}

	public void setDateWiseResourceTotalList(List<PbiDays> dateWiseResourceTotalList) {
		this.dateWiseResourceTotalList = dateWiseResourceTotalList;
	}

	public String getTimelineGenerateDate() {
		return timelineGenerateDate;
	}

	public void setTimelineGenerateDate(String timelineGenerateDate) {
		this.timelineGenerateDate = timelineGenerateDate;
	}

	public boolean getIsTimelineforYesterDay() {
		return isTimelineforYesterDay;
	}

	public void setIsTimelineforYesterDay(boolean isTimelineforYesterDay) {
		this.isTimelineforYesterDay = isTimelineforYesterDay;
	}

	public boolean getIsTimelineFinalized() {
		return isTimelineFinalized;
	}

	public void setIsTimelineFinalized(boolean isTimelineFinalized) {
		this.isTimelineFinalized = isTimelineFinalized;
	}

	public void setTimelineforYesterDay(boolean isTimelineforYesterDay) {
		this.isTimelineforYesterDay = isTimelineforYesterDay;
	}

	public Date getLastTimelineGeneratedAt() {
		return lastTimelineGeneratedAt;
	}

	public void setLastTimelineGeneratedAt(Date lastTimelineGeneratedAt) {
		this.lastTimelineGeneratedAt = lastTimelineGeneratedAt;
	}

	public void setTimelineFinalized(boolean isTimelineFinalized) {
		this.isTimelineFinalized = isTimelineFinalized;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	
	
	

}
