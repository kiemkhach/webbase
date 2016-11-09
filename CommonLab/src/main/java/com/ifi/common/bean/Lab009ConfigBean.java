package com.ifi.common.bean;

import java.util.List;

public class Lab009ConfigBean {
	private List<Lab009EnergyTypeBean> energyTypeBeanLst;
	private List<Lab009LotConsommationBean> lotConsommationBeanlst;
	private List<Lab009ProviderBean> providerBeanLst;

	public List<Lab009EnergyTypeBean> getEnergyTypeBeanLst() {
		return energyTypeBeanLst;
	}

	public void setEnergyTypeBeanLst(List<Lab009EnergyTypeBean> energyTypeBeanLst) {
		this.energyTypeBeanLst = energyTypeBeanLst;
	}

	public List<Lab009LotConsommationBean> getLotConsommationBeanlst() {
		return lotConsommationBeanlst;
	}

	public void setLotConsommationBeanlst(List<Lab009LotConsommationBean> lotConsommationBeanlst) {
		this.lotConsommationBeanlst = lotConsommationBeanlst;
	}

	public List<Lab009ProviderBean> getProviderBeanLst() {
		return providerBeanLst;
	}

	public void setProviderBeanLst(List<Lab009ProviderBean> providerBeanLst) {
		this.providerBeanLst = providerBeanLst;
	}

}
