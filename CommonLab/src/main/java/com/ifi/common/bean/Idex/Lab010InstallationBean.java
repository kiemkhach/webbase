package com.ifi.common.bean.Idex;

import java.util.List;

public class Lab010InstallationBean {
	private Integer installationId;
	private String name;
	private Float lat;
	private Float lng;
	private List<NodeInfo> installationRowLst;
	private String energyTypeFile;
	private String costFile;
	
	public String getEnergyTypeFile() {
		return energyTypeFile;
	}

	public void setEnergyTypeFile(String energyTypeFile) {
		this.energyTypeFile = energyTypeFile;
	}

	public String getCostFile() {
		return costFile;
	}

	public void setCostFile(String costFile) {
		this.costFile = costFile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getInstallationId() {
		return installationId;
	}

	public void setInstallationId(Integer installationId) {
		this.installationId = installationId;
	}

	public List<NodeInfo> getInstallationRowLst() {
		return installationRowLst;
	}

	public void setInstallationRowLst(List<NodeInfo> installationRowLst) {
		this.installationRowLst = installationRowLst;
	}

	public Float getLat() {
		return lat;
	}

	public void setLat(Float lat) {
		this.lat = lat;
	}

	public Float getLng() {
		return lng;
	}

	public void setLng(Float lng) {
		this.lng = lng;
	}

}
