package com.ifi.common.csm;

public class AgregatCSM {
	private String dateHeureAgregat;
	private String heureAgregat;
	private int index;
	private int temperature;
	private Integer moduleId;
	private Integer consommation;
	
	public Integer getConsommation() {
		return consommation;
	}

	public void setConsommation(Integer consommation) {
		this.consommation = consommation;
	}

	public String getDateHeureAgregat() {
		return dateHeureAgregat;
	}

	public void setDateHeureAgregat(String dateHeureAgregat) {
		this.dateHeureAgregat = dateHeureAgregat;
	}

	public String getHeureAgregat() {
		return heureAgregat;
	}

	public void setHeureAgregat(String heureAgregat) {
		this.heureAgregat = heureAgregat;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getTemperature() {
		return temperature;
	}

	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}

	public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}
}
