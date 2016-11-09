package com.ifi.lab.LabDAO.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "config_lab006")
public class ConfigLab006{

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "site_id")
	private Integer siteId;
	@Column(name = "site_name")
	private String siteName;
	
	@Column(name="common_portion_moduleId")
	private String commonPortionModuleId;
	
	@Column(name="energy_consumption_moduleId")
	private String energyConsumptionModuleId;

	@Column(name = "summer_start_date")
	private String summerStartDate;

	@Column(name = "winter_start_date")
	private String winterStartDate;

	@Column(name = "start_hour_HC")
	private Integer startHourHC;

	@Column(name = "start_hour_HP")
	private Integer startHourHP;

	@Column(name = "logo")
	private String logo;
	@Column(name = "uri_icon")
	private String uriIcon;
	@Column(name = "report_name")
	private String reportName;
	
	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
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

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getUriIcon() {
		return uriIcon;
	}

	public void setUriIcon(String uriIcon) {
		this.uriIcon = uriIcon;
	}

	public String getCommonPortionModuleId() {
		return commonPortionModuleId;
	}

	public void setCommonPortionModuleId(String commonPortionModuleId) {
		this.commonPortionModuleId = commonPortionModuleId;
	}

	public String getEnergyConsumptionModuleId() {
		return energyConsumptionModuleId;
	}

	public void setEnergyConsumptionModuleId(String energyConsumptionModuleId) {
		this.energyConsumptionModuleId = energyConsumptionModuleId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSummerStartDate() {
		return summerStartDate;
	}

	public void setSummerStartDate(String summerStartDate) {
		this.summerStartDate = summerStartDate;
	}

	public String getWinterStartDate() {
		return winterStartDate;
	}

	public void setWinterStartDate(String winterStartDate) {
		this.winterStartDate = winterStartDate;
	}

	public Integer getStartHourHC() {
		return startHourHC;
	}

	public void setStartHourHC(Integer startHourHC) {
		this.startHourHC = startHourHC;
	}

	public Integer getStartHourHP() {
		return startHourHP;
	}

	public void setStartHourHP(Integer startHourHP) {
		this.startHourHP = startHourHP;
	}
}
