package com.cognine.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ProjectResource {

	private int id;
	private int employeeId;
	private int roleId;
	private int projectId;
	private Date startDate;
	private Date endDate;
	private int projectAllocation;
	private int billableAllocation;
	private String currentReportingHead;
	private String newReportingHead;
	private String projectName;
	private int clientId;
	private String clientName;
	private String displayRoleName;
	private String roleName;
	private String employeeName;
	private boolean active;
	private String currentLoginUserName;
	private String employeeActiveStatus;
	private String resourceProjectStatus;
	private Date projectResourceChangedEndDate;

	@JsonInclude(JsonInclude.Include.ALWAYS)
	private List<String> resourceLeaves;

	public List<String> getResourceLeaves() {
		return resourceLeaves;
	}

	public void setResourceLeaves(String resourceLeaves) {
		if (resourceLeaves != null && !resourceLeaves.isEmpty()) {
			// Assuming the incoming string looks like "{2024-03-04,2024-02-02}"
			resourceLeaves = resourceLeaves.replaceAll("[{}]", "");
			this.resourceLeaves = Arrays.stream(resourceLeaves.split(","))
					.map(String::trim)
					.collect(Collectors.toList());
		} else {
			this.resourceLeaves = new ArrayList<>();
		}
	}

	public String getResourceProjectStatus() {
		return resourceProjectStatus;
	}

	public void setResourceProjectStatus(String resourceProjectStatus) {
		this.resourceProjectStatus = resourceProjectStatus;
	}

	private List<EmployeeReportingHeads> employeeReportingHeadsList = new ArrayList<>();

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

	public int getBillableAllocation() {
		return billableAllocation;
	}

	public void setBillableAllocation(int billableAllocation) {
		this.billableAllocation = billableAllocation;
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getDisplayRoleName() {
		return displayRoleName;
	}

	public void setDisplayRoleName(String displayRoleName) {
		this.displayRoleName = displayRoleName;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getCurrentLoginUserName() {
		return currentLoginUserName;
	}

	public void setCurrentLoginUserName(String currentLoginUserName) {
		this.currentLoginUserName = currentLoginUserName;
	}

	public String getEmployeeActiveStatus() {
		return employeeActiveStatus;
	}

	public void setEmployeeActiveStatus(String employeeActiveStatus) {
		this.employeeActiveStatus = employeeActiveStatus;
	}

	public List<EmployeeReportingHeads> getEmployeeReportingHeadsList() {
		return employeeReportingHeadsList;
	}

	public void setEmployeeReportingHeadsList(List<EmployeeReportingHeads> employeeReportingHeadsList) {
		this.employeeReportingHeadsList = employeeReportingHeadsList;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Date getProjectResourceChangedEndDate() {
		return projectResourceChangedEndDate;
	}

	public void setProjectResourceChangedEndDate(Date projectResourceChangedEndDate) {
		this.projectResourceChangedEndDate = projectResourceChangedEndDate;
	}

	@Override
	public String toString() {
		return "ProjectResource [id=" + id + ", employeeId=" + employeeId + ", roleId=" + roleId + ", projectId="
				+ projectId + ", startDate=" + startDate + ", endDate=" + endDate + ", projectAllocation="
				+ projectAllocation + ", billableAllocation=" + billableAllocation + ", currentReportingHead="
				+ currentReportingHead + ", newReportingHead=" + newReportingHead + ", projectName=" + projectName
				+ ", clientId=" + clientId + ", clientName=" + clientName + ", displayRoleName=" + displayRoleName
				+ ", employeeName=" + employeeName + ", active=" + active + ", currentLoginUserName="
				+ currentLoginUserName + ", employeeActiveStatus=" + employeeActiveStatus + "]";
	}

}
