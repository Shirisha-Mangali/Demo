package com.cognine.model;

import java.util.List;

import com.cognine.utils.Status;

public class ResourceAllocation {
	private List<AvailableResource> availableResource;
	private List<ProjectResourceDetails> projectResourcesDetails;
	private List<AllocatedProjectResource> allocatedProjectResources;
	private Status status;

	public List<AvailableResource> getAvailableResource() {
		return availableResource;
	}

	public void setAvailableResource(List<AvailableResource> availableResource) {
		this.availableResource = availableResource;
	}

	public Status getStatus() {
		return status;
	}

	public Status setStatus(Status status) {
		return this.status = status;
	}

	public List<ProjectResourceDetails> getProjectResourcesDetails() {
		return projectResourcesDetails;
	}

	public void setProjectResourcesDetails(List<ProjectResourceDetails> projectResourcesDetails) {
		this.projectResourcesDetails = projectResourcesDetails;
	}

	public List<AllocatedProjectResource> getAllocatedProjectResources() {
		return allocatedProjectResources;
	}

	public void setAllocatedProjectResources(List<AllocatedProjectResource> allocatedProjectResources) {
		this.allocatedProjectResources = allocatedProjectResources;
	}

	@Override
	public String toString() {
		return "ResourceAllocation [availableResource=" + availableResource + ", projectResourcesDetails="
				+ projectResourcesDetails + ", status=" + status + "]";
	}

}
