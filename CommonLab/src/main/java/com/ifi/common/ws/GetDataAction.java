package com.ifi.common.ws;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ifi.common.bean.TemperaturePoint;
import com.ifi.common.csm.AgregatCSM;
import com.ifi.common.csm.ModuleCSM;
import com.ifi.common.util.CalculateUtils;
import com.ifi.common.util.ConfigEnum;
import com.ifi.common.util.FrontalKey;
import com.ifi.common.util.LabUtils;
import com.ifi.common.util.PropertiesReader;

public class GetDataAction {

	public GetDataAction() {
		// System.setProperty("http.proxyHost", "10.225.1.1");
		// System.setProperty("http.proxyPort", "3128");
	}

	public static void main(String[] args) {
		System.setProperty("http.proxyHost", "10.225.1.1");
		System.setProperty("http.proxyPort", "3128");
		@SuppressWarnings({ "unused", "resource" })
		ApplicationContext context = new ClassPathXmlApplicationContext("constantConfig.xml");
		// GetIndusDataAction indus = new GetIndusDataAction();
		// Integer consommation = indus.getIndusConsommation("ENR04B6F",
		// "2015-01-01T00:00:00", "2016-04-26T23:59:59");
		// System.out.println(consommation);
		// Integer temperature = indus.getIndusLastTemperature("05130378",
		// "2015-04-01T00:00:00", "2015-04-01T23:00:00");
		// System.out.println(temperature);
	}

	public Integer getConsommationByTime(Integer moduleID, String startDate, String endDate) {
		String useINDUS = PropertiesReader.getValue(ConfigEnum.USE_INDUSTRIALIZATION);
		if (moduleID == null || startDate == null || startDate.isEmpty() || endDate == null || endDate.isEmpty()) {
			return null;
		}
		Integer consommation = null;
		GetCSMDataAction csm = new GetCSMDataAction();
		if (FrontalKey.USE_INDUS_NO.equals(useINDUS)) {
			// USE CSM
			List<AgregatCSM> agregatLst = csm.getAllAgregatByModule(moduleID, startDate, endDate, "00", "24");
			if (agregatLst.size() > 0) {
				consommation = 0;
			}
			for (AgregatCSM agregat : agregatLst) {
				if (agregat.getConsommation() != null) {
					consommation += agregat.getConsommation();
				}
			}
			return consommation;
		} else {
			// USE CSM
			GetIndusDataAction indus = new GetIndusDataAction();
			return indus.getIndusConsommation(moduleID, startDate, endDate);
		}
	}

	/**
	 * Group consommation by time; 1: group by hour 2: group by day 3: group by
	 * month
	 * 
	 * @param moduleID
	 * @param startDate
	 * @param endDate
	 * @param typeTime
	 * @return
	 */
	public Map<String, Integer> getConsommationGroupTime(Integer moduleID, String startDate, String endDate,
			int typeTime) {
		String useINDUS = PropertiesReader.getValue(ConfigEnum.USE_INDUSTRIALIZATION);
		if (moduleID == null || startDate == null || startDate.isEmpty() || endDate == null || endDate.isEmpty()) {
			return null;
		}

		// 0: day : yyyy-MM-dd ; 1: Integer
		Map<String, Integer> map = new HashMap<String, Integer>();
		if (FrontalKey.USE_INDUS_NO.equals(useINDUS)) {
			// USE CSM
			GetCSMDataAction csm = new GetCSMDataAction();
			List<AgregatCSM> agregatLst = csm.getAllAgregatByModule(moduleID, startDate, endDate, "00", "24");
			if (agregatLst == null || agregatLst.size() == 0) {
				return null;
			}

			for (AgregatCSM agregat : agregatLst) {
				Integer consommation = 0;
				String day = agregat.getDateHeureAgregat() + agregat.getHeureAgregat();
				if (map.containsKey(day)) {
					consommation = map.get(day) + agregat.getConsommation();
				} else {
					consommation = agregat.getConsommation();
				}
				map.put(day, consommation);
			}
			return map;
		} else {
			// USE CSM
			GetIndusDataAction indus = new GetIndusDataAction();
			// GetCSMDataAction csm = new GetCSMDataAction();
			/*
			 * List<IndusWDTrames> lst = indus.getWDTrames(moduleID, startDate,
			 * endDate);
			 */
			List<Integer> gatewayIdLst = new ArrayList<Integer>();
			gatewayIdLst.add(moduleID);
			Map<Integer, Map<String, Integer>> mapModule = indus.getGroupTramesByTime(gatewayIdLst, startDate, endDate,
					typeTime);
			if (mapModule.containsKey(moduleID)) {
				map = mapModule.get(moduleID);
			}

			return map;
		}
	}

