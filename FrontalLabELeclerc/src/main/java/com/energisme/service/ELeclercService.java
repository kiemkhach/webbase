package com.energisme.service;

import com.ifi.common.bean.ELeclercBean;

public interface ELeclercService {
	ELeclercBean getELeclercBySite(Integer siteId, String labName, Integer pageId);
	String getReportLink(Integer siteId);
	String getLogo(Integer siteId);
	String getUriIcon(Integer siteId);
}
