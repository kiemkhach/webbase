package com.ifi.lab.LabDAO.model.Idex;

// Generated Oct 5, 2016 7:13:22 AM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * IdexEnergyType generated by hbm2java
 */
@Entity
@Table(name = "idex_energy_type", catalog = "lab_demo")
public class IdexEnergyType implements java.io.Serializable {

	private int idexEnergyTypeId;
	private String name;
	private Integer code;
	private Set<IdexEnergySupplier> idexEnergySuppliers = new HashSet<IdexEnergySupplier>(
			0);

	public IdexEnergyType() {
	}

	public IdexEnergyType(int idexEnergyTypeId) {
		this.idexEnergyTypeId = idexEnergyTypeId;
	}

	public IdexEnergyType(int idexEnergyTypeId, String name, Integer code,
			Set<IdexEnergySupplier> idexEnergySuppliers) {
		this.idexEnergyTypeId = idexEnergyTypeId;
		this.name = name;
		this.code = code;
		this.idexEnergySuppliers = idexEnergySuppliers;
	}

	@Id
	@Column(name = "idex_energy_type_id", unique = true, nullable = false)
	public int getIdexEnergyTypeId() {
		return this.idexEnergyTypeId;
	}

	public void setIdexEnergyTypeId(int idexEnergyTypeId) {
		this.idexEnergyTypeId = idexEnergyTypeId;
	}

	@Column(name = "name", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "code")
	public Integer getCode() {
		return this.code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "idexEnergyType")
	public Set<IdexEnergySupplier> getIdexEnergySuppliers() {
		return this.idexEnergySuppliers;
	}

	public void setIdexEnergySuppliers(
			Set<IdexEnergySupplier> idexEnergySuppliers) {
		this.idexEnergySuppliers = idexEnergySuppliers;
	}

}
