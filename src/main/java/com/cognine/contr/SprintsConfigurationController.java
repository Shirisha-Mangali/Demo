//src/main/java/com/cognine/controller/SprintsConfigurationController.java
package com.cognine.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cognine.model.ConfirmSprintConfiguration;
import com.cognine.model.PBIExists;
import com.cognine.model.PBIType;
import com.cognine.model.Pagination;
import com.cognine.model.Pbi;
import com.cognine.model.PbiFilter;
import com.cognine.model.RoleBasedSprints;
import com.cognine.model.SprintDetails;
import com.cognine.model.SprintsConfiguration;
import com.cognine.model.SprintsFilter;
import com.cognine.model.TeamSize;
import com.cognine.service.SprintsConfigurationService;
import com.cognine.utils.Status;

@RestController
public class SprintsConfigurationController {
	@Autowired
	private SprintsConfigurationService sprintsConfigurationService;

	@PostMapping("addsprint")
	@Secured({ "ROLE_ADMIN", "ROLE_PMO","ROLE_DEVELOPER","ROLE_QA","ROLE_TECH.LEAD","ROLE_PMO.LEAD", "ROLE_PROJECT_MANAGER" })
	public ConfirmSprintConfiguration addSprint(@RequestBody SprintsConfiguration sprintsConfiguration) {
		return sprintsConfigurationService.addSprint(sprintsConfiguration);

	}

	@PutMapping("editsprint")
	@Secured({ "ROLE_ADMIN", "ROLE_PMO","ROLE_DEVELOPER","ROLE_QA","ROLE_TECH.LEAD","ROLE_PMO.LEAD", "ROLE_PROJECT_MANAGER" })
	public ConfirmSprintConfiguration editSprint(@RequestBody SprintsConfiguration sprintsConfiguration) {
		return sprintsConfigurationService.editSprint(sprintsConfiguration);
	}

	@PostMapping("issprintexists")
	@Secured({ "ROLE_ADMIN", "ROLE_PMO","ROLE_DEVELOPER","ROLE_QA","ROLE_TECH.LEAD","ROLE_PMO.LEAD", "ROLE_PROJECT_MANAGER" })
	public Status isSprintExists(@RequestBody SprintsConfiguration sprintsConfiguration) {
		return sprintsConfigurationService.isSprintExists(sprintsConfiguration);

	}

	@PostMapping("getteamsize")
	@Secured({ "ROLE_ADMIN", "ROLE_PMO","ROLE_DEVELOPER","ROLE_QA","ROLE_TECH.LEAD","ROLE_PMO.LEAD", "ROLE_PROJECT_MANAGER" })
	public TeamSize getTeamSize(@RequestBody SprintsConfiguration sprintsConfiguration) {
		return sprintsConfigurationService.getTeamSize(sprintsConfiguration);
	}

	@PostMapping("getsprints")
	@Secured({ "ROLE_ADMIN", "ROLE_PMO","ROLE_DEVELOPER","ROLE_QA","ROLE_TECH.LEAD","ROLE_PMO.LEAD", "ROLE_PROJECT_MANAGER" })
	public Pagination getSprints(@RequestBody SprintsFilter sprintsFilter) {
		return sprintsConfigurationService.getSprints(sprintsFilter);

	}

	@PostMapping("addpbi")
	@Secured({ "ROLE_ADMIN", "ROLE_PMO","ROLE_DEVELOPER","ROLE_QA","ROLE_TECH.LEAD","ROLE_PMO.LEAD", "ROLE_PROJECT_MANAGER" })
	public Status addPBI(@RequestBody Pbi pbi) {
		return sprintsConfigurationService.addPBI(pbi);

	}

	@GetMapping("getpbitypes")
	@Secured({ "ROLE_ADMIN", "ROLE_PMO","ROLE_DEVELOPER","ROLE_QA","ROLE_TECH.LEAD","ROLE_PMO.LEAD", "ROLE_PROJECT_MANAGER" })
	public List<PBIType> getPBITypes() {
		return sprintsConfigurationService.getPBITypes();

	}

	@PostMapping("ispbiexists")
	@Secured({ "ROLE_ADMIN", "ROLE_PMO","ROLE_DEVELOPER","ROLE_QA","ROLE_TECH.LEAD","ROLE_PMO.LEAD", "ROLE_PROJECT_MANAGER" })
	public Status isPBIExists(@RequestBody PBIExists pbiExists) {
		return sprintsConfigurationService.isPBIExists(pbiExists.getPbiId(), pbiExists.getSptintId());

	}

	@PutMapping("editpbi")
	@Secured({ "ROLE_ADMIN", "ROLE_PMO","ROLE_DEVELOPER","ROLE_QA","ROLE_TECH.LEAD","ROLE_PMO.LEAD", "ROLE_PROJECT_MANAGER" })
	public Status editPBI(@RequestBody Pbi pbi) {
		return sprintsConfigurationService.editPBI(pbi);
	}

	@DeleteMapping("deletepbi/{id}")
	@Secured({ "ROLE_ADMIN", "ROLE_PMO","ROLE_DEVELOPER","ROLE_QA","ROLE_TECH.LEAD","ROLE_PMO.LEAD", "ROLE_PROJECT_MANAGER" })
	public Status deletePBI(@PathVariable("id") int id) {
		return sprintsConfigurationService.deletePBI(id);
	}

	@PostMapping("getfilteredpbis")
	@Secured({ "ROLE_ADMIN", "ROLE_PMO","ROLE_DEVELOPER","ROLE_QA","ROLE_TECH.LEAD","ROLE_PMO.LEAD", "ROLE_PROJECT_MANAGER" })
	public Pagination getFilteredPBIs(@RequestBody PbiFilter pbiFilter) {
		return sprintsConfigurationService.getFilteredPBIs(pbiFilter);
	}

	@PostMapping("getpbidatabypriority")
	@Secured({ "ROLE_ADMIN", "ROLE_PMO","ROLE_DEVELOPER","ROLE_QA","ROLE_TECH.LEAD","ROLE_PMO.LEAD", "ROLE_PROJECT_MANAGER" })
	public Pbi getPbiDataByPriority(@RequestBody Pbi pbi) {
		return sprintsConfigurationService.getPbiDataByPriority(pbi);

	}
	
	@DeleteMapping("deletesprint/{id}")
	@Secured({ "ROLE_ADMIN", "ROLE_PMO","ROLE_DEVELOPER","ROLE_QA","ROLE_TECH.LEAD","ROLE_PMO.LEAD", "ROLE_PROJECT_MANAGER" })
	public Status deletesprint(@PathVariable("id") int id){
		return sprintsConfigurationService.deleteSprint(id);
	}
	
	@PostMapping("getrolebasedsprints")
	@Secured({ "ROLE_ADMIN", "ROLE_PMO","ROLE_DEVELOPER","ROLE_QA","ROLE_TECH.LEAD","ROLE_PMO.LEAD", "ROLE_PROJECT_MANAGER" })
	public List<SprintDetails> getRoleBasedSprints(@RequestBody RoleBasedSprints roleBasedSprints){
		return sprintsConfigurationService.getRoleBasedSprints(roleBasedSprints);
	}

}
