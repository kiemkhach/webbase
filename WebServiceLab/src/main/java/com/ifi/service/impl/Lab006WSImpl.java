package com.ifi.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ifi.common.bean.Lab006Bean;
import com.ifi.common.bean.Lab006Client;
import com.ifi.common.util.ConfigEnum;
import com.ifi.common.util.FrontalKey;
import com.ifi.common.util.LabUtils;
import com.ifi.common.util.PropertiesReader;
import com.ifi.common.ws.GetDataAction;
import com.ifi.lab.LabDAO.dao.ConfigLab006ClientDAO;
import com.ifi.lab.LabDAO.dao.ConfigLab006Dao;
import com.ifi.lab.LabDAO.dao.ConstantDAO;
import com.ifi.lab.LabDAO.dao.LabDAO;
import com.ifi.lab.LabDAO.model.ConfigLab006;
import com.ifi.lab.LabDAO.model.ConfigLab006Client;
import com.ifi.lab.LabDAO.model.Constant;
import com.ifi.rest.WSCommon;
import com.ifi.service.Lab006WS;
import com.reports.model.Lab006ClientInfo;
import com.reports.model.Lab006GlobalInfo;
import com.reports.model.LabChart;
import com.reports.util.Utils;

public class Lab006WSImpl implements Lab006WS {
	private static final int TYPE_HCE = 1;
	private static final int TYPE_HPE = 2;
	private static final int TYPE_HCH = 3;
	private static final int TYPE_HPH = 4;

	private static final String SEPERATE_DATE = "/";
	@Autowired
	private ConfigLab006Dao lab006DAO;

	@Autowired
	private ConstantDAO constantDAO;

	@Autowired
	private LabDAO labDAO;

	@Autowired
	private ConfigLab006ClientDAO lab006ClientDAO;

	private static final Logger log = Logger.getLogger(Lab006WS.class);

	private static final SimpleDateFormat sdf = new SimpleDateFormat(FrontalKey.DATE_FORMAT_DAY_1);

