package com.cognine.model;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

public class SprintDetails {
	private int id;
	private String sprintName;
	private String description;
	private int projectId;
	private String projectName;
	private int clientId;
	private String clientName;
	private Date startDate;
	private Date endDate;

	@JsonInclude(JsonInclude.Include.ALWAYS)
	private float leaves;

	@JsonInclude(JsonInclude.Include.ALWAYS)
	private int holidays;
	private int sprintDays;
	private float teamSize;
	private Date createdAt;
	private Date updatedAt;
	@JsonInclude(JsonInclude.Include.ALWAYS)
	private boolean active;
	private String createdBy;
	private String updatedBy;

	@JsonInclude(JsonInclude.Include.ALWAYS)
	private int onBoardedResourcesCount;

	@JsonInclude(JsonInclude.Include.ALWAYS)
	private int reservedResourcesCount;

	@JsonInclude(JsonInclude.Include.ALWAYS)
	private boolean hasTimelineResources;

	private Date asOn;

	@JsonInclude(JsonInclude.Include.ALWAYS)
	private int daysElapsed;

	@JsonInclude(JsonInclude.Include.ALWAYS)
	private int daysLeft;

	private int hoursPerday;

	@JsonInclude(JsonInclude.Include.ALWAYS)
	private boolean actualStoryPoints;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getHoursPerDay() {
		return hoursPerday;
	}

	public void setHoursPerDay(int hoursPerDay) {
		this.hoursPerday = hoursPerDay;
	}

	public boolean getActualStoryPoints() {
		return actualStoryPoints;
	}

	public void setActualStoryPoints(boolean actualStoryPoints) {
		this.actualStoryPoints = actualStoryPoints;
	}

	public String getSprintName() {
		return sprintName;
	}

	public void setSprintName(String sprintName) {
		this.sprintName = sprintName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public float getLeaves() {
		return leaves;
	}

	public void setLeaves(float leaves) {
		this.leaves = leaves;
	}

	public int getHolidays() {
		return holidays;
	}

	public void setHolidays(int holidays) {
		this.holidays = holidays;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public int getSprintDays() {
		return sprintDays;
	}

	public void setSprintDays(int sprintDays) {
		this.sprintDays = sprintDays;
	}

	public float getTeamSize() {
		return teamSize;
	}

	public void setTeamSize(float teamSize) {
		this.teamSize = teamSize;
	}

	public boolean isActive() {
		return active;
	}

	public boolean isHasTimelineResources() {
		return hasTimelineResources;
	}

	public void setHasTimelineResources(boolean hasTimelineResources) {
		this.hasTimelineResources = hasTimelineResources;
	}

	public int getOnBoardedResourcesCount() {
		return onBoardedResourcesCount;
	}

	public void setOnBoardedResourcesCount(int onBoardedResourcesCount) {
		this.onBoardedResourcesCount = onBoardedResourcesCount;
	}

	public int getReservedResourcesCount() {
		return reservedResourcesCount;
	}

	public void setReservedResourcesCount(int reservedResourcesCount) {
		this.reservedResourcesCount = reservedResourcesCount;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Date getAsOn() {
		return asOn;
	}

	public void setAsOn(Date asOn) {
		this.asOn = asOn;
	}

	public int getDaysElapsed() {
		return daysElapsed;
	}

	public void setDaysElapsed(int daysElapsed) {
		this.daysElapsed = daysElapsed;
	}

	public int getDaysLeft() {
		return daysLeft;
	}

	public void setDaysLeft(int daysLeft) {
		this.daysLeft = daysLeft;
	}

}
