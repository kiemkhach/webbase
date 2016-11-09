package com.ifi.lab.LabDAO.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "config_lab002")
public class Bouygues {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer siteId;
	@Column(name = "site_name")
	private String siteName;
	@Column(name = "gruesModuleId")
	private String gruesModuleId;
	@Column(name = "piedModuleId")
	private String piedModuleId;
	@Column(name = "cantonModuleId")
	private String cantonModuleId;
	@Column(name = "logo")
	private String logo;
	@Column(name = "uri_icon")
	private String uriIcon;
	@Column(name = "startDate")
	private String startDate;
	@Column(name = "numberPermit")
	private String numberPermit;
	@Column(name = "number_grues")
	private Integer numberGrues;
	@Column(name = "number_pied")
	private Integer numberPied;
	@Column(name = "number_canton")
	private Integer numberCanton;
	@Column(name = "report_name")
	private String reportName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
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

	public Bouygues() {
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getGruesModuleId() {
		return gruesModuleId;
	}

	public void setGruesModuleId(String gruesModuleId) {
		this.gruesModuleId = gruesModuleId;
	}

	public String getPiedModuleId() {
		return piedModuleId;
	}

	public void setPiedModuleId(String piedModuleId) {
		this.piedModuleId = piedModuleId;
	}

	public String getCantonModuleId() {
		return cantonModuleId;
	}

	public void setCantonModuleId(String cantonModuleId) {
		this.cantonModuleId = cantonModuleId;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getUriIcon() {
		return uriIcon;
	}

	public void setUriIcon(String uriIcon) {
		this.uriIcon = uriIcon;
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
}