	/**
	 * Service for rest
	 * 
	 * @param siteId
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	public Lab006Bean getLabBySite(Integer siteId, String fromDate, String toDate) {
		Calendar cal = Calendar.getInstance();
		if (toDate == null || toDate.isEmpty()) {
			toDate = sdf.format(cal.getTime());
		}
		if (fromDate == null || fromDate.isEmpty()) {
			int labId = labDAO.findByName(PropertiesReader.getValue(ConfigEnum.LAB_NAME_006)).getId();
			int numberMonth = 3;
			Constant constant = constantDAO.getByLabIdnKey(labId, FrontalKey.LAB006_NUMBER_MONTH);
			if (constant != null) {
				numberMonth = Integer.parseInt(constant.getValue());
			}
			cal.add(Calendar.MONTH, -numberMonth);
			fromDate = sdf.format(cal.getTime());
		}
		ConfigLab006 config = lab006DAO.findBySite(siteId);
		if (config != null) {
			// get list client by subscription id(lab_demo)
			List<ConfigLab006Client> lab006Clients = lab006ClientDAO.getBySubscription(config.getId());
			Lab006Bean labBean = new Lab006Bean();
			List<Lab006Client> listClients = new ArrayList<Lab006Client>();
			labBean.setCommonPortionReal(getConsumptionByModule(config.getCommonPortionModuleId(), fromDate, toDate));
			labBean.setTotalEnergyTheory(
					getConsumptionByModule(config.getEnergyConsumptionModuleId(), fromDate, toDate));
			// labBean.setCommonPortionReal(10000);
			// labBean.setTotalEnergyTheory(60000);

			listClients = convertSetClientToList(config, lab006Clients, fromDate, toDate);
			Integer totalClientConsumption = calculateTotalClientConsumption(listClients);
			// calculate common portion theory
			labBean.setCommonPortionTheory(labBean.getTotalEnergyTheory() - totalClientConsumption);
			// calculate total real energy
			double totalEnergyReal = totalClientConsumption + labBean.getCommonPortionReal();
			labBean.setTotalEnergyReal(totalEnergyReal);
			// calculate percent
			calculatePercent(listClients, totalEnergyReal);
			// Double commonPortionPercent =
			// ((labBean.getCommonPortionReal().doubleValue() /
			// totalEnergyReal)*100);
			if (totalEnergyReal > 0) {
				labBean.setCommonPortionPercent(calculateCommonPortionPercent(listClients));
			} else {
				labBean.setCommonPortionPercent(0d);
			}
			// get report link for clients
			for (Lab006Client lab006Client : listClients) {
				String reportClientLink = getClientResourcesLink(lab006Client.getId().toString(),
						FrontalKey.TYPE_UPLOAD_REPORT);
				String imgClientLink = getClientResourcesLink(lab006Client.getId().toString(),
						FrontalKey.TYPE_UPLOAD_IMAGE);
				lab006Client.setReportClientLink(reportClientLink);
				lab006Client.setImgClientLink(imgClientLink);
			}
			// set list clients
			labBean.setClients(listClients);

			labBean.setSiteId(siteId.toString());
			labBean.setUriIcon(config.getUriIcon());
			labBean.setLogo(config.getLogo());
			labBean.setFromDate(fromDate);
			labBean.setToDate(toDate);
			labBean.setSiteName(config.getSiteName());
			labBean.setId(config.getId());
			return labBean;
		}
		return null;
	}

	public Lab006GlobalInfo getReportGlobal(Integer siteId, String fromDate, String toDate) {
		Lab006GlobalInfo bean = new Lab006GlobalInfo();
		ConfigLab006 config = lab006DAO.findBySite(siteId);
		String summerStarDate = fromDate;
		if (config.getSummerStartDate() != null) {
			summerStarDate = convertDateConfig(config.getSummerStartDate());
		} else {
			log.error("Not Config SummerStartDate");
		}
		String winterStartDate = toDate;
		if (config.getWinterStartDate() != null) {
			winterStartDate = convertDateConfig(config.getWinterStartDate());
		} else {
			log.error("Not Config WinterStartDate");
		}
		bean.setSummerTime(getTimeSeason(summerStarDate, winterStartDate));
		bean.setWinterTime(getTimeSeason(winterStartDate, summerStarDate));
		bean.setFromDate(convertDateToFrance(fromDate));
		bean.setToDate(convertDateToFrance(toDate));
		bean.setTimeHC(getHourRange(config.getStartHourHC(), config.getStartHourHP()));
		bean.setTimeHP(getHourRange(config.getStartHourHP(), config.getStartHourHC()));
		Double totalPartie = getConsumptionByModule(config.getEnergyConsumptionModuleId(), fromDate, toDate);
		if (totalPartie != null) {
			bean.setTotalParties(totalPartie.intValue());
		} else {
			bean.setTotalParties(0);
		}

		return bean;
	}

	public List<Lab006Client> getAllReportClient(Integer siteId, String fromDate, String toDate) {
		ConfigLab006 config = lab006DAO.findBySite(siteId);
		List<ConfigLab006Client> lab006Clients = lab006ClientDAO.getBySubscription(config.getId());
		return convertSetClientToList(config, lab006Clients, fromDate, toDate);
	}

	public Lab006ClientInfo getReportClient(Integer clientId, String fromDate, String toDate) {
		int hcStartHour = 0;
		Lab006ClientInfo client = new Lab006ClientInfo();
		ConfigLab006Client configLab006Client = lab006ClientDAO.getClientById(clientId);
		ConfigLab006 config = lab006DAO.findById(configLab006Client.getSubscriptionId());
		if (config.getStartHourHC() != null) {
			hcStartHour = config.getStartHourHC();
		} else {
			log.error("Not Config StartHourHC");
		}
		int hpStartHour = 0;
		if (config.getStartHourHP() != null) {
			hpStartHour = config.getStartHourHP();
		} else {
			log.error("Not Config StartHourHP");
		}
		String summerStarDate = fromDate;
		if (config.getSummerStartDate() != null) {
			summerStarDate = convertDateConfig(config.getSummerStartDate());
		} else {
			log.error("Not Config SummerStartDate");
		}
		String winterStartDate = toDate;
		if (config.getWinterStartDate() != null) {
			winterStartDate = convertDateConfig(config.getWinterStartDate());
		} else {
			log.error("Not Config WinterStartDate");
		}

		client.setSummerTime(getTimeSeason(summerStarDate, winterStartDate));
		client.setWinterTime(getTimeSeason(winterStartDate, summerStarDate));
		client.setFromDate(convertDateToFrance(fromDate));
		client.setToDate(convertDateToFrance(toDate));
		if (config.getStartHourHC() != null && config.getStartHourHP() != null) {
			client.setTimeHC(getHourRange(config.getStartHourHC(), config.getStartHourHP()));
			client.setTimeHP(getHourRange(config.getStartHourHP(), config.getStartHourHC()));
		}
		client.setClientName(configLab006Client.getClientName());
		Double numberHCE = 0d;
		if (configLab006Client.getModuleIdHCE() != null && !"".equals(configLab006Client.getModuleIdHCE())) {
			numberHCE = getConsumptionByType(configLab006Client.getModuleIdHCE(), fromDate, toDate, hcStartHour,
					hpStartHour, summerStarDate, winterStartDate, TYPE_HCE);
		}
		Double numberHPE = 0d;
		if (configLab006Client.getModuleIdHPE() != null && !"".equals(configLab006Client.getModuleIdHPE())) {
			numberHPE = getConsumptionByType(configLab006Client.getModuleIdHPE(), fromDate, toDate, hcStartHour,
					hpStartHour, summerStarDate, winterStartDate, TYPE_HPE);
		}
		Double numberHCH = 0d;
		if (configLab006Client.getModuleIdHCH() != null && !"".equals(configLab006Client.getModuleIdHCH())) {
			numberHCH = getConsumptionByType(configLab006Client.getModuleIdHCH(), fromDate, toDate, hcStartHour,
					hpStartHour, summerStarDate, winterStartDate, TYPE_HCH);
		}
		Double numberHPH = 0d;
		if (configLab006Client.getModuleIdHPH() != null && !"".equals(configLab006Client.getModuleIdHPH())) {
			numberHPH = getConsumptionByType(configLab006Client.getModuleIdHPH(), fromDate, toDate, hcStartHour,
					hpStartHour, summerStarDate, winterStartDate, TYPE_HPH);
		}
		if (numberHCE != null) {
			client.setNumberHCE(numberHCE.intValue());
		} else {
			client.setNumberHCE(0);
		}
		if (numberHPE != null) {
			client.setNumberHPE(numberHPE.intValue());
		} else {
			client.setNumberHPE(0);
		}
		if (numberHCH != null) {
			client.setNumberHCH(numberHCH.intValue());
		} else {
			client.setNumberHCH(0);
		}
		if (numberHPH != null) {
			client.setNumberHPH(numberHPH.intValue());
		} else {
			client.setNumberHPH(0);
		}
		client.setTotal(client.getNumberHCE() + client.getNumberHCH() + client.getNumberHPE() + client.getNumberHPH());

		Double totalPartie = getConsumptionByModule(config.getEnergyConsumptionModuleId(), fromDate, toDate);
		if (totalPartie != null) {
			client.setTotalParties(totalPartie.intValue());
		} else {
			client.setTotalParties(0);
		}
		return client;
	}

	public List<LabChart> getGlobalChart(Integer siteId, String fromDateP, String toDateP) {
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(sdf.parse(toDateP));
		} catch (ParseException e) {
			log.error("Error Format date:" + toDateP);
			e.printStackTrace();
		}
		// Integer fromYear;
		// Integer toYear;
		// Integer fromMonth;
		// Integer toMonth;
		// try {
		// fromYear = Integer.parseInt(fromDateP.substring(0, 4));
		// toYear = Integer.parseInt(toDateP.substring(0, 4));
		// fromMonth = Integer.parseInt(fromDateP.substring(4, 6));
		// toMonth = Integer.parseInt(toDateP.substring(4, 6));
		// } catch (Exception e) {
		// log.error("Error", e);
		// return null;
		// }
		ConfigLab006 config = lab006DAO.findBySite(siteId);
		List<ConfigLab006Client> lab006Clients = lab006ClientDAO.getBySubscription(config.getId());

		List<LabChart> lst = new ArrayList<LabChart>();
		int total = 0;
		for (int i = 1; i < 14; i++) {
			if (i >= 2) {
				cal.add(Calendar.MONTH, -1);
			}
			String toDate = sdf.format(cal.getTime());
			cal.set(Calendar.DAY_OF_MONTH, 1);
			String fromDate = sdf.format(cal.getTime());
			List<Lab006Client> listClients = convertSetClientToList(config, lab006Clients, fromDate, toDate);
			for (Lab006Client lab006Client : listClients) {
				LabChart chartItem = new LabChart();
				chartItem.setGroupName(lab006Client.getClientName());
				Date dateTmp = cal.getTime();
				chartItem.setxCategory(dateTmp);
				String dateStr = sdf.format(dateTmp);
				if (lab006Client.getGlobalConsumptionClient() != null) {
					chartItem.setValue(lab006Client.getGlobalConsumptionClient().intValue());
					if (dateStr.compareTo(fromDateP) >= 0 && dateStr.compareTo(toDateP) <= 0) {
						total += chartItem.getValue();
					}
				} else {
					chartItem.setValue(0);
				}
				lst.add(chartItem);
			}

			// Community
			Double totalPartie = getConsumptionByModule(config.getEnergyConsumptionModuleId(), fromDate, toDate);
			LabChart chartItem = new LabChart();
			chartItem.setGroupName("Parties Communes");
			Date dateTmp = cal.getTime();
			chartItem.setxCategory(dateTmp);
			String dateStr = sdf.format(dateTmp);
			if (totalPartie != null) {
				chartItem.setValue(totalPartie.intValue());
				if (dateStr.compareTo(fromDateP) >= 0 && dateStr.compareTo(toDateP) <= 0) {
					total += chartItem.getValue();
				}
			} else {
				chartItem.setValue(0);
			}
			lst.add(chartItem);
		}
		if (lst.size() > 0) {
			lst.get(0).setTotal(total);
		}
		return lst;
	}

	public List<LabChart> getClientChart(Integer clientId, String toDateP) {
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(sdf.parse(toDateP));
		} catch (ParseException e) {
			log.error("Not a date:" + toDateP);
			e.printStackTrace();
		}
		ConfigLab006Client lab006Client = lab006ClientDAO.getClientById(clientId);
		List<ConfigLab006Client> lstClient = new ArrayList<ConfigLab006Client>();
		lstClient.add(lab006Client);
		ConfigLab006 config = lab006DAO.findById(lab006Client.getSubscriptionId());

		List<LabChart> lst = new ArrayList<LabChart>();
		for (int i = 1; i < 14; i++) {
			if (i >= 2) {
				cal.add(Calendar.MONTH, -1);
			}
			String toDate = sdf.format(cal.getTime());
			cal.set(Calendar.DAY_OF_MONTH, 1);
			String fromDate = sdf.format(cal.getTime());
			List<Lab006Client> listClients = convertSetClientToList(config, lstClient, fromDate, toDate);
			Lab006Client lab = listClients.get(0);

			LabChart chartItem = new LabChart();
			chartItem.setGroupName("HPE");
			chartItem.setxCategory(cal.getTime());
			chartItem.setValue(0);
			if (lab.getNumberHPE() != null) {
				chartItem.setValue(lab.getNumberHPE().intValue());
			}
			lst.add(chartItem);

			chartItem = new LabChart();
			chartItem.setGroupName("HCE");
			chartItem.setxCategory(cal.getTime());
			chartItem.setValue(0);
			if (lab.getNumberHCE() != null) {
				chartItem.setValue(lab.getNumberHCE().intValue());
			}
			lst.add(chartItem);

			chartItem = new LabChart();
			chartItem.setGroupName("HPH");
			chartItem.setxCategory(cal.getTime());
			chartItem.setValue(0);
			if (lab.getNumberHPH() != null) {
				chartItem.setValue(lab.getNumberHPH().intValue());
			}
			lst.add(chartItem);

			chartItem = new LabChart();
			chartItem.setGroupName("HCH");
			chartItem.setxCategory(cal.getTime());
			chartItem.setValue(0);
			if (lab.getNumberHCH() != null) {
				chartItem.setValue(lab.getNumberHCH().intValue());
			}
			lst.add(chartItem);
		}
		return lst;
	}

	private static final Map<String, String> monthShortMap = new HashMap<String, String>();

	static {
		monthShortMap.put("01", "Janv");
		monthShortMap.put("02", "Févr");
		monthShortMap.put("03", "Mars");
		monthShortMap.put("04", "Avril");
		monthShortMap.put("05", "Mai");
		monthShortMap.put("06", "Juin");
		monthShortMap.put("07", "Juil");
		monthShortMap.put("08", "Août");
		monthShortMap.put("09", "Sept");
		monthShortMap.put("10", "Oct");
		monthShortMap.put("11", "Nov");
		monthShortMap.put("12", "Déc");
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	private String getTimeSeason(String fromDate, String toDate) {
		StringBuilder sbDate = new StringBuilder();

		// FROM DATE
		String month = fromDate.substring(0, 2);
		String day = fromDate.substring(2, 4);
		sbDate.append(day);
		sbDate.append(" ");
		if (monthShortMap.containsKey(month)) {
			sbDate.append(monthShortMap.get(month));
		} else {
			sbDate.append(month);
		}

		sbDate.append(" - ");
		// TO DATE
		String toDateTmp = LabUtils.getBeforeOneDay(toDate);
		sbDate.append(toDateTmp.substring(2, 4));
		sbDate.append(" ");
		String monthStr = toDateTmp.substring(0, 2);
		if (monthShortMap.containsKey(monthStr)) {
			sbDate.append(monthShortMap.get(monthStr));
		} else {
			sbDate.append(monthStr);
		}
		return sbDate.toString();
	}

	private static final Map<String, String> monthMap = new HashMap<String, String>();

	static {
		monthMap.put("01", "Janvier");
		monthMap.put("02", "Février");
		monthMap.put("03", "Mars");
		monthMap.put("04", "Avril");
		monthMap.put("05", "Mai");
		monthMap.put("06", "Juin");
		monthMap.put("07", "Juillet");
		monthMap.put("08", "Août");
		monthMap.put("09", "Septembre");
		monthMap.put("10", "Octobre");
		monthMap.put("11", "Novembre");
		monthMap.put("12", "Décembre");
	}

	/**
	 * 
	 * @param date
	 *            YYYYMMDD
	 * @return
	 */
	private String convertDateToFrance(String date) {
		if (date == null || date.length() != 8) {
			return null;
		}
		String year = date.substring(0, 4);
		String month = date.substring(4, 6);
		String day = date.substring(6, 8);
		StringBuilder sbDate = new StringBuilder();
		if (day.startsWith("0") & day.length() == 2) {
			day = day.substring(1, 2);
		}
		sbDate.append(day);
		sbDate.append(" ");
		if (monthMap.containsKey(month)) {
			sbDate.append(monthMap.get(month));
		} else {
			sbDate.append(month);
		}
		sbDate.append(" ");
		sbDate.append(year);
		return sbDate.toString();
	}

