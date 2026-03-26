package com.cognine.model;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class PbiTimeline {
	private int pbiId;
	private String givenPbiId;
	private int sprintId;
	private String pbiType;
	private String description;
	private String storyPoints;
	private String actualStoryPoints;
	private float expectedHours;
	private float actualHours;
	private String status;
	private String statusName;
	private int statusId;
	private Date date;
	private String hours;
	private int dayId;
	private String pbiTitle;
	private int priority;
	private List<PbiResource> resources = new ArrayList<PbiResource>();
	private PbiResource pbiResource = new PbiResource();
	List<AddHoliday> addHolidayList = new ArrayList<AddHoliday>();
	List<DeleteHoliday> deleteHolidayList = new ArrayList<DeleteHoliday>();
	private TimelineNotes timelineNotes;
	private Date actualStartDate;
	private Date actualFinishDate;
	private int completionPercentage;
	private String remarks;

	public String getPbiType() {
		return pbiType;
	}

	public void setPbiType(String pbiType) {
		this.pbiType = pbiType;
	}

	public int getPbiId() {
		return pbiId;
	}

	public String getPbiTitle() {
		return pbiTitle;
	}

	public void setPbiTitle(String pbiTitle) {
		this.pbiTitle = pbiTitle;
	}

	public void setPbiId(int pbiId) {
		this.pbiId = pbiId;
	}

	public String getStoryPoints() {
		return storyPoints;
	}

	public void setStoryPoints(String storyPoints) {
		this.storyPoints = storyPoints;
	}

	public String getActualStoryPoints() {
		return actualStoryPoints;
	}

	public void setActualStoryPoints(String actualStoryPoints) {
		this.actualStoryPoints = actualStoryPoints;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public float getExpectedHours() {
		return expectedHours;
	}

	public float getActualHours() {
		return actualHours;
	}

	public void setActualHours(float actualHours) {
		this.actualHours = actualHours;
	}

	public void setExpectedHours(float expectedHours) {
		this.expectedHours = expectedHours;
	}

	public PbiResource getPbiResource() {
		return pbiResource;
	}

	public void setPbiResource(PbiResource pbiResource) {
		this.pbiResource = pbiResource;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<PbiResource> getResources() {
		return resources;
	}

	public void setResources(List<PbiResource> resources) {
		this.resources = resources;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getHours() {
		return hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}

	public int getSprintId() {
		return sprintId;
	}

	public void setSprintId(int sprintId) {
		this.sprintId = sprintId;
	}

	public List<AddHoliday> getAddHolidayList() {
		return addHolidayList;
	}

	public void setAddHolidayList(List<AddHoliday> addHolidayList) {
		this.addHolidayList = addHolidayList;
	}

	public List<DeleteHoliday> getDeleteHolidayList() {
		return deleteHolidayList;
	}

	public void setDeleteHolidayList(List<DeleteHoliday> deleteHolidayList) {
		this.deleteHolidayList = deleteHolidayList;
	}

	public int getDayId() {
		return dayId;
	}

	public void setDayId(int dayId) {
		this.dayId = dayId;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public TimelineNotes getTimelineNotes() {
		return timelineNotes;
	}

	public void setTimelineNotes(TimelineNotes timelineNotes) {
		this.timelineNotes = timelineNotes;
	}

	public String getGivenPbiId() {
		return givenPbiId;
	}

	public void setGivenPbiId(String givenPbiId) {
		this.givenPbiId = givenPbiId;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public Date getActualStartDate() {
		return actualStartDate;
	}

	public void setActualStartDate(Date actualStartDate) {
		this.actualStartDate = actualStartDate;
	}

	public Date getActualFinishDate() {
		return actualFinishDate;
	}

	public void setActualFinishDate(Date actualFinishDate) {
		this.actualFinishDate = actualFinishDate;
	}

	public int getCompletionPercentage() {
		return completionPercentage;
	}

	public void setCompletionPercentage(int completionPercentage) {
		this.completionPercentage = completionPercentage;
	}

}
