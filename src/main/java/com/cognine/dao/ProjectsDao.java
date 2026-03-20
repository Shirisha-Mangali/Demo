package com.cognine.dao;

import com.cognine.model.ProjectConfiguration;
import com.cognine.model.ProjectsFilter;
import com.cognine.model.RoleBasedProjects;
import com.cognine.model.Pmos;
import java.util.List;
public interface ProjectsDao {
    	List<ProjectConfiguration> getFilteredProjects(ProjectsFilter projectsFilter);
		List<ProjectConfiguration> getPmosProjects(RoleBasedProjects roleBasedProjects);
		List<ProjectConfiguration> getProjectsByClientId(RoleBasedProjects roleBasedProjects);
		List<ProjectConfiguration> getManagerRoleProjects(RoleBasedProjects roleBasedProjects);
		List<ProjectConfiguration> getOtherRolesProjects(RoleBasedProjects roleBasedProjects);List<ProjectConfiguration> getTechLeadRoleProjects(RoleBasedProjects roleBasedProjects);
		List<ProjectConfiguration> getOnlyTechLeadOrManagerProjects(RoleBasedProjects roleBasedProjects);
		List<Pmos> getPmosByProjectId(int projectId);

}
