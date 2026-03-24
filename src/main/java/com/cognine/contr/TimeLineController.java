//src/main/java/com/cognine/controller/TimelineController.java
package com.cognine.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cognine.model.Colors;
import com.cognine.model.PbiResource;
import com.cognine.model.PbiTimeLineData;
import com.cognine.model.PbiTimeline;
import com.cognine.model.ProjectResource;
import com.cognine.model.RemoveResource;
import com.cognine.model.ResourceHours;
import com.cognine.model.TimelineNotes;
import com.cognine.service.TimelineService;
import com.cognine.utils.Status;

@RestController
public class TimelineController {
	@Autowired
	private TimelineService timelineService;
	
	@GetMapping("/getcolors")
	public Colors getColors() {
		return timelineService.getColors();		
	}
	@PostMapping("/addtimeline")
	@Secured({ "ROLE_ADMIN", "ROLE_PMO", "ROLE_DEVELOPER", "ROLE_QA", "ROLE_TECH.LEAD","ROLE_PMO.LEAD", "ROLE_PROJECT_MANAGER" })
	public Status addTimeline(@RequestBody List<PbiTimeline> pbiTimeline) {
	Status status = null;
	boolean checkExistedResourcesInPBIs = checkExistedResourcesInPBIs(pbiTimeline);
	if(!checkExistedResourcesInPBIs) {
	for(PbiTimeline timeline: pbiTimeline) {
	status =  timelineService.addTimeline(timeline);	 
	}
	}
	else {
		status = Status.RESOURCE_ALREADY_EXISTS_IN_PBI;
	 }
	return status;
	}
	
	@PostMapping("/getallpbitimelinedata")
	@Secured({ "ROLE_ADMIN", "ROLE_PMO", "ROLE_DEVELOPER", "ROLE_QA", "ROLE_TECH.LEAD","ROLE_PMO.LEAD", "ROLE_PROJECT_MANAGER" })
	PbiTimeLineData getAllPbiTimelineData(@RequestBody PbiTimeLineData pbiTimeLineData){
		return timelineService.getAllPbiTimelineData(pbiTimeLineData.getSprintId(), pbiTimeLineData.getProjectId());
	}
	
	@GetMapping("getBillableResourcesByProjectId")
	@Secured({ "ROLE_ADMIN", "ROLE_PMO", "ROLE_DEVELOPER", "ROLE_QA", "ROLE_TECH.LEAD","ROLE_PMO.LEAD" ,  "ROLE_PROJECT_MANAGER" })
	public List<ProjectResource> getBillableResourcesByProjectId(@RequestParam("projectId") int projectId) {
		return timelineService.getBillableResourcesByProjectId(projectId);

	}
	
	@GetMapping("/getresourcessummary")
	@Secured({ "ROLE_ADMIN", "ROLE_PMO", "ROLE_DEVELOPER", "ROLE_QA", "ROLE_TECH.LEAD","ROLE_PMO.LEAD", "ROLE_PROJECT_MANAGER" })
	List<ResourceHours> getResourcesSummary(@RequestParam("sprintId") int sprintId, @RequestParam("projectId") int projectId){
		return timelineService.getResourceSummaryData(sprintId,projectId);
	}
	
	@PostMapping("removeresourcehours")
	@Secured({ "ROLE_ADMIN", "ROLE_PMO", "ROLE_DEVELOPER", "ROLE_QA", "ROLE_TECH.LEAD","ROLE_PMO.LEAD", "ROLE_PROJECT_MANAGER" })
	public Status deleteResourceHours(@RequestBody RemoveResource deleteResource) {
		return timelineService.deleteResourceHours(deleteResource);
	}
	
	@PostMapping("/savetimelinenotes")
	@Secured({ "ROLE_ADMIN", "ROLE_PMO", "ROLE_DEVELOPER", "ROLE_QA", "ROLE_TECH.LEAD","ROLE_PMO.LEAD", "ROLE_PROJECT_MANAGER" })
	public Status saveTimelineNotes(@RequestBody TimelineNotes timelineNotes) {
	    return timelineService.saveTimelineNotes(timelineNotes);
	}
	
	
	private boolean checkExistedResourcesInPBIs(List<PbiTimeline> pbis) {
        for (PbiTimeline pbi : pbis) {
            boolean isResourceExist = pbi.getResources().stream()
                .anyMatch(resource -> timelineService.isResourceWithPBIExistInTimeline(pbi.getPbiId(), resource.getResourceId()) > 0
                        && !resource.getDays().isEmpty());
            if (isResourceExist) {
                return true;
            }
        }
        return false;
    }
}
