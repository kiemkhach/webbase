/**
 * 
 */
package com.ifi.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

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
import com.ifi.rest.WSCommon;
import com.ifi.service.Lab006WS;
import com.ifi.service.Lab007WS;
import com.reports.model.Lab007ChartData;
import com.reports.model.Lab007ChartLst;
import com.reports.model.Lab007Info;
import com.reports.util.Utils;

/**
 * @author hmtri
 *
 */
public class Lab007WSImpl implements Lab007WS {
	@Autowired
	private ConfigLab007DAO lab007DAO;
	@Autowired
	private ConstantDAO constantDAO;

	@Autowired
	private LabDAO labDAO;
	private static final SimpleDateFormat sdf = new SimpleDateFormat(FrontalKey.DATE_FORMAT_DAY_1);
//	private static final Logger log = Logger.getLogger(Lab006WS.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ifi.service.Lab007WS#getConfig(java.lang.Integer)
	 */
	public Lab007Bean getConfig(Integer siteId) {
		ConfigLab007 config = lab007DAO.findBySiteId(siteId);
		Lab007Bean labBean = new Lab007Bean();
		labBean.setId(config.getId());
		labBean.setSiteName(config.getSiteName());
		return labBean;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ifi.service.Lab007WS#getLabBySite(java.lang.Integer,
	 * java.lang.String, java.lang.String)
	 */
	public Lab007Bean getLabBySite(Integer siteId, String fromDate, String toDate) {
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

		// bean.setTotalElec(151d);
		// bean.setTotalGas(1611.1);
		// bean.setTotalWater(484.5);
		// bean.setSubTotalElec(150.2);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ifi.service.Lab007WS#getReportLink(java.lang.String)
	 */
	public String getReportLink(String siteId) {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ifi.service.Lab007WS#getURIIcon(java.lang.Integer)
	 */
	public String getURIIcon(Integer siteId) {
		ConfigLab007 configLab = lab007DAO.findBySiteId(siteId);
		if (configLab == null) {
			return "This site is not config";
		}
		return configLab.getUriIcon();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ifi.service.Lab007WS#getLogo(java.lang.Integer)
	 */
	public String getLogo(Integer siteId) {
		ConfigLab007 configLab = lab007DAO.findBySiteId(siteId);
		if (configLab == null) {
			return "This site is not config";
		}
		return configLab.getLogo();
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
		Integer result = 0;
		GetDataAction dataAction = new GetDataAction();
		for (String moduleID : moduleArr) {
			Integer consommation = dataAction.getConsommationByTime(
					Integer.parseInt(Utils.getModuleNumberFromArray(moduleID)), fromDate, toDate);
			if (consommation != null) {
				if (result == null) {
					result = 0;
				}
				result += Utils.convertToKWh(moduleID, consommation).intValue();
			}
		}
		return result;
	}

	public Lab007ChartLst getChartData(Integer siteId, String fromDate, String toDate) {
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
		Lab007ChartLst labList = null;
		if (config != null) {
			labList = new Lab007ChartLst();
			List<Lab007ChartData> listData = new ArrayList<Lab007ChartData>();
			try {
				Date startDate = sdf.parse(fromDate);
				Date endDate = sdf.parse(toDate);
				Calendar startCal = Calendar.getInstance();
				startCal.setTime(startDate);
				Calendar endCal = Calendar.getInstance();
				endCal.setTime(endDate);
				while (startCal.getTime().compareTo(endCal.getTime()) <= 0) {
					String moduleElec = config.getTotalElecModuleId();
					String moduleWater = config.getTotalWaterModuleId();
					String moduleGas = config.getTotalGasModuleId();
					// String modulePylone = config.getTotalPylonesModuleId();
					Date date_tmp = startCal.getTime();
					String startDateStr = sdf.format(date_tmp);
					if (moduleElec != null && !"".equals(moduleElec)) {
						Lab007ChartData data = new Lab007ChartData();
						data.setxCategory(date_tmp);
						data.setGroupName("Electricité");
						data.setPowerValue(getConsumptionByModule(moduleElec, startDateStr, startDateStr));
						listData.add(data);
					}
					if (moduleWater != null && !"".equals(moduleWater)) {
						Lab007ChartData data = new Lab007ChartData();
						data.setxCategory(date_tmp);
						data.setGroupName("Eau");
						data.setCapacityValue(getConsumptionByModule(moduleWater, startDateStr, startDateStr));
						listData.add(data);
					}
					if (moduleGas != null && !"".equals(moduleGas)) {
						Lab007ChartData data = new Lab007ChartData();
						data.setxCategory(date_tmp);
						data.setGroupName("Gaz");
						data.setCapacityValue(getConsumptionByModule(moduleGas, startDateStr, startDateStr));
						listData.add(data);
					}
					startCal.add(Calendar.DAY_OF_YEAR, 1);
				}
				if (listData.size() > 0) {
					labList.setLabChartLst(listData);
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return labList;
	}

	public Lab007Info getLab007Info(Integer siteId, String fromDate, String toDate) {
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
		Lab007Info info = null;
		if (config != null) {
			try {
				info = new Lab007Info();
				Date startDate = sdf.parse(fromDate);
				Date endDate = sdf.parse(toDate);
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				info.setDateRange(format.format(startDate) + " - " + format.format(endDate));
				Integer totalElec = null;
				Integer totalWater = null;
				Integer totalGas = null;
				Integer totalPylone = null;
				if (config.getTotalElecModuleId() != null && !"".equals(config.getTotalElecModuleId())) {
					totalElec = getConsumptionByModule(config.getTotalElecModuleId(), fromDate, toDate);
				}
				if (config.getTotalGasModuleId() != null && !"".equals(config.getTotalGasModuleId())) {
					totalGas = getConsumptionByModule(config.getTotalGasModuleId(), fromDate, toDate);
				}
				if (config.getTotalWaterModuleId() != null && !"".equals(config.getTotalWaterModuleId())) {
					totalWater = getConsumptionByModule(config.getTotalWaterModuleId(), fromDate, toDate);
				}
				if (config.getTotalPylonesModuleId() != null && !"".equals(config.getTotalPylonesModuleId())) {
					totalPylone = getConsumptionByModule(config.getTotalPylonesModuleId(), fromDate, toDate);
				}

				info.setTotalElec(totalElec);
				info.setTotalGas(totalGas);
				info.setTotalWater(totalWater);
				info.setTotalPylone(totalPylone);

				String unitElec = "kWh";
				String unitGas = "kWh";
				String unitWater = "L";
				String unitPylone = "kWh";
				if (config.getUnitElec() != null && !"".equals(config.getUnitElec())) {
					unitElec = StringEscapeUtils.unescapeHtml4(config.getUnitElec());
				}
				if (config.getUnitGas() != null && !"".equals(config.getUnitGas())) {
					unitGas = StringEscapeUtils.unescapeHtml4(config.getUnitGas());
				}
				if (config.getUnitWater() != null && !"".equals(config.getUnitWater())) {
					unitWater = StringEscapeUtils.unescapeHtml4(config.getUnitWater());
				}
				if (config.getUnitPylones() != null && !"".equals(config.getUnitPylones())) {
					unitPylone = StringEscapeUtils.unescapeHtml4(config.getUnitPylones());
				}

				info.setUnitElec(unitElec);
				info.setUnitGas(unitGas);
				info.setUnitPylone(unitPylone);
				info.setUnitWater(unitWater);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return info;
	}

}
