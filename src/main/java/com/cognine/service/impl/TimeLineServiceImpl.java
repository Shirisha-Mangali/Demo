package com.cognine.service.impl;

import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cognine.dao.TimelineDao;
import com.cognine.model.Colors;
import com.cognine.model.HolidaysData;
import com.cognine.model.Indicators;
import com.cognine.model.PbiDays;
import com.cognine.model.PbiResource;
import com.cognine.model.PbiTimeLineData;
import com.cognine.model.PbiTimeline;
import com.cognine.model.ProjectResource;
import com.cognine.model.ProjectResourceAllocation;
import com.cognine.model.RemoveResource;
import com.cognine.model.ResourceHours;
import com.cognine.model.TimelineExcelData;
import com.cognine.model.TimelineNotes;
import com.cognine.model.TimelineStatus;
import com.cognine.service.TimelineService;
import com.cognine.utils.AppConstants;
import com.cognine.utils.ExcelHelper;
import com.cognine.utils.Status;

@Service
public class TimelineServiceImpl implements TimelineService {
	@Autowired
	private TimelineDao timelineDao;

	@Autowired
	private ExcelHelper excelHepler;

	/*
	 * This is the method for getting the colors and indicators from database
	 */
	@Override
	public Colors getColors() {
		Colors colorData = new Colors();
		List<TimelineStatus> timelineStatus;
		List<Indicators> indicators;
		timelineStatus = timelineDao.getTimelineStatus();
		indicators = timelineDao.getIndicators();
		colorData.setTimelineStatusList(timelineStatus);
		colorData.setIndicatorsList(indicators);
		return colorData;
	}

	/*
	 * This is the method for adding and updating the hours in timeline
	 */
	@Override
	@Transactional
	public Status addTimeline(PbiTimeline pbiTimeline) {
		Status status = Status.FAILED;
		if (pbiTimeline != null) {
			if (pbiTimeline.getPbiId() != 0) {

				if (timelineDao.UpdatePbi(pbiTimeline) >= 1) {
					status = Status.SUCCESS;

					for (PbiResource pbiResourcedata : pbiTimeline.getResources()) {
						for (PbiDays day : pbiResourcedata.getDays()) {
							int returnValue = timelineDao.addPbiTimeline(pbiTimeline.getPbiId(),
									pbiResourcedata.getResourceId(), day);
							if (returnValue >= 1) {
								status = Status.SUCCESS;
							} else if (timelineDao.updatePbiTimeline(pbiTimeline.getPbiId(),
									pbiResourcedata.getResourceId(), day) >= 1) {
								status = Status.SUCCESS;
							}
						}

						if (pbiResourcedata.getUpdatedDays().size() > 0) {
							for (PbiDays day : pbiResourcedata.getUpdatedDays()) {
								if (timelineDao.updatePbiTimeline(pbiTimeline.getPbiId(),
										pbiResourcedata.getResourceId(), day) >= 1) {
									status = Status.SUCCESS;
								}
							}
						}

					}
				}
			} else {
				status = Status.REQUIRED_VALUES_SHOULD_NOT_BE_NULL;
			}
		}
		return status;
	}

	/*
	 * This is the method for adding and updating the hours in timeline
	 */
	// @Override
	// public PbiTimeLineData getAllPbiTimelineData(int sprintId, Integer projectId) {
	// 	Status status = Status.FAILED;
	// 	Map<Integer, PbiTimeline> result = new HashMap<>();
	// 	PbiTimeLineData resultList = new PbiTimeLineData();
	// 	List<PbiTimeline> pbiTimelinedata = timelineDao.getAllPbiTimelineData(sprintId);
	// 	List<HolidaysData> holidaysList = timelineDao.getHolidaysList(sprintId);
	// 	if (projectId != null) {
	// 		TimelineExcelData data = timelineDao.getLastInsertedTimelineDataByProjectId(projectId);
	// 		if (data != null) {
	// 			resultList.setLastTimelineGeneratedAt(data.getTimelineDate());
	// 			if (excelHepler.isToday(data.getTimelineDate())) {
	// 				resultList.setIsTimelineFinalized(true);
	// 			} else {
	// 				resultList.setIsTimelineFinalized(false);
	// 			}
	// 		}
	// 	}

