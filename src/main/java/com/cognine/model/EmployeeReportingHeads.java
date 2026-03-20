package com.cognine.model;

import java.sql.Date;

public class EmployeeReportingHeads {
    private int id;
    private int employeeId;
    private Date startDate;
    private Date endDate;
    private int reportingHeadId;
    private String reportingHeadName;
    private Date createdAt;
    private Date updatedAt;
    
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	
	public int getReportingHeadId() {
		return reportingHeadId;
	}
	public void setReportingHeadId(int reportingHeadId) {
		this.reportingHeadId = reportingHeadId;
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
	public String getReportingHeadName() {
		return reportingHeadName;
	}
	public void setReportingHeadName(String reportingHeadName) {
		this.reportingHeadName = reportingHeadName;
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