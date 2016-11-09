package com.ifi.common.bean.Idex;

import java.util.List;

public class NodeInfo {

	private int type;
	private String parentName;
	private Integer nodeId;

	private List<InstallationOneExelRowData> data;
	
	public Integer getNodeId() {
		return nodeId;
	}

	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public List<InstallationOneExelRowData> getData() {
		return data;
	}

	public void setData(List<InstallationOneExelRowData> data) {
		this.data = data;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

}
