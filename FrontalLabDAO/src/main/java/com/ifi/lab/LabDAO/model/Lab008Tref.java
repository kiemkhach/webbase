package com.ifi.lab.LabDAO.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by nmha on 06/04/2016.
 */
@Entity
@Table(name="lab008_temperature_ref")
public class Lab008Tref {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;    
    @Column(name = "applicable_date")
    private Date applicableDate;  
    @Column(name = "temperature_ref")
    private Double temperatureRef;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}	
	public Date getApplicableDate() {
		return applicableDate;
	}
	public void setApplicableDate(Date applicableDate) {
		this.applicableDate = applicableDate;
	}
	public Double getTemperatureRef() {
		return temperatureRef;
	}
	public void setTemperatureRef(Double temperatureRef) {
		this.temperatureRef = temperatureRef;
	}
    	
}
