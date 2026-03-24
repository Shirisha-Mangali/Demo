package com.cognine.utils;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.text.ParseException;
import org.apache.commons.codec.binary.Hex;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.springframework.context.annotation.Configuration;

import com.cognine.model.HolidaysData;

@Configuration
public class ExcelHelper {

	
	private static final int MAX_COLUMN_INDEX = 16383;

	
	/*
	 * Centers the content of a cell in a row and applies formatting.
	 */
	public void centerCell(Workbook workbook, Row targetRow, int Index) {
		CellStyle style = workbook.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		Font font = workbook.createFont();
		font.setBold(true);
		font.setFontHeightInPoints((short) 9);
		font.setFontName(AppConstants.CALEBRI);
		style.setFont(font);
		targetRow.getCell(Index).setCellStyle(style);

	}

	/*
	 * Applies conditional formatting to a cell based on the provided status.
	 */
	public void applyConditionalFormatting(Sheet sheet, int row, String status, Workbook workbook) {
		Row targetRow = sheet.getRow(row);
		if (targetRow == null) {
			targetRow = sheet.createRow(row);
		}
		Cell cell = targetRow.getCell(0);
		XSSFCellStyle cellStyle = (XSSFCellStyle) sheet.getWorkbook().createCellStyle();
		XSSFColor color;
		color = convertToCustomColor(status);
		cellStyle.setFillForegroundColor(color);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cell.setCellStyle(cellStyle);
		applyBorders(cellStyle);
	}

