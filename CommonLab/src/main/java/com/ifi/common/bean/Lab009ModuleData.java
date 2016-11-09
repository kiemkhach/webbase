package com.ifi.common.bean;

public class Lab009ModuleData {
	private String moduleNum;
	private Integer year;
	private Integer month;
	private Double conso;
	private Double cout;

	public String getModuleNum() {
		return moduleNum;
	}

	public void setModuleNum(String moduleNum) {
		this.moduleNum = moduleNum;
	}

	public Double getConso() {
		return conso;
	}

	public void setConso(Double conso) {
		this.conso = conso;
	}

	public Double getCout() {
		return cout;
	}

	public void setCout(Double cout) {
		this.cout = cout;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

}
