package com.cognine.model;

import java.sql.Date;

public class ProjectResourceDetails {
	private Date startDate;
	private Date endDate;
	private int projectAllocation;
	private String projectName;
	private int projectId;
	private String clientName;
	private int projectResourceId;

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

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	
	
	public int getProjectResourceId() {
		return projectResourceId;
	}

	public void setProjectResourceId(int projectResourceId) {
		this.projectResourceId = projectResourceId;
	}

	@Override
	public String toString() {
		return "ProjectResourceDetails [startDate=" + startDate + ", endDate=" + endDate + ", projectAllocation="
				+ projectAllocation + ", projectName=" + projectName + ", projectId=" + projectId + ", clientName="
				+ clientName + "]";
	}

	
}
