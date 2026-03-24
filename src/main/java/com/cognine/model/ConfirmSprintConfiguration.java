package com.cognine.model;

import java.util.List;

import com.cognine.utils.Status;

public class ConfirmSprintConfiguration {
	
	private List<SprintDetails> sprintDetailsList;
	private Status status;
	public List<SprintDetails> getSprintDetailsList() {
		return sprintDetailsList;
	}
	public void setSprintDetailsList(List<SprintDetails> sprintDetailsList) {
		this.sprintDetailsList = sprintDetailsList;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	
	
	

}
