package com.cognine.service;
import java.util.List;
import com.cognine.model.ProjectConfiguration;
import com.cognine.model.ProjectsFilter;
import com.cognine.model.RoleBasedProjects;

public interface ProjectsService {
	List<ProjectConfiguration> getProjectsByClientId(RoleBasedProjects roleBasedProjects);
	
}