	// private String getTimeIndus(String date, String hour) {
	// if (date == null || date.length() != 8) {
	// return null;
	// }
	// StringBuilder dateSb = new StringBuilder();
	// dateSb.append(date.substring(0, 4));
	// dateSb.append("-");
	// dateSb.append(date.substring(4, 6));
	// dateSb.append("-");
	// dateSb.append(date.substring(6, 8));
	// dateSb.append("T");
	// if (hour == null) {
	// hour = "00";
	// } else if (hour.length() == 1) {
	// hour = "0" + hour;
	// }
	// dateSb.append(hour);
	// dateSb.append(":00:00");
	// return dateSb.toString();
	// }

	public Integer[] getConsommationAndDiffDate(Integer moduleID, String startDate, String endDate) {
		String useINDUS = PropertiesReader.getValue(ConfigEnum.USE_INDUSTRIALIZATION);
		if (moduleID == null || startDate == null || startDate.isEmpty() || endDate == null || endDate.isEmpty()) {
			return null;
		}
		Integer[] result = null;
		GetCSMDataAction csm = new GetCSMDataAction();
		if (FrontalKey.USE_INDUS_NO.equals(useINDUS)) {
			// USE CSM

			List<AgregatCSM> agregatLst = csm.getListAgregatByModule(moduleID, startDate, endDate, "0", "24");

			if (agregatLst.size() > 1) {
				result = new Integer[2];
				result[0] = Math.abs(agregatLst.get(0).getIndex() - agregatLst.get(agregatLst.size() - 1).getIndex());
				result[1] = LabUtils.different2Date(agregatLst.get(0).getDateHeureAgregat(),
						agregatLst.get(1).getDateHeureAgregat(), FrontalKey.DATE_FORMAT_DAY_1);
			}
		} else {
			// USE CSM
			GetIndusDataAction indus = new GetIndusDataAction();
			ModuleCSM module = csm.getModuleCSMByID(moduleID);
			result = indus.getIndusConsommationAndDiffDate(moduleID, startDate, endDate, module.getType());
		}

		return result;
	}

	public Integer getLastTemperatureByTime(Integer moduleID, String startDate, String endDate, String startHour,
			String endHour) {
		String useINDUS = PropertiesReader.getValue(ConfigEnum.USE_INDUSTRIALIZATION);
		if (moduleID == null || startDate == null || startDate.isEmpty() || endDate == null || endDate.isEmpty()) {
			return null;
		}
		if (startHour == null) {
			startHour = "01";
		}
		if (endHour == null) {
			endHour = "24";
		}
		if (FrontalKey.USE_INDUS_NO.equals(useINDUS)) {
			// USE CSM
			GetCSMDataAction csm = new GetCSMDataAction();

			List<AgregatCSM> agregatLst = csm.getListAgregatByModule(moduleID, startDate, endDate, startHour, endHour);
			Integer result = null;
			if (agregatLst.size() > 0) {
				result = agregatLst.get(0).getTemperature();
			}
			if (agregatLst.size() > 1) {
				if (agregatLst.get(1).getDateHeureAgregat().compareTo(agregatLst.get(1).getDateHeureAgregat()) > 0) {
					result = agregatLst.get(1).getTemperature();
				} else if (agregatLst.get(1).getDateHeureAgregat()
						.compareTo(agregatLst.get(1).getDateHeureAgregat()) == 0) {
					if (agregatLst.get(1).getHeureAgregat().compareTo(agregatLst.get(1).getHeureAgregat()) > 0) {
						result = agregatLst.get(1).getTemperature();
					}
				}
			}
			return result;
		} else {
			// USE CSM
			GetIndusDataAction indus = new GetIndusDataAction();
			List<Integer> moduleConsommationLst = new ArrayList<Integer>();
			moduleConsommationLst.add(moduleID);
			Map<Integer, Map<String, TemperaturePoint>> mapTemp = indus.getGroupPriosByTime(moduleConsommationLst,
					startDate + "-" + startHour, endDate + "-" + endHour, FrontalKey.TYPE_TIME_HOUR);

			if (mapTemp != null) {
				return GetIndusDataAction.getLastestTemperature(String.valueOf(moduleID), mapTemp);
			} else {
				return null;
			}
		}
	}

