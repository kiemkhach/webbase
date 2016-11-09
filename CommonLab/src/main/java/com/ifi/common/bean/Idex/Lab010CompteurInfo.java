package com.ifi.common.bean.Idex;

public class Lab010CompteurInfo {

	private Integer idexInstallationId;
	private Integer idexCounterId;
	private Integer counterType;
	private NodeInfo nodeInfo;
	private Integer nb;
	private String calculDuNc;
	private Integer keyTypeCompterVirtuel;
	private String formuleDeCalcul;
	private String compterName;
	
	public String getCompterName() {
		return compterName;
	}

	public void setCompterName(String compterName) {
		this.compterName = compterName;
	}

	public Integer getIdexInstallationId() {
		return idexInstallationId;
	}

	public void setIdexInstallationId(Integer idexInstallationId) {
		this.idexInstallationId = idexInstallationId;
	}

	public Integer getIdexCounterId() {
		return idexCounterId;
	}

	public void setIdexCounterId(Integer idexCounterId) {
		this.idexCounterId = idexCounterId;
	}

	public Integer getCounterType() {
		return counterType;
	}

	public void setCounterType(Integer counterType) {
		this.counterType = counterType;
	}

	public NodeInfo getNodeInfo() {
		return nodeInfo;
	}

	public void setNodeInfo(NodeInfo nodeInfo) {
		this.nodeInfo = nodeInfo;
	}

	public Integer getNb() {
		return nb;
	}

	public void setNb(Integer nb) {
		this.nb = nb;
	}

	public String getCalculDuNc() {
		return calculDuNc;
	}

	public void setCalculDuNc(String calculDuNc) {
		this.calculDuNc = calculDuNc;
	}

	public Integer getKeyTypeCompterVirtuel() {
		return keyTypeCompterVirtuel;
	}

	public void setKeyTypeCompterVirtuel(Integer keyTypeCompterVirtuel) {
		this.keyTypeCompterVirtuel = keyTypeCompterVirtuel;
	}

	public String getFormuleDeCalcul() {
		return formuleDeCalcul;
	}

	public void setFormuleDeCalcul(String formuleDeCalcul) {
		this.formuleDeCalcul = formuleDeCalcul;
	}

}
