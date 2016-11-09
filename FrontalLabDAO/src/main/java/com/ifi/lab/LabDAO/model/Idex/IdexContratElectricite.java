package com.ifi.lab.LabDAO.model.Idex;

// Generated Oct 5, 2016 7:13:22 AM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * IdexContratElectricite generated by hbm2java
 */
@Entity
@Table(name = "idex_contrat_electricite", catalog = "lab_demo")
public class IdexContratElectricite implements java.io.Serializable {

	private Integer idexContratElectriciteId;
	private String nomDuFournisseur;
	private Date debutDeContrat;
	private Date finDeContrat;
	private String typeDeContrat;
	private String nomDuContrat;
	private Float p;
	private Float hph;
	private Float hch;
	private Float hpe;
	private Float hce;
	private Float preduite;

	public IdexContratElectricite() {
	}

	public IdexContratElectricite(String nomDuFournisseur, Date debutDeContrat,
			Date finDeContrat, String typeDeContrat, String nomDuContrat,
			Float p, Float hph, Float hch, Float hpe, Float hce, Float preduite) {
		this.nomDuFournisseur = nomDuFournisseur;
		this.debutDeContrat = debutDeContrat;
		this.finDeContrat = finDeContrat;
		this.typeDeContrat = typeDeContrat;
		this.nomDuContrat = nomDuContrat;
		this.p = p;
		this.hph = hph;
		this.hch = hch;
		this.hpe = hpe;
		this.hce = hce;
		this.preduite = preduite;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "idex_contrat_electricite_id", unique = true, nullable = false)
	public Integer getIdexContratElectriciteId() {
		return this.idexContratElectriciteId;
	}

	public void setIdexContratElectriciteId(Integer idexContratElectriciteId) {
		this.idexContratElectriciteId = idexContratElectriciteId;
	}

	@Column(name = "nom_du_fournisseur", length = 100)
	public String getNomDuFournisseur() {
		return this.nomDuFournisseur;
	}

	public void setNomDuFournisseur(String nomDuFournisseur) {
		this.nomDuFournisseur = nomDuFournisseur;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "debut_de_contrat", length = 10)
	public Date getDebutDeContrat() {
		return this.debutDeContrat;
	}

	public void setDebutDeContrat(Date debutDeContrat) {
		this.debutDeContrat = debutDeContrat;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "fin_de_contrat", length = 10)
	public Date getFinDeContrat() {
		return this.finDeContrat;
	}

	public void setFinDeContrat(Date finDeContrat) {
		this.finDeContrat = finDeContrat;
	}

	@Column(name = "type_de_contrat", length = 10)
	public String getTypeDeContrat() {
		return this.typeDeContrat;
	}

	public void setTypeDeContrat(String typeDeContrat) {
		this.typeDeContrat = typeDeContrat;
	}

	@Column(name = "nom_du_contrat", length = 100)
	public String getNomDuContrat() {
		return this.nomDuContrat;
	}

	public void setNomDuContrat(String nomDuContrat) {
		this.nomDuContrat = nomDuContrat;
	}

	@Column(name = "p", precision = 12, scale = 0)
	public Float getP() {
		return this.p;
	}

	public void setP(Float p) {
		this.p = p;
	}

	@Column(name = "hph", precision = 12, scale = 0)
	public Float getHph() {
		return this.hph;
	}

	public void setHph(Float hph) {
		this.hph = hph;
	}

	@Column(name = "hch", precision = 12, scale = 0)
	public Float getHch() {
		return this.hch;
	}

	public void setHch(Float hch) {
		this.hch = hch;
	}

	@Column(name = "hpe", precision = 12, scale = 0)
	public Float getHpe() {
		return this.hpe;
	}

	public void setHpe(Float hpe) {
		this.hpe = hpe;
	}

	@Column(name = "hce", precision = 12, scale = 0)
	public Float getHce() {
		return this.hce;
	}

	public void setHce(Float hce) {
		this.hce = hce;
	}

	@Column(name = "preduite", precision = 12, scale = 0)
	public Float getPreduite() {
		return this.preduite;
	}

	public void setPreduite(Float preduite) {
		this.preduite = preduite;
	}

}