package com.ifi.rest;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ifi.common.bean.Lab005Bean;
import com.ifi.common.bean.Lab005DataGauge;
import com.ifi.common.util.ConfigEnum;
import com.ifi.common.util.FrontalKey;
import com.ifi.common.util.PropertiesReader;
import com.ifi.lab.LabDAO.dao.ConfigLab005Dao;
import com.ifi.lab.LabDAO.model.ConfigLab005;

@Controller
@RequestMapping("lab005")
public class Lab005Rest {

	@Autowired
	private ConfigLab005Dao lab005DAO;

	@RequestMapping(value = "getData", method = RequestMethod.GET)
	public @ResponseBody String getData(Integer siteId) {

		ConfigLab005 config = lab005DAO.getConfigBySite(siteId);

		Lab005Bean bean = new Lab005Bean();
		if (config != null) {
			bean.setSiteName(config.getSiteName());
		}
		bean.setElectric((int) (Math.random() * 10000));
		bean.setGaz((int) (Math.random() * 1000));
		bean.setElecPercent((int) (Math.random() * 100));
		bean.setGasPercent(0 - (int) (Math.random() * 100));
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		bean.setStartDate(dateFormat.format(new Date()));
		Lab005DataGauge arr1 = new Lab005DataGauge(Math.random() * 100, Math.random() * 100);
		Lab005DataGauge arr2 = new Lab005DataGauge(Math.random() * 100, Math.random() * 100);
		Lab005DataGauge arr3 = new Lab005DataGauge(Math.random() * 100, Math.random() * 100);
		Lab005DataGauge arr4 = new Lab005DataGauge(Math.random() * 100, Math.random() * 100);
		Lab005DataGauge arr5 = new Lab005DataGauge(Math.random() * 100, Math.random() * 100);
		Lab005DataGauge arr6 = new Lab005DataGauge(Math.random() * 100, Math.random() * 100);
		List<Lab005DataGauge> listCom = new ArrayList<Lab005DataGauge>();
		listCom.add(arr1);
		listCom.add(arr2);
		listCom.add(arr3);
		listCom.add(arr4);
		listCom.add(arr5);
		listCom.add(arr6);
		bean.setLstConsomation(listCom);
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(bean);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@RequestMapping(value = "getURIIcon", method = RequestMethod.GET)
	public @ResponseBody String getURIIcon(Integer siteId) {
		ConfigLab005 config = lab005DAO.getConfigBySite(siteId);
		if (config != null) {
			return config.getUriIcon();
		} else {
			return "";
		}
	}

	@RequestMapping(value = "getReportLink", method = RequestMethod.GET)
	public @ResponseBody String getReportLink(@RequestParam String siteId) {
		Integer siteNumber = WSCommon.getNumber(siteId);
		if (siteNumber == null) {
			return "siteId required";
		}
		ConfigLab005 configLab = lab005DAO.findBySite(siteNumber);
		if (configLab == null || configLab.getId() == null) {
			return "Site Id not exist";
		}
		if (configLab.getReportName() == null || configLab.getReportName().trim().equals("")) {
			return "Site Id - Report File not exist";
		}
		String reportLink = PropertiesReader.getValue(ConfigEnum.REPORT_LAB005_LINK) + FrontalKey.WINDOWS + siteId
				+ FrontalKey.WINDOWS + configLab.getReportName();
		return reportLink;
	}
}