	/**
	 * get time fromHour:00 -> toHour:00
	 * 
	 * @param fromHour
	 * @param toHour
	 * @return
	 */
	private String getHourRange(int fromHour, int toHour) {
		StringBuilder hourSb = new StringBuilder();
		if (fromHour < 10) {
			hourSb.append("0");
		}
		hourSb.append(fromHour);
		hourSb.append(":00");
		hourSb.append(" - ");
		if (toHour < 10) {
			hourSb.append("0");
		}
		hourSb.append(toHour);
		hourSb.append(":00");
		return hourSb.toString();
	}

	private List<Lab006Client> convertSetClientToList(ConfigLab006 config, List<ConfigLab006Client> clients,
			String fromDate, String toDate) {
		List<Lab006Client> list = null;
		// totalClientConsumption = 0;
		if (clients != null && !clients.isEmpty()) {
			int hcStartHour = 0;
			if (config.getStartHourHC() != null) {
				hcStartHour = config.getStartHourHC();
			} else {
				log.error("Not Config StartHourHC");
			}
			int hpStartHour = 0;
			if (config.getStartHourHP() != null) {
				hpStartHour = config.getStartHourHP();
			} else {
				log.error("Not Config StartHourHP");
			}
			String summerStarDate = fromDate;
			if (config.getSummerStartDate() != null) {
				summerStarDate = convertDateConfig(config.getSummerStartDate());
			} else {
				log.error("Not Config SummerStartDate");
			}
			String winterStartDate = toDate;
			if (config.getWinterStartDate() != null) {
				winterStartDate = convertDateConfig(config.getWinterStartDate());
			} else {
				log.error("Not Config WinterStartDate");
			}
			list = new ArrayList<Lab006Client>();
			for (ConfigLab006Client configLab006Client : clients) {
				Lab006Client client = new Lab006Client();
				client.setId(configLab006Client.getId());
				client.setClientName(configLab006Client.getClientName());
				client.setSubscriptionId(configLab006Client.getSubscriptionId());
				client.setReportClientLink(configLab006Client.getReportClientName());
				client.setImgClientLink(configLab006Client.getImageClientName());
				Double numberHCE = 0d;
				if (configLab006Client.getModuleIdHCE() != null && !"".equals(configLab006Client.getModuleIdHCE())) {
					numberHCE = getConsumptionByType(configLab006Client.getModuleIdHCE(), fromDate, toDate, hcStartHour,
							hpStartHour, summerStarDate, winterStartDate, TYPE_HCE);
				}
				Double numberHPE = 0d;
				if (configLab006Client.getModuleIdHPE() != null && !"".equals(configLab006Client.getModuleIdHPE())) {
					numberHPE = getConsumptionByType(configLab006Client.getModuleIdHPE(), fromDate, toDate, hcStartHour,
							hpStartHour, summerStarDate, winterStartDate, TYPE_HPE);
				}
				Double numberHCH = 0d;
				if (configLab006Client.getModuleIdHCH() != null && !"".equals(configLab006Client.getModuleIdHCH())) {
					numberHCH = getConsumptionByType(configLab006Client.getModuleIdHCH(), fromDate, toDate, hcStartHour,
							hpStartHour, summerStarDate, winterStartDate, TYPE_HCH);
				}
				Double numberHPH = 0d;
				if (configLab006Client.getModuleIdHPH() != null && !"".equals(configLab006Client.getModuleIdHPH())) {
					numberHPH = getConsumptionByType(configLab006Client.getModuleIdHPH(), fromDate, toDate, hcStartHour,
							hpStartHour, summerStarDate, winterStartDate, TYPE_HPH);
				}
				client.setNumberHCE(numberHCE);
				client.setNumberHPE(numberHPE);
				client.setNumberHCH(numberHCH);
				client.setNumberHPH(numberHPH);
				client.setGlobalConsumptionClient(numberHCE + numberHCH + numberHPE + numberHPH);
				list.add(client);
			}
		}

		return list;
	}

