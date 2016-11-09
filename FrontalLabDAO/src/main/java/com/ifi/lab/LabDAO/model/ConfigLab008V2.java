package com.ifi.lab.LabDAO.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "config_lab008_v2")
public class ConfigLab008V2 {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "site_id")
	private Integer siteId;
	@Column(name = "site_name")
	private String siteName;
	@Column(name = "module_outsite")
	private String moduleOutsite;
	@Column(name = "temperature_ambiante_logement_limit", columnDefinition = "int default 0")
	private Integer temperatureAmbianteLogementLimit;
	@Column(name = "temperature_production_chauffage_limit", columnDefinition = "int default 0")
	private Integer temperatureProductionChauffageLimit;
	@Column(name = "temperature_productionECS_limit", columnDefinition = "int default 0")
	private Integer temperatureProductionECSLimit;
	@Column(name = "temperature_recyclageECS_limit", columnDefinition = "int default 0")
	private Integer temperatureRecyclageECSLimit;
	@Column(name = "uri_icon")
	private String uriIcon;
	@Column(name = "logo")
	private String logo;
	@Column(name = "period_type")
	private Integer periodType;
	@Column(name = "unit_water")
	private String unitWater;
	// If boilerType = 1 draw new chart(urban heart) Else draw old
	// chart(PCI,PCS)
	@Column(name = "boiler_type", columnDefinition = "int default 1")
	private Integer boilerType;
	@Column(name = "subscribed_power", columnDefinition = "int default 1")
	private Integer subscribedPower;
	@Column(name = "energy_provider")
	private String energyProvider;

	// Chart data
	@Column(name = "avgAirTempModuleId")
	private String avgAirTempModuleId;
	@Column(name = "avgExtTempModuleId")
	private String avgExtTempModuleId;
	@Column(name = "boilerModel")
	private String boilerModel;
	@Column(name = "boilerYear")
	private Integer boilerYear;
	@Column(name = "boilerTheoryPower")
	private Double boilerTheoryPower;
	@Column(name = "gasNaturalModuleId")
	private String gasNaturalModuleId;
	@Column(name = "productionModuleId")
	private String productionModuleId;
	@Column(name = "coeff1", columnDefinition = "double default 1")
	private Double coeff1;
	@Column(name = "coeff2", columnDefinition = "double default 1")
	private Double coeff2;
	@Column(name = "coeff3", columnDefinition = "double default 1")
	private Double coeff3;
	@Column(name = "coeff4", columnDefinition = "double default 1")
	private Double coeff4;
	@Column(name = "coeff5", columnDefinition = "double default 1")
	private Double coeff5;
	@Column(name = "coeff6", columnDefinition = "double default 1")
	private Double coeff6;
	@Column(name = "coeffRadnConvection", columnDefinition = "double default 1")
	private Double coeffRadnConvection;
	@Column(name = "report_name")
	private String reportName;
	@Column(name = "fromDate")
	private Date fromDate;
	@Column(name = "modelPrecision")
	private Double modelPrecision;
	@Column(name = "chauffage")
	private String chauffageModuleId;
	@Column(name = "ecsZoneBasse")
	private String ecsZoneBasse;
	@Column(name = "ecsZoneHaute")
	private String ecsZoneHaute;

	// // New column
	// @Column(name = "ventilation")
	// private String ventilation;
	@Column(name = "productionENR")
	private String productionENR;
	@Column(name = "usedECSproduction")
	private Boolean usedECSproduction;
	@Column(name = "usedHeatproduction")
	private Boolean usedHeatproduction;

	@Column(name = "model_is_start")
	private Boolean modelIsStart;
	@Column(name = "model_is_end")
	private Boolean modelIsEnd;
	@Column(name = "model_date_start")
	private Date modelDateStart;
	@Column(name = "model_date_end")
	private Date modelDateEnd;

	public Integer getBoilerType() {
		return boilerType;
	}

	public void setBoilerType(Integer boilerType) {
		this.boilerType = boilerType;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getModuleOutsite() {
		return moduleOutsite;
	}

	public void setModuleOutsite(String moduleOutsite) {
		this.moduleOutsite = moduleOutsite;
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

	public Integer getPeriodType() {
		return periodType;
	}

	public void setPeriodType(Integer periodType) {
		this.periodType = periodType;
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

	public String getAvgAirTempModuleId() {
		return avgAirTempModuleId;
	}

	public void setAvgAirTempModuleId(String avgAirTempModuleId) {
		this.avgAirTempModuleId = avgAirTempModuleId;
	}

	public String getAvgExtTempModuleId() {
		return avgExtTempModuleId;
	}

	public void setAvgExtTempModuleId(String avgExtTempModuleId) {
		this.avgExtTempModuleId = avgExtTempModuleId;
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

	public Double getBoilerTheoryPower() {
		return boilerTheoryPower;
	}

	public void setBoilerTheoryPower(Double boilerTheoryPower) {
		this.boilerTheoryPower = boilerTheoryPower;
	}

	public String getGasNaturalModuleId() {
		return gasNaturalModuleId;
	}

	public void setGasNaturalModuleId(String gasNaturalModuleId) {
		this.gasNaturalModuleId = gasNaturalModuleId;
	}

	public String getProductionModuleId() {
		return productionModuleId;
	}

	public void setProductionModuleId(String productionModuleId) {
		this.productionModuleId = productionModuleId;
	}

	public Double getCoeff1() {
		return coeff1;
	}

	public void setCoeff1(Double coeff1) {
		this.coeff1 = coeff1;
	}

	public Double getCoeff2() {
		return coeff2;
	}

	public void setCoeff2(Double coeff2) {
		this.coeff2 = coeff2;
	}

	public Double getCoeff3() {
		return coeff3;
	}

	public void setCoeff3(Double coeff3) {
		this.coeff3 = coeff3;
	}

	public Double getCoeff4() {
		return coeff4;
	}

	public void setCoeff4(Double coeff4) {
		this.coeff4 = coeff4;
	}

	public Double getCoeff5() {
		return coeff5;
	}

	public void setCoeff5(Double coeff5) {
		this.coeff5 = coeff5;
	}

	public Double getCoeff6() {
		return coeff6;
	}

	public void setCoeff6(Double coeff6) {
		this.coeff6 = coeff6;
	}

	public Double getCoeffRadnConvection() {
		return coeffRadnConvection;
	}

	public void setCoeffRadnConvection(Double coeffRadnConvection) {
		this.coeffRadnConvection = coeffRadnConvection;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Double getModelPrecision() {
		return modelPrecision;
	}

	public void setModelPrecision(Double modelPrecision) {
		this.modelPrecision = modelPrecision;
	}

	public String getChauffageModuleId() {
		return chauffageModuleId;
	}

	public void setChauffageModuleId(String chauffageModuleId) {
		this.chauffageModuleId = chauffageModuleId;
	}

	public String getEcsZoneBasse() {
		return ecsZoneBasse;
	}

	public void setEcsZoneBasse(String ecsZoneBasse) {
		this.ecsZoneBasse = ecsZoneBasse;
	}

	public String getEcsZoneHaute() {
		return ecsZoneHaute;
	}

	public void setEcsZoneHaute(String ecsZoneHaute) {
		this.ecsZoneHaute = ecsZoneHaute;
	}

	// public String getVentilation() {
	// return ventilation;
	// }
	//
	// public void setVentilation(String ventilation) {
	// this.ventilation = ventilation;
	// }

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

	public Boolean getModelIsStart() {
		return modelIsStart;
	}

	public void setModelIsStart(Boolean modelIsStart) {
		this.modelIsStart = modelIsStart;
	}

	public Boolean getModelIsEnd() {
		return modelIsEnd;
	}

	public void setModelIsEnd(Boolean modelIsEnd) {
		this.modelIsEnd = modelIsEnd;
	}

	public Date getModelDateStart() {
		return modelDateStart;
	}

	public void setModelDateStart(Date modelDateStart) {
		this.modelDateStart = modelDateStart;
	}

	public Date getModelDateEnd() {
		return modelDateEnd;
	}

	public void setModelDateEnd(Date modelDateEnd) {
		this.modelDateEnd = modelDateEnd;
	}

}
