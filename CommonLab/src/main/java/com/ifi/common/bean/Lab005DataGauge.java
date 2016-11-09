package com.ifi.common.bean;

public class Lab005DataGauge{
	private Double elec;
	private Double gas;
	public Double getElec() {
		return elec;
	}
	public void setElec(Double elec) {
		this.elec = elec;
	}
	public Double getGas() {
		return gas;
	}
	public void setGas(Double gas) {
		this.gas = gas;
	}
	public Lab005DataGauge(Double elec, Double gas) {
		this.elec = elec;
		this.gas = gas;
	}
	public Lab005DataGauge() {
	}
}
