package com.ifi.lab.LabDAO.model.caloon;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "caloon_user")
public class CaloonUser {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "password")
	private String password;

	@Column(name = "status", nullable = false, columnDefinition = "int default 1")
	private Integer status;// userType = 0; syndic user; userType = 1 resident
							// user;

//	@Column(name = "caloon_syndic_id")
//	private Integer caloonSyndicId;// foreign key to CaloonSyndic Table
//	@Column(name = "caloon_resident_id")
//	private Integer caloonResidentId;// foreign key to CaloonResident Table

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

//	public Integer getCaloonSyndicId() {
//		return caloonSyndicId;
//	}
//
//	public void setCaloonSyndicId(Integer caloonSyndicId) {
//		this.caloonSyndicId = caloonSyndicId;
//	}
//
//	public Integer getCaloonResidentId() {
//		return caloonResidentId;
//	}
//
//	public void setCaloonResidentId(Integer caloonResidentId) {
//		this.caloonResidentId = caloonResidentId;
//	}

}
