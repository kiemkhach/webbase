package com.ifi.common.bean;

import java.util.List;

public class Lab005Bean{
	/**
	 * 
	 */
	private List<Lab005DataGauge> lstConsomation;
	private Integer electric;
	private Integer gaz;
	private Integer gasPercent;
	private Integer elecPercent;
	private String startDate;
	private String siteName;
	private String siteId;
	
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
	public List<Lab005DataGauge> getLstConsomation() {
		return lstConsomation;
	}
	public void setLstConsomation(List<Lab005DataGauge> lstConsomation) {
		this.lstConsomation = lstConsomation;
	}
	public Integer getElectric() {
		return electric;
	}
	public void setElectric(Integer electric) {
		this.electric = electric;
	}
	public Integer getGaz() {
		return gaz;
	}
	public void setGaz(Integer gaz) {
		this.gaz = gaz;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public Integer getGasPercent() {
		return gasPercent;
	}
	public void setGasPercent(Integer gasPercent) {
		this.gasPercent = gasPercent;
	}
	public Integer getElecPercent() {
		return elecPercent;
	}
	public void setElecPercent(Integer elecPercent) {
		this.elecPercent = elecPercent;
	}
}

