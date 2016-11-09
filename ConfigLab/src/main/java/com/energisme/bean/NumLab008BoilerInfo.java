package com.energisme.bean;

import java.sql.Time;
import java.util.Date;

public class NumLab008BoilerInfo {

	private Integer id;
	private Integer siteId;
	private Date date;
	private Time time;
	private Double temperature;
	private Double inputModuleValue;
	private Double outputModuleValue;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
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
