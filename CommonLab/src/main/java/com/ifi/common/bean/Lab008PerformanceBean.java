package com.ifi.common.bean;

import java.util.List;

public class Lab008PerformanceBean {
	private String siteName;
	private Integer siteId;
	private Double periodIndicatorPerformance;
	private List<Lab008PerformanceYear> listYearPerformance;
	private List<Object[]> dataCharts;
	private long startTime;
	private long endTime;
	private String fromDateStr;
	private String toDateStr;
	private List<Long> listDisplayYear;

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public Double getPeriodIndicatorPerformance() {
		return periodIndicatorPerformance;
	}

	public void setPeriodIndicatorPerformance(Double periodIndicatorPerformance) {
		this.periodIndicatorPerformance = periodIndicatorPerformance;
	}

	public List<Lab008PerformanceYear> getListYearPerformance() {
		return listYearPerformance;
	}

	public void setListYearPerformance(List<Lab008PerformanceYear> listYearPerformance) {
		this.listYearPerformance = listYearPerformance;
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

	public List<Object[]> getDataCharts() {
		return dataCharts;
	}

	public void setDataCharts(List<Object[]> dataCharts) {
		this.dataCharts = dataCharts;
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

	public List<Long> getListDisplayYear() {
		return listDisplayYear;
	}

	public void setListDisplayYear(List<Long> listDisplayYear) {
		this.listDisplayYear = listDisplayYear;
	}

}
