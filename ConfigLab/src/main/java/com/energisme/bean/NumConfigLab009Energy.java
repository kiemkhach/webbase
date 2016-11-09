package com.energisme.bean;

import java.util.List;

import com.ifi.lab.LabDAO.model.Lab009EnergyType;

public class NumConfigLab009Energy {
	private List<Lab009EnergyType> lab009EnergyTypesLst;
	private Integer energyId;
	private String energyName;
	private String energyCode;
	private Double energyEmissions;
	private String colorCode;

	public List<Lab009EnergyType> getLab009EnergyTypesLst() {
		return lab009EnergyTypesLst;
	}

	public void setLab009EnergyTypesLst(List<Lab009EnergyType> lab009EnergyTypesLst) {
		this.lab009EnergyTypesLst = lab009EnergyTypesLst;
	}

	public String getEnergyName() {
		return energyName;
	}

	public void setEnergyName(String energyName) {
		this.energyName = energyName;
	}

	public String getEnergyCode() {
		return energyCode;
	}

	public void setEnergyCode(String energyCode) {
		this.energyCode = energyCode;
	}

	public Double getEnergyEmissions() {
		return energyEmissions;
	}

	public void setEnergyEmissions(Double energyEmissions) {
		this.energyEmissions = energyEmissions;
	}

	public String getColorCode() {
		return colorCode;
	}

	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}

	public Integer getEnergyId() {
		return energyId;
	}

	public void setEnergyId(Integer energyId) {
		this.energyId = energyId;
	}
	
}
