package com.ifi.rest;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ifi.common.bean.Lab007Bean;
import com.ifi.common.util.ConfigEnum;
import com.ifi.common.util.FrontalKey;
import com.ifi.common.util.PropertiesReader;
import com.ifi.common.ws.GetDataAction;
import com.ifi.lab.LabDAO.dao.ConfigLab007DAO;
import com.ifi.lab.LabDAO.dao.ConstantDAO;
import com.ifi.lab.LabDAO.dao.LabDAO;
import com.ifi.lab.LabDAO.model.ConfigLab007;
import com.ifi.lab.LabDAO.model.Constant;
import com.reports.util.Utils;

@Controller
@RequestMapping("lab007")
public class Lab007Rest {
	@Autowired
	private ConfigLab007DAO lab007DAO;
	@Autowired
	private ConstantDAO constantDAO;

	@Autowired
	private LabDAO labDAO;
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat(FrontalKey.DATE_FORMAT_DAY_1);

	@RequestMapping(value = "getConfig", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody Lab007Bean getConfig(@RequestParam Integer siteId) {
		ConfigLab007 config = lab007DAO.findBySiteId(siteId);
		Lab007Bean labBean = new Lab007Bean();
		labBean.setId(config.getId());
		labBean.setSiteName(config.getSiteName());
		return labBean;
	}

	@RequestMapping(value = "getLabBySite", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody Lab007Bean getLabBySite(@RequestParam Integer siteId, @RequestParam String fromDate,
			@RequestParam String toDate) {
		Calendar cal = Calendar.getInstance();
		if (toDate == null || toDate.isEmpty()) {
			toDate = sdf.format(cal.getTime());
		}
		if (fromDate == null || fromDate.isEmpty()) {
			int labId = labDAO.findByName(PropertiesReader.getValue(ConfigEnum.LAB_NAME_007)).getId();
			int numberMonth = 1;
			Constant constant = constantDAO.getByLabIdnKey(labId, FrontalKey.LAB007_NUMBER_MONTH);
			if (constant != null) {
				numberMonth = Integer.parseInt(constant.getValue());
			}
			cal.add(Calendar.MONTH, -numberMonth);
			fromDate = sdf.format(cal.getTime());
		}
		
		ConfigLab007 config = lab007DAO.findBySiteId(siteId);
		
		Lab007Bean bean = new Lab007Bean();
		
		bean.setId(config.getId());
		bean.setSiteId(config.getSiteId());
		
//		bean.setTotalElec(151d);
//		bean.setTotalGas(1611.1);
//		bean.setTotalWater(484.5);
//		bean.setSubTotalElec(150.2);
		bean.setTotalElec(getConsumptionByModule(config.getTotalElecModuleId(), fromDate, toDate));
		bean.setTotalGas(getConsumptionByModule(config.getTotalGasModuleId(), fromDate, toDate));
		bean.setTotalWater(getConsumptionByModule(config.getTotalWaterModuleId(), fromDate, toDate));
		bean.setSubTotalElec(getConsumptionByModule(config.getTotalPylonesModuleId(), fromDate, toDate));
		
		bean.setUnitTotalElec(config.getUnitElec());
		bean.setUnitTotalWater(config.getUnitWater());
		bean.setUnitTotalGas(config.getUnitGas());
		bean.setUnitSubTotalElec(config.getUnitPylones());
		
		bean.setStartDate(fromDate);
		bean.setEndDate(toDate);
		bean.setSiteName(config.getSiteName());
		
		return bean;
	}
	
	/**
	 * moduleArr module id separate by comma
	 * 
	 * @param moduleStr
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	private Integer getConsumptionByModule(String moduleStr, String fromDate, String toDate) {
		if (moduleStr == null || moduleStr.isEmpty()) {
			return null;
		}
		String[] moduleArr = moduleStr.split(FrontalKey.PLUS_SPECIAL);
		Double result = 0D;
		GetDataAction dataAction = new GetDataAction();
		for (String moduleID : moduleArr) {
			Integer consommation = dataAction.getConsommationByTime(
					Integer.parseInt(Utils.getModuleNumberFromArray(moduleID)), fromDate, toDate);
			if (consommation != null) {
				/* result += agr.getConsommation(); */
				result += Utils.convertToKWh(moduleID, consommation);
			}
		}
		return result.intValue();
	}

	
	@RequestMapping(value = "getReportLink", method = RequestMethod.GET)
	public @ResponseBody String getReportLink(@RequestParam String siteId) {
		Integer siteNumber = WSCommon.getNumber(siteId);
		if (siteNumber == null) {
			return "siteId required";
		}
		ConfigLab007 configLab = lab007DAO.findBySiteId(siteNumber);
		if (configLab == null || configLab.getId() == null) {
			return "Site Id not exist";
		}
		if (configLab.getReportName() == null || configLab.getReportName().trim().equals("")) {
			return "Site Id - Report File not exist";
		}
		String reportLink = PropertiesReader.getValue(ConfigEnum.REPORT_LAB007_LINK) + FrontalKey.WINDOWS + siteId
				+ FrontalKey.WINDOWS + configLab.getReportName();
		return reportLink;
	}
	
	/**
	 * get URI of icon URI return for another system(premium)
	 * 
	 * @param siteId
	 * @return
	 */
	@RequestMapping(value = "getURIIcon", method = RequestMethod.GET)
	public @ResponseBody
	String getURIIcon(@RequestParam Integer siteId) {

		ConfigLab007 configLab = lab007DAO.findBySiteId(siteId);
		if (configLab == null) {
			return "This site is not config";
		}
		return configLab.getUriIcon();
	}

	/**
	 * get logo- logo display for page
	 * 
	 * @param siteId
	 * @return
	 */
	@RequestMapping(value = "getLogo", method = RequestMethod.GET)
	public @ResponseBody
	String getLogo(@RequestParam Integer siteId) {
		ConfigLab007 configLab = lab007DAO.findBySiteId(siteId);
		if (configLab == null) {
			return "This site is not config";
		}
		return configLab.getLogo();
	}
}
