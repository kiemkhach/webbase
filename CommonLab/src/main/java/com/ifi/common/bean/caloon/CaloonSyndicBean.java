package com.ifi.common.bean.caloon;

public class CaloonSyndicBean {
	private Integer caloonSyndicId;
	private String name;
	private String ville;
	private String address;
	private String codePostal;
	private String reportPath;

	public Integer getCaloonSyndicId() {
		return caloonSyndicId;
	}

	public void setCaloonSyndicId(Integer caloonSyndicId) {
		this.caloonSyndicId = caloonSyndicId;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

}