	// 	if (!(pbiTimelinedata.isEmpty()) || !(holidaysList.isEmpty())) {
	// 		resultList.setSprintId(sprintId);
	// 		Map<String, PbiResource> resourceResult = new HashMap<String, PbiResource>();
	// 		for (PbiTimeline pbiTimeline : pbiTimelinedata) {
	// 			if (result.get(pbiTimeline.getPbiId()) == null) {
	// 				PbiTimeline timelineData = new PbiTimeline();
	// 				result.put(pbiTimeline.getPbiId(), timelineData);
	// 				result.get(pbiTimeline.getPbiId()).setPbiId(pbiTimeline.getPbiId());
	// 				result.get(pbiTimeline.getPbiId()).setPbiType(pbiTimeline.getPbiType());
	// 				result.get(pbiTimeline.getPbiId()).setDescription(pbiTimeline.getDescription());
	// 				result.get(pbiTimeline.getPbiId()).setPbiTitle(pbiTimeline.getPbiTitle());
	// 				result.get(pbiTimeline.getPbiId()).setStatus(pbiTimeline.getStatus());
	// 				result.get(pbiTimeline.getPbiId()).setStatusName(pbiTimeline.getStatusName());
	// 				result.get(pbiTimeline.getPbiId()).setStoryPoints(pbiTimeline.getStoryPoints());
	// 				result.get(pbiTimeline.getPbiId()).setExpectedHours(pbiTimeline.getExpectedHours());
	// 				result.get(pbiTimeline.getPbiId()).setActualHours(pbiTimeline.getActualHours());
	// 				result.get(pbiTimeline.getPbiId()).setGivenPbiId(pbiTimeline.getGivenPbiId());
	// 				result.get(pbiTimeline.getPbiId()).setPriority(pbiTimeline.getPriority());

	// 				result.get(pbiTimeline.getPbiId())
	// 						.setTimelineNotes(timelineDao.getTimelineNotes(pbiTimeline.getPbiId()));

	// 				if (pbiTimeline.getPbiResource() != null) {

	// 					PbiResource resources = new PbiResource();
	// 					resources.setResourceId(pbiTimeline.getPbiResource().getResourceId());
	// 					resources.setResourceName(pbiTimeline.getPbiResource().getResourceName());
	// 					resources.setResourceRole(pbiTimeline.getPbiResource().getResourceRole());
	// 					resources.setIsBillable(pbiTimeline.getPbiResource().getIsBillable());
	// 					pbiTimeline.getResources().add(resources);

	// 					List<PbiResource> pbiResources = pbiTimeline.getResources();
	// 					if (!pbiResources.isEmpty()) {
	// 						for (PbiResource resource : pbiResources) {
	// 							if (resourceResult
	// 									.get(resource.getResourceId() + "-" + pbiTimeline.getPbiId()) == null) {
	// 								if (resource.getResourceId() != 0) {
	// 									PbiResource resourceData = new PbiResource();
	// 									resourceResult.put(resource.getResourceId() + "-" + pbiTimeline.getPbiId(),
	// 											resourceData);

	// 									resourceResult.get(resource.getResourceId() + "-" + pbiTimeline.getPbiId())
	// 											.setResourceId(resource.getResourceId());
	// 									resourceResult.get(resource.getResourceId() + "-" + pbiTimeline.getPbiId())
	// 											.setResourceName(resource.getResourceName());
	// 									resourceResult.get(resource.getResourceId() + "-" + pbiTimeline.getPbiId())
	// 											.setResourceRole(resource.getResourceRole());
	// 									resourceResult.get(resource.getResourceId() + "-" + pbiTimeline.getPbiId())
	// 											.setIsBillable(resource.getIsBillable());

