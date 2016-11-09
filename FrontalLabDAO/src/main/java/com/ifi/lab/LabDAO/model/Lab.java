package com.ifi.lab.LabDAO.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="lab")
public class Lab {
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Column(name="url")
	private String url;
	@Column(name="uri")
	private String uri;
	@Column(name="name")
	private String name;
	@Column(name="logo_path")
	private String logoPath;
	@Column(name="column_site")
	private String columnSite;
	@Column(name="table_site")
	private String tableSite;
	@Column(name="column_key")
	private String columnKey;
	
	@Transient
	private Integer siteId;
	
	public Lab() {
	}	
	
	public Lab(String url, String uri, String name) {
		super();
		this.url = url;
		this.uri = uri;
		this.name = name;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public String getColumnSite() {
		return columnSite;
	}

	public void setColumnSite(String columnSite) {
		this.columnSite = columnSite;
	}

	public String getTableSite() {
		return tableSite;
	}

	public void setTableSite(String tableSite) {
		this.tableSite = tableSite;
	}

	public String getColumnKey() {
		return columnKey;
	}

	public void setColumnKey(String columnKey) {
		this.columnKey = columnKey;
	}
	
}
