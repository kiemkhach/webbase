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
 * IdexVittel generated by hbm2java
 */
@Entity
@Table(name = "idex_vittel", catalog = "lab_demo")
public class IdexVittel implements java.io.Serializable {

	private Integer idexVittelId;
	private Date vittelDate;
	private Float volumeLivreBrut;
	private Float volumeLivreConverti;
	private Float pcs;
	private Float quantiteLivree;
	private String libelle;

	public IdexVittel() {
	}

	public IdexVittel(Date vittelDate, Float volumeLivreBrut,
			Float volumeLivreConverti, Float pcs, Float quantiteLivree,
			String libelle) {
		this.vittelDate = vittelDate;
		this.volumeLivreBrut = volumeLivreBrut;
		this.volumeLivreConverti = volumeLivreConverti;
		this.pcs = pcs;
		this.quantiteLivree = quantiteLivree;
		this.libelle = libelle;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "idex_vittel_id", unique = true, nullable = false)
	public Integer getIdexVittelId() {
		return this.idexVittelId;
	}

	public void setIdexVittelId(Integer idexVittelId) {
		this.idexVittelId = idexVittelId;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "vittel_date", length = 10)
	public Date getVittelDate() {
		return this.vittelDate;
	}

	public void setVittelDate(Date vittelDate) {
		this.vittelDate = vittelDate;
	}

	@Column(name = "volume_livre_brut", precision = 12, scale = 0)
	public Float getVolumeLivreBrut() {
		return this.volumeLivreBrut;
	}

	public void setVolumeLivreBrut(Float volumeLivreBrut) {
		this.volumeLivreBrut = volumeLivreBrut;
	}

	@Column(name = "volume_livre_converti", precision = 12, scale = 0)
	public Float getVolumeLivreConverti() {
		return this.volumeLivreConverti;
	}

	public void setVolumeLivreConverti(Float volumeLivreConverti) {
		this.volumeLivreConverti = volumeLivreConverti;
	}

	@Column(name = "pcs", precision = 12, scale = 0)
	public Float getPcs() {
		return this.pcs;
	}

	public void setPcs(Float pcs) {
		this.pcs = pcs;
	}

	@Column(name = "quantite_livree", precision = 12, scale = 0)
	public Float getQuantiteLivree() {
		return this.quantiteLivree;
	}

	public void setQuantiteLivree(Float quantiteLivree) {
		this.quantiteLivree = quantiteLivree;
	}

	@Column(name = "libelle", length = 100)
	public String getLibelle() {
		return this.libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

}