	/**
	 * Get consommation of module by season and time
	 * 
	 * @param moduleID
	 * @param startDate
	 * @param endDate
	 * @param startHour
	 * @param endHour
	 * @return
	 */
	public Map<Integer, Integer> getMapConsommationByTime(List<Integer> moduleLst, String startDate, String endDate) {
		String useINDUS = PropertiesReader.getValue(ConfigEnum.USE_INDUSTRIALIZATION);
		if (moduleLst == null || startDate == null || startDate.isEmpty() || endDate == null || endDate.isEmpty()) {
			return null;
		}
		// startDate = "20150401";
		// endDate = "20150402";
		GetCSMDataAction csm = new GetCSMDataAction();
		// ModuleCSM module = csm.getModuleCSMByID(moduleId);
		if (FrontalKey.USE_INDUS_NO.equals(useINDUS)) {
			// USE CSM
			return csm.getConsommationByTime(moduleLst, startDate, endDate, "01", "24");
		} else {
			// USE CSM
			Map<Integer, Integer> rs = new HashMap<Integer, Integer>();
			GetIndusDataAction indus = new GetIndusDataAction();
			rs = indus.getMapIndusConsommation(moduleLst, startDate, endDate);
/*			for (Integer moduleId : moduleLst) {
				Integer consommation = indus.getIndusConsommation(moduleId, startDate, endDate);
				if (consommation != null) {
					rs.put(moduleId, consommation);
				}
			}*/

			return rs;
		}
	}

	public Integer getTemperatureByModuleLownest(Integer moduleID, String startDate, String endDate, String startHour,
			String endHour) {
		String useINDUS = PropertiesReader.getValue(ConfigEnum.USE_INDUSTRIALIZATION);
		if (moduleID == null || startDate == null || startDate.isEmpty() || endDate == null || endDate.isEmpty()) {
			return null;
		}
		if (startHour == null) {
			startHour = "1";
		}
		if (endHour == null) {
			endHour = "24";
		}
		GetCSMDataAction csm = new GetCSMDataAction();
		if (FrontalKey.USE_INDUS_NO.equals(useINDUS)) {
			// USE CSM
			Integer result = null;

			List<AgregatCSM> agregatLst = csm.getAllAgregatByModule(moduleID, startDate, endDate, "00", "24");
			if (agregatLst.size() > 0) {
				result = agregatLst.get(0).getTemperature();
			}
			if (agregatLst.size() > 1) {
				result = agregatLst.get(0).getTemperature();
				for (AgregatCSM agregatCSM : agregatLst) {
					if (agregatCSM.getTemperature() < result) {
						result = agregatCSM.getTemperature();
					}
				}
			}
			return result;
		} else {
			// USE CSM
			GetIndusDataAction indus = new GetIndusDataAction();
			List<Integer> moduleConsommationLst = new ArrayList<Integer>();
			moduleConsommationLst.add(moduleID);
			Map<Integer, Map<String, TemperaturePoint>> mapTemp = indus.getGroupPriosByTime(moduleConsommationLst,
					startDate + "-" + startHour, endDate + "-" + endHour, FrontalKey.TYPE_TIME_HOUR);

			if (mapTemp != null) {
				return GetIndusDataAction.getLowestTemperature(String.valueOf(moduleID), mapTemp);
			} else {
				return null;
			}
		}
	}

