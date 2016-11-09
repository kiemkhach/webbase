package com.ifi.common.bean;

import java.util.List;

public class Lab008ConsommationBean {
	// Signature énergétique de la chaufferie Chart
	private List<Object[]> consommationRealLst;
	private double linearA;
	private int linearB;
	private double rSquare;
	private int percent;

	// Modèle prédictif
	private List<Object[]> consommationOnRangeLst;
	private List<Object[]> consommationOutRangeLst;
	private List<Object[]> consommationTheoryLst;
	private List<Object[]> limiteBasseLst;
	// private List<Object[]> limiteHauteLst;
	private List<Object[]> limitRangeLst;
	private List<Object[]> lineLst;
	private long fromDate;
	private long toDate;
	private List<Long> listStartYear;
	private List<Long> listDisplayYear;
	private int siteId;
	private String siteName;
	private String fromDateStr;
	private String toDateStr;
	private List<Lab008SaveTemplateBean> saveTemplateBeanLst;
	private Boolean modelIsStart;
	private Boolean modelIsEnd;

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

	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public double getLinearA() {
		return linearA;
	}

	public void setLinearA(double linearA) {
		this.linearA = linearA;
	}

	public int getLinearB() {
		return linearB;
	}

	public void setLinearB(int linearB) {
		this.linearB = linearB;
	}

	public double getrSquare() {
		return rSquare;
	}

	public void setrSquare(double rSquare) {
		this.rSquare = rSquare;
	}

	public List<Object[]> getConsommationRealLst() {
		return consommationRealLst;
	}

	public void setConsommationRealLst(List<Object[]> consommationRealLst) {
		this.consommationRealLst = consommationRealLst;
	}

	public List<Object[]> getConsommationOnRangeLst() {
		return consommationOnRangeLst;
	}

	public void setConsommationOnRangeLst(List<Object[]> consommationOnRangeLst) {
		this.consommationOnRangeLst = consommationOnRangeLst;
	}

	public List<Object[]> getConsommationOutRangeLst() {
		return consommationOutRangeLst;
	}

	public void setConsommationOutRangeLst(List<Object[]> consommationOutRangeLst) {
		this.consommationOutRangeLst = consommationOutRangeLst;
	}

	public List<Object[]> getConsommationTheoryLst() {
		return consommationTheoryLst;
	}

	public void setConsommationTheoryLst(List<Object[]> consommationTheoryLst) {
		this.consommationTheoryLst = consommationTheoryLst;
	}

	public List<Object[]> getLimiteBasseLst() {
		return limiteBasseLst;
	}

	public void setLimiteBasseLst(List<Object[]> limiteBasseLst) {
		this.limiteBasseLst = limiteBasseLst;
	}

	// public List<Object[]> getLimiteHauteLst() {
	// return limiteHauteLst;
	// }
	//
	// public void setLimiteHauteLst(List<Object[]> limiteHauteLst) {
	// this.limiteHauteLst = limiteHauteLst;
	// }

	public List<Object[]> getLineLst() {
		return lineLst;
	}

	public void setLineLst(List<Object[]> lineLst) {
		this.lineLst = lineLst;
	}

	public long getFromDate() {
		return fromDate;
	}

	public void setFromDate(long fromDate) {
		this.fromDate = fromDate;
	}

	public long getToDate() {
		return toDate;
	}

	public void setToDate(long toDate) {
		this.toDate = toDate;
	}

	public List<Object[]> getLimitRangeLst() {
		return limitRangeLst;
	}

	public void setLimitRangeLst(List<Object[]> limitRangeLst) {
		this.limitRangeLst = limitRangeLst;
	}

	public int getPercent() {
		return percent;
	}

	public void setPercent(int percent) {
		this.percent = percent;
	}

	public List<Long> getListStartYear() {
		return listStartYear;
	}

	public void setListStartYear(List<Long> listStartYear) {
		this.listStartYear = listStartYear;
	}

	public List<Long> getListDisplayYear() {
		return listDisplayYear;
	}

	public void setListDisplayYear(List<Long> listDisplayYear) {
		this.listDisplayYear = listDisplayYear;
	}

	public List<Lab008SaveTemplateBean> getSaveTemplateBeanLst() {
		return saveTemplateBeanLst;
	}

	public void setSaveTemplateBeanLst(List<Lab008SaveTemplateBean> saveTemplateBeanLst) {
		this.saveTemplateBeanLst = saveTemplateBeanLst;
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
}
