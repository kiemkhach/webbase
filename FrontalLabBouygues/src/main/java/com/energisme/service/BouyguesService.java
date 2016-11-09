package com.energisme.service;

import com.ifi.common.bean.BouyguesBean;

public interface BouyguesService {
	BouyguesBean getBouyguesDataBySite(String path, Integer siteId);
	String getReportLink(Integer siteId);
	String getLogo(Integer siteId);
	String getIcon(Integer siteId);
}
