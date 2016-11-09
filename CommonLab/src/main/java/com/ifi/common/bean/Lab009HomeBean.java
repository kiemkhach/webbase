package com.ifi.common.bean;

import java.util.Map;

public class Lab009HomeBean {
	private Double totalHTFacture;
	private Map<Integer, Integer> batchTypeMap;
	private Map<Integer, Map<Integer, Integer[]>> batchTypeDrillMap;
	private Map<Integer, Integer> energismeTypeMap;
	private Map<Integer, Map<Integer, Integer[]>> energismeTypeDrillMap;

	private Double totalFourniture;
	private Map<Integer, Integer> montantMap;
	private Map<Integer, Map<Integer, Integer>> montantDrillMap;
	private Map<Integer, Integer> montantEnergyMap;
	private Map<Integer, Map<Integer, Integer>> montantEnergyDrillMap;

	private Double totalEmissions;
	private Map<Integer, Double> emissionsMap;
	private Map<Integer, Map<Integer, Double>> emissionsDrillMap;
	private Map<Integer, Double> emissionsEnergyMap;
	private Map<Integer, Map<Integer, Double>> emissionsEnergyDrillMap;

	private Map<Integer, Lab009LotConsommationBean> lotConsommationMap;
	private Map<Integer, Lab009EnergyTypeBean> energyTypeMap;

	private String consomationUnit;
	private String montalUnit;
	private String emissionsUnit;

	public String getConsomationUnit() {
		return consomationUnit;
	}

	public void setConsomationUnit(String consomationUnit) {
		this.consomationUnit = consomationUnit;
	}

	public String getMontalUnit() {
		return montalUnit;
	}

	public void setMontalUnit(String montalUnit) {
		this.montalUnit = montalUnit;
	}

	public String getEmissionsUnit() {
		return emissionsUnit;
	}

	public void setEmissionsUnit(String emissionsUnit) {
		this.emissionsUnit = emissionsUnit;
	}

	public Double getTotalHTFacture() {
		return totalHTFacture;
	}

	public void setTotalHTFacture(Double totalHTFacture) {
		this.totalHTFacture = totalHTFacture;
	}

	public Map<Integer, Integer> getBatchTypeMap() {
		return batchTypeMap;
	}

	public void setBatchTypeMap(Map<Integer, Integer> batchTypeMap) {
		this.batchTypeMap = batchTypeMap;
	}

	public Map<Integer, Integer> getEnergismeTypeMap() {
		return energismeTypeMap;
	}

	public void setEnergismeTypeMap(Map<Integer, Integer> energismeTypeMap) {
		this.energismeTypeMap = energismeTypeMap;
	}

	public Double getTotalFourniture() {
		return totalFourniture;
	}

	public void setTotalFourniture(Double totalFourniture) {
		this.totalFourniture = totalFourniture;
	}

	public Map<Integer, Integer> getMontantMap() {
		return montantMap;
	}

	public void setMontantMap(Map<Integer, Integer> montantMap) {
		this.montantMap = montantMap;
	}

	public Map<Integer, Map<Integer, Integer>> getMontantDrillMap() {
		return montantDrillMap;
	}

	public void setMontantDrillMap(Map<Integer, Map<Integer, Integer>> montantDrillMap) {
		this.montantDrillMap = montantDrillMap;
	}

	public Double getTotalEmissions() {
		return totalEmissions;
	}

	public void setTotalEmissions(Double totalEmissions) {
		this.totalEmissions = totalEmissions;
	}

	public Map<Integer, Double> getEmissionsMap() {
		return emissionsMap;
	}

	public void setEmissionsMap(Map<Integer, Double> emissionsMap) {
		this.emissionsMap = emissionsMap;
	}

	public Map<Integer, Map<Integer, Double>> getEmissionsDrillMap() {
		return emissionsDrillMap;
	}

	public void setEmissionsDrillMap(Map<Integer, Map<Integer, Double>> emissionsDrillMap) {
		this.emissionsDrillMap = emissionsDrillMap;
	}

	public Map<Integer, Map<Integer, Integer[]>> getBatchTypeDrillMap() {
		return batchTypeDrillMap;
	}

	public void setBatchTypeDrillMap(Map<Integer, Map<Integer, Integer[]>> batchTypeDrillMap) {
		this.batchTypeDrillMap = batchTypeDrillMap;
	}

	public Map<Integer, Map<Integer, Integer[]>> getEnergismeTypeDrillMap() {
		return energismeTypeDrillMap;
	}

	public void setEnergismeTypeDrillMap(Map<Integer, Map<Integer, Integer[]>> energismeTypeDrillMap) {
		this.energismeTypeDrillMap = energismeTypeDrillMap;
	}

	public Map<Integer, Integer> getMontantEnergyMap() {
		return montantEnergyMap;
	}

	public void setMontantEnergyMap(Map<Integer, Integer> montantEnergyMap) {
		this.montantEnergyMap = montantEnergyMap;
	}

	public Map<Integer, Map<Integer, Integer>> getMontantEnergyDrillMap() {
		return montantEnergyDrillMap;
	}

	public void setMontantEnergyDrillMap(Map<Integer, Map<Integer, Integer>> montantEnergyDrillMap) {
		this.montantEnergyDrillMap = montantEnergyDrillMap;
	}

	public Map<Integer, Double> getEmissionsEnergyMap() {
		return emissionsEnergyMap;
	}

	public void setEmissionsEnergyMap(Map<Integer, Double> emissionsEnergyMap) {
		this.emissionsEnergyMap = emissionsEnergyMap;
	}

	public Map<Integer, Map<Integer, Double>> getEmissionsEnergyDrillMap() {
		return emissionsEnergyDrillMap;
	}

	public void setEmissionsEnergyDrillMap(Map<Integer, Map<Integer, Double>> emissionsEnergyDrillMap) {
		this.emissionsEnergyDrillMap = emissionsEnergyDrillMap;
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

}
