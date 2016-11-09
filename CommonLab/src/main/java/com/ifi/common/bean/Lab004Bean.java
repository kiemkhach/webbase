package com.ifi.common.bean;

import java.util.List;

public class Lab004Bean {
	private int siteId;
	private String siteName;
	private String siteInfo;
	private String logo;
	private String uriIcon;
	
	private String address;
	private String date;
	private String fromHour;
	private String toHour;
	
	private int type;
	private List<Lab004LineBean> lineLst;
	private Lab004LineBean lineTotal;
	private byte[] imageInByte;
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getFromHour() {
		return fromHour;
	}
	public void setFromHour(String fromHour) {
		this.fromHour = fromHour;
	}
	public String getToHour() {
		return toHour;
	}
	public void setToHour(String toHour) {
		this.toHour = toHour;
	}
	public List<Lab004LineBean> getLineLst() {
		return lineLst;
	}
	public void setLineLst(List<Lab004LineBean> lineLst) {
		this.lineLst = lineLst;
	}
	public Lab004LineBean getLineTotal() {
		return lineTotal;
	}
	public void setLineTotal(Lab004LineBean lineTotal) {
		this.lineTotal = lineTotal;
	}
	public byte[] getImageInByte() {
		return imageInByte;
	}
	public void setImageInByte(byte[] imageInByte) {
		this.imageInByte = imageInByte;
	}
	public int getSiteId() {
		return siteId;
	}
	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getSiteInfo() {
		return siteInfo;
	}
	public void setSiteInfo(String siteInfo) {
		this.siteInfo = siteInfo;
	}
	public String getUriIcon() {
		return uriIcon;
	}
	public void setUriIcon(String uriIcon) {
		this.uriIcon = uriIcon;
	}
}
