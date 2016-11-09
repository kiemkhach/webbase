package com.ifi.rest;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ifi.common.util.ConfigEnum;
import com.ifi.common.util.FrontalKey;
import com.ifi.common.util.PropertiesReader;
import com.ifi.common.ws.GetDataAction;
import com.ifi.lab.LabDAO.dao.BouyguesDAO;
import com.ifi.lab.LabDAO.model.Bouygues;
import com.reports.util.Utils;

@Controller
@RequestMapping("bouygues")
public class BouyguesRest {
	@Autowired
	private BouyguesDAO bouyguesDAO;

	@RequestMapping(value = "getAllConfig", method = RequestMethod.GET)
	public @ResponseBody
	String getAllConfig() {
		List<Bouygues> listConfigLab = bouyguesDAO.getAllBouygues();
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(listConfigLab);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Method get data from CSM by site Id
	 * 
	 * @param siteId
	 * @return Object data for bouygues
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "getBouyguesDataBySite", method = RequestMethod.GET)
	public @ResponseBody
	JSONObject getBouyguesDataBySite(@RequestParam int siteId) {

		// ServiceFactory serviceFactory = new ServiceFactory();
		// @SuppressWarnings("static-access")
		// ApplicationContext context = serviceFactory.getAppCtx();
		// BouyguesDAO bouyguesDAO = context.getBean(BouyguesDAO.class);

		Bouygues bouygues = bouyguesDAO.findBySiteId(siteId);
		// get data from csm
		Double gruesData = null;
		Double piedsData = null;
		Double cantonData = null;
		Integer numberGrues = 0;
		if(bouygues.getNumberGrues() != null && bouygues.getNumberGrues() > 0){
			numberGrues = bouygues.getNumberGrues();
		}
		if (bouygues.getGruesModuleId() != null) {
			Double tmp = getConsumptionByTime(bouygues.getGruesModuleId());
			gruesData = tmp;
			if(tmp != null && numberGrues > 0){
				gruesData = tmp/numberGrues;
			}
		}
		Integer numberPied = 0;
		if (bouygues.getNumberPied() != null && bouygues.getNumberPied() > 0) {
			numberPied = bouygues.getNumberPied();
		}
		if (bouygues.getPiedModuleId() != null) {
			Double tmp = getConsumptionByTime(bouygues.getPiedModuleId());
			piedsData = tmp;
			if(tmp != null && numberPied > 0){
				piedsData = tmp / numberPied;
			}
		}
		Integer numberCanton = 0;
		if (bouygues.getNumberCanton() != null && bouygues.getNumberCanton() > 0) {
			numberCanton = bouygues.getNumberCanton();
		}
		if (bouygues.getCantonModuleId() != null) {
			Double tmp = getConsumptionByTime(bouygues.getCantonModuleId()) ;
			cantonData = tmp;
			if(tmp != null && numberCanton > 0){
				cantonData = tmp / numberCanton;
			}
		}
		// create return object
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("gruesChantier", gruesData);
		jsonObject.put("piedsColonnes", piedsData);
		jsonObject.put("cantonement", cantonData);
		//
		jsonObject.put("numberGrues", numberGrues);
		jsonObject.put("numberPied", numberPied);
		jsonObject.put("numberCanton", numberCanton);
		//
		jsonObject.put("startDate", bouygues.getStartDate());
		jsonObject.put("numberPermit", bouygues.getNumberPermit());
		jsonObject.put("siteName", bouygues.getSiteName());
		return jsonObject;
	}

//	/**
//	 * Method configure bouygues
//	 * 
//	 * @param siteId
//	 * @param module1
//	 * @param module2
//	 * @param module3
//	 * @return
//	 */
//	@RequestMapping(value = "configBouygues", method = RequestMethod.GET)
//	public @ResponseBody
//	String addBouyguesConfig(@RequestParam int siteId,
//			@RequestParam(required = false) String siteName,
//			@RequestParam(required = false) Integer module1,
//			@RequestParam(required = false) Integer module2,
//			@RequestParam(required = false) Integer module3,
//			@RequestParam(required = false) String uriIcon,
//			@RequestParam(required = false) String logo,
//			@RequestParam(required = false) String startDate,
//			@RequestParam(required = false) String numberPermit) {
//
//		// ServiceFactory serviceFactory = new ServiceFactory();
//		// @SuppressWarnings("static-access")
//		// ApplicationContext context = serviceFactory.getAppCtx();
//		// BouyguesDAO bouyguesDAO = context.getBean(BouyguesDAO.class);
//
//		Bouygues bouygues = bouyguesDAO.findBySiteId(siteId);
//		if (bouygues == null) {
//			bouygues = new Bouygues();
//		}
//		bouygues.setSiteId(siteId);
//		if (siteName != null) {
//			bouygues.setSiteName(siteName);
//		}
//		if (startDate != null) {
//			bouygues.setStartDate(startDate);
//		}
//		if (numberPermit != null) {
//			bouygues.setNumberPermit(numberPermit);
//		}
//		bouygues.setGruesModuleId(module1);
//		bouygues.setPiedModuleId(module2);
//		bouygues.setCantonModuleId(module3);
//		if (uriIcon != null && !uriIcon.isEmpty()) {
//			bouygues.setUriIcon(uriIcon);
//		}
//		if (logo != null && !logo.isEmpty()) {
//			bouygues.setLogo(logo);
//		}
//		if (bouyguesDAO.saveOrUpdate(bouygues)) {
//			return "success";
//		}
//		return "failed";
//	}

//	/**
//	 * Config lab 002 with number module
//	 */
//	@RequestMapping(value = "configLab002", method = RequestMethod.GET)
//	public @ResponseBody
//	String configLab002(@RequestParam int siteId,
//			@RequestParam(required = false) String siteName,
//			@RequestParam(required = false) String module1,
//			@RequestParam(required = false) String module2,
//			@RequestParam(required = false) String module3,
//			@RequestParam(required = false) String uriIcon,
//			@RequestParam(required = false) String logo,
//			@RequestParam(required = false) String startDate,
//			@RequestParam(required = false) String numberPermit) {
//
//		Bouygues bouygues = bouyguesDAO.findBySiteId(siteId);
//		if (bouygues == null) {
//			bouygues = new Bouygues();
//		}
//		bouygues.setSiteId(siteId);
//		if (siteName != null) {
//			bouygues.setSiteName(siteName);
//		}
//		if (startDate != null) {
//			bouygues.setStartDate(startDate);
//		}
//		if (numberPermit != null) {
//			bouygues.setNumberPermit(numberPermit);
//		}
//		GetCSMDataAction csm = new GetCSMDataAction();
//		if (module1 != null) {
//			if (!module1.trim().isEmpty()) {
//				Integer number = csm.getModuleCSMIDByNumber(module1);
//				if (number != null) {
//					bouygues.setGruesModuleId(number);
//				} else {
//					return "module1 not exists module: " + module1;
//				}
//			} else {
//				bouygues.setGruesModuleId(null);
//			}
//		}
//
//		if (module2 != null) {
//			if (!module2.trim().isEmpty()) {
//				Integer number = csm.getModuleCSMIDByNumber(module2);
//				if (number != null) {
//					bouygues.setPiedModuleId(number);
//				} else {
//					return "module2 not exists module: " + module2;
//				}
//			} else {
//				bouygues.setPiedModuleId(null);
//			}
//		}
//
//		if (module3 != null) {
//			if (!module3.trim().isEmpty()) {
//				Integer number = csm.getModuleCSMIDByNumber(module3);
//				if (number != null) {
//					bouygues.setCantonModuleId(number);
//				} else {
//					return "module3 not exists module: " + module3;
//				}
//			} else {
//				bouygues.setCantonModuleId(null);
//			}
//		}
//		if (uriIcon != null && !uriIcon.isEmpty()) {
//			bouygues.setUriIcon(uriIcon);
//		}
//		if (logo != null && !logo.isEmpty()) {
//			bouygues.setLogo(logo);
//		}
//		if (bouyguesDAO.saveOrUpdate(bouygues)) {
//			return "success";
//		}
//		return "failed";
//	}

