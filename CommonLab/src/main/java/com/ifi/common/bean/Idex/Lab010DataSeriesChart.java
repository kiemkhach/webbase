package com.ifi.common.bean.Idex;

import java.util.List;

public class Lab010DataSeriesChart {
	private Integer ID;
	private String name;
	private Integer couterType;
	private Integer isSubModule;
	private Integer parentId;

	public Integer getCouterType() {
		return couterType;
	}

	public void setCouterType(Integer couterType) {
		this.couterType = couterType;
	}

	public Integer getIsSubModule() {
		return isSubModule;
	}

	public void setIsSubModule(Integer isSubModule) {
		this.isSubModule = isSubModule;
	}

	private List<Object[]> lstData;

	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Object[]> getLstData() {
		return lstData;
	}

	public void setLstData(List<Object[]> lstData) {
		this.lstData = lstData;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	
	

}
