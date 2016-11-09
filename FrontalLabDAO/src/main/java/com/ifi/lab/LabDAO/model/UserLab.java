package com.ifi.lab.LabDAO.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_lab")
public class UserLab implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="userId")	
	private int userId;
	@Id
	@Column(name="labId")	
	private int labId;
	@Id
	@Column(name="siteId")
	private int siteId;

	public UserLab() {
	}	
	
	public UserLab(int userId, int labId,int siteId) {
		super();
		this.userId = userId;
		this.labId = labId;
		this.siteId = siteId;
	}

	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getLabId() {
		return labId;
	}
	public void setLabId(int labId) {
		this.labId = labId;
	}

	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}
}
