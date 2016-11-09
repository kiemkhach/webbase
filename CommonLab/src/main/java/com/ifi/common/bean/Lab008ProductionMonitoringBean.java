package com.ifi.common.bean;

import java.util.List;

public class Lab008ProductionMonitoringBean {
	private String siteName;
	private Integer subscribedPower;
	private List<String> nameLst;
	private List<List<Integer[]>> consommationLst;
	private List<Integer[]> totalLst;
	private List<Lab008ProductECSSummary> dataTableLst;
	private Integer siteId;
	private String fromDateStr;
	private String toDateStr;

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public List<String> getNameLst() {
		return nameLst;
	}

	public void setNameLst(List<String> nameLst) {
		this.nameLst = nameLst;
	}

	public Integer getSubscribedPower() {
		return subscribedPower;
	}

	public void setSubscribedPower(Integer subscribedPower) {
		this.subscribedPower = subscribedPower;
	}

	public List<List<Integer[]>> getConsommationLst() {
		return consommationLst;
	}

	public void setConsommationLst(List<List<Integer[]>> consommationLst) {
		this.consommationLst = consommationLst;
	}

	public List<Integer[]> getTotalLst() {
		return totalLst;
	}

	public void setTotalLst(List<Integer[]> totalLst) {
		this.totalLst = totalLst;
	}

	public List<Lab008ProductECSSummary> getDataTableLst() {
		return dataTableLst;
	}

	public void setDataTableLst(List<Lab008ProductECSSummary> dataTableLst) {
		this.dataTableLst = dataTableLst;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public String getFromDateStr() {
		return fromDateStr;
	}

	public void setFromDateStr(String fromDateStr) {
		this.fromDateStr = fromDateStr;
	}

	public String getToDateStr() {
		return toDateStr;
	}

	public void setToDateStr(String toDateStr) {
		this.toDateStr = toDateStr;
	}

}
