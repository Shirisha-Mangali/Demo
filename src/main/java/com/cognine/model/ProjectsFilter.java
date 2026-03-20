package com.cognine.model;

public class ProjectsFilter {

	private String searchClientName;
	private String searchProjectName;
	private String searchDescription;
	private String searchStartDate;
	private String searchEndDate;
	private String searchPmoName;
	private String sortColumn;
	private String sortType;
	private int pageNum;
	private int pageSize;

	public String getSearchClientName() {
		return searchClientName;
	}

	public void setSearchClientName(String searchClientName) {
		this.searchClientName = searchClientName;
	}

	public String getSearchProjectName() {
		return searchProjectName;
	}

	public void setSearchProjectName(String searchProjectName) {
		this.searchProjectName = searchProjectName;
	}

	public String getSearchDescription() {
		return searchDescription;
	}

	public void setSearchDescription(String searchDescription) {
		this.searchDescription = searchDescription;
	}

	public String getSearchStartDate() {
		return searchStartDate;
	}

	public void setSearchStartDate(String searchStartDate) {
		this.searchStartDate = searchStartDate;
	}

	public String getSearchEndDate() {
		return searchEndDate;
	}

	public void setSearchEndDate(String searchEndDate) {
		this.searchEndDate = searchEndDate;
	}

	public String getSearchPmoName() {
		return searchPmoName;
	}

	public void setSearchPmoName(String searchPmoName) {
		this.searchPmoName = searchPmoName;
	}

	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
