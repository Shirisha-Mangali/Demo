package com.cognine.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognine.mapper.ProjectsMapper;
import com.cognine.service.ProjectsService;
import com.cognine.utils.AppConstants;
import com.cognine.utils.AvailableResourceAllocation;
import com.cognine.dao.ProjectsDao;
import com.cognine.model.ProjectConfiguration;
import com.cognine.model.ProjectResource;
import com.cognine.model.ProjectResourceDetails;
import com.cognine.model.ResourceAllocation;
import com.cognine.model.AllocatedProjectResource;
import com.cognine.model.AvailableResource;
import com.cognine.model.ConfirmResourceAllocation;
import com.cognine.model.Pmos;
import com.cognine.utils.AvailableResourceAllocation;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;
import com.cognine.utils.Status;
import com.cognine.utils.EmailSender;


import com.cognine.model.RoleBasedProjects;


@Service
public class ProjectServiceImpl implements ProjectsService {
    @Autowired
    private ProjectsDao projectsDao;

    @Autowired
	private EmailSender emailSender;
    
    @Autowired
	private AvailableResourceAllocation availableResourceAllocation;

    int responseFlag;

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

    @Override
    public Status confirmResourceAllocation(ConfirmResourceAllocation confirmResourceAllocation){
        int successFlag=0;
        Status status=Status.FAILED;
        if(confirmResourceAllocation!=null){
            if(confirmResourceAllocation.getEmployeeId()!=0 &&confirmResourceAllocation.getRoleId()!=0 && confirmResourceAllocation.getProjectId()!=0){
                if(!confirmResourceAllocation.getAvailableResource().isEmpty()){
                    for(AvailableResource availableResource: confirmResourceAllocation.getAvailableResource()){
                        if(availableResource.getStartDate()!=null){
                            if(projectsDao.addConfirmedResourceAllocation(confirmResourceAllocation.getEmployeeId(), confirmResourceAllocation.getRoleId(),confirmResourceAllocation.getProjectId(),confirmResourceAllocation.getBillableAllocation(),availableResource)>=1){
                                successFlag = 1;
                            }
                        }else{
                            responseFlag=2;
                        }
                    }
                }else{
                    successFlag=1;
                }
            }else{
                responseFlag=2;
            }
        }else{
            responseFlag=3;
        }
        if(successFlag!=1){
            if(responseFlag==2){
                status=Status.REQUIRED_VALUES_SHOULD_NOT_BE_NULL;
            }else if(responseFlag==3){
                status=Status.EXPECTING_SOME_DATA;
            }
        }else if(successFlag==1){
            if(!confirmResourceAllocation.getNewReportingHead().equals("")){
                if(projectsDao.addNewReportingHead(confirmResourceAllocation.getNewReportingHead(),confirmResourceAllocation.getEmployeeId())>=1){
                    status=Status.SUCCESS;
                }
                if(confirmResourceAllocation.getCurrentReportingHead()==null || confirmResourceAllocation.getCurrentReportingHead()==""){
                    emailSender.sendEmailWithoutCurrentReportingHead(
                        confirmResourceAllocation.getCurrentLoginUserName(),
                        confirmResourceAllocation.getEmployeeId(),confirmResourceAllocation.getEmployeeName(),
                        confirmResourceAllocation.getNewReportingHead()
                    );
                }else{
                    emailSender.sendEmail(confirmResourceAllocation.getCurrentLoginUserName(),confirmResourceAllocation.getEmployeeId(),
                    confirmResourceAllocation.getEmployeeName(),confirmResourceAllocation.getCurrentReportingHead(),
                    confirmResourceAllocation.getNewReportingHead());
                }
            }
            status=Status.SUCCESS;
        }
        return status;
    }


@SuppressWarnings({ "unused" })
	@Override
	public ResourceAllocation addResource(ProjectResource requestedResource) throws ParseException {
		List<AllocatedProjectResource> allocatedProjectResource = null;
		List<ProjectResourceDetails> projectResourceDetailsList = null;
		ResourceAllocation resultMapList = new ResourceAllocation();
		ResourceAllocation returnData = null;

		resultMapList = getResourceProjectAndAllocatedDetails(requestedResource);

//		if (requestedResource.getEndDate() != null) {
//			projectResourceDetailsList = projectsDao.getProjectDetailsByResource(requestedResource.getEmployeeId(),
//					requestedResource.getStartDate(), requestedResource.getEndDate());
//			allocatedProjectResource = projectsDao.getAllocatedProjectResourceDetailsByResource(
//					requestedResource.getEmployeeId(), requestedResource.getStartDate(),
//					requestedResource.getEndDate());
//		} else {
//			projectResourceDetailsList = projectsDao.getProjectDetailsByResourceWithoutEndDate(
//					requestedResource.getEmployeeId(), requestedResource.getStartDate());
//			allocatedProjectResource = projectsDao.getAllocatedProjectResourceDetailsByResourceWithouEndDate(
//					requestedResource.getEmployeeId(), requestedResource.getStartDate());
//		}

		if (resultMapList.getProjectResourcesDetails().isEmpty()) {

			resultMapList.setStatus(Status.FAILED);

			if (requestedResource != null) {

				if (requestedResource.getEmployeeId() != 0 && requestedResource.getRoleId() != 0
						&& requestedResource.getStartDate() != null && requestedResource.getProjectId() != 0
						&& requestedResource.getProjectAllocation() != 0) {
					if (projectsDao.addResource(requestedResource) >= 1) {

						if (!requestedResource.getNewReportingHead().equals("")) {
							if (projectsDao.addNewReportingHead(requestedResource.getNewReportingHead(),
									requestedResource.getEmployeeId()) >= 1) {
								resultMapList.setStatus(Status.SUCCESS);
							}
							if (requestedResource.getCurrentReportingHead() == null
									|| requestedResource.getCurrentReportingHead() == "") {
								emailSender.sendEmailWithoutCurrentReportingHead(
										requestedResource.getCurrentLoginUserName(), requestedResource.getEmployeeId(),
										requestedResource.getEmployeeName(), requestedResource.getNewReportingHead());
							} else {

								emailSender.sendEmail(requestedResource.getCurrentLoginUserName(),
										requestedResource.getEmployeeId(), requestedResource.getEmployeeName(),
										requestedResource.getCurrentReportingHead(),
										requestedResource.getNewReportingHead());
							}

						}

						resultMapList.setStatus(Status.SUCCESS);
					} else {
						resultMapList.setStatus(Status.FAILED);
					}

				} else {
					resultMapList.setStatus(Status.REQUIRED_VALUES_SHOULD_NOT_BE_NULL);
				}
			} else {
				resultMapList.setStatus(Status.EXPECTING_SOME_DATA);
			}

		} else {
			returnData = getConfirmationAllocation(resultMapList.getProjectResourcesDetails(),
					resultMapList.getAllocatedProjectResources(), requestedResource, resultMapList, "add");
		}
		return resultMapList;

	}
    private ResourceAllocation getResourceProjectAndAllocatedDetails(ProjectResource requestedResource)
			throws ParseException {
		List<AllocatedProjectResource> allocatedProjectResource = null;
		List<ProjectResourceDetails> projectResourceDetailsList = null;
		ResourceAllocation resultMapList = new ResourceAllocation();
		ResourceAllocation returnData = null;
		if (requestedResource.getEndDate() != null) {
			projectResourceDetailsList = projectsDao.getProjectDetailsByResource(requestedResource.getEmployeeId(),
					requestedResource.getStartDate(), requestedResource.getEndDate());
			allocatedProjectResource = projectsDao.getAllocatedProjectResourceDetailsByResource(
					requestedResource.getEmployeeId(), requestedResource.getStartDate(),
					requestedResource.getEndDate());
		} else {
			projectResourceDetailsList = projectsDao.getProjectDetailsByResourceWithoutEndDate(
					requestedResource.getEmployeeId(), requestedResource.getStartDate());
			allocatedProjectResource = projectsDao.getAllocatedProjectResourceDetailsByResourceWithouEndDate(
					requestedResource.getEmployeeId(), requestedResource.getStartDate());
		}

		returnData = getConfirmationAllocation(projectResourceDetailsList, allocatedProjectResource, requestedResource,
				resultMapList, "add");
		return resultMapList;

	}
    public ResourceAllocation getConfirmationAllocation(List<ProjectResourceDetails> projectResourceDetailsList,
			List<AllocatedProjectResource> allocatedProjectResource, ProjectResource requestedResource,
			ResourceAllocation resultMapList, String operationType) throws ParseException {
		Date startDate = null;
		Date endDate = null;
		int previousProjectAllocation = 0;
		int currentProjectAllocation = 0;
		int remainingProjectAllocation = 0;
		int nullAllocation = 0;
		List<AvailableResource> resourceList = new ArrayList<AvailableResource>();
		if (operationType.equals("update")) {
			projectsDao.removeResourceAllocation(requestedResource.getId());
		}
		for (ProjectResourceDetails projectResourceDetails : projectResourceDetailsList) {
			if (projectResourceDetails.getEndDate() == null) {
				nullAllocation = nullAllocation + projectResourceDetails.getProjectAllocation();
				if (nullAllocation == 100) {
					break;
				}

			}

		}
		if (!allocatedProjectResource.isEmpty()) {
			startDate = allocatedProjectResource.get(0).getGeneratedDates();
			previousProjectAllocation = allocatedProjectResource.get(0).getProjectAllocation();
			for (int i = 0; i < allocatedProjectResource.size(); i++) {
				currentProjectAllocation = allocatedProjectResource.get(i).getProjectAllocation();

				if (i == allocatedProjectResource.size() - 1) {
					if (nullAllocation > 0) {
						if (requestedResource.getEndDate() == null) {

							if (allocatedProjectResource.size() != 1) {

								if (previousProjectAllocation != currentProjectAllocation) {
									remainingProjectAllocation = availableResourceAllocation
											.remainingProjectAllocation(previousProjectAllocation);
									availableResourceAllocation.availableResourceAllocation(startDate,
											remainingProjectAllocation, endDate, resourceList, resultMapList);
									startDate = availableResourceAllocation.getNextDate(endDate);
								} else {
									endDate = allocatedProjectResource.get(i).getGeneratedDates();
									remainingProjectAllocation = availableResourceAllocation
											.remainingProjectAllocation(previousProjectAllocation);
									availableResourceAllocation.availableResourceAllocation(startDate,
											remainingProjectAllocation, endDate, resourceList, resultMapList);
									startDate = availableResourceAllocation.getNextDate(endDate);
								}
							} else {
								remainingProjectAllocation = availableResourceAllocation
										.remainingProjectAllocation(previousProjectAllocation);
								availableResourceAllocation.availableResourceAllocation(startDate,
										remainingProjectAllocation, endDate, resourceList, resultMapList);
								if (endDate == null) {
									break;
								} else {
									startDate = endDate;
								}
							}
							endDate = null;
							remainingProjectAllocation = availableResourceAllocation
									.remainingProjectAllocation(nullAllocation);
							availableResourceAllocation.availableResourceAllocation(startDate,
									remainingProjectAllocation, endDate, resourceList, resultMapList);
						} else {
							if (previousProjectAllocation != currentProjectAllocation) {
								endDate = allocatedProjectResource.get(i - 1).getGeneratedDates();
								remainingProjectAllocation = availableResourceAllocation
										.remainingProjectAllocation(previousProjectAllocation);
								availableResourceAllocation.availableResourceAllocation(startDate,
										remainingProjectAllocation, endDate, resourceList, resultMapList);
								startDate = allocatedProjectResource.get(i).getGeneratedDates();
								endDate = startDate;
								remainingProjectAllocation = availableResourceAllocation
										.remainingProjectAllocation(currentProjectAllocation);
								availableResourceAllocation.availableResourceAllocation(startDate,
										remainingProjectAllocation, endDate, resourceList, resultMapList);
							} else {
								endDate = allocatedProjectResource.get(i).getGeneratedDates();
								remainingProjectAllocation = availableResourceAllocation
										.remainingProjectAllocation(currentProjectAllocation);
								availableResourceAllocation.availableResourceAllocation(startDate,
										remainingProjectAllocation, endDate, resourceList, resultMapList);
							}
						}
					} else {
						endDate = allocatedProjectResource.get(i).getGeneratedDates();
						remainingProjectAllocation = availableResourceAllocation
								.remainingProjectAllocation(previousProjectAllocation);
						availableResourceAllocation.availableResourceAllocation(startDate, remainingProjectAllocation,
								endDate, resourceList, resultMapList);
						startDate = availableResourceAllocation.getEndDateAsStartDate(allocatedProjectResource.get(i));
					}
				} else if (previousProjectAllocation != currentProjectAllocation) {
					endDate = allocatedProjectResource.get(i - 1).getGeneratedDates();
					remainingProjectAllocation = availableResourceAllocation
							.remainingProjectAllocation(previousProjectAllocation);

					availableResourceAllocation.availableResourceAllocation(startDate, remainingProjectAllocation,
							endDate, resourceList, resultMapList);
					startDate = availableResourceAllocation.getEndDateAsStartDate(allocatedProjectResource.get(i));
					previousProjectAllocation = allocatedProjectResource.get(i).getProjectAllocation();

				} else {
					endDate = allocatedProjectResource.get(i).getGeneratedDates();
				}

			}

			if (requestedResource.getEndDate() == null && nullAllocation == 0) {
				startDate = availableResourceAllocation.getNextDate(endDate);
				endDate = null;
				remainingProjectAllocation = availableResourceAllocation.remainingProjectAllocation(nullAllocation);
				availableResourceAllocation.availableResourceAllocation(startDate, remainingProjectAllocation, endDate,
						resourceList, resultMapList);
			}
		} else {
			availableResourceAllocation.availableResourceAllocation(requestedResource.getStartDate(), 100, endDate,
					resourceList, resultMapList);
		}

		resultMapList.setProjectResourcesDetails(projectResourceDetailsList);
		resultMapList.setAllocatedProjectResources(allocatedProjectResource);
		resultMapList.setStatus(Status.CONFIRM_DATA);
		return resultMapList;
	}
    
}
