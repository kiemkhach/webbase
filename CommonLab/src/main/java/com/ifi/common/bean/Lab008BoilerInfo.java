package com.ifi.common.bean;

import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class Lab008BoilerInfo {

	@SerializedName("site_id")
	private Integer siteId;
	@SerializedName("date_time")
	private Date dateTime;
	private Double temperature;
	private Double inputModuleValue;
	private Double outputModuleValue;

	@Override
	public String toString() {
		return "siteId = " + siteId + ", dateTime = " + dateTime + ", temperature = " + temperature
				+ ", inputModuleValue = " + inputModuleValue + ", outputModuleValue = " + outputModuleValue;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public Double getTemperature() {
		return temperature;
	}

	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

	public Double getInputModuleValue() {
		return inputModuleValue;
	}

	public void setInputModuleValue(Double inputModuleValue) {
		this.inputModuleValue = inputModuleValue;
	}

	public Double getOutputModuleValue() {
		return outputModuleValue;
	}

	public void setOutputModuleValue(Double outputModuleValue) {
		this.outputModuleValue = outputModuleValue;
	}

}
