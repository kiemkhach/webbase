package com.ifi.common.bean;


import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class Lab008PerformanceData {

	@SerializedName("site_id")
	private Integer siteId;
	@SerializedName("date_time")
	private Date month;
	private Double datakWhMensuel;
	private Double dataDJU;

	@Override
	public String toString() {
		return "siteId = " + siteId + ", month = " + month + ", datakWhMensuel = " + datakWhMensuel + ", dataDJU = "
				+ dataDJU;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public Date getMonth() {
		return month;
	}

	public void setMonth(Date month) {
		this.month = month;
	}

	public Double getDatakWhMensuel() {
		return datakWhMensuel;
	}

	public void setDatakWhMensuel(Double datakWhMensuel) {
		this.datakWhMensuel = datakWhMensuel;
	}

	public Double getDataDJU() {
		return dataDJU;
	}

	public void setDataDJU(Double dataDJU) {
		this.dataDJU = dataDJU;
	}

}
