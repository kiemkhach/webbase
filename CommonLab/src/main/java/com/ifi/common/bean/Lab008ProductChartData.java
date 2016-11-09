package com.ifi.common.bean;

import java.util.List;

public class Lab008ProductChartData {
	private List<double[]> listPCSValue;
	private List<double[]> listPCIValue;
	private List<double[]> listRateChargeValue;
	public List<double[]> getListPCSValue() {
		return listPCSValue;
	}
	public void setListPCSValue(List<double[]> listPCSValue) {
		this.listPCSValue = listPCSValue;
	}
	public List<double[]> getListPCIValue() {
		return listPCIValue;
	}
	public void setListPCIValue(List<double[]> listPCIValue) {
		this.listPCIValue = listPCIValue;
	}
	public List<double[]> getListRateChargeValue() {
		return listRateChargeValue;
	}
	public void setListRateChargeValue(List<double[]> listRateChargeValue) {
		this.listRateChargeValue = listRateChargeValue;
	}
}
