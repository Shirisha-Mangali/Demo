package com.cognine.dao;

import java.sql.Date;
import java.util.List;


import com.cognine.model.SprintTimelineMapping;
import com.cognine.model.TimelineAttachment;
import com.cognine.model.TimelineExcelData;

public interface ExportExcelDao {

	List<String> getSprintDateSeries(int sprintId);
	
	public int addTimelineSprintMappingData(SprintTimelineMapping sprintTimelineMapping);

	public int addsprintTimelineExcel(TimelineAttachment timelineAttachment);
	
	public TimelineExcelData getTimelineExcelSheet(int sprintId);
	
	public int updateTimelineExcel(TimelineAttachment timelineAttachment);
	
	public List<Date> generateSprintDatesWithYear(int sprintId);


}
