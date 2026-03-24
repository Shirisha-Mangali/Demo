package com.cognine.model;

import java.sql.Date;

public class AllocatedProjectResource {
	private int id;
	private int projectId;
	private Date generatedDates;
	private int projectAllocation;
	private String projectName;
	private Date startDate;
	private Date endDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public Date getGeneratedDates() {
		return generatedDates;
	}

	public void setGeneratedDates(Date generatedDates) {
		this.generatedDates = generatedDates;
	}

	public int getProjectAllocation() {
		return projectAllocation;
	}

	public void setProjectAllocation(int projectAllocation) {
		this.projectAllocation = projectAllocation;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
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

}
