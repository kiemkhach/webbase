package com.ifi.service;

import com.ifi.common.bean.Lab007Bean;
import com.reports.model.Lab007ChartLst;
import com.reports.model.Lab007Info;

public interface Lab007WS {
	Lab007Bean getConfig(Integer siteId);
	Lab007Bean getLabBySite(Integer siteId, String fromDate, String toDate);
	
	
	String getReportLink(String siteId);
	
	/**
	 * get URI of icon URI return for another system(premium)
	 * 
	 * @param siteId
	 * @return {@link String}
	 */
	String getURIIcon(Integer siteId);
	
	/**
	 * get logo- logo display for page
	 * 
	 * @param siteId
	 * @return {@link String}
	 */
	String getLogo(Integer siteId);
	
	Lab007ChartLst getChartData(Integer siteId, String fromDate, String toDate);
	
	Lab007Info getLab007Info(Integer siteId, String fromDate, String toDate);
}
