package com.ifi.common.bean;

import java.util.List;

public class Lab009FilterBean {
	private Integer id;
	private String text;
	private String type;
	private List<String> valueRange;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getValueRange() {
		return valueRange;
	}

	public void setValueRange(List<String> valueRange) {
		this.valueRange = valueRange;
	}

}
