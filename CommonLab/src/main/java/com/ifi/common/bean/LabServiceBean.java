package com.ifi.common.bean;

import java.util.List;

public class LabServiceBean {
	private int type;
	private List<LabBean> labBeanLst;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public List<LabBean> getLabBeanLst() {
		return labBeanLst;
	}

	public void setLabBeanLst(List<LabBean> labBeanLst) {
		this.labBeanLst = labBeanLst;
	}
}
