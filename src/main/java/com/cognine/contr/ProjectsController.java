package com.cognine.contr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import com.cognine.model.ProjectConfiguration;
import com.cognine.model.RoleBasedProjects;
import com.cognine.service.ProjectsService;



@RestController
public class ProjectsController {
    
    @Autowired
    private ProjectsService projectsService;

    @PostMapping("getprojectsbyclientid")
    public List<ProjectConfiguration>getProjectsByClientId(@RequestBody RoleBasedProjects roleBasedProjects){
        return projectsService.getProjectsByClientId(roleBasedProjects);
    }

}
