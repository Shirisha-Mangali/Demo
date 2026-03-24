package com.cognine.model;

import java.sql.Date;

public class PbiFilter {
	private String searcPbiId;
	private String searchPbiName;
	private String searchPbiType;
	private String searchAddedAt;
	private int sprintId;
	private int pageNum;
	private int pageSize;
	public String getSearcPbiId() {
		return searcPbiId;
	}
	public void setSearcPbiId(String searcPbiId) {
		this.searcPbiId = searcPbiId;
	}
	public String getSearchPbiName() {
		return searchPbiName;
	}
	public void setSearchPbiName(String searchPbiName) {
		this.searchPbiName = searchPbiName;
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
	public String getSearchPbiType() {
		return searchPbiType;
	}
	public void setSearchPbiType(String searchPbiType) {
		this.searchPbiType = searchPbiType;
	}
	public String getSearchAddedAt() {
		return searchAddedAt;
	}
	public void setSearchAddedAt(String searchAddedAt) {
		this.searchAddedAt = searchAddedAt;
	}
	public int getSprintId() {
		return sprintId;
	}
	public void setSprintId(int sprintId) {
		this.sprintId = sprintId;
	}
}
