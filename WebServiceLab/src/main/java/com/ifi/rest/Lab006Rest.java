package com.ifi.rest;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ifi.common.bean.Lab006Bean;
import com.ifi.common.util.ConfigEnum;
import com.ifi.common.util.FrontalKey;
import com.ifi.common.util.PropertiesReader;
import com.ifi.lab.LabDAO.dao.ConfigLab006Dao;
import com.ifi.lab.LabDAO.model.ConfigLab006;
import com.ifi.service.Lab006WS;

@Controller
@RequestMapping("lab006")
public class Lab006Rest {
	
	@Autowired
	private ConfigLab006Dao lab006DAO;

	@Autowired
	private Lab006WS service;
	
	@RequestMapping(value = "getConfig", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody Lab006Bean getConfig(@RequestParam Integer siteId) {
		ConfigLab006 config = lab006DAO.findBySite(siteId);
		Lab006Bean labBean = new Lab006Bean();
		labBean.setId(config.getId());
		labBean.setSiteName(config.getSiteName());
		labBean.setLogo(config.getLogo());
		labBean.setUriIcon(config.getUriIcon());
		return labBean;
	}

	/**
	 * For display interface
	 * @param siteId
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	@RequestMapping(value = "getLabBySite", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody Lab006Bean getLabBySite(@RequestParam Integer siteId, @RequestParam String fromDate,
			@RequestParam String toDate) {
		return service.getLabBySite(siteId, fromDate, toDate);
	}
	
	@RequestMapping(value = "getReportLink", method = RequestMethod.GET)
	public @ResponseBody String getReportLink(@RequestParam String siteId) {
		Integer siteNumber = WSCommon.getNumber(siteId);
		if (siteNumber == null) {
			return "siteId required";
		}
		ConfigLab006 configLab = lab006DAO.findBySite(siteNumber);
		if (configLab == null || configLab.getId() == null) {
			return "Site Id not exist";
		}
		if (configLab.getReportName() == null || configLab.getReportName().trim().equals("")) {
			return "Site Id - Report File not exist";
		}
		String reportLink = PropertiesReader.getValue(ConfigEnum.REPORT_LAB006_LINK) + FrontalKey.WINDOWS + siteId
				+ FrontalKey.WINDOWS + configLab.getReportName();
		return reportLink;
	}

}