	/*
	 * Applies borders to the cell style.
	 */
	private static void applyBorders(XSSFCellStyle cellStyle) {
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);

	}

	/*
	 * Applies borders and formatting for cells.
	 */
	public void applyBordersForCells(Workbook workbook, Cell cell, Sheet sheet) {
		XSSFCellStyle style = (XSSFCellStyle) sheet.getWorkbook().createCellStyle();
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setFont(applyFont(workbook));
		style.setWrapText(true);
		cell.setCellStyle(style);

	}

	/*
	 * Applies borders and formatting for cells with bold.
	 */
	public void applyBordersForCellsWithBold(Workbook workbook, Cell cell, Sheet sheet) {
		XSSFCellStyle style = (XSSFCellStyle) sheet.getWorkbook().createCellStyle();
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setFont(applyFontWithBold(workbook));
		style.setWrapText(true);
		cell.setCellStyle(style);

	}

	/*
	 * Converts a color code to a custom XSSFColor.
	 */
	public XSSFColor convertToCustomColor(String colorCode) {
		byte[] rgbB;
		try {
			rgbB = Hex.decodeHex(colorCode);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return new XSSFColor(rgbB, null);
	}

	/*
	 * Formats a SQL date to a string in the "dd MMM" format.
	 */
	public String formatDate(Date sqlDate) {
		SimpleDateFormat sdf = new SimpleDateFormat(AppConstants.DD_MMM);
		String formattedDate = sdf.format(sqlDate);
		return formattedDate;
	}

	/*
	 * Formats a SQL date to a string with year in the "dd-MM-YYYY" format.
	 */
	public String formatDateWithYear(Date sqlDate) {
		SimpleDateFormat sdf = new SimpleDateFormat(AppConstants.DD_MM_YYYY);
		String formattedDate = sdf.format(sqlDate);
		return formattedDate;
	}

	/*
	 * Applies font settings and returns the font.
	 */
	private Font applyFont(Workbook workbook) {
		Font font = workbook.createFont();
		font.setFontHeightInPoints((short) 9);
		font.setFontName(AppConstants.CALEBRI);
		return font;
	}

	/*
	 * Applies font settings and returns the font.
	 */
	private Font applyFontWithBold(Workbook workbook) {
		Font font = workbook.createFont();
		font.setFontHeightInPoints((short) 9);
		font.setFontName(AppConstants.CALEBRI);
		font.setBold(true);
		return font;
	}

	/*
	 * Checks if a date is today.
	 */
	public boolean isToday(Date dateToCheck) {
		LocalDate localDateToCheck = dateToCheck.toLocalDate();
		LocalDate today = LocalDate.now();
		return today.isEqual(localDateToCheck);
	}

	/*
	 * Checks if a date is a holiday.
	 */
	public boolean isHoliday(Date dateToCheck, List<HolidaysData> dateList) {
		LocalDate localDateToCheck = dateToCheck.toLocalDate();

		for (HolidaysData holidaysData : dateList) {
			LocalDate listDate = holidaysData.getHolidayDate().toLocalDate();
			if (listDate.equals(localDateToCheck)) {
				return true; // Date found in the list
			}
		}

		return false; // Date not found in the list
	}

	/*
	 * Parses hours from a string.
	 */	
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
	 * Checks if merged regions overlap with existing ones in the sheet.
	 */
	public boolean overlapsWithExistingMergedRegions(Sheet sheet, CellRangeAddress... regions) {
		for (CellRangeAddress region : regions) {
			for (CellRangeAddress existingRegion : sheet.getMergedRegions()) {
				if (region.isInRange(existingRegion.getFirstRow(), existingRegion.getFirstColumn())
						|| region.isInRange(existingRegion.getLastRow(), existingRegion.getLastColumn())) {
					return true;
				}
			}
		}
		return false;
	}

	/*
	 * Applies border for sub rows in the sheet.
	 */
	public void applyBorderForSubRow(Workbook workbook, Sheet sheet, Row row) {
		for (int index = 0; index < row.getPhysicalNumberOfCells() - 1; index++) {
			if (index <= TimelineConstantIndexes.STATUS_INDEX) {
				Cell cell = row.getCell(index);
				if (cell == null) {
					cell = row.createCell(index);
				}
				applyBordersForCells(workbook, cell, sheet);
			}
		}
	}

	/*
	 * Clears the content and formatting of a sheet.
	 */
	
	public  Sheet clearAndCreateNewSheet(Workbook workbook, String sheetName) {
        int sheetIndex = workbook.getSheetIndex(sheetName);
        if (sheetIndex != -1) {
            workbook.removeSheetAt(sheetIndex);
        }
        Sheet newSheet = workbook.createSheet(sheetName);

        return newSheet;
    }
	
	
	/*
	 * format the string date DD_MM_YYYY and return string
	 */
	public String formatSheetDate(String inputDate) {
		try {
			SimpleDateFormat inputFormat = new SimpleDateFormat(AppConstants.DD_MM_YYYY);
			java.util.Date utilDate = inputFormat.parse(inputDate);
			return formatDate(new java.sql.Date(utilDate.getTime()));
		} catch (java.text.ParseException e) {
			e.printStackTrace();
			return null; 
		}
	}

	
	/*
	 * format the string date DD_MM_YYYY and return Date
	 */
	public Date changeStringToDate(String inputDate) {
		try {
			SimpleDateFormat inputFormat = new SimpleDateFormat(AppConstants.DD_MM_YYYY);
			return new Date(inputFormat.parse(inputDate).getTime());
		} catch (Exception e) {
			e.printStackTrace();
			return null; 
		}
	}

	
	/*
	 *change the format of sheet name in timeline with DD_MMM_YYYY 
	 */
	public java.util.Date parseDateFromSheetName(String sheetName) {
		try {
			int currentYear = Calendar.getInstance().get(Calendar.YEAR);
			SimpleDateFormat dateFormat = new SimpleDateFormat(AppConstants.DD_MMM_YYYY);
			dateFormat.setLenient(false);
			return dateFormat.parse(sheetName + " " + currentYear);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	
	/*
	 * arranging the all sheets of workbook in descending order
	 */
	public void sortSheetsByDate(Workbook workbook) {
		try {
			List<Sheet> sheetList = getSheetList(workbook);
			sheetList.sort(Comparator.comparing(sheet -> parseDateFromSheetName(sheet.getSheetName()),
					Comparator.nullsLast(Comparator.reverseOrder())));
			reorderSheets(workbook, sheetList);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private List<Sheet> getSheetList(Workbook workbook) {
		List<Sheet> sheetList = new ArrayList<>();
		Iterator<Sheet> sheetIterator = workbook.sheetIterator();
		sheetIterator.forEachRemaining(sheetList::add);
		return sheetList;
	}

	
	/*
	 * reordering the all sheets of workbook
	 */
	private void reorderSheets(Workbook workbook, List<Sheet> sheetList) {
		for (int i = 0; i < sheetList.size(); i++) {
			Sheet sortedSheet = sheetList.get(i);
			workbook.setSheetOrder(sortedSheet.getSheetName(), i);
		}
	}

	/*
	 * Apply the order based on the RoleLevel
	 */
	
	public static int getRolePriority(String role) {
        switch (role) {
            case AppConstants.ROLE_PROJECT_MANAGER:
                return 1;
            case AppConstants.ROLE_TECH_LEAD:
                return 2;
            case AppConstants.ROLE_DEVELOPER:
                return 3;
            case AppConstants.ROLE_QA:
                return 4;
            default:
                return Integer.MAX_VALUE; // Default priority for unknown roles
        }
    }
}
