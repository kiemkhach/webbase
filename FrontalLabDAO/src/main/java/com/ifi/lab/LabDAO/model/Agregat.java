package com.ifi.lab.LabDAO.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="enr_st_agregat_2")
public class Agregat{
	@Id
	@Column(name="ENR_SF_ID_Agregat")
	private int id;
	@Column(name="ENR_SF_Period_Agregat")
	private int period;
	@Column(name="ENR_SF_DateHeureAgregat")
	private String dateHeure;
	@Column(name="ENR_SF_HeureAgregat")
	private int heure;
	@Column(name="ENR_SF_Consommation")
	private int consommation;
	@Column(name="ENR_SF_Index")
	private int index;
	@Column(name="ENR_SF_Temperature_Min")
	private int temperature_min;
	@Column(name="ENR_SF_Temperature")
	private int temperature;
	@Column(name="ENR_SF_Temperature_Max")
	private int temperature_max;
	@Column(name="ENR_SF_DebitHoraire_Min")
	private int debitHoraire_min;
	@Column(name="ENR_SF_DebitHoraire")
	private int debitHoraire;
	@Column(name="ENR_SF_DebitHoraire_Max")
	private int debitHoraire_max;
	@Column(name="ENR_SF_Rssi")
	private int rssi;
	@Column(name="ENR_SF_FK_Module")
	private int moduleId;
	@Column(name="ENR_SF_INDEX_ModulePeriode")
	private String modulePeriod;
	
	public Agregat() {
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
	public String getDateHeure() {
		return dateHeure;
	}
	public void setDateHeure(String dateHeure) {
		this.dateHeure = dateHeure;
	}
	public int getHeure() {
		return heure;
	}
	public void setHeure(int heure) {
		this.heure = heure;
	}
	public int getConsommation() {
		return consommation;
	}
	public void setConsommation(int consommation) {
		this.consommation = consommation;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public int getTemperature_min() {
		return temperature_min;
	}
	public void setTemperature_min(int temperature_min) {
		this.temperature_min = temperature_min;
	}
	public int getTemperature() {
		return temperature;
	}
	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}
	public int getTemperature_max() {
		return temperature_max;
	}
	public void setTemperature_max(int temperature_max) {
		this.temperature_max = temperature_max;
	}
	public int getDebitHoraire_min() {
		return debitHoraire_min;
	}
	public void setDebitHoraire_min(int debitHoraire_min) {
		this.debitHoraire_min = debitHoraire_min;
	}
	public int getDebitHoraire() {
		return debitHoraire;
	}
	public void setDebitHoraire(int debitHoraire) {
		this.debitHoraire = debitHoraire;
	}
	public int getDebitHoraire_max() {
		return debitHoraire_max;
	}
	public void setDebitHoraire_max(int debitHoraire_max) {
		this.debitHoraire_max = debitHoraire_max;
	}
	public int getRssi() {
		return rssi;
	}
	public void setRssi(int rssi) {
		this.rssi = rssi;
	}
	public int getModuleId() {
		return moduleId;
	}
	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}
	public String getModulePeriod() {
		return modulePeriod;
	}
	public void setModulePeriod(String modulePeriod) {
		this.modulePeriod = modulePeriod;
	}
}