	// 									PbiDays pbiDay = new PbiDays();
	// 									pbiDay.setDate(pbiTimeline.getDate());
	// 									pbiDay.setHours(pbiTimeline.getHours());
	// 									pbiDay.setId(pbiTimeline.getDayId());
	// 									resourceResult.get(resource.getResourceId() + "-" + pbiTimeline.getPbiId())
	// 											.getDays().add(pbiDay);

	// 									resourceResult.get(resource.getResourceId() + "-" + pbiTimeline.getPbiId())
	// 											.setDays(resourceResult
	// 													.get(resource.getResourceId() + "-" + pbiTimeline.getPbiId())
	// 													.getDays().stream()
	// 													.sorted(Comparator.comparing(PbiDays::getDate))
	// 													.collect(Collectors.toList()));

	// 									result.get(pbiTimeline.getPbiId()).getResources().add(resourceData);
	// 								}

	// 							} else {
	// 								PbiDays pbiDay = new PbiDays();
	// 								pbiDay.setDate(pbiTimeline.getDate());
	// 								pbiDay.setHours(pbiTimeline.getHours());
	// 								pbiDay.setId(pbiTimeline.getDayId());

	// 								resourceResult.get(resource.getResourceId() + "-" + pbiTimeline.getPbiId())
	// 										.getDays().add(pbiDay);

	// 								resourceResult.get(resource.getResourceId() + "-" + pbiTimeline.getPbiId())
	// 										.setDays(resourceResult
	// 												.get(resource.getResourceId() + "-" + pbiTimeline.getPbiId())
	// 												.getDays().stream().sorted(Comparator.comparing(PbiDays::getDate))
	// 												.collect(Collectors.toList()));
	// 							}

	// 						}

	// 					}
	// 				}
	// 			} else {
	// 				if (pbiTimeline.getPbiResource() != null) {
	// 					PbiResource resources = new PbiResource();

	// 					resources.setResourceId(pbiTimeline.getPbiResource().getResourceId());
	// 					resources.setResourceName(pbiTimeline.getPbiResource().getResourceName());
	// 					resources.setResourceRole(pbiTimeline.getPbiResource().getResourceRole());
	// 					resources.setIsBillable(pbiTimeline.getPbiResource().getIsBillable());

	// 					pbiTimeline.getResources().add(resources);
	// 					List<PbiResource> pbiResources = pbiTimeline.getResources();

	// 					for (PbiResource resource : pbiResources) {
	// 						if (resourceResult.get(resource.getResourceId() + "-" + pbiTimeline.getPbiId()) == null) {

	// 							if (resource.getResourceId() != 0) {
	// 								PbiResource resourceData = new PbiResource();
	// 								resourceResult.put(resource.getResourceId() + "-" + pbiTimeline.getPbiId(),
	// 										resourceData);

	// 								resourceResult.get(resource.getResourceId() + "-" + pbiTimeline.getPbiId())
	// 										.setResourceId(resource.getResourceId());
	// 								resourceResult.get(resource.getResourceId() + "-" + pbiTimeline.getPbiId())
	// 										.setResourceName(resource.getResourceName());
	// 								resourceResult.get(resource.getResourceId() + "-" + pbiTimeline.getPbiId())
	// 										.setResourceRole(resource.getResourceRole());
	// 								resourceResult.get(resource.getResourceId() + "-" + pbiTimeline.getPbiId())
	// 										.setIsBillable(resource.getIsBillable());

	// 								PbiDays pbiDay = new PbiDays();
	// 								pbiDay.setDate(pbiTimeline.getDate());
	// 								pbiDay.setHours(pbiTimeline.getHours());
	// 								pbiDay.setId(pbiTimeline.getDayId());

	// 								resourceResult.get(resource.getResourceId() + "-" + pbiTimeline.getPbiId())
	// 										.getDays().add(pbiDay);

	// 								resourceResult.get(resource.getResourceId() + "-" + pbiTimeline.getPbiId())
	// 										.setDays(resourceResult
	// 												.get(resource.getResourceId() + "-" + pbiTimeline.getPbiId())
	// 												.getDays().stream().sorted(Comparator.comparing(PbiDays::getDate))
	// 												.collect(Collectors.toList()));

