package com.cognine.service;

import java.util.List;

import com.cognine.model.Colors;
import com.cognine.model.PbiTimeLineData;
import com.cognine.model.PbiTimeline;
import com.cognine.model.ProjectResource;
import com.cognine.model.RemoveResource;
import com.cognine.model.ResourceHours;
import com.cognine.model.TimelineNotes;
import com.cognine.utils.Status;

public interface TimelineService {

    public Colors getColors();

    public Status addTimeline(PbiTimeline pbiTimeline);

    public PbiTimeLineData getAllPbiTimelineData(int sprintId, Integer projectId);

    public List<ProjectResource> getBillableResourcesByProjectId(int projectId);

    public List<ResourceHours> getResourceSummaryData(int sprintId, int projectId);

    public Status deleteResourceHours(RemoveResource deleteResource);

    public Status saveTimelineNotes(TimelineNotes timelineNotes);

    public int isResourceWithPBIExistInTimeline(int pbiId, int resourceId);

}
