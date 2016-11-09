package com.ifi.common.bean;

import java.util.Map;

public class Lab009InvoiceYear {
	private Double totalMontalYear;
	private Integer totalConsommationYear;
	private Map<Integer, Double> mapMontalMonth;
	private Map<Integer, Integer> mapConsommationMonth;

	public Double getTotalMontalYear() {
		return totalMontalYear;
	}

	public void setTotalMontalYear(Double totalMontalYear) {
		this.totalMontalYear = totalMontalYear;
	}

	public Integer getTotalConsommationYear() {
		return totalConsommationYear;
	}

	public void setTotalConsommationYear(Integer totalConsommationYear) {
		this.totalConsommationYear = totalConsommationYear;
	}

	public Map<Integer, Double> getMapMontalMonth() {
		return mapMontalMonth;
	}

	public void setMapMontalMonth(Map<Integer, Double> mapMontalMonth) {
		this.mapMontalMonth = mapMontalMonth;
	}

	public Map<Integer, Integer> getMapConsommationMonth() {
		return mapConsommationMonth;
	}

	public void setMapConsommationMonth(Map<Integer, Integer> mapConsommationMonth) {
		this.mapConsommationMonth = mapConsommationMonth;
	}

}
