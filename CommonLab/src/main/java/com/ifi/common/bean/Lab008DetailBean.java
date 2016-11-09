package com.ifi.common.bean;

import java.util.List;

/**
 * Created by Kim Anh
 */
public class Lab008DetailBean {

	private Integer siteId;
	private String siteName;
	private Integer moduleAmbianteLogement;
	private Integer moduleProductionChauffage;
	private Integer moduleProductionECS;
	private Integer moduleRecyclageECS;
	private Integer moduleOutsite;
	private List<Lab008ECSPipeBean> moduleAmbianteLogementLst;
	private List<Lab008ECSPipeBean> moduleProductionChauffageLst;
	private List<Lab008ECSPipeBean> moduleProductionECSLst;
	private List<Lab008ECSPipeBean> moduleRecyclageECSLst;
	private List<Lab008ECSPipeBean> moduleWaterLst;
	private List<Lab008ECSPipeBean> ventilationLst;
	private Integer temperatureAmbianteLogementLimit;
	private Integer temperatureProductionChauffageLimit;
	private Integer temperatureProductionECSLimit;
	private Integer temperatureRecyclageECSLimit;
	private String fromDate;
	private String toDate;
	private Integer moduleWater;
	private String unit;
	private Integer boilerType;
	private Boolean isVentilation;

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public Integer getModuleAmbianteLogement() {
		return moduleAmbianteLogement;
	}

	public void setModuleAmbianteLogement(Integer moduleAmbianteLogement) {
		this.moduleAmbianteLogement = moduleAmbianteLogement;
	}

	public Integer getModuleProductionChauffage() {
		return moduleProductionChauffage;
	}

	public void setModuleProductionChauffage(Integer moduleProductionChauffage) {
		this.moduleProductionChauffage = moduleProductionChauffage;
	}

	public Integer getModuleProductionECS() {
		return moduleProductionECS;
	}

	public void setModuleProductionECS(Integer moduleProductionECS) {
		this.moduleProductionECS = moduleProductionECS;
	}

	public Integer getModuleRecyclageECS() {
		return moduleRecyclageECS;
	}

	public void setModuleRecyclageECS(Integer moduleRecyclageECS) {
		this.moduleRecyclageECS = moduleRecyclageECS;
	}

	public Integer getModuleOutsite() {
		return moduleOutsite;
	}

	public void setModuleOutsite(Integer moduleOutsite) {
		this.moduleOutsite = moduleOutsite;
	}

	public List<Lab008ECSPipeBean> getModuleAmbianteLogementLst() {
		return moduleAmbianteLogementLst;
	}

	public void setModuleAmbianteLogementLst(List<Lab008ECSPipeBean> moduleAmbianteLogementLst) {
		this.moduleAmbianteLogementLst = moduleAmbianteLogementLst;
	}

	public List<Lab008ECSPipeBean> getModuleProductionChauffageLst() {
		return moduleProductionChauffageLst;
	}

	public void setModuleProductionChauffageLst(List<Lab008ECSPipeBean> moduleProductionChauffageLst) {
		this.moduleProductionChauffageLst = moduleProductionChauffageLst;
	}

	public List<Lab008ECSPipeBean> getModuleProductionECSLst() {
		return moduleProductionECSLst;
	}

	public void setModuleProductionECSLst(List<Lab008ECSPipeBean> moduleProductionECSLst) {
		this.moduleProductionECSLst = moduleProductionECSLst;
	}

	public List<Lab008ECSPipeBean> getModuleRecyclageECSLst() {
		return moduleRecyclageECSLst;
	}

	public void setModuleRecyclageECSLst(List<Lab008ECSPipeBean> moduleRecyclageECSLst) {
		this.moduleRecyclageECSLst = moduleRecyclageECSLst;
	}

	public Integer getTemperatureAmbianteLogementLimit() {
		return temperatureAmbianteLogementLimit;
	}

	public void setTemperatureAmbianteLogementLimit(Integer temperatureAmbianteLogementLimit) {
		this.temperatureAmbianteLogementLimit = temperatureAmbianteLogementLimit;
	}

	public Integer getTemperatureProductionChauffageLimit() {
		return temperatureProductionChauffageLimit;
	}

	public void setTemperatureProductionChauffageLimit(Integer temperatureProductionChauffageLimit) {
		this.temperatureProductionChauffageLimit = temperatureProductionChauffageLimit;
	}

	public Integer getTemperatureProductionECSLimit() {
		return temperatureProductionECSLimit;
	}

	public void setTemperatureProductionECSLimit(Integer temperatureProductionECSLimit) {
		this.temperatureProductionECSLimit = temperatureProductionECSLimit;
	}

	public Integer getTemperatureRecyclageECSLimit() {
		return temperatureRecyclageECSLimit;
	}

	public void setTemperatureRecyclageECSLimit(Integer temperatureRecyclageECSLimit) {
		this.temperatureRecyclageECSLimit = temperatureRecyclageECSLimit;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public List<Lab008ECSPipeBean> getModuleWaterLst() {
		return moduleWaterLst;
	}

	public void setModuleWaterLst(List<Lab008ECSPipeBean> moduleWaterLst) {
		this.moduleWaterLst = moduleWaterLst;
	}

	public Integer getModuleWater() {
		return moduleWater;
	}

	public void setModuleWater(Integer moduleWater) {
		this.moduleWater = moduleWater;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Integer getBoilerType() {
		return boilerType;
	}

	public void setBoilerType(Integer boilerType) {
		this.boilerType = boilerType;
	}

	public Boolean getIsVentilation() {
		return isVentilation;
	}

	public void setIsVentilation(Boolean isVentilation) {
		this.isVentilation = isVentilation;
	}

	public List<Lab008ECSPipeBean> getVentilationLst() {
		return ventilationLst;
	}

	public void setVentilationLst(List<Lab008ECSPipeBean> ventilationLst) {
		this.ventilationLst = ventilationLst;
	}

}
