package com.ifi.common.bean;

public class BouyguesBean {
	private Double gruesChantier;
	private Double piedsColonnes;
	private Double cantonement;
	private String startDate;
	private String numberPermit;
	private String siteName;
	private Integer siteId;
	private Integer numberGrues;
	private Integer numberPied;
	private Integer numberCanton;

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getNumberPermit() {
		return numberPermit;
	}

	public void setNumberPermit(String numberPermit) {
		this.numberPermit = numberPermit;
	}

	public Integer getNumberGrues() {
		return numberGrues;
	}

	public void setNumberGrues(Integer numberGrues) {
		this.numberGrues = numberGrues;
	}

	public Integer getNumberPied() {
		return numberPied;
	}

	public void setNumberPied(Integer numberPied) {
		this.numberPied = numberPied;
	}

	public Integer getNumberCanton() {
		return numberCanton;
	}

	public void setNumberCanton(Integer numberCanton) {
		this.numberCanton = numberCanton;
	}

	public Double getGruesChantier() {
		return gruesChantier;
	}

	public void setGruesChantier(Double gruesChantier) {
		this.gruesChantier = gruesChantier;
	}

	public Double getPiedsColonnes() {
		return piedsColonnes;
	}

	public void setPiedsColonnes(Double piedsColonnes) {
		this.piedsColonnes = piedsColonnes;
	}

	public Double getCantonement() {
		return cantonement;
	}

	public void setCantonement(Double cantonement) {
		this.cantonement = cantonement;
	}
}
