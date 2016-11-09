package com.energisme.bean;

import java.util.List;

public class NumConfigLab006 {
	private Integer id;
	private String siteName;
	private String siteId;
	private String commonPortionModuleId;
	private String energyConsumptionModuleId;
	private String summerStartDate;
	private String winterStartDate;
	private Integer startHourHC;
	private Integer startHourHP;
	private String logo;
	private String uriIcon;
	private String reportName;
	private List<com.ifi.lab.LabDAO.model.ConfigLab006Client> listClients;
	
	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
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

	public List<com.ifi.lab.LabDAO.model.ConfigLab006Client> getListClients() {
		return listClients;
	}

	public void setListClients(List<com.ifi.lab.LabDAO.model.ConfigLab006Client> listClients) {
		this.listClients = listClients;
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
