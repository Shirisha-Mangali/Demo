package com.cognine.model;

import java.util.List;

public class ConfirmResourceAllocation {
	private int id;
	private int employeeId;
	private int roleId;
	private int projectId;
	private int clientId;
	private int billableAllocation;
	private String employeeName;
	private String currentReportingHead;
	private String newReportingHead;
	private List<AvailableResource> availableResource;
	private String currentLoginUserName;
	private String roleName;

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

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public int getBillableAllocation() {
		return billableAllocation;
	}

	public void setBillableAllocation(int billableAllocation) {
		this.billableAllocation = billableAllocation;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getCurrentReportingHead() {
		return currentReportingHead;
	}

	public void setCurrentReportingHead(String currentReportingHead) {
		this.currentReportingHead = currentReportingHead;
	}

	public String getNewReportingHead() {
		return newReportingHead;
	}

	public void setNewReportingHead(String newReportingHead) {
		this.newReportingHead = newReportingHead;
	}

	public List<AvailableResource> getAvailableResource() {
		return availableResource;
	}

	public void setAvailableResource(List<AvailableResource> availableResource) {
		this.availableResource = availableResource;
	}

	public String getCurrentLoginUserName() {
		return currentLoginUserName;
	}

	public void setCurrentLoginUserName(String currentLoginUserName) {
		this.currentLoginUserName = currentLoginUserName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	
}
