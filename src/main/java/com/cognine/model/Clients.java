package com.cognine.model;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

public class Clients {

	private int id;
	private String clientName;
	private String region;
	private String address;
	private String city;
	private String state;

	@JsonInclude(JsonInclude.Include.ALWAYS)
	private boolean active;
	private Date created_date;
	private Date updated_date;
	private String countryName;
	private int countryId;
	private int regionId;
	private String regionName;
	private int clientBillingTypeId;
	private String clientBillingType;
	private int projectCount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getProjectCount() {
		return projectCount;
	}

	public void setProjectCount(int projectCount) {
		this.projectCount = projectCount;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	public Date getUpdated_date() {
		return updated_date;
	}

	public void setUpdated_date(Date updated_date) {
		this.updated_date = updated_date;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public int getCountryId() {
		return countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	public int getRegionId() {
		return regionId;
	}

	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public int getClientBillingTypeId() {
		return clientBillingTypeId;
	}

	public void setClientBillingTypeId(int clientBillingTypeId) {
		this.clientBillingTypeId = clientBillingTypeId;
	}

	public String getClientBillingType() {
		return clientBillingType;
	}

	public void setClientBillingType(String clientBillingType) {
		this.clientBillingType = clientBillingType;
	}

}
