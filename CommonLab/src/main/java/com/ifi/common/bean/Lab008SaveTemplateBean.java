package com.ifi.common.bean;

public class Lab008SaveTemplateBean {
	private Integer id;
	private Integer siteId;
	private Long fromDate;
	private Long toDate;
	private String description;
	private char flag;
	private Boolean isFromBegin;
	private Boolean isCurrentDay;
	private String fromDateStr;
	private String toDateStr;
	// private Integer percent;
	// private Double rSquare;
	// private Double linearA;
	// private Double linearB;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	// public Integer getPercent() {
	// return percent;
	// }
	//
	// public void setPercent(Integer percent) {
	// this.percent = percent;
	// }
	//
	// public Double getrSquare() {
	// return rSquare;
	// }
	//
	// public void setrSquare(Double rSquare) {
	// this.rSquare = rSquare;
	// }
	//
	// public Double getLinearA() {
	// return linearA;
	// }
	//
	// public void setLinearA(Double linearA) {
	// this.linearA = linearA;
	// }
	//
	// public Double getLinearB() {
	// return linearB;
	// }
	//
	// public void setLinearB(Double linearB) {
	// this.linearB = linearB;
	// }

	public String getFromDateStr() {
		return fromDateStr;
	}

	public void setFromDateStr(String fromDateStr) {
		this.fromDateStr = fromDateStr;
	}

	public String getToDateStr() {
		return toDateStr;
	}

	public void setToDateStr(String toDateStr) {
		this.toDateStr = toDateStr;
	}

	public Long getFromDate() {
		return fromDate;
	}

	public void setFromDate(Long fromDate) {
		this.fromDate = fromDate;
	}

	public Long getToDate() {
		return toDate;
	}

	public void setToDate(Long toDate) {
		this.toDate = toDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public char getFlag() {
		return flag;
	}

	public void setFlag(char flag) {
		this.flag = flag;
	}

	public Boolean getIsFromBegin() {
		return isFromBegin;
	}

	public void setIsFromBegin(Boolean isFromBegin) {
		this.isFromBegin = isFromBegin;
	}

	public Boolean getIsCurrentDay() {
		return isCurrentDay;
	}

	public void setIsCurrentDay(Boolean isCurrentDay) {
		this.isCurrentDay = isCurrentDay;
	}

}
