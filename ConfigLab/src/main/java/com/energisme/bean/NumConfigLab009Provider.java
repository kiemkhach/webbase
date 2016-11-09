package com.energisme.bean;

import java.util.List;

import com.ifi.lab.LabDAO.model.Lab009Provider;

public class NumConfigLab009Provider {
	private List<Lab009Provider> lab009ProviderLst;
	private Integer providerId;
	private String providerName;
	private String providerCode;
	private String colorCode;
	public List<Lab009Provider> getLab009ProviderLst() {
		return lab009ProviderLst;
	}
	public void setLab009ProviderLst(List<Lab009Provider> lab009ProviderLst) {
		this.lab009ProviderLst = lab009ProviderLst;
	}
	public String getProviderName() {
		return providerName;
	}
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}
	public String getProviderCode() {
		return providerCode;
	}
	public void setProviderCode(String providerCode) {
		this.providerCode = providerCode;
	}
	public String getColorCode() {
		return colorCode;
	}
	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}
	public Integer getProviderId() {
		return providerId;
	}
	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
	}
	
	
}
