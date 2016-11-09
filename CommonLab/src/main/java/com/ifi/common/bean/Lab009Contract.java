package com.ifi.common.bean;

public class Lab009Contract {
	// private int moduleId;
	// key
	// private String invariant;
	// start key
	private Integer providerId;
	private Integer lotId;
	private Integer energyTypeId;
	private Integer siteId;
	// end key
	private String providerName;
	private String siteName;
	private String numberCompteur;
	private String numberContract;

	// public int getModuleId() {
	// return moduleId;
	// }
	//
	// public void setModuleId(int moduleId) {
	// this.moduleId = moduleId;
	// }

	public Integer getProviderId() {
		return providerId;
	}

	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
	}

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

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getNumberCompteur() {
		return numberCompteur;
	}

	public void setNumberCompteur(String numberCompteur) {
		this.numberCompteur = numberCompteur;
	}

	public String getNumberContract() {
		return numberContract;
	}

	public void setNumberContract(String numberContract) {
		this.numberContract = numberContract;
	}

}