	// 								result.get(pbiTimeline.getPbiId()).getResources().add(resourceData);
	// 							}

	// 						} else {

	// 							PbiDays pbiDay = new PbiDays();
	// 							pbiDay.setDate(pbiTimeline.getDate());
	// 							pbiDay.setHours(pbiTimeline.getHours());
	// 							pbiDay.setId(pbiTimeline.getDayId());
	// 							resourceResult.get(resource.getResourceId() + "-" + pbiTimeline.getPbiId()).getDays()
	// 									.add(pbiDay);

	// 							resourceResult.get(resource.getResourceId() + "-" + pbiTimeline.getPbiId())
	// 									.setDays(resourceResult
	// 											.get(resource.getResourceId() + "-" + pbiTimeline.getPbiId()).getDays()
	// 											.stream().sorted(Comparator.comparing(PbiDays::getDate))
	// 											.collect(Collectors.toList()));

	// 						}
	// 					}

	// 				}
	// 			}
	// 		}

	// 		List<PbiTimeline> pbi = new ArrayList<PbiTimeline>();

	// 		for (int key : result.keySet()) {

	// 			pbi.add(result.get(key));
	// 			resultList.setPbiTimeLine(pbi);
	// 		}
	// 		resultList.setPbiTimeLine(resultList.getPbiTimeLine().stream()
	// 				.sorted(Comparator.comparingInt(PbiTimeline::getPriority)).collect(Collectors.toList()));

	// 		for (PbiTimeline pbiTimeline : resultList.getPbiTimeLine()) {
	// 			pbiTimeline.setPbiResource(null);

	// 		}
	// 		status = Status.SUCCESS;
	// 	} else {
	// 		status = Status.NO_RECORDS_FOUND;
	// 	}
	// 	resultList.setHolidaysData(timelineDao.getHolidaysList());
	// 	resultList.setDateWiseResourceTotalList(timelineDao.getDateWiseResourceTotal(sprintId));
	// 	resultList.setStatus(status);
	// 	if (resultList.getPbiTimeLine() != null)
	// 		sortTimelineDataByRoleLevel(resultList);
	// 	return resultList;

