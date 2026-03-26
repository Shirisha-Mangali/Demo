package com.cognine.model;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

public class PbiDays {
	private Date date;
	
    @JsonInclude(JsonInclude.Include.ALWAYS)
	private String hours;
	private int id;
	private String day;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getHours() {
		return hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}

	@Override
	public String toString() {
		return "PbiDays [date=" + date + ", hours=" + hours + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}	
}
