package com.ifi.lab.LabDAO.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "lab008_save_template")
public class Lab008SaveTemplate {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "site_id")
	private Integer siteId;
	@Column(name = "fromDate")
	private Date fromDate;
	@Column(name = "toDate")
	private Date toDate;
	@Column(name = "description")
	private String description;
	@Column(name = "isFromBegin")
	private Boolean isFromBegin;
	@Column(name = "isCurrentDay")
	private Boolean isCurrentDay;
	// @Column(name = "percent")
	// private Integer percent;
	// @Column(name = "linearA")
	// private Double linearA;
	// @Column(name = "linearB")
	// private Double linearB;
	// @Column(name = "rSquare")
	// private Double rSquare;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean isFromBegin() {
		return isFromBegin;
	}

	public void setFromBegin(Boolean isFromBegin) {
		this.isFromBegin = isFromBegin;
	}

	public Boolean isCurrentDay() {
		return isCurrentDay;
	}

	public void setCurrentDay(Boolean isCurrentDay) {
		this.isCurrentDay = isCurrentDay;
	}

	//
	// public Integer getPercent() {
	// return percent;
	// }
	//
	// public void setPercent(Integer percent) {
	// this.percent = percent;
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
	//
	// public Double getrSquare() {
	// return rSquare;
	// }
	//
	// public void setrSquare(Double rSquare) {
	// this.rSquare = rSquare;
	// }

}
