package com.ifi.common.bean.Idex;

import java.util.List;

public class Lab010CompteurBean {
	private Integer idexCounterId;
	private String name;
	private Integer counterType;
	private Integer isSubModule;
	private Integer parent;
	private Boolean isSelected;
	
	private List<Lab010CompteurBean> childLst;

	private Float nc;

	public Integer getIdexCounterId() {
		return idexCounterId;
	}

	public void setIdexCounterId(Integer idexCounterId) {
		this.idexCounterId = idexCounterId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCounterType() {
		return counterType;
	}

	public void setCounterType(Integer counterType) {
		this.counterType = counterType;
	}

	public Integer getIsSubModule() {
		return isSubModule;
	}

	public void setIsSubModule(Integer isSubModule) {
		this.isSubModule = isSubModule;
	}

	public Integer getParent() {
		return parent;
	}

	public void setParent(Integer parent) {
		this.parent = parent;
	}

	public Float getNc() {
		return nc;
	}

	public void setNc(Float nc) {
		this.nc = nc;
	}

	public List<Lab010CompteurBean> getChildLst() {
		return childLst;
	}

	public void setChildLst(List<Lab010CompteurBean> childLst) {
		this.childLst = childLst;
	}

	public Boolean getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}
	
}
