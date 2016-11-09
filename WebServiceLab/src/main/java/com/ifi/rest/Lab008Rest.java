package com.ifi.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.commons.math3.special.Erf;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.apache.commons.math3.util.Precision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ifi.common.bean.Lab008Bean;
import com.ifi.common.bean.Lab008ConsommationBean;
import com.ifi.common.bean.Lab008PerformanceBean;
import com.ifi.common.bean.Lab008PerformanceData;
import com.ifi.common.bean.Lab008PerformanceYear;
import com.ifi.common.bean.Lab008SaveTemplateBean;
import com.ifi.common.util.ConfigEnum;
import com.ifi.common.util.FrontalKey;
import com.ifi.common.util.LabUtils;
import com.ifi.common.util.PropertiesReader;
import com.ifi.common.ws.IndusHistoryData;
import com.ifi.lab.LabDAO.dao.ConfigLab008V2DAO;
import com.ifi.lab.LabDAO.dao.Lab008SaveTemplateDAO;
import com.ifi.lab.LabDAO.dao.LabDAO;
import com.ifi.lab.LabDAO.dao.UserDAO;
import com.ifi.lab.LabDAO.dao.UserLabDAO;
import com.ifi.lab.LabDAO.model.ConfigLab008V2;
import com.ifi.lab.LabDAO.model.Lab;
import com.ifi.lab.LabDAO.model.Lab008SaveTemplate;
import com.ifi.lab.LabDAO.model.User;
import com.ifi.lab.LabDAO.model.UserLab;

/**
 * Created by hmtri on 2/29/2016.
 */
@Controller
@RequestMapping("lab008")
public class Lab008Rest {
	@Autowired
	private ConfigLab008V2DAO lab008DAO;
	@Autowired
	private LabDAO labDAO;
	@Autowired
	private UserLabDAO userLabDAO;
	@Autowired
	private UserDAO userDAO;
	// @Autowired
	// private Lab008TrefDAO lab008TrefDAO;

	@Autowired
	private Lab008SaveTemplateDAO lab008SaveTemplateDAO;

	// private static final SimpleDateFormat sdf = new
	// SimpleDateFormat(FrontalKey.DATE_FORMAT_DAY_1);

	private static final SimpleDateFormat sdf2 = new SimpleDateFormat(FrontalKey.DATE_FORMAT_DAY_2);

	private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(Lab008Rest.class);

	private static final Integer CHAR_TYPE_SIGNATURE = 1;
	// private static final Integer CHAR_TYPE_MODELE = 2;
	// private List<Double> listRateCharge;
	// private List<Double> listPCS;
	// private List<Double> listPCI;
	// private double sumProduction = 0;

