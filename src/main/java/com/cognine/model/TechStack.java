package com.cognine.model;

import java.sql.Date;

public class TechStack {
    private int id;
    private String techStackName;
    private Date createdAt;
    private Date updatedAt;
    private boolean isActive;
    private int projectTechStackMappingId;
    
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}
	public String getTechStackName() {
		return techStackName;
	}
	public void setTechStackName(String techStackName) {
		this.techStackName = techStackName;
	}
	public int getProjectTechStackMappingId() {
		return projectTechStackMappingId;
	}
	public void setProjectTechStackMappingId(int projectTechStackMappingId) {
		this.projectTechStackMappingId = projectTechStackMappingId;
	}
	   
}