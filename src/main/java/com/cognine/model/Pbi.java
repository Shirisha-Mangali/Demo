package com.cognine.model;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

public class Pbi {

	private int id;
	private String pbiId;
	private String description;
	private String pbiName;
	private int sprintId;
	private int pbiTypeId;
	private Date addedAt;
	private Date createdAt;
	private Date updateAt;
	private boolean isActive;
	private String pbiTypeName;
	private int priority;
	private int source;
	private int destination;
	
    @JsonInclude(JsonInclude.Include.ALWAYS)
	private boolean hasTimelineHours;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPbiId() {
		return pbiId;
	}

	public void setPbiId(String pbiId) {
		this.pbiId = pbiId;
	}

	public int getSprintId() {
		return sprintId;
	}

	public void setSprintId(int sprintId) {
		this.sprintId = sprintId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

	public boolean isIsActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isActive() {
		return isActive;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPbiName() {
		return pbiName;
	}

	public void setPbiName(String pbiName) {
		this.pbiName = pbiName;
	}

	public int getPbiTypeId() {
		return pbiTypeId;
	}

	public void setPbiTypeId(int pbiTypeId) {
		this.pbiTypeId = pbiTypeId;
	}

	public Date getAddedAt() {
		return addedAt;
	}

	public void setAddedAt(Date addedAt) {
		this.addedAt = addedAt;
	}

	public String getPbiTypeName() {
		return pbiTypeName;
	}

	public void setPbiTypeName(String pbiTypeName) {
		this.pbiTypeName = pbiTypeName;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}

	public int getDestination() {
		return destination;
	}

	public void setDestination(int destination) {
		this.destination = destination;
	}

	public boolean isHasTimelineHours() {
		return hasTimelineHours;
	}

	public void setHasTimelineHours(boolean hasTimelineHours) {
		this.hasTimelineHours = hasTimelineHours;
	}

}
