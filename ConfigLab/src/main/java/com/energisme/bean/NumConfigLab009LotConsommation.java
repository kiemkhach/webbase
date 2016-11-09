package com.energisme.bean;

import java.util.List;

import com.ifi.lab.LabDAO.model.Lab009LotConsommation;

public class NumConfigLab009LotConsommation {
	private List<Lab009LotConsommation> lab009LotConsommationsLst;
	private Integer lotId;
	private String lotName;
	private String lotCode;
	private String colorCode;
	public List<Lab009LotConsommation> getLab009LotConsommationsLst() {
		return lab009LotConsommationsLst;
	}
	public void setLab009LotConsommationsLst(List<Lab009LotConsommation> lab009LotConsommationsLst) {
		this.lab009LotConsommationsLst = lab009LotConsommationsLst;
	}
	public String getLotName() {
		return lotName;
	}
	public void setLotName(String lotName) {
		this.lotName = lotName;
	}
	public String getLotCode() {
		return lotCode;
	}
	public void setLotCode(String lotCode) {
		this.lotCode = lotCode;
	}
	public String getColorCode() {
		return colorCode;
	}
	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}
	public Integer getLotId() {
		return lotId;
	}
	public void setLotId(Integer lotId) {
		this.lotId = lotId;
	}
	
}