	/**
	 * Convert date config in DB From dd/MM/yyyy To YYYYMMdd
	 * 
	 * @param startDateConfig
	 * @return
	 */
	private String convertDateConfig(String startDateConfig) {
		if (startDateConfig == null) {
			return null;
		}
		String[] dateArr = startDateConfig.split(SEPERATE_DATE);
		if (dateArr.length < 2) {
			return null;
		} else {
			log.error("Format Date Wrong:" + startDateConfig);
		}
		return dateArr[1] + dateArr[0];
	}

	private int calculateTotalClientConsumption(List<Lab006Client> listClients) {
		int total = 0;
		if (listClients != null && !listClients.isEmpty()) {
			for (Lab006Client lab006Client : listClients) {
				total += lab006Client.getGlobalConsumptionClient();
			}
		}
		return total;
	}

	private Double calculateCommonPortionPercent(List<Lab006Client> listClients) {
		Double percent = 0d;
		Double rest = 0d;
		if (listClients != null && !listClients.isEmpty()) {
			for (Lab006Client lab006Client : listClients) {
				rest += lab006Client.getPercentClient();
			}
			percent = 100 - rest;
		}
		return percent;
	}

	private void calculatePercent(List<Lab006Client> listClients, double totalEnergyReal) {
		if (listClients != null && !listClients.isEmpty()) {
			if (totalEnergyReal > 0) {
				for (Lab006Client lab006Client : listClients) {
					Double percent = (Double) (lab006Client.getGlobalConsumptionClient() / totalEnergyReal * 100);
					lab006Client.setPercentClient(percent);
				}
			} else {
				for (Lab006Client lab006Client : listClients) {
					lab006Client.setPercentClient(0d);
				}
			}
		}
	}

