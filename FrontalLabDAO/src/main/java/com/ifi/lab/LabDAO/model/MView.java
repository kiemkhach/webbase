package com.ifi.lab.LabDAO.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "m_view")
public class MView {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "name")
	private String name;
	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL,
			CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "viewId")
	private List<MViewGateway> viewGatewayLst;

	public List<MViewGateway> getViewGatewayLst() {
		return viewGatewayLst;
	}

	public void setViewGatewayLst(List<MViewGateway> viewGatewayLst) {
		this.viewGatewayLst = viewGatewayLst;
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
