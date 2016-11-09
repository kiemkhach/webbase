package com.ifi.common.bean.Idex;

import java.util.Date;
import java.util.Map;

public class Lab010IdexRelevesBean {
	private Map<Integer, Float> mapData;
	private Date fromDate;
	private Date toDate;

	public Map<Integer, Float> getMapData() {
		return mapData;
	}

	public void setMapData(Map<Integer, Float> mapData) {
		this.mapData = mapData;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

}
