package com.ifi.common.bean.Idex;

import java.util.List;

public class Lab010AddBean {
	private List<IdexKeyBean> parentCompteurLst;
	private List<IdexKeyBean> parentLst;
	private List<IdexKeyBean> idexKeyBeanLst;

	public List<IdexKeyBean> getParentCompteurLst() {
		return parentCompteurLst;
	}

	public void setParentCompteurLst(List<IdexKeyBean> parentCompteurLst) {
		this.parentCompteurLst = parentCompteurLst;
	}

	public List<IdexKeyBean> getParentLst() {
		return parentLst;
	}

	public void setParentLst(List<IdexKeyBean> parentLst) {
		this.parentLst = parentLst;
	}

	public List<IdexKeyBean> getIdexKeyBeanLst() {
		return idexKeyBeanLst;
	}

	public void setIdexKeyBeanLst(List<IdexKeyBean> idexKeyBeanLst) {
		this.idexKeyBeanLst = idexKeyBeanLst;
	}

}
