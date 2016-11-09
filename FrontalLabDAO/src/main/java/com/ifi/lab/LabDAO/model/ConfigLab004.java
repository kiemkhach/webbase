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
@Table(name = "config_lab004")
public class ConfigLab004 {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "site_id")
	private Integer siteId;
	@Column(name = "site_name")
	private String siteName;
	@Column(name = "logo")
	private String logo;
	@Column(name = "site_info")
	private String siteInfo;
	@Column(name = "cible")
	private Integer cible;
	@Column(name = "unit_price")
	private Double unitPrice;
	@Column(name = "uri_icon")
	private String uriIcon;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL, CascadeType.PERSIST,
			CascadeType.MERGE }, mappedBy = "configLab004Id")
	private List<ConfigLab004Line> lineLst;

	public List<ConfigLab004Line> getLineLst() {
		return lineLst;
	}

	public void setLineLst(List<ConfigLab004Line> lineLst) {
		this.lineLst = lineLst;
	}

	public String getUriIcon() {
		return uriIcon;
	}

	public void setUriIcon(String uriIcon) {
		this.uriIcon = uriIcon;
	}

	public Integer getId() {
		return id;
	}

	public Integer getCible() {
		return cible;
	}

	public void setCible(Integer cible) {
		this.cible = cible;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getType() {
		return siteId;
	}

	public void setType(Integer type) {
		this.siteId = type;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getSiteInfo() {
		return siteInfo;
	}

	public void setSiteInfo(String siteInfo) {
		this.siteInfo = siteInfo;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

}
