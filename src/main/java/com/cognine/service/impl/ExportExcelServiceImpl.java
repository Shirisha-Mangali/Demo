package com.cognine.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognine.dao.ExportExcelDao;
import com.cognine.dao.TimelineDao;
import com.cognine.model.ExcelByteStream;
import com.cognine.model.HolidaysData;
import com.cognine.model.Indicators;
import com.cognine.model.PbiDays;
import com.cognine.model.PbiResource;
import com.cognine.model.PbiTimeLineData;
import com.cognine.model.PbiTimeline;
import com.cognine.model.ResourceHours;
import com.cognine.model.SprintTimelineMapping;
import com.cognine.model.TimelineAttachment;
import com.cognine.model.TimelineExcelData;
import com.cognine.model.TimelineStatus;
import com.cognine.service.ExportExcelService;
import com.cognine.service.TimelineService;
import com.cognine.utils.AppConstants;
import com.cognine.utils.ExcelColorUtils;
import com.cognine.utils.ExcelHelper;
import com.cognine.utils.Status;
import com.cognine.utils.TimelineConstantIndexes;

import java.sql.Date;

@Service
public class ExportExcelServiceImpl implements ExportExcelService {

	@Autowired
	private ExcelColorUtils excelColorUtils;

	@Autowired
	private ExportExcelDao exportExcelDao;

	@Autowired
	private TimelineService timelineService;

	@Autowired
	private TimelineDao timelineDao;

	@Autowired
	private ExcelHelper excelHepler;
	long totalResourcesCount = 0;

