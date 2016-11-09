package com.ifi.common.ws;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ifi.common.bean.TemperaturePoint;
import com.ifi.common.csm.ModuleCSM;
import com.ifi.common.threadPoolExcutor.ExcutorService;
import com.ifi.common.threadPoolExcutor.GetLab011ResidentThread;
import com.ifi.common.util.ConfigEnum;
import com.ifi.common.util.FrontalKey;
import com.ifi.common.util.LabUtils;
import com.ifi.common.util.PropertiesLoader;
import com.ifi.common.util.PropertiesReader;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class GetIndusDataAction {

	private ExcutorService excutorService;
	// private static final String INDUS_GETWDTRAMES =
	// "/indus/getWDTrames/gateway/{0}/datecour/{1}/{2}";
	// private static final String INDUS_GETWDTRAMES_SUBMODULE =
	// "/indus/getWDTrames/gateway/{0}/datecour/{1}/{2}/all_day/suffixes/{3}";
	// private static final String INDUS_GETPRIOSTEMP =
	// "/indus/getPriosTemp/period/{0}/{1}/all_day/module/{2}";
	private static final String INDUS_GETGROUPPRIOSTEMP = "/indus/getGroupPriosByTime/{0}/{1}/{2}/{3}";
	private static final String INDUS_GETGROUPTRAMESBYTIME = "/indus/getGroupTramesByTime/{0}/{1}/{2}/{3}";
	private static final String INDUS_GETPRIOSPULSE = "/indus/getPriosPulse/period/{0}/{1}/all_day/modules/{2}";
	private static final String INDUS_GETPRECALPULSEBYTYPE = "/indus/getPreCalPulseByType/{0}/{1}/{2}/{3}";
	private static final String INDUS_GETAVGTEMPERATUREBYTIME = "/indus/getAvgTemperatureByTime/{0}/{1}/{2}";
	private static final String INDUS_GETLASTTEMPERATUREBYTIME = "/indus/getLastTemperatureByTime/{0}/{1}/{2}";
	private static final String INDUS_GETLASTDAYHASDATA = "/indus/getLastDayHasData/{0}/{1}";

	// private static final String PRIOSTEMP_FORMAT = "yyyyMMddHHmmss";
	// private static final String DATECOUR_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
	// private static final String DATEPA_FORMAT = "yyyyMMdd-HHmmss";
	private static final String START_HOUR = "00";
	private static final String END_HOUR = "23";
	private static Client client;
	private static final Logger LOGGER = LoggerFactory.getLogger(GetIndusDataAction.class);

	public GetIndusDataAction() {
//		System.setProperty("http.proxyHost", "10.225.1.1");
//		System.setProperty("http.proxyPort", "3128");
		if (client == null) {
			client = Client.create(new DefaultClientConfig());
		}
		excutorService = new ExcutorService();
	}

	private static Map<String, String> mapSubmodule = new HashMap<String, String>();

	static {
		String subModuleStr = PropertiesReader.getValue(ConfigEnum.MAP_SUB_MODULE);
		if (subModuleStr != null) {
			String[] item = subModuleStr.trim().split(",");
			for (int i = 0; i < item.length - 1; i++) {
				String[] temp = item[i].split(":");
				mapSubmodule.put(temp[0], temp[1]);
			}
		}
	}

	/**
	 * Get pulse( water module)
	 * 
	 * @param moduleIDLst
	 * @param fromDate
	 * @param toDate
	 * @param typeTime
	 * @return
	 */
	public Map<Integer, Map<String, Integer>> getPriosPulse(List<Integer> moduleIDLst, String fromDate, String toDate,
			int typeTime) {
		if (moduleIDLst == null || moduleIDLst.size() == 0 || fromDate == null || toDate == null) {
			LOGGER.error("[Error]moduleID:" + moduleIDLst + "-fromDate:" + fromDate + "-toDate:" + toDate + "-typeTime:"
					+ typeTime);
			return null;
		}

		// String fromDateStr = LabUtils.convertDateByFormat(fromDate,
		// FrontalKey.DATE_FORMAT_DAY_4);
		// String toDateStr = LabUtils.convertDateByFormat(toDate,
		// FrontalKey.DATE_FORMAT_DAY_4);

		String modules = getGateWays(moduleIDLst);
		// String indusTypeTime = getTypeTimeIndus(typeTime);
		String dateFormat = null;
		if (typeTime == FrontalKey.TYPE_TIME_HOUR) {
			dateFormat = FrontalKey.TIMESTAMP_HOUR;
		} else if (typeTime == FrontalKey.TYPE_TIME_DAY) {
			dateFormat = FrontalKey.TIMESTAMP_DAY;
		} else if (typeTime == FrontalKey.TYPE_TIME_MONTH) {
			dateFormat = FrontalKey.TIMESTAMP_MONTH;
		}
		Map<Integer, Map<String, Integer>> map = new HashMap<Integer, Map<String, Integer>>();
		GetCSMDataAction csm = new GetCSMDataAction();
		try {
			Object[] values = { fromDate, toDate, modules };
			String link = MessageFormat.format(INDUS_GETPRIOSPULSE, values);
			String uriIndus = PropertiesReader.getValue(ConfigEnum.URI_PATH_INDUSTRIALIZATION) + link;
			WebResource webResource = client.resource(uriIndus);

			ClientResponse response = webResource.type("application/json").get(ClientResponse.class);

			if (response.getStatus() != 200) {
				return null;
			}
			String output = response.getEntity(String.class);
			if (!output.isEmpty() || output != null) {
				JSONParser json = new JSONParser();
				JSONArray jsonArray = (JSONArray) json.parse(output);
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject obj = (JSONObject) jsonArray.get(i);
					String moduleNumber = (String) obj.get("moduleid");
					Integer moduleID = csm.getModuleCSMIDByNumber(moduleNumber);
					// Get tong sumchampsa
					Map<String, PulseIndus> treeMap = new TreeMap<String, PulseIndus>();
					JSONArray pulseArray = (JSONArray) obj.get("listPriosPulse");
					if (pulseArray != null) {
						for (int j = 0; j < pulseArray.size(); j++) {
							JSONObject pulseObj = (JSONObject) pulseArray.get(j);
							String timeStamp = (String) ((JSONObject) pulseObj.get("collectionPriosPulseKey"))
									.get("timestamp");
							Integer champsa = Integer.parseInt((String) pulseObj.get("champsa"));
							if (champsa != null) {
								String keyTime = "0";
								if (dateFormat != null) {
									keyTime = LabUtils.convertDateByFormat(
											LabUtils.convertDateByFormat(timeStamp, dateFormat),
											FrontalKey.DATE_FORMAT_DAY_3);
								}
								PulseIndus pulseIndus = null;
								if (treeMap.containsKey(keyTime)) {
									pulseIndus = treeMap.get(keyTime);
								} else {
									pulseIndus = new PulseIndus();
								}
								if (pulseIndus.getMaxDate() == null
										|| pulseIndus.getMaxDate().compareTo(timeStamp) < 0) {
									pulseIndus.setMaxDate(timeStamp);
									pulseIndus.setMaxValue(champsa);
								}
								if (pulseIndus.getMinDate() == null
										|| pulseIndus.getMinDate().compareTo(timeStamp) > 0) {
									pulseIndus.setMinDate(timeStamp);
									pulseIndus.setMinValue(champsa);
								}
								treeMap.put(keyTime, pulseIndus);
							}
						}
					}
					Integer before = null;
					Map<String, Integer> treeRS = new TreeMap<String, Integer>();
					if (treeMap.size() > 0) {
						if (treeMap.size() == 1) {
							Entry<String, PulseIndus> entry = treeMap.entrySet().iterator().next();
							PulseIndus pulseIndus = entry.getValue();
							if (pulseIndus.getMaxValue() != null && pulseIndus.getMinValue() != null) {
								treeRS.put(entry.getKey(), pulseIndus.getMaxValue() - pulseIndus.getMinValue());
							}
						} else {
							for (Map.Entry<String, PulseIndus> entry : treeMap.entrySet()) {
								PulseIndus pulseIndus = entry.getValue();
								if (before != null && pulseIndus.getMaxValue() != null) {
									treeRS.put(entry.getKey(), pulseIndus.getMaxValue() - before);
								}
								if (pulseIndus.getMaxValue() != null) {
									before = pulseIndus.getMaxValue();
								}
							}
						}
						map.put(moduleID, treeRS);
					}
				}
			}
		} catch (ParseException e) {
			LOGGER.equals(e.getMessage());
			return null;
		}
		return map;
	}

	/**
	 * Get Sum consommation in time
	 * 
	 * @param moduleID
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	Integer getIndusConsommation(Integer moduleID, String fromDate, String toDate) {
		List<Integer> lst = new ArrayList<Integer>();
		lst.add(moduleID);
		Map<Integer, Map<String, Integer>> map = getGroupTramesByTime(lst, fromDate, toDate, FrontalKey.TYPE_TIME_ALL);
		if (map != null && map.containsKey(moduleID)) {
			Map<String, Integer> mapTime = map.get(moduleID);
			if (mapTime.size() > 0) {
				return map.get(moduleID).values().iterator().next();
			}
		}
		return null;
	}

	Map<Integer, Integer> getMapIndusConsommation(List<Integer> moduleIdLst, String fromDate, String toDate) {
		Map<Integer, Map<String, Integer>> map = getGroupTramesByTime(moduleIdLst, fromDate, toDate,
				FrontalKey.TYPE_TIME_ALL);
		Map<Integer, Integer> mapConsommation = new HashMap<Integer, Integer>();
		if (map == null) {
			return null;
		}
		for (Integer moduleId : moduleIdLst) {
			if (map.containsKey(moduleId)) {
				Map<String, Integer> mapTime = map.get(moduleId);
				Integer consommation = 0;
				if (mapTime.size() > 0) {
					consommation = map.get(moduleId).values().iterator().next();
				}
				mapConsommation.put(moduleId, consommation);
			}
		}
		return mapConsommation;
	}

	/**
	 * Round temperature number 2.5 -> 2 2.6 -> 3
	 * 
	 * @return
	 */
	private Integer getTemperatureRound(Double number) throws NumberFormatException {
		if (number == null) {
			return null;
		}
		Integer rs = null;
		if (number % 1 > 0.5) {
			rs = number.intValue() + 1;
		} else {
			rs = number.intValue();
		}
		return rs;
	}

	/**
	 * Get consommation and number day in range
	 * 
	 * @param moduleID
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	Integer[] getIndusConsommationAndDiffDate(Integer moduleID, String fromDate, String toDate, int type) {
		Integer[] rs = new Integer[2];
		Integer diffDate = 0;

		List<Integer> lst = new ArrayList<Integer>();
		lst.add(moduleID);
		GetIndusDataAction indus = new GetIndusDataAction();
		Map<Integer, Map<String, Integer>> map = indus.getGroupTramesByTime(lst, fromDate, toDate,
				FrontalKey.TYPE_TIME_HOUR);

		if (map.size() == 0) {
			return null;
		}
		TreeMap<String, Integer> tree = (TreeMap<String, Integer>) map.get(moduleID);
		if (tree == null || tree.size() == 0) {
			return null;
		}
		tree.firstEntry().getKey();
		tree.lastEntry().getKey();
		Integer consommation = getConsommationByMap(moduleID, map);
		diffDate = LabUtils.different2Date(tree.firstEntry().getKey(), tree.lastEntry().getKey(),
				FrontalKey.DATE_FORMAT_DAY_3);
		rs[0] = consommation;
		rs[1] = diffDate;
		return rs;
	}

	private Integer getConsommationByMap(Integer moduleID, Map<Integer, Map<String, Integer>> map) {
		if (map == null || map.size() == 0) {
			return null;
		} else {
			TreeMap<String, Integer> tree = (TreeMap<String, Integer>) map.get(moduleID);
			if (tree == null || tree.size() == 0) {
				return null;
			}
			Integer sum = 0;
			for (Map.Entry<String, Integer> entry : tree.entrySet()) {
				if (entry.getValue() != null) {
					sum += entry.getValue();
				}
			}
			return sum;
		}
	}

	public Date getLastDayHasData(List<String> moduleLst) {
		if (moduleLst == null || moduleLst.isEmpty()) {
			LOGGER.error("[Error]moduleID:" + moduleLst);
			return null;
		}
		StringBuilder moduleSB = new StringBuilder();
		int sizeMinus = moduleLst.size() - 1;
		for (int i = 0; i < sizeMinus; i++) {
			moduleSB.append(moduleLst.get(i));
			moduleSB.append(",");
		}
		moduleSB.append(moduleLst.get(sizeMinus));
		Object[] values = { moduleSB.toString(), 10 };
		String link = MessageFormat.format(INDUS_GETLASTDAYHASDATA, values);
		String uriIndus = PropertiesReader.getValue(ConfigEnum.URI_PATH_INDUSTRIALIZATION) + link;
		WebResource webResource = client.resource(uriIndus);

		ClientResponse response = webResource.type("application/json").get(ClientResponse.class);

		if (response.getStatus() != 200) {
			return null;
		}
		String output = response.getEntity(String.class);
		if (output != null && !output.isEmpty()) {
			return LabUtils.convertDateByFormat(output, FrontalKey.DATE_FORMAT_DAY_1);
		} else {
			return null;
		}
	}

	public Date getLastDayHasData(String module) {
		Object[] values = { module, 10 };
		String link = MessageFormat.format(INDUS_GETLASTDAYHASDATA, values);
		String uriIndus = PropertiesReader.getValue(ConfigEnum.URI_PATH_INDUSTRIALIZATION) + link;
		WebResource webResource = client.resource(uriIndus);

		ClientResponse response = webResource.type("application/json").get(ClientResponse.class);

		if (response.getStatus() != 200) {
			return null;
		}
		String output = response.getEntity(String.class);
		if (output != null && !output.isEmpty()) {
			return LabUtils.convertDateByFormat(output, FrontalKey.DATE_FORMAT_DAY_1);
		} else {
			return null;
		}
	}

	public Map<String, Double> getWS_AvgTemperatureByTime(String module, String fromDate, String toDate) {
		Map<String, Double> map = new HashMap<String, Double>();
		if (module == null || module.isEmpty() || fromDate == null || toDate == null) {
			LOGGER.error("[Error]moduleID:" + module + "-fromDate:" + fromDate + "-toDate:" + toDate);
			return map;
		}

		Object[] values = { module.toString(), fromDate, toDate };
		String link = MessageFormat.format(INDUS_GETAVGTEMPERATUREBYTIME, values);
		String uriIndus = PropertiesReader.getValue(ConfigEnum.URI_PATH_INDUSTRIALIZATION) + link;
		WebResource webResource = client.resource(uriIndus);

		ClientResponse response = webResource.type("application/json").get(ClientResponse.class);

		if (response.getStatus() != 200) {
			return null;
		}
		String output = response.getEntity(String.class);

		TypeReference<HashMap<String, Double>> typeRef = new TypeReference<HashMap<String, Double>>() {
		};

		ObjectMapper mapper = new ObjectMapper();
		try {
			map = mapper.readValue(output, typeRef);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}

	public Map<String, Double> getLastTemperatureByTime(List<String> moduleLst, String fromDate, String toDate) {
		Map<String, Double> map = new HashMap<String, Double>();

		if (moduleLst == null || moduleLst.isEmpty() || fromDate == null || toDate == null) {
			LOGGER.error("[Error]moduleID:" + moduleLst + "-fromDate:" + fromDate + "-toDate:" + toDate);
			return map;
		}

		long timeOutExpiredMs = 0;
		int sizeMinus = moduleLst.size();
		List<String> lstModule = null;
		int last;
		int step = 0;
		int quotient = 0;
		while (step < sizeMinus) {
			if (excutorService.getExecutorPool().getActiveCount() <= PropertiesLoader.threadActiveCount) {
				last = step + PropertiesLoader.threadConcurentModuleStep;
				if (last > sizeMinus) {
					last = sizeMinus;
				}
				lstModule = new ArrayList<String>();
				for (int k = step; k < last; k++) {
					lstModule.add(moduleLst.get(k));
				}
				Thread getLab011ResidentThread = new GetLab011ResidentThread(excutorService, 1, lstModule, fromDate,
						toDate, null, FrontalKey.THREAD_TYPE_LAST_TEMPEATURE_BYTIME);
				excutorService.excute(getLab011ResidentThread);
				step = last;
				quotient++;
			}
		}

		timeOutExpiredMs = System.currentTimeMillis() + PropertiesLoader.threadTimeOutWaitRestAPI * 1000;
		while (excutorService.getLstThread().size() < quotient) {
			try {
				if (System.currentTimeMillis() >= timeOutExpiredMs) {
					LOGGER.error("break when wait get data from RestAPI service ! timeOut");
					return null;
				}
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		for (int i = 0; i < quotient; i++) {
			GetLab011ResidentThread getLab011ResidentThread = (GetLab011ResidentThread) excutorService.getLstThread()
					.get(i);
			if (getLab011ResidentThread.getMapTmp() != null) {
				map.putAll(getLab011ResidentThread.getMapTmp());
			} else {
				LOGGER.error("error get Rest Service is null");
			}
		}
		excutorService.removeArrayThread();
		return map;
	}

	public Map<String, Double> getWS_LastTemperatureByTime(List<String> moduleLst, String fromDate, String toDate) {
		Map<String, Double> map = new HashMap<String, Double>();
		if (moduleLst == null || moduleLst.isEmpty() || fromDate == null || toDate == null) {
			LOGGER.error("[Error]moduleID:" + moduleLst + "-fromDate:" + fromDate + "-toDate:" + toDate);
			return map;
		}
		StringBuilder moduleSB = new StringBuilder();
		int sizeMinus = moduleLst.size() - 1;
		for (int i = 0; i < sizeMinus; i++) {
			moduleSB.append(moduleLst.get(i));
			moduleSB.append(",");
		}
		moduleSB.append(moduleLst.get(sizeMinus));
		Object[] values = { moduleSB.toString(), fromDate, toDate };
		String link = MessageFormat.format(INDUS_GETLASTTEMPERATUREBYTIME, values);
		String uriIndus = PropertiesReader.getValue(ConfigEnum.URI_PATH_INDUSTRIALIZATION) + link;
		WebResource webResource = client.resource(uriIndus);

		ClientResponse response = webResource.type("application/json").get(ClientResponse.class);

		if (response.getStatus() != 200) {
			return null;
		}
		String output = response.getEntity(String.class);

		TypeReference<HashMap<String, Double>> typeRef = new TypeReference<HashMap<String, Double>>() {
		};

		ObjectMapper mapper = new ObjectMapper();
		try {
			map = mapper.readValue(output, typeRef);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}

	public Map<String, Map<String, Double>> getPreCalPulseByType(List<String> moduleLst, String fromDate, String toDate,
			int typeTime) {
		Map<String, Map<String, Double>> map = new HashMap<String, Map<String, Double>>();
		if (moduleLst == null || moduleLst.isEmpty() || fromDate == null || toDate == null) {
			LOGGER.error("[Error]moduleID:" + moduleLst + "-fromDate:" + fromDate + "-toDate:" + toDate + "-typeTime:"
					+ typeTime);
			return map;
		}

		long timeOutExpiredMs = 0;
		List<String> lstModule = null;
		int sizeMinus = moduleLst.size();

		int quotient = 0;
		int last;
		int step = 0;
		while (step < sizeMinus) {
			if (excutorService.getExecutorPool().getActiveCount() <= PropertiesLoader.threadActiveCount) {
				last = step + PropertiesLoader.threadConcurentModuleStep;
				if (last > sizeMinus) {
					last = sizeMinus;
				}
				lstModule = new ArrayList<String>();
				for (int k = step; k < last; k++) {
					lstModule.add(moduleLst.get(k));
				}
				Thread getLab011ResidentThread = new GetLab011ResidentThread(excutorService, 1, lstModule, fromDate,
						toDate, typeTime, FrontalKey.THREAD_TYPE_PRECALPULSEBYTYPE);
				excutorService.excute(getLab011ResidentThread);
				step = last;
				quotient++;
			}
		}

		timeOutExpiredMs = System.currentTimeMillis() + PropertiesLoader.threadTimeOutWaitRestAPI * 1000;
		while (excutorService.getLstThread().size() < quotient) {
			try {
				if (System.currentTimeMillis() >= timeOutExpiredMs) {
					LOGGER.error("break when wait get data from RestAPI service ! timeOut");
					return null;
				}
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		for (int i = 0; i < quotient; i++) {

			GetLab011ResidentThread getLab011ResidentThread = (GetLab011ResidentThread) excutorService.getLstThread()
					.get(i);
			if (getLab011ResidentThread.getTmp() != null) {
				map.putAll(getLab011ResidentThread.getTmp());
			} else {
				LOGGER.error("error RestAPI service is null");
				return null;
			}
		}

		LOGGER.info("***********************************************" + "Client:" + client.toString()
				+ "***********************************************");
		LOGGER.info("*********************************************************************************************************************************************");
		LOGGER.info("excutorService.getExecutorPool().getMaximumPoolSize():"
				+ excutorService.getExecutorPool().getMaximumPoolSize());
		LOGGER.info("excutorService.getExecutorPool().getActiveCount():"
				+ excutorService.getExecutorPool().getActiveCount());
		LOGGER.info("excutorService.getExecutorPool().getCorePoolSize():"
				+ excutorService.getExecutorPool().getCorePoolSize());
		LOGGER.info("excutorService.getExecutorPool().getPoolSize():" + excutorService.getExecutorPool().getPoolSize());
		LOGGER.info("excutorService.getExecutorPool().getRejectedExecutionHandler():"
				+ excutorService.getExecutorPool().getRejectedExecutionHandler());
		LOGGER.info("excutorService.getExecutorPool().getCompletedTaskCount():"
				+ excutorService.getExecutorPool().getCompletedTaskCount());
		LOGGER.info(
				"excutorService.getExecutorPool().getTaskCount():" + excutorService.getExecutorPool().getTaskCount());
		LOGGER.info("excutorService.getLstThread().size():" + excutorService.getLstThread().size());
		LOGGER.info("*********************************************************************************************************************************************");
		LOGGER.info("*********************************************************************************************************************************************");
		excutorService.removeArrayThread();
		return map;
	}

	public Map<String, Map<String, Double>> getWS_PreCalPulseByType(String module, String fromDate, String toDate,
			int typeTime) {
		Map<String, Map<String, Double>> map = new HashMap<String, Map<String, Double>>();
		if (module == null || module.isEmpty() || fromDate == null || toDate == null) {
			LOGGER.error("[Error]moduleID:" + module + "-fromDate:" + fromDate + "-toDate:" + toDate + "-typeTime:"
					+ typeTime);
			return null;
		}
		String indusTypeTime = getTypeTimeIndus(typeTime);
		StringBuilder moduleSB = new StringBuilder();

		moduleSB.append(module);
		Object[] values = { moduleSB.toString(), fromDate, toDate, indusTypeTime };
		String link = MessageFormat.format(INDUS_GETPRECALPULSEBYTYPE, values);
		String uriIndus = PropertiesReader.getValue(ConfigEnum.URI_PATH_INDUSTRIALIZATION) + link;

		WebResource webResource = client.resource(uriIndus);

		ClientResponse response = webResource.type("application/json").get(ClientResponse.class);

		if (response.getStatus() != 200) {
			return null;
		}
		String output = response.getEntity(String.class);

		TypeReference<HashMap<String, TreeMap<String, Double>>> typeRef = new TypeReference<HashMap<String, TreeMap<String, Double>>>() {
		};

		ObjectMapper mapper = new ObjectMapper();
		try {
			map = mapper.readValue(output, typeRef);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}

	public Map<String, Map<String, Double>> getWS_PreCalPulseByType(List<String> moduleLst, String fromDate,
			String toDate, int typeTime) {
		Map<String, Map<String, Double>> map = new HashMap<String, Map<String, Double>>();
		if (moduleLst == null || moduleLst.isEmpty() || fromDate == null || toDate == null) {
			LOGGER.error("[Error]moduleID:" + moduleLst + "-fromDate:" + fromDate + "-toDate:" + toDate + "-typeTime:"
					+ typeTime);
			return map;
		}
		String indusTypeTime = getTypeTimeIndus(typeTime);
		StringBuilder moduleSB = new StringBuilder();
		int sizeMinus = moduleLst.size() - 1;
		for (int i = 0; i < sizeMinus; i++) {
			moduleSB.append(moduleLst.get(i));
			moduleSB.append(",");
		}
		moduleSB.append(moduleLst.get(sizeMinus));
		Object[] values = { moduleSB.toString(), fromDate, toDate, indusTypeTime };
		String link = MessageFormat.format(INDUS_GETPRECALPULSEBYTYPE, values);
		String uriIndus = PropertiesReader.getValue(ConfigEnum.URI_PATH_INDUSTRIALIZATION) + link;
		WebResource webResource = client.resource(uriIndus);

		ClientResponse response = webResource.type("application/json").get(ClientResponse.class);

		if (response.getStatus() != 200) {
			return null;
		}
		String output = response.getEntity(String.class);

		TypeReference<HashMap<String, TreeMap<String, Double>>> typeRef = new TypeReference<HashMap<String, TreeMap<String, Double>>>() {
		};

		ObjectMapper mapper = new ObjectMapper();
		try {
			map = mapper.readValue(output, typeRef);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, Map<String, TemperaturePoint>> getGroupPriosByTime(List<Integer> gatewayIdLst, String fromDate,
			String toDate, int typeTime) {
		if (gatewayIdLst == null || fromDate == null || toDate == null) {
			LOGGER.error("[Error]moduleID:" + gatewayIdLst + "-fromDate:" + fromDate + "-toDate:" + toDate
					+ "-typeTime:" + typeTime);
			return null;
		}
		String gateways = getGateWays(gatewayIdLst);
		String indusTypeTime = getTypeTimeIndus(typeTime);
		String fromHour = START_HOUR;
		String toHour = END_HOUR;
		Map<Integer, Map<String, TemperaturePoint>> map = new HashMap<Integer, Map<String, TemperaturePoint>>();
		GetCSMDataAction csm = new GetCSMDataAction();
		try {
			Object[] values = { gateways, fromDate + "-" + fromHour, toDate + "-" + toHour, indusTypeTime };
			String link = MessageFormat.format(INDUS_GETGROUPPRIOSTEMP, values);
			String uriIndus = PropertiesReader.getValue(ConfigEnum.URI_PATH_INDUSTRIALIZATION) + link;
			WebResource webResource = client.resource(uriIndus);

			ClientResponse response = webResource.type("application/json").get(ClientResponse.class);

			if (response.getStatus() != 200) {
				return null;
			}
			String output = response.getEntity(String.class);
			if (!output.isEmpty() || output != null) {
				JSONParser json = new JSONParser();
				JSONObject jObj = (JSONObject) json.parse(output);
				Set<String> setModule = (Set<String>) (jObj.keySet());
				Iterator<String> iteratorModule = setModule.iterator();
				while (iteratorModule.hasNext()) {
					String keyModule = iteratorModule.next();
					Integer moduleID = csm.getModuleCSMIDByNumber(keyModule);
					if (moduleID == null) {
						continue;
					}
					JSONObject keyDayLst = (JSONObject) jObj.get(keyModule);// 2014082709
																			// obj
					Set<String> setTime = (Set<String>) (keyDayLst.keySet());
					Iterator<String> iteratorTime = setTime.iterator();
					TreeMap<String, TemperaturePoint> treeTmp = new TreeMap<String, TemperaturePoint>();
					while (iteratorTime.hasNext()) {
						String keyTime = iteratorTime.next();
						JSONObject valueObj = (JSONObject) keyDayLst.get(keyTime);
						List<String> key = new ArrayList<String>(valueObj.keySet());
						TemperaturePoint temperaturePoint = new TemperaturePoint();
						temperaturePoint.setMinTemp(getTemperatureRound((Double) valueObj.get(key.get(0))));
						temperaturePoint.setMaxTemp(getTemperatureRound((Double) valueObj.get(key.get(1))));
						temperaturePoint.setAvgTemp(getTemperatureRound((Double) valueObj.get(key.get(2))));
						treeTmp.put(keyTime, temperaturePoint);
					}
					map.put(moduleID, treeTmp);
				}
			}
		} catch (ParseException e) {
			LOGGER.equals(e.getMessage());
			return null;
		}
		return map;
	}

	private String getGroupTrames(String gatewayTrame, String fromDate, String toDate, int typeTime) {
		if (gatewayTrame.length() > 1) {
			gatewayTrame = gatewayTrame.substring(0, gatewayTrame.length() - 2);
			String indusTypeTime = getTypeTimeIndus(typeTime);
			Object[] values = { gatewayTrame, fromDate, toDate, indusTypeTime };
			String link = MessageFormat.format(INDUS_GETGROUPTRAMESBYTIME, values);
			String uriIndus = PropertiesReader.getValue(ConfigEnum.URI_PATH_INDUSTRIALIZATION) + link;
			WebResource webResource = client.resource(uriIndus);

			ClientResponse response = webResource.type("application/json").get(ClientResponse.class);

			if (response.getStatus() != 200) {
				return null;
			}
			String output = response.getEntity(String.class);
			return output;
		}
		return null;
	}

	/**
	 * 
	 * @param gatewayIdLst
	 * @param fromDate
	 *            yyy
	 * @param toDate
	 * @param typeTime
	 * @return k1: module id, k2 Time, K3 consommation
	 */
	@SuppressWarnings("unchecked")
	public Map<Integer, Map<String, Integer>> getGroupTramesByTime(List<Integer> gatewayIdLst, String fromDate,
			String toDate, int typeTime) {
		if (gatewayIdLst == null || gatewayIdLst.size() == 0 || fromDate == null || toDate == null) {
			LOGGER.error("[Error]moduleID:" + gatewayIdLst + "-fromDate:" + fromDate + "-toDate:" + toDate
					+ "-typeTime:" + typeTime);
			return null;
		}

		String gatewayTrame = "";
		List<Integer> gatewayPulseIDLst = new ArrayList<Integer>();
		GetCSMDataAction csm = new GetCSMDataAction();
		for (Integer moduleId : gatewayIdLst) {
			ModuleCSM moduleCSM = csm.getModuleCSMByID(moduleId);
			if (moduleCSM != null) {
				String moduleNumber = moduleCSM.getNumberModule();
				if (moduleNumber.startsWith("02") || moduleNumber.startsWith("07")) {
					gatewayPulseIDLst.add(moduleId);
				} else {
					gatewayTrame += moduleNumber;
					gatewayTrame += ",";
				}
			}
		}
		Map<Integer, Map<String, Integer>> map = new HashMap<Integer, Map<String, Integer>>();
		try {
			String output = getGroupTrames(gatewayTrame, fromDate, toDate, typeTime);
			if (output != null && !output.isEmpty()) {
				JSONParser json = new JSONParser();
				JSONObject jObj = (JSONObject) json.parse(output);
				Set<String> setModule = (Set<String>) (jObj.keySet());
				Iterator<String> iteratorModule = setModule.iterator();
				while (iteratorModule.hasNext()) {
					String keyModule = iteratorModule.next();
					Integer moduleID = csm.getModuleCSMIDByNumber(keyModule);
					if (moduleID == null) {
						continue;
					}
					JSONObject moduleObj = (JSONObject) jObj.get(keyModule);// 2014082709
					Set<String> setTime = (Set<String>) (moduleObj.keySet());
					Iterator<String> iteratorTime = setTime.iterator();
					TreeMap<String, Integer> treeTmp = new TreeMap<String, Integer>();
					while (iteratorTime.hasNext()) {
						String keyTime = iteratorTime.next();
						Integer valueObj = ((Long) moduleObj.get(keyTime)).intValue();
						treeTmp.put(keyTime, valueObj);
					}
					map.put(moduleID, treeTmp);
				}
			}
		} catch (ParseException e) {
			LOGGER.equals(e.getMessage());
			return null;
		}

		if (gatewayPulseIDLst.size() > 0) {
			// gatewayPulse = gatewayPulse.substring(0, gatewayPulse.length() -
			// 2);
			// GetCSMDataAction csm = new GetCSMDataAction();

			// csm.getModuleCSMIDByNumber(numberModule)
			Map<Integer, Map<String, Integer>> mapTmp = getPriosPulse(gatewayPulseIDLst, fromDate, toDate, typeTime);
			if (mapTmp != null) {
				map.putAll(mapTmp);
			}
		}
		return map;
	}

	/**
	 * Lay nhiet do cua thoi gian sau cung
	 * 
	 * @param moduleID
	 * @param mapTempartureLowest
	 * @return
	 */
	public static Integer getLastestTemperature(String moduleStr,
			Map<Integer, Map<String, TemperaturePoint>> mapTempartureByHour) {
		if (mapTempartureByHour == null) {
			return null;
		}
		Integer moduleID = null;
		try {
			moduleID = Integer.parseInt(moduleStr);
		} catch (NumberFormatException nfe) {
			return null;
		}
		if (mapTempartureByHour.containsKey(moduleID)) {
			TreeMap<String, TemperaturePoint> map = (TreeMap<String, TemperaturePoint>) mapTempartureByHour
					.get(moduleID);
			if (map.size() > 0) {
				return map.lastEntry().getValue().getAvgTemp();
			}
		}
		return null;
	}

	/**
	 * Lay ve nhiet do thap nhat cua module tu map
	 * 
	 * @param moduleStr
	 * @param mapTempartureAll
	 * @return
	 */
	public static Integer getLowestTemperature(String moduleStr,
			Map<Integer, Map<String, TemperaturePoint>> mapTempartureAll) {
		Integer moduleID = null;
		if (mapTempartureAll == null) {
			return null;
		}
		try {
			moduleID = Integer.parseInt(moduleStr);
		} catch (NumberFormatException nfe) {
			return null;
		}
		if (mapTempartureAll.containsKey(moduleID)) {
			TreeMap<String, TemperaturePoint> map = (TreeMap<String, TemperaturePoint>) mapTempartureAll.get(moduleID);
			if (map == null || map.size() == 0) {
				return null;
			}
			int min = map.firstEntry().getValue().getMinTemp();
			for (Map.Entry<String, TemperaturePoint> entry : map.entrySet()) {
				if (entry.getValue().getMinTemp() < min) {
					min = entry.getValue().getMinTemp();
				}
			}
			return map.lastEntry().getValue().getMinTemp();
		} else {
			return null;
		}
	}

	private String getGateWays(List<Integer> gatewayIdLst) {
		if (gatewayIdLst == null || gatewayIdLst.size() == 0) {
			return null;
		}
		StringBuilder gatewayBD = new StringBuilder();
		int sizeMinus = gatewayIdLst.size() - 1;
		GetCSMDataAction csm = new GetCSMDataAction();
		for (int i = 0; i < sizeMinus; i++) {
			ModuleCSM module = csm.getModuleCSMByID(gatewayIdLst.get(i));
			if (module != null) {
				gatewayBD.append(module.getNumberModule());
				gatewayBD.append(FrontalKey.COMMA);
			}
		}
		ModuleCSM module = csm.getModuleCSMByID(gatewayIdLst.get(sizeMinus));
		gatewayBD.append(module.getNumberModule());
		return gatewayBD.toString();
	}

	private String getTypeTimeIndus(int typeTime) {
		if (typeTime == FrontalKey.TYPE_TIME_HOUR) {
			return FrontalKey.INDUS_TYPE_TIME_HOUR;
		} else if (typeTime == FrontalKey.TYPE_TIME_DAY) {
			return FrontalKey.INDUS_TYPE_TIME_DAY;
		} else if (typeTime == FrontalKey.TYPE_TIME_MONTH) {
			return FrontalKey.INDUS_TYPE_TIME_MONTH;
		} else {
			return FrontalKey.INDUS_TYPE_TIME_ALL;
		}
	}

	private class PulseIndus {
		private String maxDate;
		private String minDate;
		private Integer maxValue;
		private Integer minValue;

		public String getMaxDate() {
			return maxDate;
		}

		public void setMaxDate(String maxDate) {
			this.maxDate = maxDate;
		}

		public String getMinDate() {
			return minDate;
		}

		public void setMinDate(String minDate) {
			this.minDate = minDate;
		}

		public Integer getMaxValue() {
			return maxValue;
		}

		public void setMaxValue(Integer maxValue) {
			this.maxValue = maxValue;
		}

		public Integer getMinValue() {
			return minValue;
		}

		public void setMinValue(Integer minValue) {
			this.minValue = minValue;
		}

	}
}
