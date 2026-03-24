package com.cognine.service;

import java.util.List;

import com.cognine.model.ConfirmSprintConfiguration;
import com.cognine.model.PBIType;
import com.cognine.model.Pagination;
import com.cognine.model.Pbi;
import com.cognine.model.PbiFilter;
import com.cognine.model.RoleBasedSprints;
import com.cognine.model.SprintDetails;
import com.cognine.model.SprintsConfiguration;
import com.cognine.model.SprintsFilter;
import com.cognine.model.TeamSize;
import com.cognine.utils.Status;
public interface SprintsConfigurationService {

	ConfirmSprintConfiguration addSprint(SprintsConfiguration sprintsConfiguration);

	ConfirmSprintConfiguration editSprint(SprintsConfiguration sprintsConfiguration);

	Status isSprintExists(SprintsConfiguration sprintsConfiguration);

	TeamSize getTeamSize(SprintsConfiguration sprintsConfiguration);

	Pagination getSprints(SprintsFilter sprintsFilter);

	Status addPBI(Pbi pbi);

	List<PBIType> getPBITypes();

	Status isPBIExists(String pbiId, int sprintId);

	Status editPBI(Pbi pbi);

	Status deletePBI(int id);

	Pagination getFilteredPBIs(PbiFilter pbiFilter);

	Pbi getPbiDataByPriority(Pbi pbi);
		
	Status deleteSprint(int sprintId);
	
	List<SprintDetails> getRoleBasedSprints(RoleBasedSprints roleBasedSprints);

}