	/*
	 * This is the method export the timeline excel based on user request
	 */
	@Override
	public ExcelByteStream exportToExcel(PbiTimeLineData pbiTimeLineData) throws IOException {
		Status status = Status.FAILED;
		ExcelByteStream excelByteStream = new ExcelByteStream();
		byte[] excelBytes = null;
		int returnValue = 0;
		int mappingId = 0;
		if (pbiTimeLineData.getTimelineGenerateDate() != null) {
			TimelineExcelData data = exportExcelDao.getTimelineExcelSheet(pbiTimeLineData.getSprintId());
			SprintTimelineMapping sprintTimelineData = new SprintTimelineMapping();
			sprintTimelineData.setSprintId(pbiTimeLineData.getSprintId());
			sprintTimelineData
					.setTimelineDate(excelHepler.changeStringToDate(pbiTimeLineData.getTimelineGenerateDate()));
			sprintTimelineData.setIsTimelineFinalized(true);
			ZipSecureFile.setMinInflateRatio(0);

			if (data == null) {
				Workbook workbook = new XSSFWorkbook();
				Sheet sheet = workbook
						.createSheet(excelHepler.formatSheetDate(pbiTimeLineData.getTimelineGenerateDate()));
				excelBytes = generateSheet(pbiTimeLineData, workbook, sheet);
				mappingId = exportExcelDao.addTimelineSprintMappingData(sprintTimelineData);
				TimelineAttachment timelineAttachmentData = new TimelineAttachment();
				timelineAttachmentData.setSprintPresentDayId(mappingId);
				timelineAttachmentData.setTimelineAttachment(excelBytes);

				if (mappingId > 0) {
					returnValue = exportExcelDao.addsprintTimelineExcel(timelineAttachmentData);
				}
			} else {
				Workbook existingWorkbook = WorkbookFactory
						.create(new ByteArrayInputStream(data.getTimelineAttachment()));
				Sheet sheet = existingWorkbook.createSheet();

				if (!excelHepler.isToday(data.getTimelineDate())) {

					if (!pbiTimeLineData.getIsTimelineforYesterDay()) {
						sheet = existingWorkbook
								.createSheet(excelHepler.formatSheetDate(pbiTimeLineData.getTimelineGenerateDate()));
					} else {
						if (pbiTimeLineData.getTimelineGenerateDate()
								.equals(excelHepler.formatDateWithYear(data.getTimelineDate()))) {
							sheet = existingWorkbook
									.getSheet(excelHepler.formatSheetDate(pbiTimeLineData.getTimelineGenerateDate()));
							sheet = excelHepler.clearAndCreateNewSheet(existingWorkbook,sheet.getSheetName());
						} else {
							sheet = existingWorkbook.createSheet(
									excelHepler.formatSheetDate(pbiTimeLineData.getTimelineGenerateDate()));
						}

					}
				} else {
					sheet = existingWorkbook
							.getSheet(excelHepler.formatSheetDate(pbiTimeLineData.getTimelineGenerateDate()));
					sheet = excelHepler.clearAndCreateNewSheet(existingWorkbook,sheet.getSheetName());

				}
				excelBytes = generateSheet(pbiTimeLineData, existingWorkbook, sheet);

				TimelineAttachment timelineAttachmentData = new TimelineAttachment();
				if (!excelHepler.isToday(data.getTimelineDate())) {
					if (!pbiTimeLineData.getIsTimelineforYesterDay()) {
						mappingId = exportExcelDao.addTimelineSprintMappingData(sprintTimelineData);
						timelineAttachmentData.setSprintPresentDayId(mappingId);
						timelineAttachmentData.setTimelineAttachment(excelBytes);
						timelineAttachmentData.setId(data.getId());
						if (mappingId > 0) {
							returnValue = exportExcelDao.updateTimelineExcel(timelineAttachmentData);
						}
					} else if (pbiTimeLineData.getIsTimelineforYesterDay()) {
						if (pbiTimeLineData.getTimelineGenerateDate()
								.equals(excelHepler.formatDateWithYear(data.getTimelineDate()))) {
							timelineAttachmentData.setId(data.getId());
							timelineAttachmentData.setSprintPresentDayId(data.getSprintAttachmentMappingId());
							timelineAttachmentData.setTimelineAttachment(excelBytes);

							if (data.getSprintAttachmentMappingId() > 0) {
								returnValue = exportExcelDao.updateTimelineExcel(timelineAttachmentData);
							}
						} else {
							mappingId = exportExcelDao.addTimelineSprintMappingData(sprintTimelineData);
							timelineAttachmentData.setSprintPresentDayId(mappingId);
							timelineAttachmentData.setTimelineAttachment(excelBytes);
							timelineAttachmentData.setId(data.getId());
							if (mappingId > 0) {
								returnValue = exportExcelDao.updateTimelineExcel(timelineAttachmentData);
							}

						}
					}
				}

				else {
					timelineAttachmentData.setId(data.getId());
					timelineAttachmentData.setSprintPresentDayId(data.getSprintAttachmentMappingId());
					timelineAttachmentData.setTimelineAttachment(excelBytes);

					if (data.getSprintAttachmentMappingId() > 0) {
						returnValue = exportExcelDao.updateTimelineExcel(timelineAttachmentData);
					}
				}



			}
			TimelineExcelData updatedData = null;
			if (returnValue > 0) {
				updatedData = exportExcelDao.getTimelineExcelSheet(pbiTimeLineData.getSprintId());
			}
			String byteStream = Base64.getEncoder().encodeToString(updatedData.getTimelineAttachment());
			status = Status.SUCCESS;
			excelByteStream.setByteStream(byteStream);
		} else {
			status = Status.REQUIRED_VALUES_SHOULD_NOT_BE_NULL;
		}
		excelByteStream.setStatus(status);

		return excelByteStream;

	}

