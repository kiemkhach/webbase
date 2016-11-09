package com.ifi.common.bean;

public class MonitorViewBean {
	private Integer id;
	private String name;
	private Integer gatewayQuantity;

	public Integer getGatewayQuantity() {
		return gatewayQuantity;
	}

	public void setGatewayQuantity(Integer gatewayQuantity) {
		this.gatewayQuantity = gatewayQuantity;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
