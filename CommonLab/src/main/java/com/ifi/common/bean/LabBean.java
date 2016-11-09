package com.ifi.common.bean;

import java.util.List;

public class LabBean {
	
	private Integer labId;
	private String path;

	private List<LabSiteBean> labSiteLst;

	public List<LabSiteBean> getLabSiteLst() {
		return labSiteLst;
	}

	public void setLabSiteLst(List<LabSiteBean> labSiteLst) {
		this.labSiteLst = labSiteLst;
	}

	public Integer getLabId() {
		return labId;
	}

	public void setLabId(Integer labId) {
		this.labId = labId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
