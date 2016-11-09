package com.energisme.service;

import com.ifi.common.bean.CarreFourBean;

public interface CarreFourService {
	CarreFourBean getCarreFourBySite(Integer siteId);
	String getReportLink(Integer siteId);
	String getLogo(Integer siteId);
	String getUriIcon(Integer siteId);
//	boolean isCheck(String userName,Integer siteID);
}
