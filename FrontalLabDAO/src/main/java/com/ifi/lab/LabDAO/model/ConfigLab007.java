package com.ifi.lab.LabDAO.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="config_lab007")
public class ConfigLab007 {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "site_id")
	private Integer siteId;
	@Column(name = "site_name")
	private String siteName;
	@Column(name = "totalElecModuleId")
	private String totalElecModuleId;
	@Column(name= "totalPylonesModuleId")
	private String totalPylonesModuleId;
	@Column(name ="totalGasModuleId")
	private String totalGasModuleId;
	@Column(name = "totalWaterModuleId")
	private String totalWaterModuleId;
	@Column(name = "unitElec")
	private String unitElec;
	@Column(name= "unitPylones")
	private String unitPylones;
	@Column(name ="unitGas")
	private String unitGas;
	@Column(name = "unitWater")
	private String unitWater;
	@Column(name = "uri_icon")
	private String uriIcon;
	@Column(name = "logo")
	private String logo;
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

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getTotalElecModuleId() {
		return totalElecModuleId;
	}

	public void setTotalElecModuleId(String totalElecModuleId) {
		this.totalElecModuleId = totalElecModuleId;
	}

	public String getTotalPylonesModuleId() {
		return totalPylonesModuleId;
	}

	public void setTotalPylonesModuleId(String totalPylonesModuleId) {
		this.totalPylonesModuleId = totalPylonesModuleId;
	}

	public String getTotalGasModuleId() {
		return totalGasModuleId;
	}

	public void setTotalGasModuleId(String totalGasModuleId) {
		this.totalGasModuleId = totalGasModuleId;
	}

	public String getTotalWaterModuleId() {
		return totalWaterModuleId;
	}

	public void setTotalWaterModuleId(String totalWaterModuleId) {
		this.totalWaterModuleId = totalWaterModuleId;
	}

	public String getUnitElec() {
		return unitElec;
	}

	public void setUnitElec(String unitElec) {
		this.unitElec = unitElec;
	}

	public String getUnitPylones() {
		return unitPylones;
	}

	public void setUnitPylones(String unitPylones) {
		this.unitPylones = unitPylones;
	}

	public String getUnitGas() {
		return unitGas;
	}

	public void setUnitGas(String unitGas) {
		this.unitGas = unitGas;
	}

	public String getUnitWater() {
		return unitWater;
	}

	public void setUnitWater(String unitWater) {
		this.unitWater = unitWater;
	}

	public String getUriIcon() {
		return uriIcon;
	}

	public void setUriIcon(String uriIcon) {
		this.uriIcon = uriIcon;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
}