	// }


// 	@Override
// public PbiTimeLineData getAllPbiTimelineData(int sprintId, Integer projectId) {
//     Status status = Status.FAILED;
//     Map<Integer, PbiTimeline> result = new HashMap<>();
//     PbiTimeLineData resultList = new PbiTimeLineData();
//     List<PbiTimeline> pbiTimelinedata = timelineDao.getAllPbiTimelineData(sprintId);
//     List<HolidaysData> holidaysList = timelineDao.getHolidaysList(sprintId);
//     if (projectId != null) {
//         TimelineExcelData data = timelineDao.getLastInsertedTimelineDataByProjectId(projectId);
//         if (data != null) {
//             resultList.setLastTimelineGeneratedAt(data.getTimelineDate());
//             if (excelHepler.isToday(data.getTimelineDate())) {
//                 resultList.setIsTimelineFinalized(true);
//             } else {
//                 resultList.setIsTimelineFinalized(false);
//             }
//         }
//     }

//     if (!(pbiTimelinedata.isEmpty()) || !(holidaysList.isEmpty())) {
//         resultList.setSprintId(sprintId);
//         Map<String, PbiResource> resourceResult = new HashMap<String, PbiResource>();
//         for (PbiTimeline pbiTimeline : pbiTimelinedata) {
//             if (result.get(pbiTimeline.getPbiId()) == null) {
//                 PbiTimeline timelineData = new PbiTimeline();
//                 result.put(pbiTimeline.getPbiId(), timelineData);
//                 result.get(pbiTimeline.getPbiId()).setPbiId(pbiTimeline.getPbiId());
//                 result.get(pbiTimeline.getPbiId()).setPbiType(pbiTimeline.getPbiType());
//                 result.get(pbiTimeline.getPbiId()).setDescription(pbiTimeline.getDescription());
//                 result.get(pbiTimeline.getPbiId()).setPbiTitle(pbiTimeline.getPbiTitle());
//                 result.get(pbiTimeline.getPbiId()).setStatus(pbiTimeline.getStatus());
//                 result.get(pbiTimeline.getPbiId()).setStatusName(pbiTimeline.getStatusName());
//                 result.get(pbiTimeline.getPbiId()).setStoryPoints(pbiTimeline.getStoryPoints());
//                 result.get(pbiTimeline.getPbiId()).setExpectedHours(pbiTimeline.getExpectedHours());
//                 result.get(pbiTimeline.getPbiId()).setActualHours(pbiTimeline.getActualHours());
//                 result.get(pbiTimeline.getPbiId()).setGivenPbiId(pbiTimeline.getGivenPbiId());
//                 result.get(pbiTimeline.getPbiId()).setPriority(pbiTimeline.getPriority());
//                 result.get(pbiTimeline.getPbiId())
//                         .setTimelineNotes(timelineDao.getTimelineNotes(pbiTimeline.getPbiId()));

//                 if (pbiTimeline.getPbiResource() != null) {

//                     // Block 1 — build resources object
//                     PbiResource resources = new PbiResource();
//                     resources.setResourceId(pbiTimeline.getPbiResource().getResourceId());
//                     resources.setResourceName(pbiTimeline.getPbiResource().getResourceName());
//                     resources.setResourceRole(pbiTimeline.getPbiResource().getResourceRole());
//                     resources.setIsBillable(pbiTimeline.getPbiResource().getIsBillable());
//                     resources.setStartDate(pbiTimeline.getPbiResource().getStartDate()); // ← ADD
//                     resources.setEndDate(pbiTimeline.getPbiResource().getEndDate());     // ← ADD
//                     pbiTimeline.getResources().add(resources);

//                     List<PbiResource> pbiResources = pbiTimeline.getResources();
//                     if (!pbiResources.isEmpty()) {
//                         for (PbiResource resource : pbiResources) {
//                             if (resourceResult
//                                     .get(resource.getResourceId() + "-" + pbiTimeline.getPbiId()) == null) {
//                                 if (resource.getResourceId() != 0) {
//                                     PbiResource resourceData = new PbiResource();
//                                     resourceResult.put(resource.getResourceId() + "-" + pbiTimeline.getPbiId(),
//                                             resourceData);

//                                     // Block 2 — new PBI, new resourceData
//                                     resourceResult.get(resource.getResourceId() + "-" + pbiTimeline.getPbiId())
//                                             .setResourceId(resource.getResourceId());
//                                     resourceResult.get(resource.getResourceId() + "-" + pbiTimeline.getPbiId())
//                                             .setResourceName(resource.getResourceName());
//                                     resourceResult.get(resource.getResourceId() + "-" + pbiTimeline.getPbiId())
//                                             .setResourceRole(resource.getResourceRole());
//                                     resourceResult.get(resource.getResourceId() + "-" + pbiTimeline.getPbiId())
//                                             .setIsBillable(resource.getIsBillable());
//                                     resourceResult.get(resource.getResourceId() + "-" + pbiTimeline.getPbiId())
//                                             .setStartDate(resource.getStartDate()); // ← ADD
//                                     resourceResult.get(resource.getResourceId() + "-" + pbiTimeline.getPbiId())
//                                             .setEndDate(resource.getEndDate());     // ← ADD

//                                     PbiDays pbiDay = new PbiDays();
//                                     pbiDay.setDate(pbiTimeline.getDate());
//                                     pbiDay.setHours(pbiTimeline.getHours());
//                                     pbiDay.setId(pbiTimeline.getDayId());
//                                     resourceResult.get(resource.getResourceId() + "-" + pbiTimeline.getPbiId())
//                                             .getDays().add(pbiDay);

//                                     resourceResult.get(resource.getResourceId() + "-" + pbiTimeline.getPbiId())
//                                             .setDays(resourceResult
//                                                     .get(resource.getResourceId() + "-" + pbiTimeline.getPbiId())
//                                                     .getDays().stream()
//                                                     .sorted(Comparator.comparing(PbiDays::getDate))
//                                                     .collect(Collectors.toList()));

//                                     result.get(pbiTimeline.getPbiId()).getResources().add(resourceData);
//                                 }
//                             } else {
//                                 PbiDays pbiDay = new PbiDays();
//                                 pbiDay.setDate(pbiTimeline.getDate());
//                                 pbiDay.setHours(pbiTimeline.getHours());
//                                 pbiDay.setId(pbiTimeline.getDayId());

//                                 resourceResult.get(resource.getResourceId() + "-" + pbiTimeline.getPbiId())
//                                         .getDays().add(pbiDay);

//                                 resourceResult.get(resource.getResourceId() + "-" + pbiTimeline.getPbiId())
//                                         .setDays(resourceResult
//                                                 .get(resource.getResourceId() + "-" + pbiTimeline.getPbiId())
//                                                 .getDays().stream().sorted(Comparator.comparing(PbiDays::getDate))
//                                                 .collect(Collectors.toList()));
//                             }
//                         }
//                     }
//                 }
//             } else {
//                 if (pbiTimeline.getPbiResource() != null) {

//                     // Block 3 — existing PBI, build resources object
//                     PbiResource resources = new PbiResource();
//                     resources.setResourceId(pbiTimeline.getPbiResource().getResourceId());
//                     resources.setResourceName(pbiTimeline.getPbiResource().getResourceName());
//                     resources.setResourceRole(pbiTimeline.getPbiResource().getResourceRole());
//                     resources.setIsBillable(pbiTimeline.getPbiResource().getIsBillable());
//                     resources.setStartDate(pbiTimeline.getPbiResource().getStartDate()); // ← ADD
//                     resources.setEndDate(pbiTimeline.getPbiResource().getEndDate());     // ← ADD

//                     pbiTimeline.getResources().add(resources);
//                     List<PbiResource> pbiResources = pbiTimeline.getResources();

//                     for (PbiResource resource : pbiResources) {
//                         if (resourceResult.get(resource.getResourceId() + "-" + pbiTimeline.getPbiId()) == null) {
//                             if (resource.getResourceId() != 0) {
//                                 PbiResource resourceData = new PbiResource();
//                                 resourceResult.put(resource.getResourceId() + "-" + pbiTimeline.getPbiId(),
//                                         resourceData);

//                                 // Block 4 — existing PBI, new resourceData
//                                 resourceResult.get(resource.getResourceId() + "-" + pbiTimeline.getPbiId())
//                                         .setResourceId(resource.getResourceId());
//                                 resourceResult.get(resource.getResourceId() + "-" + pbiTimeline.getPbiId())
//                                         .setResourceName(resource.getResourceName());
//                                 resourceResult.get(resource.getResourceId() + "-" + pbiTimeline.getPbiId())
//                                         .setResourceRole(resource.getResourceRole());
//                                 resourceResult.get(resource.getResourceId() + "-" + pbiTimeline.getPbiId())
//                                         .setIsBillable(resource.getIsBillable());
//                                 resourceResult.get(resource.getResourceId() + "-" + pbiTimeline.getPbiId())
//                                         .setStartDate(resource.getStartDate()); // ← ADD
//                                 resourceResult.get(resource.getResourceId() + "-" + pbiTimeline.getPbiId())
//                                         .setEndDate(resource.getEndDate());     // ← ADD

//                                 PbiDays pbiDay = new PbiDays();
//                                 pbiDay.setDate(pbiTimeline.getDate());
//                                 pbiDay.setHours(pbiTimeline.getHours());
//                                 pbiDay.setId(pbiTimeline.getDayId());

//                                 resourceResult.get(resource.getResourceId() + "-" + pbiTimeline.getPbiId())
//                                         .getDays().add(pbiDay);

//                                 resourceResult.get(resource.getResourceId() + "-" + pbiTimeline.getPbiId())
//                                         .setDays(resourceResult
//                                                 .get(resource.getResourceId() + "-" + pbiTimeline.getPbiId())
//                                                 .getDays().stream().sorted(Comparator.comparing(PbiDays::getDate))
//                                                 .collect(Collectors.toList()));

//                                 result.get(pbiTimeline.getPbiId()).getResources().add(resourceData);
//                             }
//                         } else {
//                             PbiDays pbiDay = new PbiDays();
//                             pbiDay.setDate(pbiTimeline.getDate());
//                             pbiDay.setHours(pbiTimeline.getHours());
//                             pbiDay.setId(pbiTimeline.getDayId());
//                             resourceResult.get(resource.getResourceId() + "-" + pbiTimeline.getPbiId()).getDays()
//                                     .add(pbiDay);

//                             resourceResult.get(resource.getResourceId() + "-" + pbiTimeline.getPbiId())
//                                     .setDays(resourceResult
//                                             .get(resource.getResourceId() + "-" + pbiTimeline.getPbiId()).getDays()
//                                             .stream().sorted(Comparator.comparing(PbiDays::getDate))
//                                             .collect(Collectors.toList()));
//                         }
//                     }
//                 }
//             }
//         }

//         List<PbiTimeline> pbi = new ArrayList<PbiTimeline>();
//         for (int key : result.keySet()) {
//             pbi.add(result.get(key));
//             resultList.setPbiTimeLine(pbi);
//         }
//         resultList.setPbiTimeLine(resultList.getPbiTimeLine().stream()
//                 .sorted(Comparator.comparingInt(PbiTimeline::getPriority)).collect(Collectors.toList()));

//         for (PbiTimeline pbiTimeline : resultList.getPbiTimeLine()) {
//             pbiTimeline.setPbiResource(null);
//         }
//         status = Status.SUCCESS;
//     } else {
//         status = Status.NO_RECORDS_FOUND;
//     }
//     resultList.setHolidaysData(timelineDao.getHolidaysList());
//     resultList.setDateWiseResourceTotalList(timelineDao.getDateWiseResourceTotal(sprintId));
//     resultList.setStatus(status);
//     if (resultList.getPbiTimeLine() != null)
//         sortTimelineDataByRoleLevel(resultList);
//     return resultList;
// }

