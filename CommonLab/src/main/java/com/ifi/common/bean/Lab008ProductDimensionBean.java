package com.ifi.common.bean;

import java.util.List;

public class Lab008ProductDimensionBean {
	private Integer siteId;
	private String siteName;
	private String fromDateStr;
	private String toDateStr;
	private Integer contractPower;
	private Integer reachMax;
	private Integer ratio;
	private Integer averageAllSite;
	private Integer minTempOutside;
	private List<Object> dataLst;
	private List<Object> listDimensionValue;
	private List<Object> lineLst;
	private double linearA;
	private double linearB;
	private double rSquare;
	
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
	public Integer getContractPower() {
		return contractPower;
	}
	public void setContractPower(Integer contractPower) {
		this.contractPower = contractPower;
	}
	public Integer getReachMax() {
		return reachMax;
	}
	public void setReachMax(Integer reachMax) {
		this.reachMax = reachMax;
	}
	public Integer getRatio() {
		return ratio;
	}
	public void setRatio(Integer ratio) {
		this.ratio = ratio;
	}
	public List<Object> getDataLst() {
		return dataLst;
	}
	public void setDataLst(List<Object> dataLst) {
		this.dataLst = dataLst;
	}
	public Integer getAverageAllSite() {
		return averageAllSite;
	}
	public void setAverageAllSite(Integer averageAllSite) {
		this.averageAllSite = averageAllSite;
	}
	public List<Object> getListDimensionValue() {
		return listDimensionValue;
	}
	public void setListDimensionValue(List<Object> listDimensionValue) {
		this.listDimensionValue = listDimensionValue;
	}
	public List<Object> getLineLst() {
		return lineLst;
	}
	public void setLineLst(List<Object> lineLst) {
		this.lineLst = lineLst;
	}
	public double getLinearA() {
		return linearA;
	}
	public void setLinearA(double linearA) {
		this.linearA = linearA;
	}
	public double getrSquare() {
		return rSquare;
	}
	public void setrSquare(double rSquare) {
		this.rSquare = rSquare;
	}
	public double getLinearB() {
		return linearB;
	}
	public void setLinearB(double linearB) {
		this.linearB = linearB;
	}
	public Integer getMinTempOutside() {
		return minTempOutside;
	}
	public void setMinTempOutside(Integer minTempOutside) {
		this.minTempOutside = minTempOutside;
	}
	
}
