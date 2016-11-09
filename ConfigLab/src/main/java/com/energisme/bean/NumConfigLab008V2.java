package com.energisme.bean;

import java.util.List;

import com.ifi.lab.LabDAO.model.Lab008ECS;;

public class NumConfigLab008V2 {
	private Integer id;
	private String siteId;
	private String siteName;
	private String uriIcon;
	private String logo;
	private String moduleHeating;
	private String moduleOutsite;
	private List<Lab008ECS> lab008ConsommationChauffageLst;
	private List<Lab008ECS> lab008ConsommationECSLst;
	private List<Lab008ECS> lab008TemperatureAmbianteLogementLst;
	private List<Lab008ECS> lab008TemperatureProductionChauffageLst;
	private List<Lab008ECS> lab008TemperatureProductionECSLst;
	private List<Lab008ECS> lab008TemperatureRecyclageECSLst;
	private Integer temperatureAmbianteLogementLimit;
	private Integer temperatureProductionChauffageLimit;
	private Integer temperatureProductionECSLimit;
	private Integer temperatureRecyclageECSLimit;
	private List<Lab008ECS> moduleWaterLst;
	private List<Lab008ECS> moduleVentilationLst;
	private Integer boilerType;
	private String unitWater;
	private Integer subscribedPower;
	private String energyProvider;
	private String ventilation;
	private String productionENR;
	private Boolean usedECSproduction;
	private Boolean usedHeatproduction;

	public List<Lab008ECS> getModuleWaterLst() {
		return moduleWaterLst;
	}

	public void setModuleWaterLst(List<Lab008ECS> moduleWaterLst) {
		this.moduleWaterLst = moduleWaterLst;
	}

	public List<Lab008ECS> getModuleVentilationLst() {
		return moduleVentilationLst;
	}

	public void setModuleVentilationLst(List<Lab008ECS> moduleVentilationLst) {
		this.moduleVentilationLst = moduleVentilationLst;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getUriIcon() {
		return uriIcon;
	}

	public void setUriIcon(String uriIcon) {
		this.uriIcon = uriIcon;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getModuleHeating() {
		return moduleHeating;
	}

	public void setModuleHeating(String moduleHeating) {
		this.moduleHeating = moduleHeating;
	}

	public String getModuleOutsite() {
		return moduleOutsite;
	}

	public void setModuleOutsite(String moduleOutsite) {
		this.moduleOutsite = moduleOutsite;
	}

	public List<Lab008ECS> getLab008ConsommationChauffageLst() {
		return lab008ConsommationChauffageLst;
	}

	public void setLab008ConsommationChauffageLst(List<Lab008ECS> lab008ConsommationChauffageLst) {
		this.lab008ConsommationChauffageLst = lab008ConsommationChauffageLst;
	}

	public List<Lab008ECS> getLab008ConsommationECSLst() {
		return lab008ConsommationECSLst;
	}

	public void setLab008ConsommationECSLst(List<Lab008ECS> lab008ConsommationECSLst) {
		this.lab008ConsommationECSLst = lab008ConsommationECSLst;
	}

	public List<Lab008ECS> getLab008TemperatureAmbianteLogementLst() {
		return lab008TemperatureAmbianteLogementLst;
	}

	public void setLab008TemperatureAmbianteLogementLst(List<Lab008ECS> lab008TemperatureAmbianteLogementLst) {
		this.lab008TemperatureAmbianteLogementLst = lab008TemperatureAmbianteLogementLst;
	}

	public List<Lab008ECS> getLab008TemperatureProductionChauffageLst() {
		return lab008TemperatureProductionChauffageLst;
	}

	public void setLab008TemperatureProductionChauffageLst(List<Lab008ECS> lab008TemperatureProductionChauffageLst) {
		this.lab008TemperatureProductionChauffageLst = lab008TemperatureProductionChauffageLst;
	}

	public List<Lab008ECS> getLab008TemperatureProductionECSLst() {
		return lab008TemperatureProductionECSLst;
	}

	public void setLab008TemperatureProductionECSLst(List<Lab008ECS> lab008TemperatureProductionECSLst) {
		this.lab008TemperatureProductionECSLst = lab008TemperatureProductionECSLst;
	}

	public List<Lab008ECS> getLab008TemperatureRecyclageECSLst() {
		return lab008TemperatureRecyclageECSLst;
	}

	public void setLab008TemperatureRecyclageECSLst(List<Lab008ECS> lab008TemperatureRecyclageECSLst) {
		this.lab008TemperatureRecyclageECSLst = lab008TemperatureRecyclageECSLst;
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

	public String getUnitWater() {
		return unitWater;
	}

	public void setUnitWater(String unitWater) {
		this.unitWater = unitWater;
	}

	public Integer getBoilerType() {
		return boilerType;
	}

	public void setBoilerType(Integer boilerType) {
		this.boilerType = boilerType;
	}

	public Integer getSubscribedPower() {
		return subscribedPower;
	}

	public void setSubscribedPower(Integer subscribedPower) {
		this.subscribedPower = subscribedPower;
	}

	public String getEnergyProvider() {
		return energyProvider;
	}

	public void setEnergyProvider(String energyProvider) {
		this.energyProvider = energyProvider;
	}

	public String getVentilation() {
		return ventilation;
	}

	public void setVentilation(String ventilation) {
		this.ventilation = ventilation;
	}

	public String getProductionENR() {
		return productionENR;
	}

	public void setProductionENR(String productionENR) {
		this.productionENR = productionENR;
	}

	public Boolean getUsedECSproduction() {
		return usedECSproduction;
	}

	public void setUsedECSproduction(Boolean usedECSproduction) {
		this.usedECSproduction = usedECSproduction;
	}

	public Boolean getUsedHeatproduction() {
		return usedHeatproduction;
	}

	public void setUsedHeatproduction(Boolean usedHeatproduction) {
		this.usedHeatproduction = usedHeatproduction;
	}

}
