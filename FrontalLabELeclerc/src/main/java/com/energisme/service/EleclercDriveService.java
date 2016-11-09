package com.energisme.service;

import com.ifi.common.bean.EleclercDriveBean;

public interface EleclercDriveService {
	Integer getMaxValueDrive(String labName, String key);
	String getDriveReportLink(Integer siteId);
	EleclercDriveBean getELeclercDrive(String labName, int pageId,
			Integer siteId);
}
