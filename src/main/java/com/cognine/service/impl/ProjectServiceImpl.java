package com.cognine.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognine.mapper.ProjectsMapper;
import com.cognine.service.ProjectsService;
import com.cognine.utils.AppConstants;
import com.cognine.dao.ProjectsDao;
import com.cognine.model.ProjectConfiguration;
import com.cognine.model.Pmos;
import java.util.*;
import java.util.stream.Collectors;

import com.cognine.model.RoleBasedProjects;

@Service
public class ProjectServiceImpl implements ProjectsService {
    @Autowired
    private ProjectsDao projectsDao;

    private List<ProjectConfiguration> getUniqueProjects(List<ProjectConfiguration> projects) {
		return projects.stream().collect(
				Collectors.toMap(ProjectConfiguration::getId, role -> role, (existing, replacement) -> existing))
				.values().stream().collect(Collectors.toList());
	}
    private List<ProjectConfiguration> getProjectManagerAndTechLeadProjects(RoleBasedProjects roleBasedProjects) {
		List<ProjectConfiguration> projects = new ArrayList<>();
		if (roleBasedProjects.getIsTechLeadOrManager()) {
			projects.addAll(projectsDao.getOnlyTechLeadOrManagerProjects(roleBasedProjects));
		}
		if (roleBasedProjects.getIsDevOrQA()) {
			projects.addAll(projectsDao.getOtherRolesProjects(roleBasedProjects));
		}
		projects.addAll(projectsDao.getManagerRoleProjects(roleBasedProjects));
		projects.addAll(projectsDao.getTechLeadRoleProjects(roleBasedProjects));
		return getUniqueProjects(projects);
	}


    @Override
    public List<ProjectConfiguration> getProjectsByClientId(RoleBasedProjects roleBasedProjects){
        List<ProjectConfiguration> projectConfigurationList=new ArrayList<>();
        if(roleBasedProjects.getRoleName().equals("ROLE_ADMIN")||roleBasedProjects.getRoleName().equals(AppConstants.ROLE_PMO_LEAD)){
            projectConfigurationList=projectsDao.getProjectsByClientId(roleBasedProjects);
        }
        else if(roleBasedProjects.getRoleName().equals(AppConstants.ROLE_PMO)){
            projectConfigurationList.addAll(projectsDao.getPmosProjects(roleBasedProjects));
            if(roleBasedProjects.getIsTechLeadOrManager()){
                projectConfigurationList.addAll(getProjectManagerAndTechLeadProjects(roleBasedProjects));
                projectConfigurationList = getUniqueProjects(projectConfigurationList);
            }
            if(roleBasedProjects.getIsDevOrQA()){
                projectConfigurationList.addAll(projectsDao.getOtherRolesProjects(roleBasedProjects));
                projectConfigurationList=getUniqueProjects(projectConfigurationList);
            }
        }
        else if(roleBasedProjects.getRoleName().equals(AppConstants.ROLE_PROJECT_MANAGER)||roleBasedProjects.getRoleName().equals(AppConstants.ROLE_TECH_LEAD)){
            projectConfigurationList= getProjectManagerAndTechLeadProjects(roleBasedProjects);
        }
        else{
            projectConfigurationList=projectsDao.getOtherRolesProjects(roleBasedProjects);
        }
        for(ProjectConfiguration projectConfigurationData:projectConfigurationList){
            List<Pmos>pmosList=projectsDao.getPmosByProjectId(projectConfigurationData.getId());
            projectConfigurationData.getAddedPmos().addAll(pmosList);
        }
        return projectConfigurationList;
    }
    
}
