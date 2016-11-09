package com.ifi.common.ws;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import com.ifi.common.csm.AgregatCSM;
import com.ifi.common.csm.ModuleCSM;
import com.ifi.common.csm.SiteCSM;
import com.ifi.common.csm.UserCSM;
import com.ifi.common.util.ConfigEnum;
import com.ifi.common.util.FrontalKey;
import com.ifi.common.util.PropertiesReader;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class GetCSMDataAction {

	private static final String API_DATABASE_VERSION = "/api_database_1.0.12";
	// private static final String API_DATABASE_VERSION =
	// "/api_database_1.0.12";
	private static final String URI_CHECK_LOGIN = "/api_logic_1.0.7/index.php";
	private static final String URI_PATH_CSM = "/api_database_1.0.12/users/get/from/token/";
	private static final String URI_AGREGAT_CSM = API_DATABASE_VERSION + "/aggregates/get/from/module_datetime/";
	private static final String URI_GET_USER_BY_CLIENT = API_DATABASE_VERSION + "/users/get/from/client/";
	private static final String URI_GET_MODULE_BY_NUMBER = API_DATABASE_VERSION + "/modules/get/from/serialnumber/";
	private static final String URI_GET_MODULE_BY_ID = API_DATABASE_VERSION + "/modules/get/";
	private static final String URI_AGREGAT_ALL = API_DATABASE_VERSION + "/aggregates/get/from/module_datetime_all/";
	private static final String GET_SITE_BY_ID = API_DATABASE_VERSION + "/sites/get/";
	private static final String URI_AGREGAT_SEASON = API_DATABASE_VERSION + "/aggregates/get/from/module_season/";

	private static Client client;
	private static final Logger LOGGER = LoggerFactory.getLogger(GetCSMDataAction.class);

	private static Map<Integer, ModuleCSM> mapModule = new HashMap<Integer, ModuleCSM>();

	private static Map<String, Integer> mapModuleNumber = new HashMap<String, Integer>();

	public GetCSMDataAction() {
		// // TODO: DELETE WHEN COMMIT
//		System.setProperty("http.proxyHost", "10.225.1.1");
//		System.setProperty("http.proxyPort", "3128");
		if (client == null) {
			client = Client.create(new DefaultClientConfig());
		}
	}

	/**
	 * Check login by UserName and Password
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public UserCSM checkLogin(String username, String password) {

		try {
			UserCSM userCSM = new UserCSM();
			String uriCheckLogin = PropertiesReader.getValue(ConfigEnum.URI_PATH_LOGIN) + URI_CHECK_LOGIN;
			WebResource webResource = client.resource(uriCheckLogin);
			String input = "{\"request\":\"enr_api_login\",\"username\":\"" + username + "\",\"password\":\"" + password
					+ "\"}";

			ClientResponse response = webResource.type("application/json").post(ClientResponse.class, input);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}
			String output = response.getEntity(String.class);
			if (!output.isEmpty() || output != null) {
				JSONParser json = new JSONParser();
				Object obj = json.parse(output);

				JSONObject jsonObject = (JSONObject) obj;
				String dataTemp = jsonObject.get("data").toString();
				if ("false".equals(dataTemp)) {
					return null;
				} else {
					JSONObject data = (JSONObject) jsonObject.get("data");
					String token = (String) data.get("token");
					userCSM.setToken(token);
					userCSM.setUserName((String) data.get("login"));
					userCSM.setUserId(Integer.parseInt((String) data.get("id")));
					Object clientID = data.get("clientID");
					if (clientID != null) {
						userCSM.setClientId(Integer.valueOf(clientID.toString()));
					}
				}
			}
			return userCSM;
		} catch (ParseException e) {
			System.out.println("UniformInterfaceException: " + e);
			return null;
		}
	}

	/**
	 * Check login by Token
	 * 
	 * @param token
	 * @return
	 */
	public UserCSM checkLoginByToken(String token) {

		try {
			if (token.endsWith("/")) {
				token += "endtoken";
			}
			if (token.contains("\\")) {
				token = token.replace("\\", "/");
			}
			JSONObject jsObj = new JSONObject();
			String uriGetCSMTreeData = PropertiesReader.getValue(ConfigEnum.URI_PATH_LOGIN) + URI_PATH_CSM + token;
			WebResource webResource = client.resource(uriGetCSMTreeData);
			ClientResponse response = webResource.type("application/json").post(ClientResponse.class, "");
			if (response.getStatus() != 200) {
				return null;
			}
			String output = response.getEntity(String.class);
			JSONParser json = new JSONParser();
			Object obj;
			obj = json.parse(output);
			JSONArray jsonArray = (JSONArray) obj;
			jsObj = (JSONObject) jsonArray.get(0);
			System.out.println(jsObj.get("name"));
			UserCSM user = new UserCSM();
			user.setUserName((String) jsObj.get("login"));
			user.setLevel(Integer.parseInt((String) jsObj.get("level")));
			user.setClientId(Integer.parseInt((String) jsObj.get("clientID")));
			user.setUserId(Integer.parseInt((String) jsObj.get("id")));
			user.setToken(token);
			return user;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<String> getListUserCSMByClient(Integer clientID) {
		try {
			List<String> userLst = new ArrayList<String>();
			String uriCheckLogin = PropertiesReader.getValue(ConfigEnum.URI_PATH_LOGIN) + URI_GET_USER_BY_CLIENT
					+ clientID;
			WebResource webResource = client.resource(uriCheckLogin);

			ClientResponse response = webResource.type("application/json").post(ClientResponse.class, "");

			if (response.getStatus() != 200) {
				return null;
			}
			String output = response.getEntity(String.class);
			if (!output.isEmpty() || output != null) {
				JSONParser json = new JSONParser();
				Object obj = json.parse(output);

				JSONArray jsonArray = (JSONArray) obj;
				for (int i = 0; i < jsonArray.size(); i++) {
					// UserCSM user = new UserCSM();
					JSONObject jsonObject = (JSONObject) jsonArray.get(i);
					userLst.add((String) jsonObject.get("login"));
					// user.setLevel(Integer.parseInt((String)jsonObject.get("level")));
					// user.setUserName((String)jsonObject.get("login"));
				}
			}
			return userLst;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * get module id by module number
	 */
	public Integer getModuleCSMIDByNumber(String numberModule) {
		if (mapModuleNumber.containsKey(numberModule)) {
			return mapModuleNumber.get(numberModule);
		}
		RestTemplate restClient = new RestTemplate();
		try {
			String uriCheckLogin = PropertiesReader.getValue(ConfigEnum.URI_PATH_API) + URI_GET_MODULE_BY_NUMBER
					+ numberModule;
			LOGGER.info("uriCheck : " + uriCheckLogin);
			String output = restClient.getForObject(uriCheckLogin, String.class);
			LOGGER.info("output : " + output);
			if (output != null && !output.isEmpty()) {
				JSONParser json = new JSONParser();
				Object obj = json.parse(output);

				JSONArray jsonArray = (JSONArray) obj;
				JSONObject jsonObject = (JSONObject) jsonArray.get(0);
				Integer moduleId = Integer.parseInt((String) jsonObject.get("id"));
				mapModuleNumber.put(numberModule, moduleId);
				return moduleId;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Get module csm by ID
	 * 
	 * @param moduleId
	 * @return
	 */
	public ModuleCSM getModuleCSMByID(Integer moduleId) {
		if (mapModule.containsKey(moduleId)) {
			return mapModule.get(moduleId);
		}
		RestTemplate restClient = new RestTemplate();
		try {
			if (moduleId == null) {
				return null;
			}
			String uriCheckLogin = PropertiesReader.getValue(ConfigEnum.URI_PATH_API) + URI_GET_MODULE_BY_ID + moduleId;
			LOGGER.info("uriCheck : " + uriCheckLogin);
			String output = restClient.getForObject(uriCheckLogin, String.class);
			LOGGER.info("output : " + output);
			if (!output.isEmpty() || output != null) {
				JSONParser json = new JSONParser();
				Object obj = json.parse(output);

				JSONArray jsonArray = (JSONArray) obj;
				JSONObject jsonObject = (JSONObject) jsonArray.get(0);
				ModuleCSM module = new ModuleCSM();
				String numberModule = (String) jsonObject.get("num");
				module.setId(moduleId);
				module.setNumberModule((String) jsonObject.get("num"));
				module.setType(Integer.parseInt((String) jsonObject.get("type")));
				module.setUnit((String) jsonObject.get("unit"));
				mapModule.put(moduleId, module);
				if (numberModule != null) {
					mapModuleNumber.put(numberModule, moduleId);
				}
				return module;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getCalculateModuleByName(String moduleNameCal) {
		if (moduleNameCal == null || moduleNameCal.isEmpty()) {
			return moduleNameCal;
		}
		String[] plusArr = moduleNameCal.trim().split(FrontalKey.PLUS_SPECIAL);
		String rs = "";
		for (int i = 0; i < plusArr.length; i++) {
			String[] multiArr = plusArr[i].trim().split(FrontalKey.MULTIPLICATION_SPECIAL);
			String moduleNumber = "";
			if (multiArr.length == 1) {
				moduleNumber = multiArr[0].trim();
			} else {
				moduleNumber = multiArr[multiArr.length - 1].trim();
			}
			Integer moduleID = getModuleCSMIDByNumber(moduleNumber);
			if (moduleID == null) {
				return null;
			}
			for (int j = 0; j < multiArr.length - 1; j++) {
				rs += multiArr[j].trim();
				rs += FrontalKey.MULTIPLICATION;
			}
			rs += moduleID;
			if (i < plusArr.length - 1) {
				rs += FrontalKey.PLUS;
			}
		}
		return rs;
	}

	public String getCalculateModuleByID(String moduleIDCal) {
		if (moduleIDCal == null || moduleIDCal.isEmpty()) {
			return moduleIDCal;
		}
		String[] plusArr = moduleIDCal.trim().split(FrontalKey.PLUS_SPECIAL);
		String rs = "";
		for (int i = 0; i < plusArr.length; i++) {
			String[] multiArr = plusArr[i].trim().split(FrontalKey.MULTIPLICATION_SPECIAL);
			String moduleID = "";
			if (multiArr.length == 1) {
				moduleID = multiArr[0].trim();
			} else {
				moduleID = multiArr[multiArr.length - 1].trim();
			}

			ModuleCSM module = getModuleCSMByID(Integer.parseInt(moduleID));
			if (module == null) {
				return null;
			}
			String moduleNumber = module.getNumberModule();
			for (int j = 0; j < multiArr.length - 1; j++) {
				rs += multiArr[j].trim();
				rs += FrontalKey.MULTIPLICATION;
			}
			rs += moduleNumber;
			if (i < plusArr.length - 1) {
				rs += FrontalKey.PLUS;
			}
		}
		return rs;
	}

	/**
	 * get module id by module number
	 */
	public String getModuleNumberByID(Integer moduleId) {
		RestTemplate restClient = new RestTemplate();
		try {
			if (moduleId == null) {
				return null;
			}
			String uriCheckLogin = PropertiesReader.getValue(ConfigEnum.URI_PATH_API) + URI_GET_MODULE_BY_ID + moduleId;
			LOGGER.info("uriCheck : " + uriCheckLogin);
			String output = restClient.getForObject(uriCheckLogin, String.class);
			LOGGER.info("output : " + output);
			if (!output.isEmpty() || output != null) {
				JSONParser json = new JSONParser();
				Object obj = json.parse(output);

				JSONArray jsonArray = (JSONArray) obj;
				JSONObject jsonObject = (JSONObject) jsonArray.get(0);
				return (String) jsonObject.get("num");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * get module type by module number
	 */
	public Integer getModuleTypeByID(Integer moduleId) {
		RestTemplate restClient = new RestTemplate();
		try {
			if (moduleId == null) {
				return null;
			}
			String uriCheckLogin = PropertiesReader.getValue(ConfigEnum.URI_PATH_API) + URI_GET_MODULE_BY_ID + moduleId;
			LOGGER.info("uriCheck : " + uriCheckLogin);
			// WebResource webResource = client.resource(uriCheckLogin);
			//
			// ClientResponse response = webResource.type("application/json")
			// .post(ClientResponse.class, null);
			//
			// if (response.getStatus() != 200) {
			// return null;
			// }
			// String output = response.getEntity(String.class);
			String output = restClient.getForObject(uriCheckLogin, String.class);
			LOGGER.info("output : " + output);
			if (!output.isEmpty() || output != null) {
				JSONParser json = new JSONParser();
				Object obj = json.parse(output);

				JSONArray jsonArray = (JSONArray) obj;
				JSONObject jsonObject = (JSONObject) jsonArray.get(0);
				return Integer.parseInt((String) jsonObject.get("type"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// /**
	// * get module id by module number
	// */
	// public String getUnitByModuleID(Integer moduleId) {
	// RestTemplate restClient = new RestTemplate();
	// try {
	// if (moduleId == null) {
	// return null;
	// }
	// String uriCheckLogin = PropertiesReader.getValue(ConfigEnum.URI_PATH_API)
	// + URI_GET_MODULE_BY_ID + moduleId;
	// LOGGER.info("uriCheck : " + uriCheckLogin);
	// // WebResource webResource = client.resource(uriCheckLogin);
	// //
	// // ClientResponse response = webResource.type("application/json")
	// // .post(ClientResponse.class, null);
	// //
	// // if (response.getStatus() != 200) {
	// // return null;
	// // }
	// // String output = response.getEntity(String.class);
	// String output = restClient.getForObject(uriCheckLogin, String.class);
	// LOGGER.info("output : " + output);
	// if (!output.isEmpty() || output != null) {
	// JSONParser json = new JSONParser();
	// Object obj = json.parse(output);
	//
	// JSONArray jsonArray = (JSONArray) obj;
	// JSONObject jsonObject = (JSONObject) jsonArray.get(0);
	// return (String) jsonObject.get("unit");
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return null;
	// }

	/**
	 * Method use for Lab service Get Site detail by siteId
	 * 
	 * @param token
	 * @param siteId
	 * @return
	 */
	public SiteCSM getSiteDetailByID(int siteId) {

		try {
			String uriFileBySite = PropertiesReader.getValue(ConfigEnum.URI_PATH_API) + GET_SITE_BY_ID + siteId;
			WebResource webResource = client.resource(uriFileBySite);
			String input = "";
			ClientResponse response = webResource.type("application/json").post(ClientResponse.class, input);
			if (response.getStatus() != 200) {
				return null;
			}
			String output = response.getEntity(String.class);
			JSONParser json = new JSONParser();
			JSONObject jsObj = new JSONObject();
			Object obj = json.parse(output);
			JSONArray jsonArray = (JSONArray) obj;
			if (jsonArray.size() > 0) {
				jsObj = (JSONObject) jsonArray.get(0);
				SiteCSM siteCsm = new SiteCSM();
				siteCsm.setSiteId(Integer.parseInt(jsObj.get("id").toString()));
				siteCsm.setSiteName((String) jsObj.get("name"));
				siteCsm.setLatitude((String) jsObj.get("latitude"));
				siteCsm.setLongitude((String) jsObj.get("longitude"));
				return siteCsm;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
	Map<Integer, Integer> getConsommationByTime(List<Integer> moduleLst, String startDate, String endDate,
			String startHour, String endHour) {
		try {
			Map<Integer, Integer> mapRs = new HashMap<Integer, Integer>();
			if (moduleLst == null || moduleLst.isEmpty()) {
				return mapRs;
			}
			int sizeMinus = moduleLst.size() - 1;
			StringBuilder moduleSB = new StringBuilder();
			for (int i = 0; i < sizeMinus; i++) {
				moduleSB.append(moduleLst.get(i));
				moduleSB.append(",");
			}
			if (startHour.length() == 1) {
				startHour = "0" + startHour;
			}
			if (endHour.length() == 1) {
				endHour = "0" + endHour;
			}
			moduleSB.append(moduleLst.get(sizeMinus));
			String uriGetSite = PropertiesReader.getValue(ConfigEnum.URI_PATH_API) + URI_AGREGAT_SEASON
					+ startDate.trim() + "/" + endDate.trim() + "/" + startHour.trim() + "/" + endHour.trim() + "/"
					+ moduleSB.toString();
			WebResource webResource = client.resource(uriGetSite);
			String input = "";

			ClientResponse response = webResource.type("application/json").post(ClientResponse.class, input);

			if (response.getStatus() != 200) {
				// throw new RuntimeException("Failed : HTTP error code : "
				// + response.getStatus());
				return mapRs;
			}
			String output = response.getEntity(String.class);
			if (!output.isEmpty() || output != null) {
				JSONParser json = new JSONParser();
				Object obj = json.parse(output);

				JSONArray jsonArray = (JSONArray) obj;
				for (Object object : jsonArray) {
					JSONObject data = (JSONObject) object;
					mapRs.put(Integer.parseInt((String) data.get("moduleID")),
							Integer.parseInt((String) data.get("consumption")));
				}
			}
			return mapRs;
		} catch (ParseException e) {
			System.out.println("UniformInterfaceException: " + e);
			return null;
		}
	}

	/**
	 * Group
	 * 
	 * @param lstTrame
	 * @param dateFormat
	 * @return
	 */
	TreeMap<String, Integer> groupAgregatByTime(List<AgregatCSM> lstAgregat, int typeTime) {
		TreeMap<String, Integer> map = new TreeMap<String, Integer>();
		for (AgregatCSM agregat : lstAgregat) {
			String key = null;
			if (typeTime == FrontalKey.TYPE_TIME_HOUR) {
				key = agregat.getDateHeureAgregat() + agregat.getHeureAgregat();
			} else if (typeTime == FrontalKey.TYPE_TIME_DAY) {
				key = agregat.getDateHeureAgregat() + "00";
			} else {
				key = agregat.getDateHeureAgregat().substring(0, 6) + "0100";
			}
			if (agregat.getConsommation() != null) {
				if (map.containsKey(key)) {
					map.put(key, map.get(key) + agregat.getConsommation());
				} else {
					map.put(key, agregat.getConsommation());
				}
			}
		}
		return map;
	}

	List<AgregatCSM> getListAgregatByModule(Integer moduleID, String startDate, String endDate, String startHour,
			String endHour) {
		try {
			List<AgregatCSM> agregatLst = new ArrayList<AgregatCSM>();
			if (moduleID == null) {
				return agregatLst;
			}
			String uriGetSite = PropertiesReader.getValue(ConfigEnum.URI_PATH_API) + URI_AGREGAT_CSM + moduleID + "/"
					+ startDate.trim() + "/" + endDate.trim() + "/" + startHour.trim() + "/" + endHour.trim();
			WebResource webResource = client.resource(uriGetSite);
			String input = "";

			ClientResponse response = webResource.type("application/json").post(ClientResponse.class, input);

			if (response.getStatus() != 200) {
				// throw new RuntimeException("Failed : HTTP error code : "
				// + response.getStatus());
				return agregatLst;
			}
			String output = response.getEntity(String.class);
			if (!output.isEmpty() || output != null) {
				JSONParser json = new JSONParser();
				Object obj = json.parse(output);

				JSONArray jsonArray = (JSONArray) obj;
				for (Object object : jsonArray) {
					AgregatCSM agregat = new AgregatCSM();
					JSONObject data = (JSONObject) object;
					agregat.setDateHeureAgregat((String) data.get("date"));
					agregat.setHeureAgregat((String) data.get("hour"));
					agregat.setIndex(Integer.parseInt((String) data.get("counterIndex")));
					agregat.setTemperature(Integer.parseInt((String) data.get("temperature")));
					agregat.setModuleId(moduleID);
					agregatLst.add(agregat);
				}

			}
			return agregatLst;
		} catch (ParseException e) {
			System.out.println("UniformInterfaceException: " + e);
			return null;
		}
	}

	/**
	 * Get all agregat record in time
	 * 
	 * @param moduleID
	 * @param startDate
	 * @param endDate
	 * @param startHour
	 * @param endHour
	 * @return
	 */
	List<AgregatCSM> getAllAgregatByModule(Integer moduleID, String startDate, String endDate, String startHour,
			String endHour) {
		try {
			List<AgregatCSM> agregatLst = new ArrayList<AgregatCSM>();
			if (moduleID == null) {
				return agregatLst;
			}
			String uriGetSite = PropertiesReader.getValue(ConfigEnum.URI_PATH_API) + URI_AGREGAT_ALL + moduleID + "/"
					+ startDate.trim() + "/" + endDate.trim() + "/" + startHour.trim() + "/" + endHour.trim();
			WebResource webResource = client.resource(uriGetSite);
			String input = "";

			ClientResponse response = webResource.type("application/json").post(ClientResponse.class, input);

			if (response.getStatus() != 200) {
				// throw new RuntimeException("Failed : HTTP error code : "
				// + response.getStatus());
				return agregatLst;
			}
			String output = response.getEntity(String.class);
			if (!output.isEmpty() || output != null) {
				JSONParser json = new JSONParser();
				Object obj = json.parse(output);

				JSONArray jsonArray = (JSONArray) obj;
				for (Object object : jsonArray) {
					AgregatCSM agregat = new AgregatCSM();
					JSONObject data = (JSONObject) object;
					agregat.setDateHeureAgregat((String) data.get("date"));
					agregat.setHeureAgregat((String) data.get("hour"));
					agregat.setIndex(Integer.parseInt((String) data.get("counterIndex")));
					agregat.setTemperature(Integer.parseInt((String) data.get("temperature")));
					agregat.setConsommation(Integer.parseInt((String) data.get("consumption")));
					agregat.setModuleId(moduleID);
					agregatLst.add(agregat);
				}

			}
			return agregatLst;
		} catch (ParseException e) {
			System.out.println("UniformInterfaceException: " + e);
			return null;
		}
	}

}
