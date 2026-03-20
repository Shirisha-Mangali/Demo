package com.cognine.model;

public class RoleBasedProjects {
	private int employeeId;
	private int clientId;
	private int roleId;
	private boolean isActive;
	private String roleName;
	private boolean isTechLeadOrManager;
	private boolean isDevOrQA;

	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public int getClientId() {
		return clientId;
	}
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}
	public boolean getIsTechLeadOrManager() {
		return isTechLeadOrManager;
	}
	public void setIsTechLeadOrManager(boolean isTechLeadOrManager) {
		this.isTechLeadOrManager = isTechLeadOrManager;
	}
	
	
	public boolean getIsDevOrQA() {
		return isDevOrQA;
	}

	public void setISDevOrQA(boolean isDevOrQA) {
		this.isDevOrQA = isDevOrQA;
	}

}
