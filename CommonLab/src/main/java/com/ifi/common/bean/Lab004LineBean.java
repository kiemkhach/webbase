package com.ifi.common.bean;

public class Lab004LineBean {
	private Integer id;
	// get from mudule ID
	private Integer consommation;

	private Integer previousConsommation;
	// Fixed
	private Integer money;

	// Fixed
	private Integer previousMoney;
	// Fixed
	private Integer mass;
	// proportion = consommation/mass
	private Integer product;

	private Integer previousProduct;
	// Fixed
	private Integer cible;
	// Calculate from DB
	private Integer ecart;
	// ecartRate
	private Integer ecartMoney;

	private Integer previousEcart;

	// compare with previous hour: 1: increa 01: decrea : null: not exists
	private Boolean isIncrease;
	
	// clock 0--> 100;
	private Double clock;
	
	public Double getClock() {
		return clock;
	}

	public void setClock(Double clock) {
		this.clock = clock;
	}

	private String lineCode;

	public Lab004LineBean() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPreviousProduct() {
		return previousProduct;
	}

	public Integer getPreviousMoney() {
		return previousMoney;
	}

	public void setPreviousMoney(Integer previousMoney) {
		this.previousMoney = previousMoney;
	}

	public void setPreviousProduct(Integer previousProduct) {
		this.previousProduct = previousProduct;
	}

	public Integer getPreviousConsommation() {
		return previousConsommation;
	}

	public void setPreviousConsommation(Integer previousConsommation) {
		this.previousConsommation = previousConsommation;
	}

	public Integer getPreviousEcart() {
		return previousEcart;
	}

	public void setPreviousEcart(Integer previousEcart) {
		this.previousEcart = previousEcart;
	}

	public Boolean getIsIncrease() {
		return isIncrease;
	}

	public void setIsIncrease(Boolean isIncrease) {
		this.isIncrease = isIncrease;
	}

	public Integer getConsommation() {
		return consommation;
	}

	public void setConsommation(Integer consommation) {
		this.consommation = consommation;
	}

	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

	public Integer getMass() {
		return mass;
	}

	public void setMass(Integer mass) {
		this.mass = mass;
	}

	public Integer getProduct() {
		return product;
	}

	public void setProduct(Integer product) {
		this.product = product;
	}

	public Integer getCible() {
		return cible;
	}

	public void setCible(Integer cible) {
		this.cible = cible;
	}

	public Integer getEcart() {
		return ecart;
	}

	public void setEcart(Integer ecart) {
		this.ecart = ecart;
	}

	public String getLineCode() {
		return lineCode;
	}

	public void setLineCode(String lineCode) {
		this.lineCode = lineCode;
	}

	public Integer getEcartMoney() {
		return ecartMoney;
	}

	public void setEcartMoney(Integer ecartMoney) {
		this.ecartMoney = ecartMoney;
	}

}
