package com.ifi.common.bean;

import java.util.Date;

public class MGatewayFileBean {

	private Integer id;
	private Integer gatewayId;
	private Date dateData;
	private String content;
	private String fileName;
	private String gatewayNo;
	private Double fileSizeKB;
	private Long fileSize;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGatewayId() {
		return gatewayId;
	}

	public void setGatewayId(Integer gatewayId) {
		this.gatewayId = gatewayId;
	}

	public Date getDateData() {
		return dateData;
	}

	public void setDateData(Date dateData) {
		this.dateData = dateData;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getGatewayNo() {
		return gatewayNo;
	}

	public void setGatewayNo(String gatewayNo) {
		this.gatewayNo = gatewayNo;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public Double getFileSizeKB() {
		return fileSizeKB;
	}

	public void setFileSizeKB(Double fileSizeKB) {
		this.fileSizeKB = fileSizeKB;
	}

}
