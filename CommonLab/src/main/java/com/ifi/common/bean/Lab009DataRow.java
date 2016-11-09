package com.ifi.common.bean;

import java.util.List;

public class Lab009DataRow {

	private Integer id;
	private String name;
	private Integer contracts;
	private Double energyTotal;
	private Double montalTotal;
	private Double performance;
	private Integer percentRate;
	private List<String> listInvariant;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Double getMontalTotal() {
		return montalTotal;
	}

	public void setMontalTotal(Double montalTotal) {
		this.montalTotal = montalTotal;
	}

	public Double getPerformance() {
		return performance;
	}

	public void setPerformance(Double performance) {
		this.performance = performance;
	}

	public Integer getPercentRate() {
		return percentRate;
	}

	public void setPercentRate(Integer percentRate) {
		this.percentRate = percentRate;
	}

	public List<String> getListInvariant() {
		return listInvariant;
	}

	public void setListInvariant(List<String> listInvariant) {
		this.listInvariant = listInvariant;
	}

}
