package com.cognine.dao.impl;
import com.cognine.dao.ProjectsDao;
import com.cognine.model.AvailableResource;
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

	@Override
	public int addConfirmedResourceAllocation(int employeeId, int roleId, int projectId, int billableAllocation,
			AvailableResource availableResource) {
		int returnVal = 0;
		try {

			returnVal = projectsMapper.addConfirmedResourceAllocation(employeeId, roleId, projectId, billableAllocation,
					availableResource);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnVal;
	}

	@Override
	public int addNewReportingHead(String newReportingHead, int employeeId) {
		int returnVal = 0;
		try {

			returnVal = projectsMapper.addNewReportingHead(newReportingHead, employeeId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnVal;
	}

}
