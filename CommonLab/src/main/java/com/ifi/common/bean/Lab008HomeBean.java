package com.ifi.common.bean;

import java.util.List;

/**
 * Created by Kim Anh
 */
public class Lab008HomeBean {

	private Integer siteId;
	private String siteName;
	// private String time;
	private Integer moduleHeating;
	private Integer moduleOutsite;
	private Integer moduleDeparture;
	private List<Lab008ECSPipeBean> lab008ECSPipeLst;
	private List<Lab008ECSPipeBean> lab008ChauffageLst;
	private String fromDate;
	private String toDate;
	private Integer subscribedPower;
	private String energyProvider;

	private Integer boilerType;
	private String boilerModel;
	private Integer boilerYear;
	private Integer boilerTheoryPower;
	private Integer consumptionGasNaturalPCI;
	private Integer consumptionGasNaturalPCS;
	private Integer consumptionProduction;
	private Integer lossBySmoke;
	private Integer lossByRadnConvection;
	private Integer productionENR;
	private Boolean usedECSproduction;
	private Boolean usedHeatproduction;

	public Integer getBoilerType() {
		return boilerType;
	}

	public void setBoilerType(Integer boilerType) {
		this.boilerType = boilerType;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public Integer getModuleHeating() {
		return moduleHeating;
	}

	public void setModuleHeating(Integer moduleHeating) {
		this.moduleHeating = moduleHeating;
	}

	public Integer getModuleOutsite() {
		return moduleOutsite;
	}

	public void setModuleOutsite(Integer moduleOutsite) {
		this.moduleOutsite = moduleOutsite;
	}

	public Integer getModuleDeparture() {
		return moduleDeparture;
	}

	public void setModuleDeparture(Integer moduleDeparture) {
		this.moduleDeparture = moduleDeparture;
	}

	public List<Lab008ECSPipeBean> getLab008ECSPipeLst() {
		return lab008ECSPipeLst;
	}

	public void setLab008ECSPipeLst(List<Lab008ECSPipeBean> lab008ecsPipeLst) {
		lab008ECSPipeLst = lab008ecsPipeLst;
	}

	public List<Lab008ECSPipeBean> getLab008ChauffageLst() {
		return lab008ChauffageLst;
	}

	public void setLab008ChauffageLst(List<Lab008ECSPipeBean> lab008ChauffageLst) {
		this.lab008ChauffageLst = lab008ChauffageLst;
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

	public String getBoilerModel() {
		return boilerModel;
	}

	public void setBoilerModel(String boilerModel) {
		this.boilerModel = boilerModel;
	}

	public Integer getBoilerYear() {
		return boilerYear;
	}

	public void setBoilerYear(Integer boilerYear) {
		this.boilerYear = boilerYear;
	}

	public Integer getBoilerTheoryPower() {
		return boilerTheoryPower;
	}

	public void setBoilerTheoryPower(Integer boilerTheoryPower) {
		this.boilerTheoryPower = boilerTheoryPower;
	}

	public Integer getConsumptionGasNaturalPCI() {
		return consumptionGasNaturalPCI;
	}

	public void setConsumptionGasNaturalPCI(Integer consumptionGasNaturalPCI) {
		this.consumptionGasNaturalPCI = consumptionGasNaturalPCI;
	}

	public Integer getConsumptionGasNaturalPCS() {
		return consumptionGasNaturalPCS;
	}

	public void setConsumptionGasNaturalPCS(Integer consumptionGasNaturalPCS) {
		this.consumptionGasNaturalPCS = consumptionGasNaturalPCS;
	}

	public Integer getConsumptionProduction() {
		return consumptionProduction;
	}

	public void setConsumptionProduction(Integer consumptionProduction) {
		this.consumptionProduction = consumptionProduction;
	}

	public Integer getLossBySmoke() {
		return lossBySmoke;
	}

	public void setLossBySmoke(Integer lossBySmoke) {
		this.lossBySmoke = lossBySmoke;
	}

	public Integer getLossByRadnConvection() {
		return lossByRadnConvection;
	}

	public void setLossByRadnConvection(Integer lossByRadnConvection) {
		this.lossByRadnConvection = lossByRadnConvection;
	}

	public Integer getProductionENR() {
		return productionENR;
	}

	public void setProductionENR(Integer productionENR) {
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
