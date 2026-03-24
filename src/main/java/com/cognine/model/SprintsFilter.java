package com.cognine.model;

public class SprintsFilter {
	
	private int projectId;
	private String sprintName;
	private String sprintStartDate;
	private String sprintEndDate;
	private int pageNum;
	private int pageSize;
	
	
	
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	public String getSprintName() {
		return sprintName;
	}
	public void setSprintName(String sprintName) {
		this.sprintName = sprintName;
	}
	public String getSprintStartDate() {
		return sprintStartDate;
	}
	public void setSprintStartDate(String sprintStartDate) {
		this.sprintStartDate = sprintStartDate;
	}
	public String getSprintEndDate() {
		return sprintEndDate;
	}
	public void setSprintEndDate(String sprintEndDate) {
		this.sprintEndDate = sprintEndDate;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	

}