    @Override
    public PbiTimeLineData getAllPbiTimeLineData(int sprintId, Integer projectId){
        Status status=Status.FAILED;
        Map<Integer,PbiTimeline> result=new HashMap<>();
        PbiTimeLineData resultList=new PbiTimeLineData();
        List<PbiTimeline> pbiTimeLinedata=timelineDao.getAllPbiTimelineData(sprintId);
        List<HolidaysData> holidaysList=timelineDao.getHolidaysList(sprintId);
        if(projectId!=null){
            TimelineExcelData data=timelineDao.getLastInsertedTimelineDataByProjectId(sprintId);
            if(data!=null){
                resultList.setLastTimeGeneratedAt(data.getTimeLineDate());
                if(excelHepler.isToday(data.getTimelineDate())){
                    resultList.setIsTimelineFinalized(true);
                }else{
                    resultList.setTimelineFinalized(false);
                }
            }
        }
    }

 
 

	/*
	 * This is the method for get Billable Resources based on ProjectId
	 */
	@Override
	public List<ProjectResource> getBillableResourcesByProjectId(int projectId) {
		return timelineDao.getBillableResourcesByProjectId(projectId);
	}

	/*
	 * This is the method for get Resources Hours Summary data based on sprintId
	 */
	@Override
	public List<ResourceHours> getResourceSummaryData(int sprintId, int projectId) {
		List<ResourceHours> resourceHoursList = new ArrayList<>();
		ResourceHours totalHoursRow = new ResourceHours();

		resourceHoursList = timelineDao.getResourceSummaryData(sprintId, projectId);
		if (!resourceHoursList.isEmpty())
			resourceHoursList = sortProjectSummeryByRoleLevel(resourceHoursList);

		Map<Date, Double> dateWiseTotal = resourceHoursList.stream()
				.flatMap(resource -> resource.totalHoursList.stream()).collect(Collectors.groupingBy(PbiDays::getDate,
						Collectors.summingDouble(pbiDays -> parseHours(pbiDays.getHours()))));

		List<PbiDays> dateWiseTotalList = dateWiseTotal.entrySet().stream().map(entry -> {
			PbiDays totalDay = new PbiDays();
			totalDay.setDate(entry.getKey());
			String formattedHours;
			if (entry.getValue() % 1 == 0) {
				formattedHours = new DecimalFormat("#").format(entry.getValue());
			} else {
				formattedHours = new DecimalFormat("#.##").format(entry.getValue());

			}
			totalDay.setHours(formattedHours);
			return totalDay;
		}).sorted(Comparator.comparing(PbiDays::getDate)).collect(Collectors.toList());

		totalHoursRow.setTotalHoursList(dateWiseTotalList);
		if (resourceHoursList.size() > 0) {
			resourceHoursList.add(totalHoursRow);
		}
		return resourceHoursList;
	}

