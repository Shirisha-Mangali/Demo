package com.cognine.dao;

import com.cognine.model.ProjectConfiguration;
import com.cognine.model.ProjectResource;
import com.cognine.model.ProjectResourceDetails;
import com.cognine.model.ProjectsFilter;
import com.cognine.model.RoleBasedProjects;
import com.cognine.model.AllocatedProjectResource;
import com.cognine.model.AvailableResource;
import com.cognine.model.Pmos;
import java.util.List;
import java.util.Date;
public interface ProjectsDao {
    	List<ProjectConfiguration> getFilteredProjects(ProjectsFilter projectsFilter);
		List<ProjectConfiguration> getPmosProjects(RoleBasedProjects roleBasedProjects);
		List<ProjectConfiguration> getProjectsByClientId(RoleBasedProjects roleBasedProjects);
		List<ProjectConfiguration> getManagerRoleProjects(RoleBasedProjects roleBasedProjects);
		List<ProjectConfiguration> getOtherRolesProjects(RoleBasedProjects roleBasedProjects);List<ProjectConfiguration> getTechLeadRoleProjects(RoleBasedProjects roleBasedProjects);
		List<ProjectConfiguration> getOnlyTechLeadOrManagerProjects(RoleBasedProjects roleBasedProjects);
		List<Pmos> getPmosByProjectId(int projectId);

		int addConfirmedResourceAllocation(int employeeId, int roleId, int projectId, int billableAllocation,
			AvailableResource availableResource);
		
		int addNewReportingHead(String newReportingHead, int employeeId);
		
		List<ProjectResourceDetails> getProjectDetailsByResource(int employeeId, Date start_date, Date end_date);

		//int isExists(int employeeId, int projectId, Date startDate, Date endDate);

		List<ProjectResourceDetails> getProjectDetailsByResourceWithoutEndDate(int employeeId, Date startDate);

		List<AllocatedProjectResource> getAllocatedProjectResourceDetailsByResource(int employeeId, Date startDate,
			Date endDate);

		List<AllocatedProjectResource> getAllocatedProjectResourceDetailsByResourceWithouEndDate(int employeeId,
			Date startDate);

		void removeResourceAllocation(int id);
		int addResource(ProjectResource projectResource);
}
