package com.ifi.lab.LabDAO.model.Idex;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "idex_config", catalog = "lab_demo")
public class IdexConfig {

	private Integer id;
	@Column(name = "installation_id")
	private Integer installationId;
	@Column(name = "unit_consommation")
	private Integer unitConsommation; // 1: KWH, 2: MWH, 3:GWH
	@Column(name = "unit_montal")
	private Integer unitMontal; // 1: E, 2: KE, 3:ME
	@Column(name = "contract_nb")
	private Integer contractNB;
	@Column(name = "contract_dju")
	private Integer contractDju;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getInstallationId() {
		return installationId;
	}

	public void setInstallationId(Integer installationId) {
		this.installationId = installationId;
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

	public Integer getContractNB() {
		return contractNB;
	}

	public void setContractNB(Integer contractNB) {
		this.contractNB = contractNB;
	}

	public Integer getContractDju() {
		return contractDju;
	}

	public void setContractDju(Integer contractDju) {
		this.contractDju = contractDju;
	}
}
