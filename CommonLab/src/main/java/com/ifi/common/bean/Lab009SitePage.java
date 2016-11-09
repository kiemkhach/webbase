package com.ifi.common.bean;

import java.util.List;
import java.util.Map;

public class Lab009SitePage {

	private String reference;
	private String denomination;
	private List<String[]> siteProperty;
	private Double totalHTFacture;
	private Map<Integer, Integer> batchTypeMap;
	private Map<Integer, Map<Integer, Integer>> batchTypeDrillMap;

	private Double totalFourniture;
	private Map<Integer, Integer> montantMap;
	private Map<Integer, Map<Integer, Integer>> montantDrillMap;

	private Double totalEmissions;
	private Map<Integer, Double> emissionsMap;
	private Map<Integer, Map<Integer, Double>> emissionsDrillMap;

	private Map<Integer, Lab009LotConsommationBean> lotConsommationMap;
	private Map<Integer, Lab009EnergyTypeBean> energyTypeMap;

	private Double montalSite;
	private Double montalSurface;
	private Double emission;
	private Double water;
	private Double totalMontal;
	private Double montalSurfaceTotal;
	private Double emissionTotal;
	private Double waterTotal;
	private List<Object[]> detailContractLst;

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

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getDenomination() {
		return denomination;
	}

	public void setDenomination(String denomination) {
		this.denomination = denomination;
	}

	public List<String[]> getSiteProperty() {
		return siteProperty;
	}

	public void setSiteProperty(List<String[]> siteProperty) {
		this.siteProperty = siteProperty;
	}

	public Map<Integer, Integer> getBatchTypeMap() {
		return batchTypeMap;
	}

	public void setBatchTypeMap(Map<Integer, Integer> batchTypeMap) {
		this.batchTypeMap = batchTypeMap;
	}

	public Map<Integer, Map<Integer, Integer>> getBatchTypeDrillMap() {
		return batchTypeDrillMap;
	}

	public void setBatchTypeDrillMap(Map<Integer, Map<Integer, Integer>> batchTypeDrillMap) {
		this.batchTypeDrillMap = batchTypeDrillMap;
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

	public Double getTotalHTFacture() {
		return totalHTFacture;
	}

	public void setTotalHTFacture(Double totalHTFacture) {
		this.totalHTFacture = totalHTFacture;
	}

	public Double getTotalFourniture() {
		return totalFourniture;
	}

	public void setTotalFourniture(Double totalFourniture) {
		this.totalFourniture = totalFourniture;
	}

	public Double getTotalEmissions() {
		return totalEmissions;
	}

	public void setTotalEmissions(Double totalEmissions) {
		this.totalEmissions = totalEmissions;
	}

	public Double getMontalSite() {
		return montalSite;
	}

	public void setMontalSite(Double montalSite) {
		this.montalSite = montalSite;
	}

	public Double getTotalMontal() {
		return totalMontal;
	}

	public void setTotalMontal(Double totalMontal) {
		this.totalMontal = totalMontal;
	}

	public Double getMontalSurface() {
		return montalSurface;
	}

	public void setMontalSurface(Double montalSurface) {
		this.montalSurface = montalSurface;
	}

	public Double getMontalSurfaceTotal() {
		return montalSurfaceTotal;
	}

	public void setMontalSurfaceTotal(Double montalSurfaceTotal) {
		this.montalSurfaceTotal = montalSurfaceTotal;
	}

	public Double getEmission() {
		return emission;
	}

	public void setEmission(Double emission) {
		this.emission = emission;
	}

	public Double getEmissionTotal() {
		return emissionTotal;
	}

	public void setEmissionTotal(Double emissionTotal) {
		this.emissionTotal = emissionTotal;
	}

	public Double getWater() {
		return water;
	}

	public void setWater(Double water) {
		this.water = water;
	}

	public Double getWaterTotal() {
		return waterTotal;
	}

	public void setWaterTotal(Double waterTotal) {
		this.waterTotal = waterTotal;
	}

	public List<Object[]> getDetailContractLst() {
		return detailContractLst;
	}

	public void setDetailContractLst(List<Object[]> detailContractLst) {
		this.detailContractLst = detailContractLst;
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
