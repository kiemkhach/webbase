package com.ifi.lab.LabDAO.model.caloon;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "caloon_syndic")
public class CaloonSyndic {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "address")
	private String address;
	@Column(name = "chauffage")
	private String chauffage;
	@Column(name = "eau_chaude")
	private String eauChaude;
	@Column(name = "eau_froide")
	private String eauFroide;
	@Column(name = "chauffage_communes")
	private String chauffageCommunes;
	@Column(name = "eau_chaude_communes")
	private String eauChaudeCommunes;
	@Column(name = "eau_froide_communes")
	private String eauFroideCommunes;
	@Column(name = "report_path")
	private String reportPath;
	@Column(name = "code_postal")
	private String codePostal;
	@Column(name = "ville")
	private String ville;
	@Column(name = "name")
	private String name;
	@Column(name = "caloon_user_id")
	private Integer caloonUserId;
	@Column(name = "is_default_syndic", nullable = false, columnDefinition = "boolean default false")
	private Boolean isDefaultSyndic;
	@Column(name = "coeff_unit", nullable = false, columnDefinition = "double default 1") // coefficients for convert kwh to m3
	private Double coeffUnit;
	@Column(name = "coeff_total", nullable = false, columnDefinition = "double default 1") // coefficients for total
	private Double coeffTotal;
	@Column(name = "coeff_ecs", nullable = false, columnDefinition = "double default 1") // coefficients for ECS
	private Double coeffEcs;
	
	public Double getCoeffUnit() {
		return coeffUnit;
	}

	public void setCoeffUnit(Double coeffUnit) {
		this.coeffUnit = coeffUnit;
	}

	public Double getCoeffTotal() {
		return coeffTotal;
	}

	public void setCoeffTotal(Double coeffTotal) {
		this.coeffTotal = coeffTotal;
	}

	public Double getCoeffEcs() {
		return coeffEcs;
	}

	public void setCoeffEcs(Double coeffEcs) {
		this.coeffEcs = coeffEcs;
	}

	public Boolean getIsDefaultSyndic() {
		return isDefaultSyndic;
	}

	public void setIsDefaultSyndic(Boolean isDefaultSyndic) {
		this.isDefaultSyndic = isDefaultSyndic;
	}

	public Integer getCaloonUserId() {
		return caloonUserId;
	}

	public void setCaloonUserId(Integer caloonUserId) {
		this.caloonUserId = caloonUserId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getReportPath() {
		return reportPath;
	}

	public void setReportPath(String reportPath) {
		this.reportPath = reportPath;
	}

	public String getChauffage() {
		return chauffage;
	}

	public void setChauffage(String chauffage) {
		this.chauffage = chauffage;
	}

	public String getEauChaude() {
		return eauChaude;
	}

	public void setEauChaude(String eauChaude) {
		this.eauChaude = eauChaude;
	}

	public String getEauFroide() {
		return eauFroide;
	}

	public void setEauFroide(String eauFroide) {
		this.eauFroide = eauFroide;
	}

	public String getChauffageCommunes() {
		return chauffageCommunes;
	}

	public void setChauffageCommunes(String chauffageCommunes) {
		this.chauffageCommunes = chauffageCommunes;
	}

	public String getEauChaudeCommunes() {
		return eauChaudeCommunes;
	}

	public void setEauChaudeCommunes(String eauChaudeCommunes) {
		this.eauChaudeCommunes = eauChaudeCommunes;
	}

	public String getEauFroideCommunes() {
		return eauFroideCommunes;
	}

	public void setEauFroideCommunes(String eauFroideCommunes) {
		this.eauFroideCommunes = eauFroideCommunes;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
