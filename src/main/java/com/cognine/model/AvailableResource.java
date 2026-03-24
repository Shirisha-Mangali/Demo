package com.cognine.model;

import java.util.Date;
import java.util.List;

public class AvailableResource {

	private int availableProjectAllocation;
	private int allocatePercentage;

	private Date startDate;
	private Date endDate;

	public int getAvailableProjectAllocation() {
		return availableProjectAllocation;
	}

	public void setAvailableProjectAllocation(int availableProjectAllocation) {
		this.availableProjectAllocation = availableProjectAllocation;
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

	public int getAllocatePercentage() {
		return allocatePercentage;
	}

	public void setAllocatePercentage(int allocatePercentage) {
		this.allocatePercentage = allocatePercentage;
	}

	@Override
	public String toString() {
		return "AvailableResource [availableProjectAllocation=" + availableProjectAllocation + ", allocatePercentage="
				+ allocatePercentage + ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}

	
}
