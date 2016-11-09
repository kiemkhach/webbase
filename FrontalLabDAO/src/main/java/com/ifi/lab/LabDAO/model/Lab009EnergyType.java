package com.ifi.lab.LabDAO.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "lab009_energy_type")
public class Lab009EnergyType {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "energy_code")
	private String energyCode;

	@Column(name = "energy_name")
	private String energyName;

	@Column(name = "energy_emissions")
	private Double energyEmissions;

	@Column(name = "color_code")
	private String colorCode;

	@Column(name = "category",columnDefinition = "INTEGER DEFAULT 1")
	private Integer category;// 1: electric 2:water

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEnergyCode() {
		return energyCode;
	}

	public void setEnergyCode(String energyCode) {
		this.energyCode = energyCode;
	}

	public String getEnergyName() {
		return energyName;
	}

	public void setEnergyName(String energyName) {
		this.energyName = energyName;
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

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

}