	/*
	 * This is the method for delete the particular resource from timeline
	 */
	@Override
	public Status deleteResourceHours(RemoveResource deleteResource) {
		Status status = Status.FAILED;
		if (deleteResource.getPbiId() != 0 && deleteResource.getResourceId() != 0) {
			int returnValue = timelineDao.deleteResourceHours(deleteResource);
			if (returnValue > 0) {
				status = Status.SUCCESS;
			}
		} else {
			status = Status.REQUIRED_VALUES_SHOULD_NOT_BE_NULL;
		}
		return status;
	}

	public double parseHours(String hoursString) {
		try {
			if (!hoursString.equals(AppConstants.LEAVE_VALUE)) {
				double parsedValue = Double.parseDouble(hoursString);
				if (parsedValue % 1 == 0) {
					return (int) parsedValue;
				} else {
					return parsedValue;
				}
			}
		} catch (NumberFormatException e) {

		}
		return 0;
	}

	/*
	 * This is the method save the timeline notes for respected pbi
	 */
	@Override
	public Status saveTimelineNotes(TimelineNotes timelineNotes) {
		Status status = Status.FAILED;
		if (timelineNotes.getNotes() != null) {
			int returnValue = 0;
			if (timelineDao.isPbiHasTimelineNotes(timelineNotes) > 0) {
				returnValue = timelineDao.updateTimelineNotes(timelineNotes);
			} else {
				returnValue = timelineDao.saveTimelineNotes(timelineNotes);
			}
			if (returnValue > 0)
				status = Status.SUCCESS;
		} else {
			status = Status.REQUIRED_VALUES_SHOULD_NOT_BE_NULL;
		}
		return status;
	}

