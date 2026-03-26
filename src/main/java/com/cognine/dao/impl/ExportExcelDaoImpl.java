package com.cognine.dao.impl;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cognine.dao.ExportExcelDao;
import com.cognine.mapper.ExportExcelMapper;
import com.cognine.model.SprintTimelineMapping;
import com.cognine.model.TimelineAttachment;
import com.cognine.model.TimelineExcelData;

@Repository
public class ExportExcelDaoImpl implements ExportExcelDao {
	@Autowired
	private ExportExcelMapper exportExcelMapper;

	@Override
	public List<String> getSprintDateSeries(int sprintId) {
		return exportExcelMapper.getSprintDateSeries(sprintId);
	}

	@Override
	public int addTimelineSprintMappingData(SprintTimelineMapping sprintTimelineMapping) {
		return exportExcelMapper.addTimelineSprintMappingData(sprintTimelineMapping);
	}

	@Override
	public int addsprintTimelineExcel(TimelineAttachment timelineAttachment) {
		return exportExcelMapper.addsprintTimelineExcel(timelineAttachment);
	}

	@Override
	public TimelineExcelData getTimelineExcelSheet(int sprintId ) {
		return exportExcelMapper.getTimelineExcelSheet(sprintId);
	}

	@Override
	public int updateTimelineExcel(TimelineAttachment timelineAttachment) {
		return exportExcelMapper.updateTimelineExcel(timelineAttachment);
	}

	@Override
	public List<Date> generateSprintDatesWithYear(int sprintId) {
		return exportExcelMapper.generateSprintDatesWithYear(sprintId);
	}

	
}
