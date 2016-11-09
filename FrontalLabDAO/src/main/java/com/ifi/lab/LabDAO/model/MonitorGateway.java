package com.ifi.lab.LabDAO.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "monitor_gateway")
public class MonitorGateway {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "serial_no")
	private String serialNo;
	@Column(name = "date_data")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateData;
	@Column(name = "last_updated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdated;
	@Column(name = "type")
	private String type;
	@Column(name = "active")
	private int active = 1;
	@Transient
	private int numFiles;

	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL,
			CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "gatewayId")
	private List<MonitorModule> moduleLst;

	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL,
			CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "gatewayId")
	private List<MViewGateway> viewGatewayLst;

	public List<MViewGateway> getViewGatewayLst() {
		return viewGatewayLst;
	}

	public void setViewGatewayLst(List<MViewGateway> viewGatewayLst) {
		this.viewGatewayLst = viewGatewayLst;
	}

	public List<MonitorModule> getModuleLst() {
		return moduleLst;
	}

	public void setModuleLst(List<MonitorModule> moduleLst) {
		this.moduleLst = moduleLst;
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

	public Date getDateData() {
		return dateData;
	}

	public void setDateData(Date dateData) {
		this.dateData = dateData;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public int getNumFiles() {
		return numFiles;
	}

	public void setNumFiles(int numFiles) {
		this.numFiles = numFiles;
	}

}