	/**
	 * Return many point tempreature by time
	 *
	 * @return obj[0] Date, obj[]
	 * 
	 */
	public List<Object[]> getAllTemperatureByTime(Integer moduleID, String startDate, String endDate) {
		String useINDUS = PropertiesReader.getValue(ConfigEnum.USE_INDUSTRIALIZATION);

		List<Object[]> lst = new ArrayList<Object[]>();
		GetCSMDataAction csm = new GetCSMDataAction();
		if (FrontalKey.USE_INDUS_NO.equals(useINDUS)) {

			List<AgregatCSM> agregatLst = csm.getAllAgregatByModule(moduleID, startDate, endDate, "00", "24");
			for (AgregatCSM agregat : agregatLst) {
				String date = agregat.getDateHeureAgregat() + agregat.getHeureAgregat();
				int temperature = agregat.getTemperature();
				lst.add(new Object[] { LabUtils.convertDateByFormat(date, FrontalKey.DATE_FORMAT_DAY_3).getTime(),
						temperature });
			}
		} else {

			GetIndusDataAction indus = new GetIndusDataAction();
			List<Integer> moduleConsommationLst = new ArrayList<Integer>();
			Map<Integer, Map<String, TemperaturePoint>> mapTemperature = indus.getGroupPriosByTime(
					moduleConsommationLst, startDate + "-00", endDate + "-23", FrontalKey.TYPE_TIME_HOUR);

			TreeMap<String, TemperaturePoint> tree = null;
			if (mapTemperature.containsKey(moduleID)) {
				tree = (TreeMap<String, TemperaturePoint>) mapTemperature.get(moduleID);
			} else {
				return lst;
			}
			for (Map.Entry<String, TemperaturePoint> entry : tree.entrySet()) {
				String key = entry.getKey();
				Date date = LabUtils.convertDateByFormat(key, FrontalKey.DATE_FORMAT_DAY_3);
				TemperaturePoint point = entry.getValue();
				lst.add(new Object[] { date.getTime(), point.getAvgTemp() });

			}
		}

		return lst;
	}

