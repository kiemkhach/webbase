package com.ifi.common.bean.Idex;

import java.util.List;

public class Lab010CostChartBean {

	private String montalUnit;
	private List<Lab010CategoryCostBean> energyPurchasedLst;

	private List<Lab010CategoryCostBean> heatSoldLst;

	public List<Lab010CategoryCostBean> getEnergyPurchasedLst() {
		return energyPurchasedLst;
	}

	public void setEnergyPurchasedLst(List<Lab010CategoryCostBean> energyPurchasedLst) {
		this.energyPurchasedLst = energyPurchasedLst;
	}

	public List<Lab010CategoryCostBean> getHeatSoldLst() {
		return heatSoldLst;
	}

	public void setHeatSoldLst(List<Lab010CategoryCostBean> heatSoldLst) {
		this.heatSoldLst = heatSoldLst;
	}

	public String getMontalUnit() {
		return montalUnit;
	}

	public void setMontalUnit(String montalUnit) {
		this.montalUnit = montalUnit;
	}

}
