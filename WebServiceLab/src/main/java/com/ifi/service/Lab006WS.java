package com.ifi.service;

import java.util.List;

import com.ifi.common.bean.Lab006Bean;
import com.ifi.common.bean.Lab006Client;
import com.reports.model.Lab006ClientInfo;
import com.reports.model.Lab006GlobalInfo;
import com.reports.model.LabChart;

public interface Lab006WS {
	Lab006Bean getLabBySite(Integer siteId, String fromDate, String toDate);

	Lab006GlobalInfo getReportGlobal(Integer siteId, String fromDate, String toDate);

	List<Lab006Client> getAllReportClient(Integer siteId, String fromDate, String toDate);

	Lab006ClientInfo getReportClient(Integer clientId, String fromDate, String toDate);

	List<LabChart> getGlobalChart(Integer siteId, String fromDateP, String toDateP);

	List<LabChart> getClientChart(Integer clientId, String toDateP);
}
