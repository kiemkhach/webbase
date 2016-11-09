package com.ifi.common.bean;

import java.util.List;
import java.util.Map;

public class Lab009DetailBean {

	private String type;
	private String energyTypeName;
	private Integer energyTypeId;
	private Integer contracts;
	private Double energyTotal;
	private String energyUnit;
	private Double montalTotal;
	private String montalUnit;
	private Double performance;
	private Double percent;
	private List<Lab009DataRow> providerBeanLst;
	private Lab009DataRow providerBeanTotal;
	private Map<Integer, Double> yearLineChart;
	private Map<Integer, Map<Integer, Double>> monthLineChart;

	private Map<Integer, Map<Integer, Double>> yearBarChart;
	private Map<Integer, Map<Integer, Map<Integer, Double>>> monthBarChart;

	private Map<Integer, Lab009LotConsommationBean> lotConsommationMap;
	private Map<Integer, Lab009EnergyTypeBean> energyTypeMap;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEnergyTypeName() {
		return energyTypeName;
	}

	public void setEnergyTypeName(String energyTypeName) {
		this.energyTypeName = energyTypeName;
	}

	public Integer getEnergyTypeId() {
		return energyTypeId;
	}

	public void setEnergyTypeId(Integer energyTypeId) {
		this.energyTypeId = energyTypeId;
	}

	public Integer getContracts() {
		return contracts;
	}

	public void setContracts(Integer contracts) {
		this.contracts = contracts;
	}

	public Double getEnergyTotal() {
		return energyTotal;
	}

	public void setEnergyTotal(Double energyTotal) {
		this.energyTotal = energyTotal;
	}

	public String getEnergyUnit() {
		return energyUnit;
	}

	public void setEnergyUnit(String energyUnit) {
		this.energyUnit = energyUnit;
	}

	public Double getMontalTotal() {
		return montalTotal;
	}

	public void setMontalTotal(Double montalTotal) {
		this.montalTotal = montalTotal;
	}

	public String getMontalUnit() {
		return montalUnit;
	}

	public void setMontalUnit(String montalUnit) {
		this.montalUnit = montalUnit;
	}

	public Double getPerformance() {
		return performance;
	}

	public void setPerformance(Double performance) {
		this.performance = performance;
	}

	public Double getPercent() {
		return percent;
	}

	public void setPercent(Double percent) {
		this.percent = percent;
	}

	public List<Lab009DataRow> getProviderBeanLst() {
		return providerBeanLst;
	}

	public void setProviderBeanLst(List<Lab009DataRow> providerBeanLst) {
		this.providerBeanLst = providerBeanLst;
	}

	public Lab009DataRow getProviderBeanTotal() {
		return providerBeanTotal;
	}

	public void setProviderBeanTotal(Lab009DataRow providerBeanTotal) {
		this.providerBeanTotal = providerBeanTotal;
	}

	public Map<Integer, Map<Integer, Double>> getMonthLineChart() {
		return monthLineChart;
	}

	public void setMonthLineChart(Map<Integer, Map<Integer, Double>> monthLineChart) {
		this.monthLineChart = monthLineChart;
	}

	public Map<Integer, Lab009LotConsommationBean> getLotConsommationMap() {
		return lotConsommationMap;
	}

	public void setLotConsommationMap(Map<Integer, Lab009LotConsommationBean> lotConsommationMap) {
		this.lotConsommationMap = lotConsommationMap;
	}

	public Map<Integer, Lab009EnergyTypeBean> getEnergyTypeMap() {
		return energyTypeMap;
	}

	public void setEnergyTypeMap(Map<Integer, Lab009EnergyTypeBean> energyTypeMap) {
		this.energyTypeMap = energyTypeMap;
	}

	public Map<Integer, Double> getYearLineChart() {
		return yearLineChart;
	}

	public void setYearLineChart(Map<Integer, Double> yearLineChart) {
		this.yearLineChart = yearLineChart;
	}

	public Map<Integer, Map<Integer, Double>> getYearBarChart() {
		return yearBarChart;
	}

	public void setYearBarChart(Map<Integer, Map<Integer, Double>> yearBarChart) {
		this.yearBarChart = yearBarChart;
	}

	public Map<Integer, Map<Integer, Map<Integer, Double>>> getMonthBarChart() {
		return monthBarChart;
	}

	public void setMonthBarChart(Map<Integer, Map<Integer, Map<Integer, Double>>> monthBarChart) {
		this.monthBarChart = monthBarChart;
	}

}
