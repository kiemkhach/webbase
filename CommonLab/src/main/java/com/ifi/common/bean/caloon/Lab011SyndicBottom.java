package com.ifi.common.bean.caloon;

import java.util.List;

public class Lab011SyndicBottom {
	private Integer globaleConsommation;
	private Double globaleMoyenneConsommation;
	private Integer wastageEnergy;
	private List<Lab011Client> lab011ClientsLst;
	private Lab011Client partiesCommunes;

	public Integer getWastageEnergy() {
		return wastageEnergy;
	}

	public void setWastageEnergy(Integer wastageEnergy) {
		this.wastageEnergy = wastageEnergy;
	}

	public List<Lab011Client> getLab011ClientsLst() {
		return lab011ClientsLst;
	}

	public void setLab011ClientsLst(List<Lab011Client> lab011ClientsLst) {
		this.lab011ClientsLst = lab011ClientsLst;
	}

	public Lab011Client getPartiesCommunes() {
		return partiesCommunes;
	}

	public void setPartiesCommunes(Lab011Client partiesCommunes) {
		this.partiesCommunes = partiesCommunes;
	}

	public Integer getGlobaleConsommation() {
		return globaleConsommation;
	}

	public void setGlobaleConsommation(Integer globaleConsommation) {
		this.globaleConsommation = globaleConsommation;
	}

	public Double getGlobaleMoyenneConsommation() {
		return globaleMoyenneConsommation;
	}

	public void setGlobaleMoyenneConsommation(Double globaleMoyenneConsommation) {
		this.globaleMoyenneConsommation = globaleMoyenneConsommation;
	}

}
