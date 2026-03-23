package com.cognine.model;

public class RoleBasedClients {

	private int employeeId;
	private int roleId;
	private String roleName;
	private boolean isActive;
	private boolean isTechLeadOrManager;
	private boolean isDevOrQA;


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

	public boolean getIsDevOrQA() {
		return isDevOrQA;
	}

	public void setISDevOrQA(boolean isDevOrQA) {
		this.isDevOrQA = isDevOrQA;
	}

	public boolean getIsTechLeadOrManager() {
		return isTechLeadOrManager;
	}

	public void setIsTechLeadOrManager(boolean isTechLeadOrManager) {
		this.isTechLeadOrManager = isTechLeadOrManager;
	}


	
}
