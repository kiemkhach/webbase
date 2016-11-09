package com.ifi.common.bean.caloon;

public class Lab011Client {
	private Integer residentId;
	private Integer energy;
	private Double rate;
	private String clientName;

	public Integer getResidentId() {
		return residentId;
	}

	public void setResidentId(Integer residentId) {
		this.residentId = residentId;
	}

	public Lab011Client(Integer energy) {
		this.energy = energy;
	}

	public Lab011Client(Integer energy, Double rate) {
		super();
		this.energy = energy;
		this.rate = rate;
	}

	public Lab011Client(Integer energy, Double rate, String clientName) {
		super();
		this.energy = energy;
		this.rate = rate;
		this.clientName = clientName;
	}

	public Integer getEnergy() {
		return energy;
	}

	public void setEnergy(Integer energy) {
		this.energy = energy;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

}
