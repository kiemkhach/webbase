package com.ifi.common.bean.Idex;

import java.util.List;

public class Lab010InstallationTree {

	private Integer installationId;

	private String name;

	private Boolean isUpdateable;

	private List<Lab010InstallationNode> energySupplyLst;

	private List<Lab010InstallationNode> primaryCompteurLst;

	private List<Lab010InstallationNode> productionBoilerLst;

	private List<Lab010InstallationNode> sortieCompteurLst;

	private List<Lab010InstallationNode> consommationSiteLst;

	private List<List<Lab010InstallationNode>> utileCompteurLst;

	private Boolean isConsommationFirst;

	private String urlToLab008;
	private String urlToPerial;

	public Integer getInstallationId() {
		return installationId;
	}

	public void setInstallationId(Integer installationId) {
		this.installationId = installationId;
	}

	public Boolean getIsConsommationFirst() {
		return isConsommationFirst;
	}

	public void setIsConsommationFirst(Boolean isConsommationFirst) {
		this.isConsommationFirst = isConsommationFirst;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Lab010InstallationNode> getEnergySupplyLst() {
		return energySupplyLst;
	}

	public void setEnergySupplyLst(List<Lab010InstallationNode> energySupplyLst) {
		this.energySupplyLst = energySupplyLst;
	}

	public List<Lab010InstallationNode> getPrimaryCompteurLst() {
		return primaryCompteurLst;
	}

	public void setPrimaryCompteurLst(List<Lab010InstallationNode> primaryCompteurLst) {
		this.primaryCompteurLst = primaryCompteurLst;
	}

	public List<Lab010InstallationNode> getProductionBoilerLst() {
		return productionBoilerLst;
	}

	public void setProductionBoilerLst(List<Lab010InstallationNode> productionBoilerLst) {
		this.productionBoilerLst = productionBoilerLst;
	}

	public List<Lab010InstallationNode> getSortieCompteurLst() {
		return sortieCompteurLst;
	}

	public void setSortieCompteurLst(List<Lab010InstallationNode> sortieCompteurLst) {
		this.sortieCompteurLst = sortieCompteurLst;
	}

	public List<Lab010InstallationNode> getConsommationSiteLst() {
		return consommationSiteLst;
	}

	public void setConsommationSiteLst(List<Lab010InstallationNode> consommationSiteLst) {
		this.consommationSiteLst = consommationSiteLst;
	}

	public List<List<Lab010InstallationNode>> getUtileCompteurLst() {
		return utileCompteurLst;
	}

	public void setUtileCompteurLst(List<List<Lab010InstallationNode>> utileCompteurLst) {
		this.utileCompteurLst = utileCompteurLst;
	}

	public Boolean getIsUpdateable() {
		return isUpdateable;
	}

	public void setIsUpdateable(Boolean isUpdateable) {
		this.isUpdateable = isUpdateable;
	}

	public String getUrlToLab008() {
		return urlToLab008;
	}

	public void setUrlToLab008(String urlToLab008) {
		this.urlToLab008 = urlToLab008;
	}

	public String getUrlToPerial() {
		return urlToPerial;
	}

	public void setUrlToPerial(String urlToPerial) {
		this.urlToPerial = urlToPerial;
	}

}
