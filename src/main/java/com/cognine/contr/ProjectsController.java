package com.cognine.contr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import com.cognine.model.ConfirmResourceAllocation;
import com.cognine.model.ProjectConfiguration;
import com.cognine.model.ProjectResource;
import com.cognine.model.RoleBasedProjects;
import com.cognine.service.ProjectsService;
import org.springframework.security.access.annotation.Secured;
import com.cognine.utils.Status;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.cognine.model.ResourceAllocation;


@RestController
public class ProjectsController {
    
    @Autowired
    private ProjectsService projectsService;

    @PostMapping("getprojectsbyclientid")
    public List<ProjectConfiguration>getProjectsByClientId(@RequestBody RoleBasedProjects roleBasedProjects){
        return projectsService.getProjectsByClientId(roleBasedProjects);
    }
    @PostMapping("confirmresourceallocation")
    @Secured({ "ROLE_ADMIN", "ROLE_PMO","ROLE_PMO.LEAD" })
    public Status confirmresourceallocation(@RequestBody ConfirmResourceAllocation confirmResourceAllocation){
        return projectsService.confirmResourceAllocation(confirmResourceAllocation);
    }
    @PostMapping("addresource")
	@Secured({ "ROLE_ADMIN", "ROLE_PMO","ROLE_PMO.LEAD" })
	public ResourceAllocation addResource(@RequestBody ProjectResource requestedProjectResource)
			throws ParseException, JsonMappingException, JsonProcessingException {
		return projectsService.addResource(requestedProjectResource);
	}
	

}
