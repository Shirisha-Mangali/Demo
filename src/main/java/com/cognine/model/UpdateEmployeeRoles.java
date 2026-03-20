package com.cognine.model;

import java.util.List;

public class UpdateEmployeeRoles {
	private int employeeId;
	private String employeeName;
	private List<Roles> addedRoles;
	private List<Roles> deletedRoles;
	private List<Roles> updatedRoles;

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public List<Roles> getAddedRoles() {
		return addedRoles;
	}

	public void setAddedRoles(List<Roles> addedRoles) {
		this.addedRoles = addedRoles;
	}

	public List<Roles> getDeletedRoles() {
		return deletedRoles;
	}

	public void setDeletedRoles(List<Roles> deletedRoles) {
		this.deletedRoles = deletedRoles;
	}

	public List<Roles> getUpdatedRoles() {
		return updatedRoles;
	}

	public void setUpdatedRoles(List<Roles> updatedRoles) {
		this.updatedRoles = updatedRoles;
	}

}