	/*
	 * This is the method generate the excel sheet with updated timeline data
	 */
	public byte[] generateSheet(PbiTimeLineData pbiTimeLineData, Workbook workbook, Sheet sheet) throws IOException {
		byte[] excelBytes = null;
		List<PbiTimeline> pbis = pbiTimeLineData.getPbiTimeLine();
		totalResourcesCount = pbis.stream().mapToLong(pbi -> {
			long resourceCount = pbi.getResources().isEmpty() ? 1 : pbi.getResources().size();
			return resourceCount;
		}).sum();

		List<ResourceHours> resourceList = timelineService.getResourceSummaryData(pbiTimeLineData.getSprintId(), pbiTimeLineData.getProjectId());
		List<HolidaysData> holidays = timelineDao.getHolidaysList();
		List<Date> sprintDates =  exportExcelDao.generateSprintDatesWithYear(pbiTimeLineData.getSprintId());

		// Create header row
		Row headerRow = sheet.createRow(0);
		headerRow.createCell(TimelineConstantIndexes.WORK_ITEM_TYPE_INDEX).setCellValue(AppConstants.WORK_ITEM_TYPE);
		headerRow.createCell(TimelineConstantIndexes.DESCRIPTION_INDEX).setCellValue(AppConstants.DESCRIPTION);
		headerRow.createCell(TimelineConstantIndexes.STORY_POINTS_INDEX).setCellValue(AppConstants.STORY_POINTS);
		headerRow.createCell(TimelineConstantIndexes.EXPECTED_HOURS_INDEX).setCellValue(AppConstants.EXPECTED_HOURS);
		headerRow.createCell(TimelineConstantIndexes.ACTUAL_HOURS_INDEX).setCellValue(AppConstants.ACTUAL_HOURS);
		headerRow.createCell(TimelineConstantIndexes.STATUS_INDEX).setCellValue(AppConstants.STATUS);
		headerRow.createCell(TimelineConstantIndexes.ASSIGNED_TO_INDEX).setCellValue(AppConstants.ASSIGNED_TO);

		// Create data rows
		int rowNum = 1;
		for (PbiTimeline pbi : pbis) {
			Row pbiRow = sheet.createRow(rowNum++);
			pbiRow.createCell(TimelineConstantIndexes.WORK_ITEM_TYPE_INDEX).setCellValue(pbi.getPbiType());
			pbiRow.createCell(TimelineConstantIndexes.DESCRIPTION_INDEX)
					.setCellValue(pbi.getGivenPbiId() + " " + pbi.getPbiTitle());
			if(NumberUtils.isDigits(pbi.getStoryPoints())){
				pbiRow.createCell(TimelineConstantIndexes.STORY_POINTS_INDEX).setCellValue(Integer.parseInt(pbi.getStoryPoints()));
		    }else{
				pbiRow.createCell(TimelineConstantIndexes.STORY_POINTS_INDEX).setCellValue(pbi.getStoryPoints());
		    }
			
			pbiRow.createCell(TimelineConstantIndexes.EXPECTED_HOURS_INDEX)
					.setCellValue(excelHepler.parseHours(String.valueOf(pbi.getExpectedHours())));
			pbiRow.createCell(TimelineConstantIndexes.ACTUAL_HOURS_INDEX)
					.setCellValue(excelHepler.parseHours(String.valueOf(pbi.getActualHours())));
			Cell statusCell = pbiRow.createCell(TimelineConstantIndexes.STATUS_INDEX);
			statusCell.setCellValue(pbi.getStatusName());
			if (pbi.getTimelineNotes() != null)
				setCellNote(sheet, statusCell, pbi.getTimelineNotes().getNotes());

			if (pbi.getResources().size() > 0) {
				boolean isMoreThenOneResource = false;
				if(pbi.getResources().size() > 1 && pbi.getPbiTitle().length() > 60) pbiRow.setHeightInPoints(25);
				for (PbiResource resource : pbi.getResources()) {

					if (!isMoreThenOneResource) {
						Cell dayCell = headerRow.createCell(TimelineConstantIndexes.HOURS_INDEX);
						Cell resourceCell = pbiRow.createCell(TimelineConstantIndexes.ASSIGNED_TO_INDEX);

						resourceCell.setCellValue(resource.getResourceName());
						Cell hourCell = pbiRow.createCell(TimelineConstantIndexes.HOURS_INDEX);

						excelHepler.applyBordersForCells(workbook, hourCell, sheet);
						excelHepler.applyBordersForCells(workbook, dayCell, sheet);
						excelHepler.applyBordersForCells(workbook, resourceCell, sheet);

						for (PbiDays day : resource.getDays()) {
							dayCell.setCellValue(excelHepler.formatDate(day.getDate()));

							if (excelHepler.parseHours(day.getHours()) != 0) {
								hourCell.setCellValue(excelHepler.parseHours(day.getHours()));
							} else {
								hourCell.setCellValue("");
							}
							applyColorsForLeavesAndHolidays(day, holidays, sheet, hourCell, workbook, false,
									pbiTimeLineData);

							dayCell = headerRow.createCell(dayCell.getColumnIndex() + 1);
							hourCell = pbiRow.createCell(hourCell.getColumnIndex() + 1);
						}

						int headerRowSize = headerRow.getPhysicalNumberOfCells();
						for (int index = 0; index < headerRowSize; index++) {
							Cell cell = headerRow.getCell(index);
							if (index != headerRowSize - 1) {
								excelColorUtils.headerBackgroundColor(AppConstants.TIMELINE_HEADER, sheet,
										(XSSFWorkbook) workbook, cell);
							}
						}

					} else {
						Row subrow = sheet.createRow(rowNum++);
						if(pbi.getResources().size() > 1 && pbi.getPbiTitle().length() > 60) subrow.setHeightInPoints(25);
						Cell resourceCell = subrow.createCell(TimelineConstantIndexes.ASSIGNED_TO_INDEX);

						resourceCell.setCellValue(resource.getResourceName());

						Cell hourCell = subrow.createCell(TimelineConstantIndexes.HOURS_INDEX);

						excelHepler.applyBordersForCells(workbook, hourCell, sheet);

						excelHepler.applyBordersForCells(workbook, resourceCell, sheet);

						for (PbiDays day : resource.getDays()) {
							if (excelHepler.parseHours(day.getHours()) != 0) {
								hourCell.setCellValue(excelHepler.parseHours(day.getHours()));
							} else {
								hourCell.setCellValue("");

							}

							applyColorsForLeavesAndHolidays(day, holidays, sheet, hourCell, workbook, false,
									pbiTimeLineData);

							hourCell = subrow.createCell(hourCell.getColumnIndex() + 1);

						}

						excelHepler.applyBorderForSubRow(workbook, sheet, subrow);

					}
					isMoreThenOneResource = true;

					int resourceCount = pbi.getResources().size();

					// Merge cells based on the count of resources
					mergeCellForPbiRow(pbiRow, sheet, resourceCount);
				}

				for (int index = 0; index < sheet.getRow(0).getPhysicalNumberOfCells(); index++) {
					sheet.autoSizeColumn(index);
				}
				try {
					generateTotalResourceHours(sheet, resourceList, workbook, holidays, pbiTimeLineData);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				applyBorderForPbiRow(workbook, sheet, pbiRow, pbi);
			} else {
				pbiRow.createCell(TimelineConstantIndexes.ASSIGNED_TO_INDEX).setCellValue(" ");
				applyBorderForPbiRow(workbook, sheet, pbiRow, pbi);
				applyBorderForNoResourceHourCells(workbook, sprintDates, holidays, pbiRow, sheet,pbiTimeLineData);
			}

		}
		// Write the workbook to a file
		try {
			if(findSheetIndexContains(workbook,"Sheet") != -1 )
				workbook.removeSheetAt(findSheetIndexContains(workbook,"Sheet"));
			excelHepler.sortSheetsByDate(workbook);
			workbook.setActiveSheet(0);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			workbook.write(bos);
			workbook.close();
			bos.close();
			excelBytes = bos.toByteArray();
			return excelBytes;

		} catch (IOException e) {
			e.printStackTrace();
		}
		return excelBytes;
	}

	/*
	 * This is the method generate the resource wise total hours in excel sheet
	 */
	void generateTotalResourceHours(Sheet sheet, List<ResourceHours> resources, Workbook workbook,
			List<HolidaysData> holidays, PbiTimeLineData pbiTimeLineData) throws Exception {
		int rowNum = (int) totalResourcesCount + 3;
		int cellNum = TimelineConstantIndexes.ASSIGNED_TO_INDEX;
		int dataNum = TimelineConstantIndexes.ASSIGNED_TO_INDEX;

		boolean isMoreThenOneResource = false;

		Row resourceHoursRow = sheet.createRow(rowNum++);
		Cell resourceCell = resourceHoursRow.createCell(cellNum);
		resourceCell.setCellValue(AppConstants.RESOURCE_FIELD);

		excelColorUtils.resourceHeaderBackgroundColor(AppConstants.SUMMARY_HEADER, sheet, (XSSFWorkbook) workbook,
				resourceCell);
		Cell TotalHoursColumnwiseCell = resourceHoursRow
				.createCell(cellNum + resources.get(0).getTotalHoursList().size() + 1);
		TotalHoursColumnwiseCell.setCellValue(AppConstants.TOTAL_FIELD);
		excelColorUtils.resourceHeaderBackgroundColor(AppConstants.SUMMARY_HEADER, sheet, (XSSFWorkbook) workbook,
				TotalHoursColumnwiseCell);

		for (ResourceHours resource : resources) {
			int resourceindex = resources.indexOf(resource);

			Row dataRow = sheet.createRow(rowNum++);

			Cell dataCell = dataRow.createCell(dataNum);
			dataCell.setCellValue(resource.getEmployeeName());
			if (resources.size() == resourceindex + 1) {
				resource.setEmployeeName(AppConstants.TOTAL_FIELD);
				excelHepler.applyBordersForCellsWithBold(workbook, dataCell, sheet);
			} else {
				excelHepler.applyBordersForCells(workbook, dataCell, sheet);
			}
			for (PbiDays day : resource.getTotalHoursList()) {

				if (!isMoreThenOneResource) {
					resourceCell = resourceHoursRow.createCell(resourceCell.getColumnIndex() + 1);
					if (day.getDate() != null)
						resourceCell.setCellValue(excelHepler.formatDate(day.getDate()));
					excelColorUtils.resourceHeaderBackgroundColor(AppConstants.SUMMARY_HEADER, sheet,
							(XSSFWorkbook) workbook, resourceCell);
					dataCell = dataRow.createCell(dataCell.getColumnIndex() + 1);
					if (day.getHours().equalsIgnoreCase(AppConstants.LEAVE_VALUE)) {
						dataCell.setCellValue(AppConstants.LEAVE_FIELD);

					} else {
						dataCell.setCellValue(excelHepler.parseHours(day.getHours()));
					}
					applyColorsForLeavesAndHolidays(day, holidays, sheet, dataCell, workbook, true, pbiTimeLineData);

				} else {

					dataCell = dataRow.createCell(dataCell.getColumnIndex() + 1);
					if (day.getHours().equalsIgnoreCase(AppConstants.LEAVE_VALUE)) {
						dataCell.setCellValue(AppConstants.LEAVE_FIELD);
					} else {
						dataCell.setCellValue(excelHepler.parseHours(day.getHours()));

					}

					if (resources.size() != resourceindex + 1) {
						applyColorsForLeavesAndHolidays(day, holidays, sheet, dataCell, workbook, true,
								pbiTimeLineData);
					} else {
						excelColorUtils.summeryTotalBackgroundColor(AppConstants.SUMMARY_BACKGROUND, sheet,
								(XSSFWorkbook) workbook, dataCell);

					}

				}

			}
			isMoreThenOneResource = true;

			Cell totalHoursDataCell = dataRow.createCell(dataCell.getColumnIndex() + 1);

			totalHoursDataCell.setCellFormula("SUM(" + CellReference.convertNumToColString(dataNum + 1) + rowNum + ":"
					+ CellReference.convertNumToColString(dataCell.getColumnIndex()) + rowNum + ")");
			excelColorUtils.summeryTotalBackgroundColor(AppConstants.SUMMARY_BACKGROUND, sheet, (XSSFWorkbook) workbook,
					totalHoursDataCell);

		}

		generateStatusandIndicatorTable(sheet, resourceHoursRow, resourceHoursRow, workbook);

	}

	/*
	 * This is the method generate status and Indicator tables
	 */
	private void generateStatusandIndicatorTable(Sheet sheet, Row columnRow, Row dataRow, Workbook workbook) {
		List<TimelineStatus> timelineStatus = timelineDao.getTimelineStatus();
		List<Indicators> indicators = timelineDao.getIndicators();

		int statusNum = (int) totalResourcesCount + 3;

		Row statusRow = sheet.getRow(statusNum);
		statusRow.createCell(0).setCellValue(AppConstants.STATUS);
		excelHepler.centerCell(workbook, statusRow, 0);

		CellRangeAddress newMergedRegion = new CellRangeAddress(statusRow.getRowNum(), statusRow.getRowNum(), 0, 1);

		if (!excelHepler.overlapsWithExistingMergedRegions(sheet, newMergedRegion)) {
			sheet.addMergedRegion(newMergedRegion);
		}

		// Create data rows for statusValues
		for (TimelineStatus status : timelineStatus) {
			dataRow = sheet.getRow(statusNum + 1);
			if (dataRow == null) {
				dataRow = sheet.createRow(statusNum + 1);
			}
			dataRow.createCell(1).setCellValue(status.getStatus());
			dataRow.createCell(0).setCellValue(" ");

			excelHepler.applyBordersForCells(workbook, dataRow.getCell(1), sheet);
			excelHepler.applyConditionalFormatting(sheet, statusNum + 1, status.getColor(), workbook);

			statusNum = statusNum + 1;
		}

		Row indicatorRow = sheet.getRow(statusNum + 1);
		if(indicatorRow == null) {
		 indicatorRow = sheet.createRow(statusNum + 1);
		}
		indicatorRow.createCell(0).setCellValue(AppConstants.INDICATORS_FIELD);

		excelHepler.centerCell(workbook, indicatorRow, 0);

		CellRangeAddress indicatorMergedRegion = new CellRangeAddress(indicatorRow.getRowNum(),
				indicatorRow.getRowNum(), 0, 1);

		if (!excelHepler.overlapsWithExistingMergedRegions(sheet, indicatorMergedRegion)) {
			sheet.addMergedRegion(indicatorMergedRegion);
		}

		statusNum = statusNum + 1;

		// Create data rows for indicatorValues
		for (Indicators indicator : indicators) {
			int indicatorIndex = indicators.indexOf(indicator);
			if (indicatorIndex < indicators.size() - 3) {
				dataRow = sheet.getRow(statusNum + 1);
				if (dataRow == null) {
					dataRow = sheet.createRow(statusNum + 1);
				}
				dataRow.createCell(1).setCellValue(indicator.getIndicator());
				dataRow.createCell(0).setCellValue(" ");

				excelHepler.applyBordersForCells(workbook, dataRow.getCell(1), sheet);
				excelHepler.applyConditionalFormatting(sheet, statusNum + 1, indicator.getColor(), workbook);

				statusNum = statusNum + 1;
			}
		}

		sheet.setColumnWidth(TimelineConstantIndexes.WORK_ITEM_TYPE_INDEX, 5000);
		sheet.setColumnWidth(TimelineConstantIndexes.DESCRIPTION_INDEX, 8000);
		sheet.setColumnWidth(TimelineConstantIndexes.STATUS_INDEX, 6000);
		sheet.setColumnWidth(TimelineConstantIndexes.ASSIGNED_TO_INDEX, 6000);

	}
	
	/*
	 * This is the method apply the borders for pbi details
	 */
	public void applyBorderForPbiRow(Workbook workbook, Sheet sheet, Row row, PbiTimeline pbi) {

		for (int index = 0; index < row.getPhysicalNumberOfCells() ; index++) {
			if (index <= TimelineConstantIndexes.STATUS_INDEX)
				excelHepler.applyBordersForCells(workbook, row.getCell(index), sheet);
			if (index == TimelineConstantIndexes.STATUS_INDEX) {
				excelColorUtils.statusBackgroundColor(pbi.getStatusName(), sheet, (XSSFWorkbook) workbook,
						row.getCell(index));
				sheet.setColumnWidth(index, 5000);
			} else if(index == TimelineConstantIndexes.ASSIGNED_TO_INDEX) {
				excelHepler.applyBordersForCells(workbook, row.getCell(index), sheet);

			}
		}
	}

	/*
	 * This is the method apply the borders for Leaves, Holidays and other cells.
	 */
	public void applyColorsForLeavesAndHolidays(PbiDays day, List<HolidaysData> holidays, Sheet sheet, Cell dataCell,
			Workbook workbook, boolean isLeaveColorForTotalHourse, PbiTimeLineData pbiTimeLineData) {

		if (excelHepler.isHoliday(day.getDate(), holidays)) {
			excelColorUtils.holidayBackgroundColor(AppConstants.HOLIDAY, sheet, (XSSFWorkbook) workbook, dataCell);
		}
		else if (day.getHours().equalsIgnoreCase(AppConstants.LEAVE_VALUE) && isLeaveColorForTotalHourse) {
			excelColorUtils.leaveBackgroundColor(AppConstants.LEAVE, sheet, (XSSFWorkbook) workbook, dataCell);
		}

		else if (excelHepler.isToday(day.getDate()) && !pbiTimeLineData.getIsTimelineforYesterDay()) {
			excelColorUtils.currentDateBackgroundColor(AppConstants.PRESENT_DAY, sheet, (XSSFWorkbook) workbook,
					dataCell);
		} else if ((pbiTimeLineData.getIsTimelineforYesterDay()
				&& pbiTimeLineData.getTimelineGenerateDate().equals(excelHepler.formatDateWithYear(day.getDate())))) {
			excelColorUtils.currentDateBackgroundColor(AppConstants.PRESENT_DAY, sheet, (XSSFWorkbook) workbook,
					dataCell);
		}  else {
			excelHepler.applyBordersForCells(workbook, dataCell, sheet);
		}

	}

	public String getYesterDay() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		Date yesterday = new Date(cal.getTimeInMillis());
		String formattedDate = new SimpleDateFormat(AppConstants.DD_MM_YYYY).format(yesterday);
		return formattedDate;
	}

