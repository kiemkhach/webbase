package com.ifi.common.bean.Idex;

import java.util.List;

public class Lab010CostAnalysis {
	// Achart
	private Float totalAchart;
	private String montalUnit;
	private List<Lab010EnergyTypeBean> energyTypeBeanLst;
	private List<Lab010SiteBean> siteBeanLst;
	private List<Lab010SiteBean> clientBeanLst;
	private Float totalVente;
	private Float totalBilan;
	
	private String fromMonth;
	private String toMonth;
	
	public List<Lab010SiteBean> getClientBeanLst() {
		return clientBeanLst;
	}

	public void setClientBeanLst(List<Lab010SiteBean> clientBeanLst) {
		this.clientBeanLst = clientBeanLst;
	}

	public Float getTotalAchart() {
		return totalAchart;
	}

	public void setTotalAchart(Float totalAchart) {
		this.totalAchart = totalAchart;
	}

	public String getMontalUnit() {
		return montalUnit;
	}

	public void setMontalUnit(String montalUnit) {
		this.montalUnit = montalUnit;
	}

	public List<Lab010EnergyTypeBean> getEnergyTypeBeanLst() {
		return energyTypeBeanLst;
	}

	public void setEnergyTypeBeanLst(List<Lab010EnergyTypeBean> energyTypeBeanLst) {
		this.energyTypeBeanLst = energyTypeBeanLst;
	}

	public List<Lab010SiteBean> getSiteBeanLst() {
		return siteBeanLst;
	}

	public void setSiteBeanLst(List<Lab010SiteBean> siteBeanLst) {
		this.siteBeanLst = siteBeanLst;
	}

	public Float getTotalBilan() {
		return totalBilan;
	}

	public void setTotalBilan(Float totalBilan) {
		this.totalBilan = totalBilan;
	}

	public Float getTotalVente() {
		return totalVente;
	}

	public void setTotalVente(Float totalVente) {
		this.totalVente = totalVente;
	}

	public String getFromMonth() {
		return fromMonth;
	}

	public void setFromMonth(String fromMonth) {
		this.fromMonth = fromMonth;
	}

	public String getToMonth() {
		return toMonth;
	}

	public void setToMonth(String toMonth) {
		this.toMonth = toMonth;
	}

}
