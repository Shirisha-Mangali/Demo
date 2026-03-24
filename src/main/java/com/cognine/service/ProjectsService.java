package com.cognine.service;
import java.util.List;

import com.cognine.model.ConfirmResourceAllocation;
import com.cognine.model.ProjectConfiguration;
import com.cognine.model.ProjectResource;
import com.cognine.model.ProjectsFilter;
import com.cognine.model.ResourceAllocation;
import com.cognine.model.RoleBasedProjects;
import com.cognine.utils.Status;
import java.text.ParseException;


public interface ProjectsService {
	List<ProjectConfiguration> getProjectsByClientId(RoleBasedProjects roleBasedProjects);
	Status confirmResourceAllocation(ConfirmResourceAllocation confirmResourceAllocation);
	ResourceAllocation addResource(ProjectResource requestedProjectResource) throws ParseException;

}