	/*
	 * This is the method apply the comments to particular cell
	 */
	private void setCellNote(Sheet sheet, Cell cell, String noteText) {
		Workbook workbook = sheet.getWorkbook();
		CreationHelper creationHelper = workbook.getCreationHelper();

		Drawing<?> drawing = sheet.createDrawingPatriarch();

		ClientAnchor anchor = creationHelper.createClientAnchor();
		anchor.setCol1(cell.getColumnIndex());
		anchor.setCol2(cell.getColumnIndex() + 5);
		anchor.setRow1(cell.getRowIndex());
		anchor.setRow2(cell.getRowIndex() + 4);

		Comment comment = drawing.createCellComment(anchor);

		RichTextString str = creationHelper.createRichTextString(noteText);

		Font font = workbook.createFont();
		font.setFontName(AppConstants.CALEBRI);
		font.setFontHeightInPoints((short) 9);
		str.applyFont(font);

		comment.setString(str);
		cell.setCellComment(comment);
	}



	// Check if the regions overlap with existing merged regions

	private void mergeCellForPbiRow(Row pbiRow, Sheet sheet, int resourceCount) {
		if (resourceCount > 1) {
			CellRangeAddress pbiIdRegion = new CellRangeAddress(pbiRow.getRowNum(),
					pbiRow.getRowNum() + resourceCount - 1, 0, 0);
			CellRangeAddress pbiTypeRegion = new CellRangeAddress(pbiRow.getRowNum(),
					pbiRow.getRowNum() + resourceCount - 1, 1, 1);
			CellRangeAddress statusRegion = new CellRangeAddress(pbiRow.getRowNum(),
					pbiRow.getRowNum() + resourceCount - 1, 2, 2);
			CellRangeAddress titleRegion = new CellRangeAddress(pbiRow.getRowNum(),
					pbiRow.getRowNum() + resourceCount - 1, 3, 3);
			CellRangeAddress expectedRegion = new CellRangeAddress(pbiRow.getRowNum(),
					pbiRow.getRowNum() + resourceCount - 1, 4, 4);
			CellRangeAddress actualRegion = new CellRangeAddress(pbiRow.getRowNum(),
					pbiRow.getRowNum() + resourceCount - 1, 5, 5);

			// Check if the regions overlap with existing merged regions
			if (!excelHepler.overlapsWithExistingMergedRegions(sheet, pbiIdRegion, pbiTypeRegion, statusRegion,
					titleRegion, expectedRegion, actualRegion)) {
				sheet.addMergedRegion(pbiIdRegion);
				sheet.addMergedRegion(pbiTypeRegion);
				sheet.addMergedRegion(statusRegion);
				sheet.addMergedRegion(titleRegion);
				sheet.addMergedRegion(expectedRegion);
				sheet.addMergedRegion(actualRegion);
			}
		}
	}

	private int findSheetIndexContains(Workbook workbook, String substring) {
		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
			String sheetName = workbook.getSheetName(i);
			if (sheetName.contains(substring)) {
				return i;
			}
		}
		return -1;
	}

	//apply  borders for when resources are not existed in pbi
	
	public void applyBorderForNoResourceHourCells(Workbook workbook, List<Date> sprintDates, List<HolidaysData> holidays, Row pbiRow, Sheet sheet, PbiTimeLineData pbiTimeLineData) {
	       int  sprintDateIndex = 0;
	        for (int index = TimelineConstantIndexes.HOURS_INDEX; index < sprintDates.size() + TimelineConstantIndexes.HOURS_INDEX ; index++) {
	        	PbiDays day = new PbiDays();
		        day.setDate(sprintDates.get(sprintDateIndex));
		        day.setHours("");
	            Cell cell = pbiRow.getCell(index);
	            if (cell == null) {
	                cell = pbiRow.createCell(index);
	            }
		        applyColorsForLeavesAndHolidays(day, holidays, sheet, cell, workbook, false, pbiTimeLineData);
		        sprintDateIndex++;
	        }
	}



}
