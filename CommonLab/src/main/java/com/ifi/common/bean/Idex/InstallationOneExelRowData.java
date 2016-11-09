package com.ifi.common.bean.Idex;

public class InstallationOneExelRowData {
	
	private int keyId;
	private int rowIndex;
	private String name;
	private String value;
	private String comment;
	private int idexRowValue;
	private Integer keyTypeId;
	
	public Integer getKeyTypeId() {
		return keyTypeId;
	}

	public void setKeyTypeId(Integer keyTypeId) {
		this.keyTypeId = keyTypeId;
	}

	public int getIdexRowValue() {
		return idexRowValue;
	}

	public void setIdexRowValue(int idexRowValue) {
		this.idexRowValue = idexRowValue;
	}

	public int getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getKeyId() {
		return keyId;
	}

	public void setKeyId(int keyId) {
		this.keyId = keyId;
	}
}
