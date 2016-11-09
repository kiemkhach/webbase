package com.ifi.common.bean.caloon;

public class CaloonUserBean {

	private Integer id;

	private String userName;

	private String firstName;

	private String lastName;

	private String password;

	private Integer status;// userType = 0; syndic user; userType = 1 resident
							// user;

	private Integer caloonSyndicId;// foreign key to CaloonSyndic Table
	
	private Integer caloonResidentId;// foreign key to CaloonResident Table

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getCaloonSyndicId() {
		return caloonSyndicId;
	}

	public void setCaloonSyndicId(Integer caloonSyndicId) {
		this.caloonSyndicId = caloonSyndicId;
	}

	public Integer getCaloonResidentId() {
		return caloonResidentId;
	}

	public void setCaloonResidentId(Integer caloonResidentId) {
		this.caloonResidentId = caloonResidentId;
	}
}