	/**
	 * 
	 * @param moduleID
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Map<String, TemperaturePoint> getMapTemperatureByTime(Integer moduleID, String startDate, String endDate,
			int typeTime) {
		String useINDUS = PropertiesReader.getValue(ConfigEnum.USE_INDUSTRIALIZATION);

		Map<String, TemperaturePoint> map = new TreeMap<String, TemperaturePoint>();
		GetCSMDataAction csm = new GetCSMDataAction();
		if (FrontalKey.USE_INDUS_NO.equals(useINDUS)) {

			List<AgregatCSM> agregatLst = csm.getAllAgregatByModule(moduleID, startDate, endDate, "00", "24");
			for (AgregatCSM agregat : agregatLst) {
				String key = null;
				if (typeTime == FrontalKey.TYPE_TIME_HOUR) {
					key = agregat.getDateHeureAgregat() + agregat.getHeureAgregat();
				} else if (typeTime == FrontalKey.TYPE_TIME_DAY) {
					key = agregat.getDateHeureAgregat() + "00";
				} else {
					key = agregat.getDateHeureAgregat().substring(0, 6) + "0100";
				}
				int temperature = agregat.getTemperature();
				addTemperaturePoint(key, temperature, map);
			}
		} else {

			GetIndusDataAction indus = new GetIndusDataAction();
			List<Integer> moduleConsommationLst = new ArrayList<Integer>();
			moduleConsommationLst.add(moduleID);
			Map<Integer, Map<String, TemperaturePoint>> mapTemperature = indus
					.getGroupPriosByTime(moduleConsommationLst, startDate, endDate, typeTime);

			if (mapTemperature != null && mapTemperature.containsKey(moduleID)) {
				map = (TreeMap<String, TemperaturePoint>) mapTemperature.get(moduleID);
			}
		}

		return map;
	}

	private void addTemperaturePoint(String key, Integer value, Map<String, TemperaturePoint> map) {
		TemperaturePoint point = null;
		if (key == null || map == null) {
			return;
		}
		if (map.containsKey(key)) {
			point = map.get(key);
		} else {
			point = new TemperaturePoint();
		}
		if (value != null) {
			if (point.getCountTemp() == null) {
				point.setCountTemp(1);
				point.setMaxTemp(value);
				point.setMinTemp(value);
				point.setSumTemp(value);
			} else {
				point.setCountTemp(point.getCountTemp() + 1);
				if (point.getMaxTemp() < value) {
					point.setMaxTemp(value);
				}
				if (point.getMinTemp() > value) {
					point.setMinTemp(value);
				}
				point.setSumTemp(point.getSumTemp() + value);
			}
		}
		map.put(key, point);
	}

	public Map<Long, Integer> getAllConsommationByTime(String moduleStr, String startDate, String endDate,
			int typeTime) {
		Map<Long, Integer> mapRs = new TreeMap<Long, Integer>();
		if (CalculateUtils.isContainCalculate(moduleStr)) {
			List<Integer> idLst = CalculateUtils.getIDFromStr(moduleStr);
			Map<String, Map<Integer, Integer>> mapData = new TreeMap<String, Map<Integer, Integer>>();
			for (Integer moduleID : idLst) {
				Map<String, Integer> map = getConsommationByMap(moduleID, startDate, endDate, typeTime);

				if (map != null) {
					for (Map.Entry<String, Integer> entry : map.entrySet()) {
						String keyTime = entry.getKey();
						Integer value = entry.getValue();
						Map<Integer, Integer> mapConsomation = new HashMap<Integer, Integer>();
						if (mapData.containsKey(keyTime)) {
							mapConsomation = mapData.get(keyTime);
						}
						Integer consommation = value;
						mapConsomation.put(moduleID, consommation);
						mapData.put(keyTime, mapConsomation);
					}
				}
			}

			for (Map.Entry<String, Map<Integer, Integer>> entry : mapData.entrySet()) {
				Map<Integer, Integer> mapConsommation = entry.getValue();

				Integer consommation = CalculateUtils.getConsommation(moduleStr, mapConsommation);
				mapRs.put(LabUtils.convertDateByFormat(entry.getKey(), FrontalKey.DATE_FORMAT_DAY_3).getTime(),
						consommation);
			}
		} else {
			Map<String, Integer> map = getConsommationByMap(Integer.parseInt(moduleStr), startDate, endDate, typeTime);
			if (map != null) {
				for (Map.Entry<String, Integer> entry : map.entrySet()) {
					mapRs.put(
							LabUtils.convertDateByFormat(entry.getKey(), FrontalKey.DATE_FORMAT_DAY_3).getTime(),
							entry.getValue());
				}
			}
		}

		return mapRs;
	}

	public Map<String, Map<Integer, Integer>> getAllModuleByTime(List<Integer> moduleIdLst, String startDate,
			String endDate, int typeTime) {
		GetIndusDataAction indus = new GetIndusDataAction();

		Map<Integer, Map<String, Integer>> mapByModule = indus.getGroupTramesByTime(moduleIdLst, startDate, endDate,
				typeTime);
		Map<String, Map<Integer, Integer>> rsMap = new TreeMap<String, Map<Integer, Integer>>();
		if (mapByModule != null) {
			for (Map.Entry<Integer, Map<String, Integer>> entry : mapByModule.entrySet()) {
				Integer moduleKey = entry.getKey();
				Map<String, Integer> timeValue = entry.getValue();
				for (Map.Entry<String, Integer> entryTime : timeValue.entrySet()) {
					String timeKey = entryTime.getKey();
					Integer value = entryTime.getValue();
					rsMap = LabUtils.putValueToMapMap(rsMap, timeKey, moduleKey, value);
				}
			}
		}
		return rsMap;

	}

	private Map<String, Integer> getConsommationByMap(Integer moduleID, String startDate, String endDate,
			int typeTime) {
		Map<String, Integer> map = null;
		String useINDUS = PropertiesReader.getValue(ConfigEnum.USE_INDUSTRIALIZATION);
		GetCSMDataAction csm = new GetCSMDataAction();
		if (FrontalKey.USE_INDUS_NO.equals(useINDUS)) {
			List<AgregatCSM> agregatLst = csm.getAllAgregatByModule(moduleID, startDate, endDate, "00", "24");
			map = csm.groupAgregatByTime(agregatLst, typeTime);
		} else {

			GetIndusDataAction indus = new GetIndusDataAction();
			List<Integer> moduleConsommationLst = new ArrayList<Integer>();
			moduleConsommationLst.add(moduleID);
			Map<Integer, Map<String, Integer>> mapTemperature = indus.getGroupTramesByTime(moduleConsommationLst,
					startDate + "-00", endDate + "-23", typeTime);

			if (mapTemperature.containsKey(moduleID)) {
				map = (TreeMap<String, Integer>) mapTemperature.get(moduleID);
			}
		}

		return map;
	}

	/**
	 * Get map consommation by time key: yyyMMddHH value: consommation
	 * 
	 * @param moduleID
	 * @param startDate
	 * @param endDate
	 * @param typeTime
	 * @return
	 */
	public Map<String, Integer> getMapTimeOfConsommation(String moduleStr, String startDate, String endDate,
			int typeTime) {
		List<Integer> idLst = CalculateUtils.getIDFromStr(moduleStr);
		Map<String, Map<Integer, Integer>> mapData = new TreeMap<String, Map<Integer, Integer>>();
		GetIndusDataAction indus = new GetIndusDataAction();
		Map<Integer, Map<String, Integer>> mapIndus = indus.getGroupTramesByTime(idLst, startDate, endDate, typeTime);
		if (mapIndus != null) {

			for (Integer moduleID : idLst) {
				Map<String, Integer> map = mapIndus.get(moduleID);
				if (map != null) {
					for (Map.Entry<String, Integer> entry : map.entrySet()) {
						String keyTime = entry.getKey();
						Integer value = entry.getValue();
						Map<Integer, Integer> mapConsomation = new HashMap<Integer, Integer>();
						if (mapData.containsKey(keyTime)) {
							mapConsomation = mapData.get(keyTime);
						}
						Integer consommation = value;
						mapConsomation.put(moduleID, consommation);
						mapData.put(keyTime, mapConsomation);
					}
				}
			}
		}
		Map<String, Integer> map = new TreeMap<String, Integer>();
		for (Map.Entry<String, Map<Integer, Integer>> entry : mapData.entrySet()) {
			String keyTime = entry.getKey();
			Map<Integer, Integer> mapConsommation = entry.getValue();

			Integer tmp = CalculateUtils.getConsommation(moduleStr, mapConsommation);
			if (tmp != null) {
				map.put(keyTime, tmp);
			}
		}

		return map;
	}

