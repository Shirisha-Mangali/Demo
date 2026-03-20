package com.cognine.dao.impl;
import com.cognine.dao.ProjectsDao;
import com.cognine.model.Pmos;
import com.cognine.model.ProjectConfiguration;
import com.cognine.model.ProjectsFilter;
import com.cognine.model.RoleBasedProjects;

import java.util.List;
import com.cognine.mapper.ProjectsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectsDaoImpl implements ProjectsDao {
    @Autowired
    private ProjectsMapper projectsMapper;
    @Override
	public List<ProjectConfiguration> getFilteredProjects(ProjectsFilter projectsFilter) {
		return projectsMapper.getFilteredProjects(projectsFilter);
	}
    @Override
	public List<ProjectConfiguration> getProjectsByClientId(RoleBasedProjects roleBasedProjects) {
		return projectsMapper.getProjectsByClientId(roleBasedProjects);
	}

	@Override
	public List<Pmos> getPmosByProjectId(int projectId) {
		return projectsMapper.getPmosByProjectId(projectId);
	}
    @Override
	public List<ProjectConfiguration> getPmosProjects(RoleBasedProjects roleBasedProjects) {

		return projectsMapper.getPmosProjects(roleBasedProjects);
	}
    @Override
	public List<ProjectConfiguration> getOtherRolesProjects(RoleBasedProjects roleBasedProjects) {
		return projectsMapper.getOtherRolesProjects(roleBasedProjects);
	}
    @Override
	public List<ProjectConfiguration> getManagerRoleProjects(RoleBasedProjects roleBasedProjects) {
		return projectsMapper.getManageRoleProjects(roleBasedProjects);
	}

	@Override
	public List<ProjectConfiguration> getTechLeadRoleProjects(RoleBasedProjects roleBasedProjects) {
		return projectsMapper.getTechLeadRoleProjects(roleBasedProjects);
	}

	@Override
	public List<ProjectConfiguration> getOnlyTechLeadOrManagerProjects(RoleBasedProjects roleBasedProjects) {
		return projectsMapper.getOnlyTechLeadOrManagerProjects(roleBasedProjects);
	}

}
