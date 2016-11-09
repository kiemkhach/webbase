package com.ifi.common.bean;

public class Lab009DetailContract {
	private String moduleNumber;
	private String energyType;
	private Integer energyTypeID;
	private String providerName;
	private Integer providerID;
	private String noCompteur;
	private String noContract;
	private Double montal;
	private Double consommation;
	private Double performace;

	public String getModuleNumber() {
		return moduleNumber;
	}

	public void setModuleNumber(String moduleNumber) {
		this.moduleNumber = moduleNumber;
	}

	public Integer getEnergyTypeID() {
		return energyTypeID;
	}

	public void setEnergyTypeID(Integer energyTypeID) {
		this.energyTypeID = energyTypeID;
	}

	public Integer getProviderID() {
		return providerID;
	}

	public void setProviderID(Integer providerID) {
		this.providerID = providerID;
	}

	public String getEnergyType() {
		return energyType;
	}

	public void setEnergyType(String energyType) {
		this.energyType = energyType;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getNoCompteur() {
		return noCompteur;
	}

	public void setNoCompteur(String noCompteur) {
		this.noCompteur = noCompteur;
	}

	public String getNoContract() {
		return noContract;
	}

	public void setNoContract(String noContract) {
		this.noContract = noContract;
	}

	public Double getMontal() {
		return montal;
	}

	public void setMontal(Double montal) {
		this.montal = montal;
	}

	public Double getConsommation() {
		return consommation;
	}

	public void setConsommation(Double consommation) {
		this.consommation = consommation;
	}

	public Double getPerformace() {
		return performace;
	}

	public void setPerformace(Double performace) {
		this.performace = performace;
	}

}
