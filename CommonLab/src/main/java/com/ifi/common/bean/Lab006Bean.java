package com.ifi.common.bean;

import java.util.List;

public class Lab006Bean {
	private Integer id;
	private String siteName;
	private String siteId;
	private Double totalEnergyReal;
	private Double totalEnergyTheory;
	private Double commonPortionReal;
	private Double commonPortionTheory;
	private Double commonPortionPercent;
	private List<Lab006Client> clients;
	private String logo;
	private String uriIcon;
	private String fromDate;
	private String toDate;
	private String summerTime;
	private String winterTime;
	private String timeHC;
	private String timeHP;

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


	public Double getTotalEnergyReal() {
		return totalEnergyReal;
	}

	public void setTotalEnergyReal(Double totalEnergyReal) {
		this.totalEnergyReal = totalEnergyReal;
	}

	public Double getTotalEnergyTheory() {
		return totalEnergyTheory;
	}

	public Double getCommonPortionPercent() {
		return commonPortionPercent;
	}

	public void setCommonPortionPercent(Double commonPortionPercent) {
		this.commonPortionPercent = commonPortionPercent;
	}

	public void setTotalEnergyTheory(Double totalEnergyTheory) {
		this.totalEnergyTheory = totalEnergyTheory;
	}

	public Double getCommonPortionReal() {
		return commonPortionReal;
	}

	public void setCommonPortionReal(Double commonPortionReal) {
		this.commonPortionReal = commonPortionReal;
	}

	public Double getCommonPortionTheory() {
		return commonPortionTheory;
	}

	public void setCommonPortionTheory(Double commonPortionTheory) {
		this.commonPortionTheory = commonPortionTheory;
	}

	public List<Lab006Client> getClients() {
		return clients;
	}

	public void setClients(List<Lab006Client> clients) {
		this.clients = clients;
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

	public String getSummerTime() {
		return summerTime;
	}

	public void setSummerTime(String summerTime) {
		this.summerTime = summerTime;
	}

	public String getWinterTime() {
		return winterTime;
	}

	public void setWinterTime(String winterTime) {
		this.winterTime = winterTime;
	}

	public String getTimeHC() {
		return timeHC;
	}

	public void setTimeHC(String timeHC) {
		this.timeHC = timeHC;
	}

	public String getTimeHP() {
		return timeHP;
	}

	public void setTimeHP(String timeHP) {
		this.timeHP = timeHP;
	}
}
