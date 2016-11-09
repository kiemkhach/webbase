package com.ifi.lab.LabDAO.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="config_lab006_client")
public class ConfigLab006Client{
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="subscriptionId")
	private Integer subscriptionId;
	
	@Column(name="client_name")
	private String clientName;
	
	@Column(name="moduleId_HCE")
	private String moduleIdHCE;
	
	@Column(name="moduleId_HPE")
	private String moduleIdHPE;
	
	@Column(name="moduleId_HCH")
	private String moduleIdHCH;
	
	@Column(name="moduleId_HPH")
	private String moduleIdHPH;

	@Column(name="report_client_name")
	private String reportClientName;

	@Column(name="image_client_name")
	private String imageClientName;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getSubscriptionId() {
		return subscriptionId;
	}

	public void setSubscriptionId(Integer subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getModuleIdHCE() {
		return moduleIdHCE;
	}

	public void setModuleIdHCE(String moduleIdHCE) {
		this.moduleIdHCE = moduleIdHCE;
	}

	public String getModuleIdHPE() {
		return moduleIdHPE;
	}

	public void setModuleIdHPE(String moduleIdHPE) {
		this.moduleIdHPE = moduleIdHPE;
	}

	public String getModuleIdHCH() {
		return moduleIdHCH;
	}

	public void setModuleIdHCH(String moduleIdHCH) {
		this.moduleIdHCH = moduleIdHCH;
	}

	public String getModuleIdHPH() {
		return moduleIdHPH;
	}

	public void setModuleIdHPH(String moduleIdHPH) {
		this.moduleIdHPH = moduleIdHPH;
	}

	public String getReportClientName() {
		return reportClientName;
	}

	public void setReportClientName(String reportClientName) {
		this.reportClientName = reportClientName;
	}

	public String getImageClientName() {
		return imageClientName;
	}

	public void setImageClientName(String imageClientName) {
		this.imageClientName = imageClientName;
	}
}
