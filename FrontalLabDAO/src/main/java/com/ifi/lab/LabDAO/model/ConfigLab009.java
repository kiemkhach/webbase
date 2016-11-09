package com.ifi.lab.LabDAO.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "config_lab009")
public class ConfigLab009 {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "client_id")
	private Integer clientId;
	@Column(name = "client_name")
	private String clientName;
	@Column(name = "unit_emissions")
	private String unitEmissions;
	@Column(name = "unit_consommation") // kWh, MWh or GWh
	private Integer unitConsommation;
	@Column(name = "unit_montal") // €, k€ or M€
	private Integer unitMontal;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getClientId() {
		return clientId;
	}
	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getUnitEmissions() {
		return unitEmissions;
	}
	public void setUnitEmissions(String unitEmissions) {
		this.unitEmissions = unitEmissions;
	}
	public Integer getUnitConsommation() {
		return unitConsommation;
	}
	public void setUnitConsommation(Integer unitConsommation) {
		this.unitConsommation = unitConsommation;
	}
	public Integer getUnitMontal() {
		return unitMontal;
	}
	public void setUnitMontal(Integer unitMontal) {
		this.unitMontal = unitMontal;
	}

	

}