	/**
	 * Get number for consumption by time
	 * 
	 * @param 225
	 * @return
	 */
	private Double getConsumptionByTime(String moduleStr) {
		if(moduleStr == null || moduleStr.isEmpty()){
			return null;
		}
		String[] moduleArr = moduleStr.split(FrontalKey.PLUS_SPECIAL);

		//
		SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");
		Calendar cal = Calendar.getInstance();

		// SET TIME
		if (!"YES"
				.equals(PropertiesReader.getValue(ConfigEnum.IS_CURRENT_TIME))) {
			cal.set(Calendar.YEAR,
					PropertiesReader.getValueInt(ConfigEnum.CURRENT_TIME_YEAR));
			cal.set(Calendar.MONTH,
					PropertiesReader.getValueInt(ConfigEnum.CURRENT_TIME_MONTH) - 1);
			cal.set(Calendar.DAY_OF_MONTH,
					PropertiesReader.getValueInt(ConfigEnum.CURRENT_TIME_DAY));
			cal.set(Calendar.HOUR_OF_DAY, 1);
		}

		// Date time now
//		String endHour = String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
		String endDate = sdf.format(cal.getTime());

		// Date time before 7 days
		cal.add(Calendar.DAY_OF_MONTH, -7);
//		String startHour = String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
		String startDate = sdf.format(cal.getTime());

		Double result = null;
		
		GetDataAction dataAction = new GetDataAction();
		for (String moduleID : moduleArr) {
			Integer consommation = dataAction.getConsommationByTime(
					Integer.parseInt(Utils.getModuleNumberFromArray(moduleID)), startDate, endDate);
			if (consommation != null) {
				if (result == null) {
					result = 0D;
				}
				/* result += agr.getConsommation(); */
				result += Utils.convertToKWh(moduleID, consommation);
			}
		}
		return result;
	}

	/**
	 * return link of report in config(pdf file)
	 * 
	 * @return
	 */
	@RequestMapping(value = "getReportLink", method = RequestMethod.GET)
	public @ResponseBody String getReportLink(@RequestParam String siteId) {
		Integer siteNumber = WSCommon.getNumber(siteId);
		if (siteNumber == null) {
			return "siteId required";
			
		}		
		Bouygues configLab = bouyguesDAO.findBySiteId(siteNumber);
		if(configLab == null) {
			return "Site Id not exist";
		}
		if(configLab.getReportName() == null || configLab.getReportName().trim().equals("")) {
			return "Site Id - Report File not exist";
		}
		String reportLink = PropertiesReader.getValue(ConfigEnum.PDF_REPORT_BOUYGUES_LINK) 
				+ FrontalKey.WINDOWS + siteId + FrontalKey.WINDOWS + configLab.getReportName(); 
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

		Bouygues configLab = bouyguesDAO.findBySiteId(siteId);

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
		Bouygues configLab = bouyguesDAO.findBySiteId(siteId);
		;

		if (configLab == null) {
			return "This site is not config";
		}
		return configLab.getLogo();
	}

}
