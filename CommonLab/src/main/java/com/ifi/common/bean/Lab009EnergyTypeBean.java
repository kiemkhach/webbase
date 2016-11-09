package com.ifi.common.bean;

public class Lab009EnergyTypeBean {
	private Integer id;
//	private String energyCode;
	private String energyName;
	private String colorCode;
	private Double energyEmissions;

	public Double getEnergyEmissions() {
		return energyEmissions;
	}

	public void setEnergyEmissions(Double energyEmissions) {
		this.energyEmissions = energyEmissions;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

//	public String getEnergyCode() {
//		return energyCode;
//	}
//
//	public void setEnergyCode(String energyCode) {
//		this.energyCode = energyCode;
//	}

	public String getEnergyName() {
		return energyName;
	}

	public void setEnergyName(String energyName) {
		this.energyName = energyName;
	}

	public String getColorCode() {
		return colorCode;
	}

	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}

}
