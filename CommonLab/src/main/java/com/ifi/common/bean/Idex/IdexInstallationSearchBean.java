package com.ifi.common.bean.Idex;

import java.util.List;

public class IdexInstallationSearchBean {
	private Integer installationId;
	private String name;
	private String code;
	private Float lat;
	private Float lng;
	List<Object> valueSearch;
	
	public Integer getInstallationId() {
		return installationId;
	}
	public void setInstallationId(Integer installationId) {
		this.installationId = installationId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public List<Object> getValueSearch() {
		return valueSearch;
	}
	public void setValueSearch(List<Object> valueSearch) {
		this.valueSearch = valueSearch;
	}
	public Float getLat() {
		return lat;
	}
	public void setLat(Float lat) {
		this.lat = lat;
	}
	public Float getLng() {
		return lng;
	}
	public void setLng(Float lng) {
		this.lng = lng;
	}

}
