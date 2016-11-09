package com.ifi.common.bean.Idex;

import java.util.List;

public class Lab010EnergyAnalysis {

	// Mix energy
	private Float mixEnergyTotal;
	private String totalUnit;
	private Float nCTotal;
	private List<Lab010EnergyTypeBean> energyTypeBeanLst;

	// Rendement
	private Integer transformation;
	private Integer distribution;
	private Integer global;
	private List<Lab010BoilerBean> transformationEnergyTypeLst;
	private List<Lab010BoilerBean> boilerBeanLst;
	private Integer pertesReseau;

	// Consommations
	private Integer contractuelNB;
	private Integer contractuelDJU;
	private Integer realiseNB;
	private Integer realiseDJU;
	private Float installationNB_DJU;
	private Float installationNC_DJU;
	private Float installationNC_NB;

	private List<Lab010CompteurBean> primaireCompteurLst;
	private List<Lab010CompteurBean> sortieCompteurLst;
	private List<Lab010CompteurBean> utileCompteurLst;
	
	private String fromMonth;
	private String toMonth;

	public Float getnCTotal() {
		return nCTotal;
	}

	public void setnCTotal(Float nCTotal) {
		this.nCTotal = nCTotal;
	}

	private String attachment;

	public String getTotalUnit() {
		return totalUnit;
	}

	public void setTotalUnit(String totalUnit) {
		this.totalUnit = totalUnit;
	}

	public Float getMixEnergyTotal() {
		return mixEnergyTotal;
	}

	public void setMixEnergyTotal(Float mixEnergyTotal) {
		this.mixEnergyTotal = mixEnergyTotal;
	}

	public List<Lab010EnergyTypeBean> getEnergyTypeBeanLst() {
		return energyTypeBeanLst;
	}

	public void setEnergyTypeBeanLst(List<Lab010EnergyTypeBean> energyTypeBeanLst) {
		this.energyTypeBeanLst = energyTypeBeanLst;
	}

	public Integer getTransformation() {
		return transformation;
	}

	public void setTransformation(Integer transformation) {
		this.transformation = transformation;
	}

	public Integer getDistribution() {
		return distribution;
	}

	public void setDistribution(Integer distribution) {
		this.distribution = distribution;
	}

	public Integer getGlobal() {
		return global;
	}

	public void setGlobal(Integer global) {
		this.global = global;
	}

	public List<Lab010BoilerBean> getBoilerBeanLst() {
		return boilerBeanLst;
	}

	public void setBoilerBeanLst(List<Lab010BoilerBean> boilerBeanLst) {
		this.boilerBeanLst = boilerBeanLst;
	}

	public Integer getPertesReseau() {
		return pertesReseau;
	}

	public void setPertesReseau(Integer pertesReseau) {
		this.pertesReseau = pertesReseau;
	}

	public Integer getContractuelNB() {
		return contractuelNB;
	}

	public void setContractuelNB(Integer contractuelNB) {
		this.contractuelNB = contractuelNB;
	}

	public Integer getContractuelDJU() {
		return contractuelDJU;
	}

	public void setContractuelDJU(Integer contractuelDJU) {
		this.contractuelDJU = contractuelDJU;
	}

	public Integer getRealiseNB() {
		return realiseNB;
	}

	public void setRealiseNB(Integer realiseNB) {
		this.realiseNB = realiseNB;
	}

	public Integer getRealiseDJU() {
		return realiseDJU;
	}

	public void setRealiseDJU(Integer realiseDJU) {
		this.realiseDJU = realiseDJU;
	}

	public Float getInstallationNB_DJU() {
		return installationNB_DJU;
	}

	public void setInstallationNB_DJU(Float installationNB_DJU) {
		this.installationNB_DJU = installationNB_DJU;
	}

	public Float getInstallationNC_DJU() {
		return installationNC_DJU;
	}

	public void setInstallationNC_DJU(Float installationNC_DJU) {
		this.installationNC_DJU = installationNC_DJU;
	}

	public Float getInstallationNC_NB() {
		return installationNC_NB;
	}

	public void setInstallationNC_NB(Float installationNC_NB) {
		this.installationNC_NB = installationNC_NB;
	}

	public List<Lab010CompteurBean> getPrimaireCompteurLst() {
		return primaireCompteurLst;
	}

	public void setPrimaireCompteurLst(List<Lab010CompteurBean> primaireCompteurLst) {
		this.primaireCompteurLst = primaireCompteurLst;
	}

	public List<Lab010CompteurBean> getSortieCompteurLst() {
		return sortieCompteurLst;
	}

	public void setSortieCompteurLst(List<Lab010CompteurBean> sortieCompteurLst) {
		this.sortieCompteurLst = sortieCompteurLst;
	}

	public List<Lab010CompteurBean> getUtileCompteurLst() {
		return utileCompteurLst;
	}

	public void setUtileCompteurLst(List<Lab010CompteurBean> utileCompteurLst) {
		this.utileCompteurLst = utileCompteurLst;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public List<Lab010BoilerBean> getTransformationEnergyTypeLst() {
		return transformationEnergyTypeLst;
	}

	public void setTransformationEnergyTypeLst(List<Lab010BoilerBean> transformationEnergyTypeLst) {
		this.transformationEnergyTypeLst = transformationEnergyTypeLst;
	}

	public String getFromMonth() {
		return fromMonth;
	}

	public void setFromMonth(String fromMonth) {
		this.fromMonth = fromMonth;
	}

	public String getToMonth() {
		return toMonth;
	}

	public void setToMonth(String toMonth) {
		this.toMonth = toMonth;
	}

}
