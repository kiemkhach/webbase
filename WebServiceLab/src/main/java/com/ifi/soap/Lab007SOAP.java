package com.ifi.soap;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ifi.service.Lab007WS;
import com.ifi.service.impl.Lab007WSImpl;
import com.reports.model.Lab007ChartLst;
import com.reports.model.Lab007Info;

@WebService
@SOAPBinding(style = Style.RPC)
public class Lab007SOAP {
	private static final ApplicationContext CONTEXT = new ClassPathXmlApplicationContext("serviceContext.xml");
	@Autowired
	private Lab007WS service007 = (Lab007WSImpl) CONTEXT.getBean("lab007WS");
	
	@WebMethod(operationName="getLab007ChartData")
	public Lab007ChartLst getChartData(@WebParam(name="siteId") Integer siteId,@WebParam(name="fromDate") String fromDate,@WebParam(name="toDate") String toDate){
		return service007.getChartData(siteId, fromDate, toDate);
	}
	@WebMethod(operationName="getLab007Info")
	public Lab007Info getLab007Info(@WebParam(name="siteId") Integer siteId,@WebParam(name="fromDate") String fromDate,@WebParam(name="toDate") String toDate){
		return service007.getLab007Info(siteId, fromDate, toDate);
	}
}