	/**
	 * moduleArr module id sepecrate by comma
	 * 
	 * @param moduleStr
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	private Double getConsumptionByModule(String moduleStr, String fromDate, String toDate) {
		GetDataAction csm = new GetDataAction();
		if (moduleStr == null || moduleStr.isEmpty()) {
			return null;
		}
		String[] moduleArr = moduleStr.split(FrontalKey.PLUS_SPECIAL);
		Double rs = 0d;
		List<Integer> moduleLst = new ArrayList<Integer>();
		for (String module : moduleArr) {
			moduleLst.add(Integer.parseInt(Utils.getModuleNumberFromArray(module)));
		}
		Map<Integer, Integer> map = csm.getMapConsommationByTime(moduleLst, fromDate, toDate);
		for (String module : moduleArr) {
			Integer moduleId = Integer.parseInt(Utils.getModuleNumberFromArray(module));
			Integer consommation = map.get(moduleId);
			if (consommation != null) {
				rs += Utils.convertToKWh(module, consommation);
			}
		}
		return rs;
	}

	/**
	 * Get Consumption By Type Type 1:HCE;2:HPE;3:HCH;4:HPH
	 * 
	 * @param moduleStr
	 * @param fromDate
	 * @param toDate
	 * @param startHour
	 * @param endHour
	 * @param type
	 * @param summerStarDate
	 *            MMdd
	 * @param winterStartDate
	 *            MMdd
	 * @return
	 */
	private Double getConsumptionByType(String moduleStr, String fromDate, String toDate, Integer hcStartHour,
			Integer hpStarHour, String summerStarDate, String winterStartDate, int type) {

		Double result = 0d;
		if (moduleStr == null || moduleStr.isEmpty()) {
			return result;
		}
		String[] moduleArr = moduleStr.split(FrontalKey.PLUS_SPECIAL);
		GetDataAction csm = new GetDataAction();

		List<Integer> moduleLst = new ArrayList<Integer>();
		for (String moduleID : moduleArr) {
			moduleLst.add(Integer.parseInt(Utils.getModuleNumberFromArray(moduleID)));
		}

		Integer startHour = 0;
		Integer endHour = 0;

		String startRangeDate = "";
		String endRangeDate = "";

		if (type == TYPE_HCE || type == TYPE_HPE) {
			if (type == TYPE_HCE) {
				startHour = hcStartHour +1 ;
				endHour = hpStarHour;
			} else {
				startHour = hpStarHour +1;
				endHour = hcStartHour;
			}
			startRangeDate = summerStarDate;
			endRangeDate = LabUtils.getBeforeOneDay(winterStartDate);
		} else if (type == TYPE_HCH || type == TYPE_HPH) {
			if (type == TYPE_HCH) {
				startHour = hcStartHour +1;
				endHour = hpStarHour;
			} else {
				startHour = hpStarHour +1;
				endHour = hcStartHour;
			}
			startRangeDate = winterStartDate;
			endRangeDate = LabUtils.getBeforeOneDay(summerStarDate);
		}

		Map<Integer, Integer> mapConsommation = new HashMap<Integer, Integer>();
		List<String[]> rangeLst = getDateRange(fromDate, toDate, startRangeDate, endRangeDate);
		for (String[] rangeArr : rangeLst) {
			//TODO: bo startHour, endHour
			Map<Integer, Integer> mapTmp = csm.getMapConsommationByTime(moduleLst, rangeArr[0], rangeArr[1]);
			for (Map.Entry<Integer, Integer> entry : mapTmp.entrySet()) {
				Integer key = entry.getKey();
				Integer value = entry.getValue();
				if (mapConsommation.containsKey(key)) {
					mapConsommation.put(key, mapConsommation.get(key) + value);
				} else {
					mapConsommation.put(key, value);
				}
			}
		}

		for (String moduleID : moduleArr) {
			Integer moduleId = Integer.parseInt(Utils.getModuleNumberFromArray(moduleID));
			Integer con = mapConsommation.get(moduleId);
			if (con != null) {
				result += Utils.convertToKWh(moduleID, con);
			}
		}

		return result;
	}

