package com.cognine.model;

import java.sql.Date;

public class HolidaysData {
private int id;
private Date holidayDate;
private String holidayName;
private boolean isActive;

public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public Date getHolidayDate() {
	return holidayDate;
}
public void setHolidayDate(Date holidayDate) {
	this.holidayDate = holidayDate;
}
public String getHolidayName() {
	return holidayName;
}
public void setHolidayName(String holidayName) {
	this.holidayName = holidayName;
}
public boolean getIsActive() {
	return isActive;
}
public void setActive(boolean isActive) {
	this.isActive = isActive;
}
}
