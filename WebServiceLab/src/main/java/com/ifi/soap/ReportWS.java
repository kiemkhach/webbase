package com.ifi.soap;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ifi.service.Lab006WS;
import com.ifi.service.Lab007WS;
import com.ifi.service.impl.Lab006WSImpl;
import com.ifi.service.impl.Lab007WSImpl;
import com.reports.model.Lab006ClientInfo;
import com.reports.model.Lab006GlobalClientLst;
import com.reports.model.Lab006GlobalInfo;
import com.reports.model.Lab007ChartLst;
import com.reports.model.LabChart;
import com.reports.model.LabChartLst;

@WebService
@SOAPBinding(style = Style.RPC)
public class ReportWS {

	private static final ApplicationContext CONTEXT = new ClassPathXmlApplicationContext("serviceContext.xml");

	@Autowired
	private Lab006WS service006 = (Lab006WSImpl) CONTEXT.getBean("lab006WS");
	
	@Autowired
	private Lab007WS service007 = (Lab007WSImpl) CONTEXT.getBean("lab007WS");
	// [START]LAB006

	// GLOBAL
	@WebMethod(operationName = "lab006GlobalInfo")
	public Lab006GlobalInfo lab006GlobalInfo(@WebParam(name = "siteId") Integer siteId,
			@WebParam(name = "fromDate") String fromDate, @WebParam(name = "toDate") String toDate) {
		return service006.getReportGlobal(siteId, fromDate, toDate);
	}

	@WebMethod(operationName = "lab006GlobalClientLst")
	public Lab006GlobalClientLst lab006GlobalClientLst(@WebParam(name = "siteId") Integer siteId,
			@WebParam(name = "fromDate") String fromDate, @WebParam(name = "toDate") String toDate) {
		Lab006GlobalClientLst clients = new Lab006GlobalClientLst();
		clients.setClients(service006.getAllReportClient(siteId, fromDate, toDate));
		return clients;
	}

	@WebMethod(operationName = "lab006GlobalChart")
	public LabChartLst lab006GlobalChart(@WebParam(name = "siteId") Integer siteId,
			@WebParam(name = "fromDate") String fromDate, @WebParam(name = "toDate") String toDate) {
		List<LabChart> lst = service006.getGlobalChart(siteId, fromDate, toDate);
		LabChartLst chart = new LabChartLst();
		chart.setLabChartLst(lst);
		return chart;
	}

	// CLIENT
	@WebMethod(operationName = "lab006ClientInfo")
	public Lab006ClientInfo lab006ClientInfo(@WebParam(name = "clientId") Integer clientId,
			@WebParam(name = "fromDate") String fromDate, @WebParam(name = "toDate") String toDate) {
		return service006.getReportClient(clientId, fromDate, toDate);
	}

	@WebMethod(operationName = "lab006ClientChart")
	public LabChartLst lab006ClientChart(@WebParam(name = "clientId") Integer clientId,
			@WebParam(name = "fromDate") String fromDate, @WebParam(name = "toDate") String toDate) {

		LabChartLst chart = new LabChartLst();
		List<LabChart> itemLst = service006.getClientChart(clientId, toDate);
		chart.setLabChartLst(itemLst);
		return chart;
	}
	// [END]LAB006
	
	//[START] LAB007
	@WebMethod(operationName="getLab007ChartData")
	public Lab007ChartLst getChartData(@WebParam(name="siteId") Integer siteId,@WebParam(name="fromDate") String fromDate,@WebParam(name="toDate") String toDate){
		return service007.getChartData(siteId, fromDate, toDate);
	}
	//[END] LAB007
}
