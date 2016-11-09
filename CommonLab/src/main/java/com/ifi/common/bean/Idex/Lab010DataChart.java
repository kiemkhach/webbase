package com.ifi.common.bean.Idex;

import java.util.List;

public class Lab010DataChart {
	List<Lab010CompteurBean> compteurLst;
	List<Lab010DataSeriesChart> dataSeriesChartLst;
	private String unit;

	public List<Lab010CompteurBean> getCompteurLst() {
		return compteurLst;
	}

	public void setCompteurLst(List<Lab010CompteurBean> compteurLst) {
		this.compteurLst = compteurLst;
	}

	public List<Lab010DataSeriesChart> getDataSeriesChartLst() {
		return dataSeriesChartLst;
	}

	public void setDataSeriesChartLst(List<Lab010DataSeriesChart> dataSeriesChartLst) {
		this.dataSeriesChartLst = dataSeriesChartLst;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	
}
