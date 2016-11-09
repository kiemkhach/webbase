package com.ifi.lab.LabDAO.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "lab009_module")
public class Lab009Module implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "module_id")
	private Integer moduleId;
	@ManyToOne
	@JoinColumn(name = "lot_consommation_id", nullable = true)
	private Lab009LotConsommation lotConsommation;// FK
	@ManyToOne
	@JoinColumn(name = "energy_type_id", nullable = true)
	private Lab009EnergyType energyType;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

	public Lab009LotConsommation getLotConsommation() {
		return lotConsommation;
	}

	public void setLotConsommation(Lab009LotConsommation lotConsommation) {
		this.lotConsommation = lotConsommation;
	}

	public Lab009EnergyType getEnergyType() {
		return energyType;
	}

	public void setEnergyType(Lab009EnergyType energyType) {
		this.energyType = energyType;
	}

}
