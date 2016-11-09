package com.ifi.common.bean.Idex;

import java.util.List;

public class Lab010EnergyTypeBean {
	private Integer energyTypeId;
	private String energyName;
	private Float consommation;
	private Integer rate;
	private Float montal;
	private List<Object[]> subLst;
//	private String energyType;

	public Integer getEnergyTypeId() {
		return energyTypeId;
	}

	public void setEnergyTypeId(Integer energyTypeId) {
		this.energyTypeId = energyTypeId;
	}

	public String getEnergyName() {
		return energyName;
	}

	public void setEnergyName(String energyName) {
		this.energyName = energyName;
	}

	public Float getConsommation() {
		return consommation;
	}

	public void setConsommation(Float consommation) {
		this.consommation = consommation;
	}

	public Integer getRate() {
		return rate;
	}

	public void setRate(Integer rate) {
		this.rate = rate;
	}

	public Float getMontal() {
		return montal;
	}

	public void setMontal(Float montal) {
		this.montal = montal;
	}

	public List<Object[]> getSubLst() {
		return subLst;
	}

	public void setSubLst(List<Object[]> subLst) {
		this.subLst = subLst;
	}

//	public String getEnergyType() {
//		return energyType;
//	}
//
//	public void setEnergyType(String energyType) {
//		this.energyType = energyType;
//	}

}
