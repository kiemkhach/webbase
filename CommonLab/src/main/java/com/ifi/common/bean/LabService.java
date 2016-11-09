package com.ifi.common.bean;

import java.util.List;

public class LabService {
	private Integer labId;
	private String labName;
	private String url;
	private String logoUri;
	private List<LabSubcription> subLst;

	public Integer getLabId() {
		return labId;
	}

	public void setLabId(Integer labId) {
		this.labId = labId;
	}

	public String getLabName() {
		return labName;
	}

	public void setLabName(String labName) {
		this.labName = labName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLogoUri() {
		return logoUri;
	}

	public void setLogoUri(String logoUri) {
		this.logoUri = logoUri;
	}

	public List<LabSubcription> getSubLst() {
		return subLst;
	}

	public void setSubLst(List<LabSubcription> subLst) {
		this.subLst = subLst;
	}

}
