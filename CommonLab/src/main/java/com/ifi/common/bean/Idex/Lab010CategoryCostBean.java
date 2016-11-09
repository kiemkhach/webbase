package com.ifi.common.bean.Idex;

import java.util.List;

public class Lab010CategoryCostBean {
	private Integer id;
	private String name;
	private List<Lab010SubCategory> subCategoryLst;

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

	public List<Lab010SubCategory> getSubCategoryLst() {
		return subCategoryLst;
	}

	public void setSubCategoryLst(List<Lab010SubCategory> subCategoryLst) {
		this.subCategoryLst = subCategoryLst;
	}

}
