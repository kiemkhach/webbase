package com.ifi.common.bean.Idex;

public class IdexKeyBean {
	private Integer idexKeyId;
	private String keyName;
	private String comment;
	private boolean isValidate;
	private Integer keyType;
	
	public Integer getKeyType() {
		return keyType;
	}
	public void setKeyType(Integer keyType) {
		this.keyType = keyType;
	}
	public boolean getIsValidate() {
		return isValidate;
	}
	public void setIsValidate(boolean isValidate) {
		this.isValidate = isValidate;
	}
	public Integer getIdexKeyId() {
		return idexKeyId;
	}
	public void setIdexKeyId(Integer idexKeyId) {
		this.idexKeyId = idexKeyId;
	}
	public String getKeyName() {
		return keyName;
	}
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}

}