	public PbiTimeLineData sortTimelineDataByRoleLevel(PbiTimeLineData timelineData) {

		for (PbiTimeline pbi : timelineData.getPbiTimeLine()) {
			List<PbiResource> resources = pbi.getResources();
			// Collections.sort(resources,
			// Comparator.comparing(PbiResource::getResourceName));
			Comparator<PbiResource> resourceComparator = Comparator.comparing((PbiResource r) -> {
				// Order roles based on priority
				return ExcelHelper.getRolePriority(r.getResourceRole());
			}).thenComparing(PbiResource::getResourceName); // Then c
			Collections.sort(resources, resourceComparator);
			pbi.setResources(resources);
		}

		return timelineData;
	}

	public List<ResourceHours> sortProjectSummeryByRoleLevel(List<ResourceHours> projectSummeryResources) {
		Comparator<ResourceHours> resourceComparator = Comparator.comparing((ResourceHours r) -> {
			// Order roles based on priority
			return ExcelHelper.getRolePriority(r.getRoleName());
		}).thenComparing(ResourceHours::getEmployeeName);

		Collections.sort(projectSummeryResources, resourceComparator);

		return projectSummeryResources;
	}

	@Override
	public int isResourceWithPBIExistInTimeline(int pbiId, int resourceId) {
		return timelineDao.isResourceWithPBIExistInTimeline(pbiId, resourceId);
	}

}
