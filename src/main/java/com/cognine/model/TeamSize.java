package com.cognine.model;

import com.cognine.utils.Status;

public class TeamSize {
	private Status status;
	private Float teamSize;
	private int resourceCount;
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public Float getTeamSize() {
		return teamSize;
	}
	public void setTeamSize(Float teamSize) {
		this.teamSize = teamSize;
	}
	public int getResourceCount() {
		return resourceCount;
	}
	public void setResourceCount(int resourceCount) {
		this.resourceCount = resourceCount;
	}
	
	
	

}
