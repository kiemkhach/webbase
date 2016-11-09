package com.ifi.lab.LabDAO.model.Idex;

// Generated Oct 7, 2016 4:14:38 AM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * IdexCost generated by hbm2java
 */
@Entity
@Table(name = "idex_cost", catalog = "lab_demo")
public class IdexCost implements java.io.Serializable {

	private Integer idexCostId;
	private IdexEnergySupplier idexEnergySupplier;
	private String name;
	private IdexSite idexSiteIn;
//	private IdexSite idexSiteOut;
	private Integer idexInstallationId;
//	private Set<IdexCostDetail> idexCostDetails = new HashSet<IdexCostDetail>(0);

	public IdexCost() {
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "idex_cost_id", unique = true, nullable = false)
	public Integer getIdexCostId() {
		return this.idexCostId;
	}

	public void setIdexCostId(Integer idexCostId) {
		this.idexCostId = idexCostId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idex_energy_supplier_id")
	public IdexEnergySupplier getIdexEnergySupplier() {
		return this.idexEnergySupplier;
	}

	public void setIdexEnergySupplier(IdexEnergySupplier idexEnergySupplier) {
		this.idexEnergySupplier = idexEnergySupplier;
	}

	@Column(name = "name", length = 250)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "idex_installation_id")
	public Integer getIdexInstallationId() {
		return idexInstallationId;
	}

	public void setIdexInstallationId(Integer idexInstallationId) {
		this.idexInstallationId = idexInstallationId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idex_site_in_id")
	public IdexSite getIdexSiteIn() {
		return idexSiteIn;
	}

	public void setIdexSiteIn(IdexSite idexSiteIn) {
		this.idexSiteIn = idexSiteIn;
	}

//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "idex_site_out_id")
//	public IdexSite getIdexSiteOut() {
//		return idexSiteOut;
//	}
//
//	public void setIdexSiteOut(IdexSite idexSiteOut) {
//		this.idexSiteOut = idexSiteOut;
//	}
//
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "idexCost")
//	public Set<IdexCostDetail> getIdexCostDetails() {
//		return this.idexCostDetails;
//	}
//
//	public void setIdexCostDetails(Set<IdexCostDetail> idexCostDetails) {
//		this.idexCostDetails = idexCostDetails;
//	}

}
