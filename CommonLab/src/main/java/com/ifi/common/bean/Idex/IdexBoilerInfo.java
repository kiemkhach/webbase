package com.ifi.common.bean.Idex;

import java.util.HashSet;
import java.util.Set;

public class IdexBoilerInfo {

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getIdex_installation_id() {
		return idex_installation_id;
	}
	public void setIdex_installation_id(int idex_installation_id) {
		this.idex_installation_id = idex_installation_id;
	}
	public Set<Integer> getListCounterId() {
		return listCounterId;
	}
	public void setListCounterId(Set<Integer> listCounterId) {
		this.listCounterId = listCounterId;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	
	private String name;
	private int idex_installation_id;
	private Set<Integer> listCounterId = new HashSet<Integer>();
	private String logo;
}