	/**
	 * Get date in multi range
	 * 
	 * @param fromDate
	 * @param toDate
	 * @param startRangeDate
	 * @param endRangeDate
	 * @return
	 */
	private List<String[]> getDateRange(String fromDate, String toDate, String startRangeDate, String endRangeDate) {
		Integer fromYear = Integer.parseInt(fromDate.substring(0, 4));
		Integer toYear = Integer.parseInt(toDate.substring(0, 4));
		List<String[]> rangeLst = new ArrayList<String[]>();
		for (int i = fromYear; i <= toYear; i++) {
			String[] arr = null;
			if (startRangeDate.compareTo(endRangeDate) <= 0) {
				arr = new String[2];
				arr[0] = i + startRangeDate;
				arr[1] = i + endRangeDate;
				rangeLst.add(arr);
			} else {
				if (i == fromYear) {
					arr = new String[2];
					arr[0] = i + "0101";
					arr[1] = i + endRangeDate;
					rangeLst.add(arr);
				}
				arr = new String[2];
				arr[0] = i + startRangeDate;
				arr[1] = (i + 1) + endRangeDate;
				rangeLst.add(arr);
			}
		}

		List<String[]> datelst = new ArrayList<String[]>();
		for (String[] rangeArr : rangeLst) {
			boolean ok = true;
			String fromDateTmp = null;
			String toDateTmp = null;
			if (fromDate.compareTo(rangeArr[0]) <= 0) {
				fromDateTmp = rangeArr[0];
			} else if (fromDate.compareTo(rangeArr[1]) <= 0) {
				fromDateTmp = fromDate;
			} else {
				ok = false;
			}

			if (toDate.compareTo(rangeArr[1]) >= 0) {
				toDateTmp = rangeArr[1];
			} else if (toDate.compareTo(rangeArr[0]) >= 0) {
				toDateTmp = toDate;
			} else {
				ok = false;
			}
			if (ok) {
				String[] arr = new String[] { fromDateTmp, toDateTmp };
				datelst.add(arr);
			}
		}
		return datelst;
	}