	/**
	 * Get map consommation by time key: yyyMMddHH value: consommation
	 * 
	 * @param moduleID
	 * @param startDate
	 * @param endDate
	 * @param typeTime
	 * @return
	 */
	public Map<String, Integer> getMapTimeOfPulse(String moduleStr, String startDate, String endDate, int typeTime) {
		List<Integer> idLst = CalculateUtils.getIDFromStr(moduleStr);
		Map<String, Map<Integer, Integer>> mapData = new TreeMap<String, Map<Integer, Integer>>();
		GetIndusDataAction indus = new GetIndusDataAction();
		Map<Integer, Map<String, Integer>> mapIndus = indus.getPriosPulse(idLst, startDate, endDate, typeTime);
		if (mapIndus != null) {
			for (Integer moduleID : idLst) {
				Map<String, Integer> map = mapIndus.get(moduleID);
				if (map != null) {
					for (Map.Entry<String, Integer> entry : map.entrySet()) {
						String keyTime = entry.getKey();
						Integer value = entry.getValue();
						Map<Integer, Integer> mapConsomation = new HashMap<Integer, Integer>();
						if (mapData.containsKey(keyTime)) {
							mapConsomation = mapData.get(keyTime);
						}
						Integer consommation = value;
						mapConsomation.put(moduleID, consommation);
						mapData.put(keyTime, mapConsomation);
					}
				}
			}

		}
		Map<String, Integer> map = new TreeMap<String, Integer>();
		for (Map.Entry<String, Map<Integer, Integer>> entry : mapData.entrySet()) {
			String keyTime = entry.getKey();
			Map<Integer, Integer> mapConsommation = entry.getValue();

			Integer tmp = CalculateUtils.getConsommation(moduleStr, mapConsommation);
			if (tmp != null) {
				map.put(keyTime, tmp);
			}
		}

		return map;
	}

}