	@RequestMapping(value = "getConfig", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody Lab008Bean getConfig(@RequestParam Integer siteId) {
		ConfigLab008V2 config = lab008DAO.findBySiteId(siteId);
		Lab008Bean labBean = new Lab008Bean();
		labBean.setId(config.getId());
		labBean.setSiteName(config.getSiteName());
		return labBean;
	}

	@RequestMapping(value = "getListSiteByUser", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody List<Lab008Bean> getListSiteByUser(@RequestParam String userName) {
		User user = userDAO.findByUserName(userName);
		List<Lab008Bean> listConfig = null;
		if (user != null) {
			Lab lab8 = labDAO.findByName(PropertiesReader.getValue(ConfigEnum.LAB_NAME_008));
			if (lab8 != null) {
				List<UserLab> listUserLab = userLabDAO.getByUserAndLab(user.getId(), lab8.getId());
				if (listUserLab != null && !listUserLab.isEmpty()) {
					listConfig = new ArrayList<Lab008Bean>();
					for (UserLab item : listUserLab) {
						ConfigLab008V2 obj = lab008DAO.findBySiteId(item.getSiteId());
						if (obj != null) {
							Lab008Bean bean = new Lab008Bean();
							bean.setId(obj.getId());
							bean.setSiteName(obj.getSiteName());
							bean.setSiteId(obj.getSiteId());
							listConfig.add(bean);
						}
					}
				}
			}
		}
		return listConfig;
	}

	/**
	 * get data for chart Suivi des performances;
	 * 
	 * @param siteId
	 * @param fromTime
	 *            dd/MM/yyyy
	 * @param toTime
	 *            dd/MM/yyyy
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "getPerformanceData", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody Lab008PerformanceBean getPerformanceData(@RequestParam Integer siteId,
			@RequestParam String fromTime, @RequestParam String toTime, @RequestParam String actionName)
			throws ParseException {

		if (siteId == null || siteId == 0) {
			List<ConfigLab008V2> lst = lab008DAO.getAllConfig();
			if (lst.size() > 0) {
				siteId = lst.get(0).getSiteId();
			} else {
				return null;
			}
		}

		Date toDay = LabUtils.getCurrentDay();
		Date firstDate = IndusHistoryData.getFirstDayPerformanceData(siteId);

		Date fromDate = null;

		if (fromTime == null || fromTime.isEmpty()) {
			fromDate = firstDate;
		} else {
			fromDate = LabUtils.convertDateByFormat(fromTime, FrontalKey.DATE_SLASH_FORMAT);
		}
		Date toDate = null;
		if (toTime == null || toTime.isEmpty()) {
			toDate = toDay;
		} else {
			toDate = LabUtils.convertDateByFormat(toTime, FrontalKey.DATE_SLASH_FORMAT);
		}

		if (fromDate == null) {
			return null;
		}

		if (toDate == null) {
			return null;
		}

		if (actionName != null && !actionName.isEmpty()) {
			if (actionName.equals(FrontalKey.ACTION_BACK_SITE)) {
				List<ConfigLab008V2> lst = lab008DAO.getAllConfig();
				for (int i = 0; i < lst.size(); i++) {
					if (lst.get(i).getSiteId().equals(siteId)) {
						if (i > 0) {
							siteId = lst.get(i - 1).getSiteId();
						} else {
							siteId = lst.get(lst.size() - 1).getSiteId();
						}
						break;
					}
				}
				fromDate = firstDate;
				toDate = toDay;
			} else if (actionName.equals(FrontalKey.ACTION_NEXT_SITE)) {
				List<ConfigLab008V2> lst = lab008DAO.getAllConfig();
				for (int i = 0; i < lst.size(); i++) {
					if (lst.get(i).getSiteId().equals(siteId)) {
						if (i < lst.size() - 1) {
							siteId = lst.get(i + 1).getSiteId();
						} else {
							siteId = lst.get(0).getSiteId();
						}
						break;
					}
				}
				fromDate = firstDate;
				toDate = toDay;
			} else if (actionName.equals(FrontalKey.ACTION_BACK_FROM)) {
				fromDate = LabUtils.getPreviousMonth(fromDate);
			} else if (actionName.equals(FrontalKey.ACTION_NEXT_FROM)) {
				fromDate = LabUtils.getNextMonth(fromDate);
			} else if (actionName.equals(FrontalKey.ACTION_BACK_TO)) {
				toDate = LabUtils.getPreviousMonth(toDate);
			} else if (actionName.equals(FrontalKey.ACTION_NEXT_TO)) {
				toDate = LabUtils.getNextMonth(toDate);
			}
		}

		Lab008PerformanceBean bean = new Lab008PerformanceBean();
		bean.setFromDateStr(LabUtils.convertDateByFormat(fromDate, FrontalKey.DATE_SLASH_FORMAT));
		bean.setToDateStr(LabUtils.convertDateByFormat(toDate, FrontalKey.DATE_SLASH_FORMAT));
		double indicatorPerformance = 0;
		List<Object[]> dataChart = new ArrayList<Object[]>();
		List<Lab008PerformanceYear> listPerformanceYear = new ArrayList<Lab008PerformanceYear>();

		ConfigLab008V2 config = lab008DAO.findBySiteId(siteId);
		if (config == null) {
			return bean;
		}

		Date fromDateTmp = LabUtils.addMonth(fromDate, -11);
		List<Lab008PerformanceData> listDataPerfomance = IndusHistoryData.getPerformanceData(siteId, fromDateTmp,
				toDate);
		Date startRealDate = null;
		Date endRealDate = null;
		int countMonth = 0;
		if (listDataPerfomance != null && !listDataPerfomance.isEmpty()) {

			int listDataPerfomanceSize = listDataPerfomance.size();
			// Loop year
			int position = 0;
			Date tmpDate = fromDate;
			// for (int i = fromYear; i <= toYear; i++) {
			double first = 0;
			double last = 0;
			while (tmpDate.compareTo(toDate) <= 0) {
				countMonth++;
				// Loop month
				double signatureEnergy = 0;
				Date endDate = tmpDate;
				Date startDate = LabUtils.addMonth(endDate, -11);
				double sumMensuel = 0;
				double sumDataDJU = 0;
				boolean isFind = true;
				// Chi lay cac du lieu ma co du 12 thang
				int countData = 0;
				for (int k = position; k < listDataPerfomanceSize; k++) {
					Lab008PerformanceData tmp = listDataPerfomance.get(k);
					Date d = tmp.getMonth();
					if (d.compareTo(endDate) > 0) {
						break;
					} else if (d.compareTo(startDate) >= 0) {
						if (isFind) {
							position = k;
							isFind = false;
						}
						countData++;
						sumMensuel += tmp.getDatakWhMensuel();
						sumDataDJU += tmp.getDataDJU();
					}
				}
				if (countData >= 12) {
					System.out.println("add");
					if (sumDataDJU != 0) {
						signatureEnergy = sumMensuel / sumDataDJU;
					}
					dataChart.add(new Object[] { sdf2.format(tmpDate), signatureEnergy });
					if (startRealDate == null) {
						startRealDate = tmpDate;
					}
					endRealDate = tmpDate;
				} else {
					if (startRealDate != null) {
						break;
					}
				}

				if (countMonth == 1) {
					first = signatureEnergy;
				}

				// Tinh ty le cho tung nam va lay so nam khi da qua du 12
				// thang
				if (countMonth == 13 || tmpDate.compareTo(toDate) == 0) {
					last = signatureEnergy;
					countMonth = 0;
					Lab008PerformanceYear lab008PerformanceYear = new Lab008PerformanceYear();
					lab008PerformanceYear.setYear(LabUtils.getYear(tmpDate));
					if (last != 0 && first != 0) {
						lab008PerformanceYear.setPerformance(((last - first) * 100) / first);
					} else {
						lab008PerformanceYear.setPerformance(0);
					}
					listPerformanceYear.add(lab008PerformanceYear);
				}

				tmpDate = LabUtils.addMonth(tmpDate, 1);
			}

			if (startRealDate == null || endRealDate == null) {
				return bean;
			}
			// Get Performance by year
			int size = dataChart.size();
			last = (Double) dataChart.get(size - 1)[1];
			first = (Double) dataChart.get(0)[1];
			for (int i = 0; i < size; i++) {
				if ((Double) dataChart.get(i)[1] > 0) {
					first = (Double) dataChart.get(i)[1];
					break;
				}
			}
			for (int i = size - 1; i >= 0; i--) {
				if ((Double) dataChart.get(i)[1] > 0) {
					last = (Double) dataChart.get(i)[1];
					break;
				}
			}
			indicatorPerformance = ((last - first) * 100) / first;

		}
		// int numYearSelected = endCal.get(Calendar.YEAR) -
		// startCal.get(Calendar.YEAR) + 1;

		// Collections.reverse(listDataPerfomance);
		// List<Long> listStartYear = LabUtils.getListStartYear(fromDate,
		// toDate);
		List<Long> listDisplayYear = LabUtils.getListDisplayYear(startRealDate, endRealDate);
		if (startRealDate != null && endRealDate != null) {
			bean.setListDisplayYear(listDisplayYear);
		}
		bean.setPeriodIndicatorPerformance(indicatorPerformance);
		bean.setDataCharts(dataChart);
		bean.setListYearPerformance(listPerformanceYear);
		bean.setSiteId(siteId);
		bean.setSiteName(config.getSiteName());
		bean.setStartTime(startRealDate.getTime());
		bean.setEndTime(endRealDate.getTime());

		// bean.setListStartYear(listStartYear);
		return bean;
	}

	// /**
	// * moduleArr module id sepecrate by comma
	// *
	// * @param moduleStr
	// * @param fromDate
	// * @param toDate
	// * @return
	// */
	// private Double getConsumptionByModule(String moduleStr, String fromDate,
	// String toDate) {
	// GetDataAction csm = new GetDataAction();
	// if (moduleStr == null || moduleStr.isEmpty()) {
	// return null;
	// }
	// String[] moduleArr = moduleStr.split(FrontalKey.PLUS_SPECIAL);
	// Double rs = 0d;
	// List<Integer> moduleLst = new ArrayList<Integer>();
	// for (String module : moduleArr) {
	// moduleLst.add(Integer.parseInt(Utils.getModuleNumberFromArray(module)));
	// }
	// Map<Integer, Integer> map = csm.getMapConsommationByTime(moduleLst,
	// fromDate, toDate, null, null);
	// for (String module : moduleArr) {
	// Integer moduleId =
	// Integer.parseInt(Utils.getModuleNumberFromArray(module));
	// Integer consommation = map.get(moduleId);
	// if (consommation != null) {
	// rs += Utils.convertToKWh(module, consommation);
	// }
	// }
	// return rs;
	// }
	//
	// private Integer getTemperatureByModule(String moduleStr, String fromDate,
	// String toDate) {
	// if (moduleStr == null || moduleStr.isEmpty()) {
	// return null;
	// }
	// // String[] moduleArr = moduleStr.split(FrontalKey.PLUS_SPECIAL);
	// // Integer result = null;
	// GetDataAction csm = new GetDataAction();
	// return csm.getLastTemperatureByTime(Integer.parseInt(moduleStr),
	// fromDate, toDate, "1", "24");
	// }

	@RequestMapping(value = "getReportLink", method = RequestMethod.GET)
	public @ResponseBody String getReportLink(@RequestParam String siteId) {
		Integer siteNumber = WSCommon.getNumber(siteId);
		if (siteNumber == null) {
			return "siteId required";
		}
		ConfigLab008V2 configLab = lab008DAO.findBySiteId(siteNumber);
		if (configLab == null || configLab.getId() == null) {
			return "Site Id not exist";
		}
		if (configLab.getReportName() == null || configLab.getReportName().trim().equals("")) {
			return "Site Id - Report File not exist";
		}
		String reportLink = PropertiesReader.getValue(ConfigEnum.REPORT_LAB008_LINK) + FrontalKey.WINDOWS + siteId
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
	public @ResponseBody String getURIIcon(@RequestParam Integer siteId) {

		ConfigLab008V2 configLab = lab008DAO.findBySiteId(siteId);
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
	public @ResponseBody String getLogo(@RequestParam Integer siteId) {
		ConfigLab008V2 configLab = lab008DAO.findBySiteId(siteId);
		if (configLab == null) {
			return "This site is not config";
		}
		return configLab.getLogo();
	}

	class ConsumptionMonth {
		private Date month;
		private Integer consumption;
		private Double dju;

		public Date getMonth() {
			return month;
		}

		public void setMonth(Date month) {
			this.month = month;
		}

		public Integer getConsumption() {
			return consumption;
		}

		public void setConsumption(Integer consumption) {
			this.consumption = consumption;
		}

		public Double getDju() {
			return dju;
		}

		public void setDju(Double dju) {
			this.dju = dju;
		}

	}

	/**
	 * Draw chart data Modele predictif
	 * 
	 * @param siteId
	 * @param fromTime
	 *            yyyyMM
	 * @param toTime
	 *            yyyyMM
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "getPrevisionConsommationData", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody Lab008ConsommationBean getPrevisionConsommationData(@RequestParam Integer siteId,
			@RequestParam String fromTime, @RequestParam String toTime, @RequestParam Integer intervalConfidence,
			@RequestParam String actionName, @RequestParam Integer chartType, @RequestParam Boolean modelIsStart,
			@RequestParam Boolean modelIsEnd) throws ParseException {

		if (siteId == null || siteId == 0) {
			List<ConfigLab008V2> lst = lab008DAO.getAllConfig();
			if (lst.size() > 0) {
				siteId = lst.get(0).getSiteId();
			} else {
				return null;
			}
		}

		if (actionName != null && !actionName.isEmpty()) {
			if (actionName.equals(FrontalKey.ACTION_BACK_SITE)) {
				List<ConfigLab008V2> lst = lab008DAO.getAllConfig();
				for (int i = 0; i < lst.size(); i++) {
					if (lst.get(i).getSiteId().equals(siteId)) {
						if (i > 0) {
							siteId = lst.get(i - 1).getSiteId();
						} else {
							siteId = lst.get(lst.size() - 1).getSiteId();
						}
						break;
					}
				}
			} else if (actionName.equals(FrontalKey.ACTION_NEXT_SITE)) {
				List<ConfigLab008V2> lst = lab008DAO.getAllConfig();
				for (int i = 0; i < lst.size(); i++) {
					if (lst.get(i).getSiteId().equals(siteId)) {
						if (i < lst.size() - 1) {
							siteId = lst.get(i + 1).getSiteId();
						} else {
							siteId = lst.get(0).getSiteId();
						}
						break;
					}
				}
			}
		}

		ConfigLab008V2 configLab008 = lab008DAO.findBySiteId(siteId);
		if (configLab008 == null) {
			return null;
		}

		Date toDay = LabUtils.getCurrentDay();
		Date firstDate = IndusHistoryData.getFirstDayPerformanceData(siteId);

		Date fromDate = null;
		Date toDate = null;
		if (fromTime == null || fromTime.isEmpty()) {
			if (CHAR_TYPE_SIGNATURE.equals(chartType)) {
				if (modelIsStart != null) {
					fromDate = firstDate;
				} else {
					if (configLab008.getModelIsStart() != null) {
						if (configLab008.getModelIsStart()) {
							fromDate = firstDate;
						} else {
							fromDate = configLab008.getModelDateStart();
						}
						modelIsStart = configLab008.getModelIsStart();
					} else {
						fromDate = firstDate;
						modelIsStart = true;
					}
				}
			} else {
				fromDate = firstDate;
			}
		} else {
			if (CHAR_TYPE_SIGNATURE.equals(chartType) && modelIsStart) {
				fromDate = firstDate;
			} else {
				fromDate = LabUtils.convertDateByFormat(fromTime, FrontalKey.DATE_SLASH_FORMAT);
			}
		}
		if (fromDate == null) {
			return null;
		}

		if (toTime == null || toTime.isEmpty()) {
			if (CHAR_TYPE_SIGNATURE.equals(chartType)) {
				if (modelIsEnd != null) {
					toDate = toDay;
				} else {
					if (configLab008.getModelIsEnd() != null) {
						if (configLab008.getModelIsEnd()) {
							toDate = toDay;
						} else {
							toDate = configLab008.getModelDateEnd();
						}
						modelIsEnd = configLab008.getModelIsEnd();
					} else {
						toDate = toDay;
						modelIsEnd = true;
					}
				}
			} else {
				toDate = toDay;
			}
		} else {
			if (CHAR_TYPE_SIGNATURE.equals(chartType) && modelIsEnd) {
				toDate = toDay;
			} else {
				toDate = LabUtils.convertDateByFormat(toTime, FrontalKey.DATE_SLASH_FORMAT);
			}
		}
		if (toDate == null) {
			return null;
		}

		if (actionName != null && !actionName.isEmpty()) {
			if (actionName.equals(FrontalKey.ACTION_BACK_FROM)) {
				fromDate = LabUtils.getPreviousMonth(fromDate);
			} else if (actionName.equals(FrontalKey.ACTION_NEXT_FROM)) {
				fromDate = LabUtils.getNextMonth(fromDate);
			} else if (actionName.equals(FrontalKey.ACTION_BACK_TO)) {
				toDate = LabUtils.getPreviousMonth(toDate);
			} else if (actionName.equals(FrontalKey.ACTION_NEXT_TO)) {
				toDate = LabUtils.getNextMonth(toDate);
			}
		}

		Lab008ConsommationBean bean = new Lab008ConsommationBean();
		bean.setFromDateStr(LabUtils.convertDateByFormat(fromDate, FrontalKey.DATE_SLASH_FORMAT));
		bean.setToDateStr(LabUtils.convertDateByFormat(toDate, FrontalKey.DATE_SLASH_FORMAT));
		bean.setSiteId(configLab008.getSiteId());
		bean.setSiteName(configLab008.getSiteName());

		List<Lab008PerformanceData> lab008PerfomanceDataLst = IndusHistoryData.getPerformanceData(siteId, fromDate,
				toDate);
		if (lab008PerfomanceDataLst == null || lab008PerfomanceDataLst.isEmpty()) {
			return bean;
		}

		List<Long> listStartYear = LabUtils.getListStartYear(fromDate, toDate);
		List<Long> listDisplayYear = LabUtils.getListDisplayYear(fromDate, toDate);
		double percent = 0;
		if (intervalConfidence == null || intervalConfidence == 0) {
			if (configLab008.getModelPrecision() != null) {
				percent = configLab008.getModelPrecision();
			} else {
				percent = 0.95;
			}
		} else {
			percent = (double) intervalConfidence / 100;
		}
		double confidence = getConfidence(lab008PerfomanceDataLst, percent);

		double[][] data = new double[lab008PerfomanceDataLst.size()][2];
		for (int i = 0; i < lab008PerfomanceDataLst.size(); i++) {
			Lab008PerformanceData tmp = lab008PerfomanceDataLst.get(i);
			data[i] = new double[] { tmp.getDataDJU(), tmp.getDatakWhMensuel() };
		}

		SimpleRegression regression = new SimpleRegression();
		regression.addData(data);
		double slope = regression.getSlope();
		double intercept = regression.getIntercept();
		double rSquare = regression.getRSquare();

		List<Object[]> consommationRealLst = new ArrayList<Object[]>();
		List<Object[]> consommationTheoryLst = new ArrayList<Object[]>();
		List<Object[]> limiteBasseLst = new ArrayList<Object[]>();
		// List<Object[]> limiteHauteLst = new ArrayList<Object[]>();
		List<Object[]> lineLst = new ArrayList<Object[]>();
		List<Object[]> consommationOnRangeLst = new ArrayList<Object[]>();
		List<Object[]> consommationOutRangeLst = new ArrayList<Object[]>();
		List<Object[]> limitRangeLst = new ArrayList<Object[]>();

		double minDJU = lab008PerfomanceDataLst.get(0).getDataDJU();
		double maxDJU = lab008PerfomanceDataLst.get(0).getDataDJU();
		double confidenceX2 = confidence * 2;
		for (int i = 0; i < lab008PerfomanceDataLst.size(); i++) {
			Lab008PerformanceData tmp = lab008PerfomanceDataLst.get(i);
			Double datakWhMensuel = tmp.getDatakWhMensuel();
			Double dataDJU = tmp.getDataDJU();
			Long time = tmp.getMonth().getTime();
			if (datakWhMensuel == null) {
				datakWhMensuel = 0d;
			}
			if (dataDJU == null) {
				dataDJU = 0d;
			}
			if (dataDJU > maxDJU) {
				maxDJU = dataDJU;
			}
			if (dataDJU < minDJU) {
				minDJU = dataDJU;
			}
			// add data to chart1
			consommationRealLst.add(new Object[] { dataDJU, datakWhMensuel, time });
			double theory = tmp.getDataDJU() * slope + intercept;
			if (theory < 0) {
				theory = 0;
			}
			consommationTheoryLst.add(new Object[] { tmp.getMonth(), theory });
			double lowThreshold = theory - confidence;
			limiteBasseLst.add(new Object[] { time, lowThreshold });
			double highThreshold = theory + confidence;
			// limiteHauteLst.add(new Object[] { tmp.getMonth(), highThreshold
			// });

			if (datakWhMensuel >= lowThreshold && datakWhMensuel <= highThreshold) {
				consommationOnRangeLst.add(new Object[] { time, datakWhMensuel });
				// consommationOutRangeLst.add(new Object[] { tmp.getMonth(), 0
				// });
			} else {
				consommationOutRangeLst.add(new Object[] { time, datakWhMensuel });
				// consommationOnRangeLst.add(new Object[] { tmp.getMonth(), 0
				// });
			}

			limitRangeLst.add(new Object[] { time, confidenceX2 });
		}

		double yMin = slope * minDJU + intercept;
		lineLst.add(new Object[] { minDJU, yMin });

		double yMax = slope * maxDJU + intercept;
		lineLst.add(new Object[] { maxDJU, yMax });

		// Get save template

		bean.setSaveTemplateBeanLst(getLab008SaveTemplateBeanLst(siteId));

		// Chart 1
		bean.setLinearA(Precision.round(slope, 1));
		bean.setLinearB((int) Math.round(intercept));
		bean.setrSquare(Precision.round(rSquare, 4));
		bean.setConsommationRealLst(consommationRealLst);
		bean.setConsommationTheoryLst(consommationTheoryLst);
		bean.setConsommationOnRangeLst(consommationOnRangeLst);
		bean.setConsommationOutRangeLst(consommationOutRangeLst);
		bean.setLimiteBasseLst(limiteBasseLst);
		// bean.setLimiteHauteLst(limiteHauteLst);
		bean.setLineLst(lineLst);
		bean.setLimitRangeLst(limitRangeLst);
		bean.setFromDate(fromDate.getTime());
		bean.setToDate(toDate.getTime());
		bean.setListStartYear(listStartYear);
		bean.setListDisplayYear(listDisplayYear);
		bean.setPercent((int) (percent * 100));

		if (CHAR_TYPE_SIGNATURE.equals(chartType)) {
			configLab008.setModelIsStart(modelIsStart);
			configLab008.setModelDateStart(fromDate);
			configLab008.setModelIsEnd(modelIsEnd);
			configLab008.setModelDateEnd(toDate);
			lab008DAO.saveOrUpdate(configLab008);

			bean.setModelIsStart(modelIsStart);
			bean.setModelIsEnd(modelIsEnd);
		}

		return bean;
	}

	/**
	 * Save template
	 * 
	 * @param siteId
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "getLab008SaveTemplateBySite", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody List<Lab008SaveTemplateBean> getLab008SaveTemplateBySite(@RequestParam Integer siteId)
			throws ParseException {

		// Date toDay = LabUtils.getCurrentDay();
		// Date firstDate = IndusHistoryData.getFirstDayPerformanceData(siteId);
		return getLab008SaveTemplateBeanLst(siteId);
	}

	private List<Lab008SaveTemplateBean> getLab008SaveTemplateBeanLst(Integer siteId) {
		List<Lab008SaveTemplate> lstSave = lab008SaveTemplateDAO.findBySiteId(siteId);
		List<Lab008SaveTemplateBean> saveTemplateBeanLst = new ArrayList<Lab008SaveTemplateBean>();

		char flag = 'A';
		for (Lab008SaveTemplate tmp : lstSave) {
			saveTemplateBeanLst.add(getBean(tmp, flag++));
		}
		return saveTemplateBeanLst;
	}

	private Lab008SaveTemplateBean getBean(Lab008SaveTemplate tmp, char flag) {
		Lab008SaveTemplateBean saveBean = new Lab008SaveTemplateBean();
		saveBean.setId(tmp.getId());
		saveBean.setSiteId(tmp.getSiteId());
		saveBean.setIsFromBegin(tmp.isFromBegin());
		saveBean.setIsCurrentDay(tmp.isCurrentDay());
		if (tmp.getFromDate() != null) {
			saveBean.setFromDate(tmp.getFromDate().getTime());
			saveBean.setFromDateStr(LabUtils.convertDateByFormat(tmp.getFromDate(), FrontalKey.DATE_SLASH_FORMAT));
		}
		if (tmp.getToDate() != null) {
			saveBean.setToDate(tmp.getToDate().getTime());
			saveBean.setToDateStr(LabUtils.convertDateByFormat(tmp.getToDate(), FrontalKey.DATE_SLASH_FORMAT));
		}
		saveBean.setFlag(flag);
		saveBean.setDescription(tmp.getDescription());
		return saveBean;
	}

	@RequestMapping(value = "saveLab008SaveTemplate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody int saveLab008SaveTemplate(@RequestParam Integer id, @RequestParam Integer siteId,
			@RequestParam String fromDate, @RequestParam String toDate, @RequestParam Boolean isFromBegin,
			@RequestParam Boolean isCurrentDay, @RequestParam String description) throws ParseException {

		Lab008SaveTemplate obj = new Lab008SaveTemplate();
		if (id != null && id > 0) {
			obj.setId(id);
		}
		obj.setSiteId(siteId);
		obj.setFromDate(LabUtils.convertDateByFormat(fromDate, FrontalKey.DATE_SLASH_FORMAT));
		obj.setToDate(LabUtils.convertDateByFormat(toDate, FrontalKey.DATE_SLASH_FORMAT));
		obj.setFromBegin(isFromBegin);
		obj.setCurrentDay(isCurrentDay);
		obj.setDescription(description);

		boolean ok = lab008SaveTemplateDAO.saveOrUpdate(obj);
		if (!ok) {
			return -1;
		} else {
			return obj.getId();
		}
	}

	@RequestMapping(value = "deleteLab008SaveTemplate", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody Boolean deleteLab008SaveTemplate(@RequestParam Integer id) throws ParseException {
		return lab008SaveTemplateDAO.delete(id);
	}

	// public static void main(String[] args) {
	// // nmha: fortest SimpleRegression
	// double[][] data = { { 363, 1100283 }, { 386.3, 1118284 }, { 307.6,
	// 1223692 }, { 202.3, 484545 },
	// { 118.5, 160470 }, { 29.2, 12889 }, { 7.6, 0 }, { 14.8, 0 }, { 48.1, 0 },
	// { 57.6, 0 },
	// { 326.9, 462923 }, { 440.2, 1262048 }, { 431.7, 1245760 }, { 396.2,
	// 1132197 }, { 330.6, 1288988 },
	// { 211.6, 570206 }, { 98, 76739 }, { 19, 26670 }, { 0, 0 }, { 15.8, 0 }, {
	// 12.5, 0 }, { 88.6, 4759 },
	// { 251.9, 675488 }, { 387.3, 997154 }, { 319.9, 1208325 }, { 263.4, 815950
	// }, { 308.8, 787122 },
	// { 92.9, 325211 }, { 81.4, 46810 }, { 29.4, 10807 }, { 20.6, 0 }, { 24.9,
	// 0 }, { 88.7, 1287 },
	// { 181.9, 278817 }, { 316.5, 784956 }, { 402.9, 1108625 }, { 337.1,
	// 1130739 }, { 301, 891443 },
	// { 304.6, 816404 }, { 236.5, 629172 }, { 53.8, 112809 }, { 36.5, 0 }, {
	// 12, 1031 }, { 19.5, 0 },
	// { 96.1, 0 }, { 205.7, 103761 }, { 284, 763687 }, { 429.1, 893586 }, {
	// 491.9, 1253064 },
	// { 370.8, 1234932 }, { 310.3, 711760 }, { 180.3, 465609 }, { 90.5, 97134
	// }, { 39.3, 0 }, { 5.8, 0 },
	// { 2.8, 0 }, { 33.7, 0 }, { 157.6, 86501 }, { 221.8, 412839 }, { 414.2,
	// 981194 }, { 501.6, 1312730 },
	// { 369.6, 1171323 }, { 311.2, 728500 }, { 188.3, 430246 }, { 142.4, 160522
	// }, { 25.5, 0 }, { 0.2, 0 },
	// { 14.5, 0 }, { 68.7, 0 }, { 183.2, 160328 }, { 319.9, 581922 }, { 499.9,
	// 1593571 }, { 391.6, 1173237 },
	// { 297.4, 1021081 }, { 262.4, 661905 }, { 109.3, 252238 }, { 58.6, 51538
	// }, { 36, 0 }, { 20.2, 0 },
	// { 13, 0 }, { 28.7, 0 }, { 136.3, 117463 }, { 204, 408023 }, { 306.7,
	// 745132 }, { 347.05, 788157 },
	// { 469.4, 1322483 }, { 239.1, 593700 }, { 253.1, 581531 }, { 99.45, 149724
	// }, { 42.25, 26439 },
	// { 24.95, 5161 }, { 4.75, 0 }, { 77.65, 0 }, { 157.4, 122929 }, { 297.35,
	// 639985 }, { 333.05, 878326 },
	// { 408.85, 1096022 }, { 387.85, 1167874 }, { 359.45, 862276 }, { 238.55,
	// 670660 }, { 170.45, 238532 },
	// { 50.45, 2398 }, { 1.7, 1613 }, { 4.9, 5187 }, { 45.25, 0 }, { 114.5,
	// 38505 }, { 308.15, 626163 },
	// { 379.65, 890288 }, { 319.95, 845931 }, { 297.3, 860176 }, { 266.75,
	// 533245 }, { 175.04, 312942 },
	// { 114.15, 122360 }, { 71.65, 0 }, { 35.51, 0 }, { 75.18, 0 }, { 61.45, 0
	// }, { 168.72, 44452 },
	// { 271.06, 415593 } };
	// SimpleRegression regression = new SimpleRegression();
	//
	// regression.addData(data);
	// // System.out.println("intercept:" + regression.getIntercept());
	// // System.out.println("getInterceptStdErr:" +
	// // regression.getInterceptStdErr());
	// // System.out.println("getInterceptStdErr:" +
	// // regression.getMeanSquareError());
	// // System.out.println("slope:" + regression.getSlope());
	// // System.out.println("slope:" + regression.getR());
	// // System.out.println("slope:" + regression.getRegressionSumSquares());
	// // System.out.println("slope:" + regression.getSignificance());
	// // System.out.println("rSquare:" + regression.getRSquare());
	// // System.out.println("----------------");
	// System.out.println("getSlopeConfidenceInterval123:" +
	// regression.getSlopeConfidenceInterval());
	// //
	// TDistribution distribution = new TDistribution(117);
	// System.out.println(regression.getSlopeStdErr());
	// System.out.println(distribution.inverseCumulativeProbability(0.05));
	//
	// double p = 0.05;
	// double z = Math.sqrt(2) * Erf.erfcInv(p);
	// System.out.println(z);
	// //
	// // System.out.println("getInterceptStdErr:" +
	// // regression.getInterceptStdErr());
	// //
	// // System.out.println("TotalSumSquares:" +
	// // regression.getTotalSumSquares());
	// // System.out.println("getSlopeStdErr:" + regression.getSlopeStdErr());
	// // System.out.println(regression.getN());
	// // System.out.println("getMeanSquareError:" +
	// // regression.getMeanSquareError());
	// // // 5.2
	// // System.out.println("getSumSquaredErrors:" +
	// // regression.getSumSquaredErrors());
	// // // 5.1
	// // System.out.println("getSumSquaredErrors:" +
	// // regression.getRegressionSumSquares());
	// //
	// //
	// System.out.println("getInterceptStdErr:"+regression.getInterceptStdErr());
	//
	// System.out.println("---------------");
	// int n = data.length;
	// System.out.println(n);
	// double sumX = 0;
	// double sumY = 0;
	//
	// for (int i = 0; i < n; i++) {
	// sumX += data[i][0];
	// sumY += data[i][1];
	// }
	// double avgX = sumX / n;
	// double avgY = sumY / n;
	// double sum1 = 0;
	// double sum2 = 0;
	// double sum3 = 0;
	// for (int i = 0; i < n; i++) {
	// sum1 += (data[i][1] - avgY) * (data[i][1] - avgY);
	// sum2 += (data[i][0] - avgX) * (data[i][1] - avgY);
	// sum3 += (data[i][0] - avgX) * (data[i][0] - avgX);
	// }
	// double tmp = (1d / (n - 2)) * (sum1 - (sum2 * sum2 / sum3));
	// double a = Math.sqrt(tmp);
	// System.out.println(a);
	//
	// System.out.println("---------------");
	//
	// }

	private double getConfidence(List<Lab008PerformanceData> lab008PerfomanceDataLst, double percent) {
		double confidence = 0;
		double zScore = 0;
		double sey = 0;
		int df = lab008PerfomanceDataLst.size() - 2;

		zScore = Math.sqrt(2) * Erf.erfcInv(1 - percent);
		sey = getSey(lab008PerfomanceDataLst);
		confidence = zScore * sey / Math.sqrt(df);
		if (Double.isNaN(confidence) || confidence == Double.POSITIVE_INFINITY) {
			LOG.error("Confidence error: lab008PerfomanceDataLstSize:" + df + "-sey:" + sey + "-zScore:" + zScore);
			confidence = 0;
		}

		return confidence;
	}

	/**
	 * Reference calculate sey here
	 * http://office.microsoft.com/en-au/excel-help/steyx-function-HP010062545.
	 * aspx
	 * 
	 * @param lab008PerfomanceDataLst
	 * @return
	 */
	private double getSey(List<Lab008PerformanceData> lab008PerfomanceDataLst) {
		int n = lab008PerfomanceDataLst.size();
		double sumX = 0;
		double sumY = 0;

		for (int i = 0; i < n; i++) {
			Lab008PerformanceData tmp = lab008PerfomanceDataLst.get(i);
			sumX += tmp.getDataDJU();
			sumY += tmp.getDatakWhMensuel();
		}
		double avgX = sumX / n;
		double avgY = sumY / n;
		double sum1 = 0;
		double sum2 = 0;
		double sum3 = 0;
		for (int i = 0; i < n; i++) {
			Lab008PerformanceData tmp = lab008PerfomanceDataLst.get(i);
			double x = tmp.getDataDJU();
			double y = tmp.getDatakWhMensuel();
			sum1 += (y - avgY) * (y - avgY);
			sum2 += (x - avgX) * (y - avgY);
			sum3 += (x - avgX) * (x - avgX);
		}
		double tmp = (1d / (n - 2)) * (sum1 - (sum2 * sum2 / sum3));
		double a = Math.sqrt(tmp);
		return a;
	}

	public static void main(String[] args) {
		// double d = 123;
		// double e = 0;
		// if (d/e == Double.POSITIVE_INFINITY)
		// System.out.println("Check for POSITIVE_INFINITY works");
		// double s = Double.NaN;
		// if (s == Double.NaN)
		// System.out.println("Comparison with NaN incorrectly returns true");
		// if (Double.isNaN(s))
		// System.out.println("Double.isNaN() correctly returns true"+s);

		double percent = 1;
		double confidence = 0;
		double zScore = 0;
		double sey = 0;
		int df = 10 - 2;

		zScore = Math.sqrt(2) * Erf.erfcInv(1 - percent);
		sey = 0;
		confidence = zScore * sey / Math.sqrt(df);
		if (Double.isNaN(confidence) || confidence == Double.POSITIVE_INFINITY) {
			LOG.error("Confidence error: lab008PerfomanceDataLstSize:" + df + "-sey:" + sey + "-zScore:" + zScore);
			confidence = 0;
		}

		System.out.println(confidence);
	}

}
