package com.ifi.lab.LabDAO.model.caloon;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ifi.lab.LabDAO.model.Idex.IdexCounter;

/**
 * Table for Client resident information
 * 
 * @author ndlong
 *
 */
@Entity
@Table(name = "caloon_resident")
public class CaloonResident {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "appartement_number")
	private String appartementNumber;
	// @Column(name = "temp_appartement")
	// private String tempAppartement;
	// @Column(name = "temp_meteo")
	// private String tempMeteo;
	@Column(name = "chauffage")
	private String chauffage;
	@Column(name = "eau_chaude")
	private String eauChaude;
	@Column(name = "eau_froide")
	private String eauFroide;
	@Column(name = "caloon_syndic_id")
	private Integer caloonSyndicId;
	@Column(name = "client_name")
	private String clientName;
	@Column(name = "superficie")
	private Integer superficie;
	@Column(name = "logements", nullable = false, columnDefinition = "boolean default true")
	private Boolean logements;// true: occupied - false: empty
	@Column(name = "caloon_user_id")
	private Integer caloonUserId;

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

	public String getAppartementNumber() {
		return appartementNumber;
	}

	public void setAppartementNumber(String appartementNumber) {
		this.appartementNumber = appartementNumber;
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

	public Integer getCaloonSyndicId() {
		return caloonSyndicId;
	}

	public void setCaloonSyndicId(Integer caloonSyndicId) {
		this.caloonSyndicId = caloonSyndicId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public Integer getSuperficie() {
		return superficie;
	}

	public void setSuperficie(Integer superficie) {
		this.superficie = superficie;
	}

	public Boolean getLogements() {
		return logements;
	}

	public void setLogements(Boolean logements) {
		this.logements = logements;
	}

}
