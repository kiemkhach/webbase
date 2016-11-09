package com.ifi.common.bean;

import java.util.List;

public class Lab008ProductECSBean {
	private Integer siteId;
	private String siteName;
	private List<List<Double>> dataLst;
	private long startTime;
	private long endTime;
	private int typeTime;
	private List<Integer[]> listStartYear;
	private List<Integer[]> listDisplayYear;
	private String fromDateStr;
	private String toDateStr;
	private List<Object[]> nameChart;
	private List<Long> lstCategory;

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public int getTypeTime() {
		return typeTime;
	}

	public void setTypeTime(int typeTime) {
		this.typeTime = typeTime;
	}

	public List<Integer[]> getListStartYear() {
		return listStartYear;
	}

	public void setListStartYear(List<Integer[]> listStartYear) {
		this.listStartYear = listStartYear;
	}

	public List<Integer[]> getListDisplayYear() {
		return listDisplayYear;
	}

	public void setListDisplayYear(List<Integer[]> listDisplayYear) {
		this.listDisplayYear = listDisplayYear;
	}

	public String getFromDateStr() {
		return fromDateStr;
	}

	public void setFromDateStr(String fromDateStr) {
		this.fromDateStr = fromDateStr;
	}

	public String getToDateStr() {
		return toDateStr;
	}

	public void setToDateStr(String toDateStr) {
		this.toDateStr = toDateStr;
	}

	public List<Object[]> getNameChart() {
		return nameChart;
	}

	public void setNameChart(List<Object[]> nameChart) {
		this.nameChart = nameChart;
	}

	public List<List<Double>> getDataLst() {
		return dataLst;
	}

	public void setDataLst(List<List<Double>> dataLst) {
		this.dataLst = dataLst;
	}

	public List<Long> getLstCategory() {
		return lstCategory;
	}

	public void setLstCategory(List<Long> lstCategory) {
		this.lstCategory = lstCategory;
	}

}
