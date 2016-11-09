package com.ifi.common.bean;

import java.util.Map;

public class Lab009Invoice {

	private Integer siteId;
	// private Integer moduleId;
	private Integer lotId;
	private Integer energyTypeId;
	private String providerName;
	private Integer consommationTotal;
	private Double montantTotal;
	private String invariant;
	// Map by Year
	private Map<Integer, Lab009InvoiceYear> invoiceYearMap;

	public String getInvariant() {
		return invariant;
	}

	public void setInvariant(String invariant) {
		this.invariant = invariant;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}
	//
	// public Integer getModuleId() {
	// return moduleId;
	// }
	//
	// public void setModuleId(Integer moduleId) {
	// this.moduleId = moduleId;
	// }

	public Integer getLotId() {
		return lotId;
	}

	public void setLotId(Integer lotId) {
		this.lotId = lotId;
	}

	public Integer getEnergyTypeId() {
		return energyTypeId;
	}

	public void setEnergyTypeId(Integer energyTypeId) {
		this.energyTypeId = energyTypeId;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public Double getMontantTotal() {
		return montantTotal;
	}

	public void setMontantTotal(Double montantTotal) {
		this.montantTotal = montantTotal;
	}

	public Integer getConsommationTotal() {
		return consommationTotal;
	}

	public void setConsommationTotal(Integer consommationTotal) {
		this.consommationTotal = consommationTotal;
	}

	public Map<Integer, Lab009InvoiceYear> getInvoiceYearMap() {
		return invoiceYearMap;
	}

	public void setInvoiceYearMap(Map<Integer, Lab009InvoiceYear> invoiceYearMap) {
		this.invoiceYearMap = invoiceYearMap;
	}

}
