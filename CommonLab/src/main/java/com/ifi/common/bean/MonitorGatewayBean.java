package com.ifi.common.bean;

import java.util.Date;

public class MonitorGatewayBean {
	private Integer id;
	private String serialNo;
	private String dateData;
	private Date dateData2;
	private String lastUpdated;
	private String type;
	private int status;
	private int numFiles;
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDateData() {
		return dateData;
	}
	public void setDateData(String dateData) {
		this.dateData = dateData;
	}
	public String getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	public Date getDateData2() {
		return dateData2;
	}
	public void setDateData2(Date dateData2) {
		this.dateData2 = dateData2;
	}
	public int getNumFiles() {
		return numFiles;
	}
	public void setNumFiles(int numFiles) {
		this.numFiles = numFiles;
	}
	
}
