package com.reports.model;

import java.util.Date;

public class LabChart {
	private Date xCategory;
	private Integer value;
	private String groupName;
	private Integer total;

	public Date getxCategory() {
		return xCategory;
	}

	public void setxCategory(Date xCategory) {
		this.xCategory = xCategory;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

}
