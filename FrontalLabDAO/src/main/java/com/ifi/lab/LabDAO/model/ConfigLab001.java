package com.ifi.lab.LabDAO.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "config_lab001")
public class ConfigLab001 {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "site_id")
	private Integer siteId;
	@Column(name = "site_superficies")
	private Integer siteSuperficies;
	@Column(name = "site_info")
	private String siteInfo;
	@Column(name = "site_name")
	private String siteName;

	@Column(name = "module_pos")
	private String modulePos;
	@Column(name = "module_neg")
	private String moduleNeg;
	@Column(name = "module_reg")
	private String moduleReg;

	@Column(name = "module_eclairage")
	private String moduleEclairage;
	@Column(name = "module_bureau")
	private String moduleBureau;
	@Column(name = "module_boulangerie")
	private String moduleBoulangerie;
	@Column(name = "module_cvc")
	private String moduleCvc;

	@Column(name = "module_temperature_in")
	private String moduleTemperatureIn;
	@Column(name = "module_temperature_out")
	private String moduleTemperatureOut;
	@Column(name = "module_drive")
	private String moduleDrive;

	@Column(name = "module_electric")
	private String moduleElectric;
	@Column(name = "module_gaz")
	private String moduleGaz;
	@Column(name = "module_temperature_franis")
	private String moduleTemperatureFranis;
	@Column(name = "module_energy_previous_year")
	private String moduleEnergyPreviousYear;
	@Column(name = "uri_icon")
	private String uriIcon;
	@Column(name = "logo")
	private String logo;
	@Column(name = "report_name")
	private String reportName;

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
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

	public Integer getSiteSuperficies() {
		return siteSuperficies;
	}

	public void setSiteSuperficies(Integer siteSuperficies) {
		this.siteSuperficies = siteSuperficies;
	}

	public String getSiteInfo() {
		return siteInfo;
	}

	public void setSiteInfo(String siteInfo) {
		this.siteInfo = siteInfo;
	}

	public String getModulePos() {
		return modulePos;
	}

	public void setModulePos(String modulePos) {
		this.modulePos = modulePos;
	}

	public String getModuleNeg() {
		return moduleNeg;
	}

	public void setModuleNeg(String moduleNeg) {
		this.moduleNeg = moduleNeg;
	}

	public String getModuleReg() {
		return moduleReg;
	}

	public void setModuleReg(String moduleReg) {
		this.moduleReg = moduleReg;
	}

	public String getModuleEclairage() {
		return moduleEclairage;
	}

	public void setModuleEclairage(String moduleEclairage) {
		this.moduleEclairage = moduleEclairage;
	}

	public String getModuleBureau() {
		return moduleBureau;
	}

	public void setModuleBureau(String moduleBureau) {
		this.moduleBureau = moduleBureau;
	}

	public String getModuleBoulangerie() {
		return moduleBoulangerie;
	}

	public void setModuleBoulangerie(String moduleBoulangerie) {
		this.moduleBoulangerie = moduleBoulangerie;
	}

	public String getModuleCvc() {
		return moduleCvc;
	}

	public void setModuleCvc(String moduleCvc) {
		this.moduleCvc = moduleCvc;
	}

	public String getModuleTemperatureIn() {
		return moduleTemperatureIn;
	}

	public void setModuleTemperatureIn(String moduleTemperatureIn) {
		this.moduleTemperatureIn = moduleTemperatureIn;
	}

	public String getModuleTemperatureOut() {
		return moduleTemperatureOut;
	}

	public void setModuleTemperatureOut(String moduleTemperatureOut) {
		this.moduleTemperatureOut = moduleTemperatureOut;
	}

	public String getModuleDrive() {
		return moduleDrive;
	}

	public void setModuleDrive(String moduleDrive) {
		this.moduleDrive = moduleDrive;
	}

	public String getModuleElectric() {
		return moduleElectric;
	}

	public void setModuleElectric(String moduleElectric) {
		this.moduleElectric = moduleElectric;
	}

	public String getModuleGaz() {
		return moduleGaz;
	}

	public void setModuleGaz(String moduleGaz) {
		this.moduleGaz = moduleGaz;
	}

	public String getModuleTemperatureFranis() {
		return moduleTemperatureFranis;
	}

	public void setModuleTemperatureFranis(String moduleTemperatureFranis) {
		this.moduleTemperatureFranis = moduleTemperatureFranis;
	}

	public String getModuleEnergyPreviousYear() {
		return moduleEnergyPreviousYear;
	}

	public void setModuleEnergyPreviousYear(String moduleEnergyPreviousYear) {
		this.moduleEnergyPreviousYear = moduleEnergyPreviousYear;
	}
}
