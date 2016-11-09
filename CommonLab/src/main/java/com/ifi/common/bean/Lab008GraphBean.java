package com.ifi.common.bean;

import java.util.List;
import java.util.Map;

public class Lab008GraphBean {
	private long startTime;
	private long endTime;
	private List<String[]> nameChart;
	private List<List<Integer>> dataChartLst;
	private List<Integer> limitChartLst;
	private int type;
	private String waterUnit;
	private Map<Integer, List<String>> mapECSName;
	private List<Long> lstCategory;

	private int typeTime;

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

	public List<String[]> getNameChart() {
		return nameChart;
	}

	public void setNameChart(List<String[]> nameChart) {
		this.nameChart = nameChart;
	}

	public List<List<Integer>> getDataChartLst() {
		return dataChartLst;
	}

	public void setDataChartLst(List<List<Integer>> dataChartLst) {
		this.dataChartLst = dataChartLst;
	}

	public List<Integer> getLimitChartLst() {
		return limitChartLst;
	}

	public void setLimitChartLst(List<Integer> limitChartLst) {
		this.limitChartLst = limitChartLst;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getWaterUnit() {
		return waterUnit;
	}

	public void setWaterUnit(String waterUnit) {
		this.waterUnit = waterUnit;
	}

	public int getTypeTime() {
		return typeTime;
	}

	public void setTypeTime(int typeTime) {
		this.typeTime = typeTime;
	}

	public Map<Integer, List<String>> getMapECSName() {
		return mapECSName;
	}

	public void setMapECSName(Map<Integer, List<String>> mapECSName) {
		this.mapECSName = mapECSName;
	}

	public List<Long> getLstCategory() {
		return lstCategory;
	}

	public void setLstCategory(List<Long> lstCategory) {
		this.lstCategory = lstCategory;
	}

}
