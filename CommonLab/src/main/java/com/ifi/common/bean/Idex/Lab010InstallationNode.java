package com.ifi.common.bean.Idex;

import java.util.List;

public class Lab010InstallationNode {
	private Integer id;
	private String name;
	private String icon;
	private List<Lab010CompteurBean> childLst;
	private List<Integer> parentLst;
	private Integer typeEnergy;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public List<Lab010CompteurBean> getChildLst() {
		return childLst;
	}

	public void setChildLst(List<Lab010CompteurBean> childLst) {
		this.childLst = childLst;
	}

	public List<Integer> getParentLst() {
		return parentLst;
	}

	public void setParentLst(List<Integer> parentLst) {
		this.parentLst = parentLst;
	}

	public Integer getTypeEnergy() {
		return typeEnergy;
	}

	public void setTypeEnergy(Integer typeEnergy) {
		this.typeEnergy = typeEnergy;
	}
}
