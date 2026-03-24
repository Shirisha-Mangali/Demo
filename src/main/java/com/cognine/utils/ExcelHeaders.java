package com.cognine.utils;

public enum ExcelHeaders {

	EMPLOYEE_ID("Resource Id"), EMPLOYEE_ACTIVE("Resource Status"), EMPLOYEE_NAME("Resource Name"),
	RESOURCE_START_DATE("Resource Start Date"), RESOURCE_END_DATE("Resource End Date"), PROJECT_ID("Project Id"),
	PROJECT_NAME("Project Name"), PROJECT_START_DATE("Project Start Date"), PROJECT_END_DATE("Project End Date"),
	PROJECT_ALLOCATION("Project Allocation"), FUTURE_RESOURCE("Future Resource"), From_Date("From Date"),
	To_Date("To Date"), Allocation("Allocation");

	private final String text;

	ExcelHeaders(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}
}
