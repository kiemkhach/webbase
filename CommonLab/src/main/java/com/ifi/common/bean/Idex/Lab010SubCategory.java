package com.ifi.common.bean.Idex;

import java.util.List;

public class Lab010SubCategory {
	private Integer id;
	private String name;
	private List<Object[]> dataLst;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Object[]> getDataLst() {
		return dataLst;
	}

	public void setDataLst(List<Object[]> dataLst) {
		this.dataLst = dataLst;
	}

}
