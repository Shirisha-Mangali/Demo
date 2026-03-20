package com.cognine.model;

import java.sql.Date;

public class Pmos {
	private int id;
	private int employeeId;
	private String employeeName;
	private int projectId;
	private Date createdDate;
	private Date updatedDate;
	private boolean status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	@Override
	public String toString() {
		return "Pmos [id=" + id + ", employeeId=" + employeeId + ", employeeName=" + employeeName + ", projectId="
				+ projectId + ", createdDate=" + createdDate + ", updatedDate=" + updatedDate + ", status=" + status
				+ "]";
	}

}