	// /**
	// * Get consumption by time
	// *
	// * @param moduleID
	// * @param fromDate
	// * @param toDate
	// * @param startHour
	// * @param endHour
	// * @return
	// */
	// private Integer getConsumptionByTime(Integer moduleID, String fromDate,
	// String toDate, String startHour,
	// String endHour) {
	// GetCSMDataAction csm = new GetCSMDataAction();
	//
	// List<AgregatCSM> agregatLst = csm.getListAgregatByModule(moduleID,
	// fromDate, toDate, startHour, endHour);
	// Integer result = 0;
	// if (agregatLst.size() > 1) {
	// result = Math.abs(agregatLst.get(0).getIndex() -
	// agregatLst.get(agregatLst.size() - 1).getIndex());
	// }
	// return result;
	// }

	private String getClientResourcesLink(String id, int type) {
		Integer siteNumber = WSCommon.getNumber(id);
		if (siteNumber == null) {
			return "siteId required";
		}
		ConfigLab006Client configLabClient = lab006ClientDAO.getClientById(siteNumber);
		if (configLabClient == null || configLabClient.getId() == null) {
			return "Site Id not exist";
		}
		String result = null;
		if (type == FrontalKey.TYPE_UPLOAD_IMAGE) {
			if (configLabClient.getImageClientName() == null
					|| configLabClient.getImageClientName().trim().equals("")) {
				return result;
			}
			result = PropertiesReader.getValue(ConfigEnum.IMAGE_LAB006_CLIENT_LINK) + FrontalKey.WINDOWS + id
					+ FrontalKey.WINDOWS + configLabClient.getImageClientName();
		}
		if (type == FrontalKey.TYPE_UPLOAD_REPORT) {
			if (configLabClient.getReportClientName() == null
					|| configLabClient.getReportClientName().trim().equals("")) {
				return "Site Id - Report File not exist";
			}
			result = PropertiesReader.getValue(ConfigEnum.REPORT_LAB006_CLIENT_LINK) + FrontalKey.WINDOWS + id
					+ FrontalKey.WINDOWS + configLabClient.getReportClientName();
		}
		return result;
	}
}
