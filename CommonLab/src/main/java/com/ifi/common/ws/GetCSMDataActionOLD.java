package com.ifi.common.ws;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ifi.common.bean.Lab009ModuleData;
import com.ifi.common.csm.ModuleCSM;
import com.ifi.common.csm.SiteCSM;
import com.ifi.common.csm.UserCSM;
import com.ifi.common.util.ConfigEnum;
import com.ifi.common.util.LabUtils;
import com.ifi.common.util.PropertiesReader;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;

/**
 * Connect to OLD DB
 * 
 * @author ndlong
 *
 */
public class GetCSMDataActionOLD {

	private static final String API_DATABASE_VERSION = "/api_database_1.0.11";
	private static final String URI_CHECK_LOGIN_OLD = "/api_logic_1.0.6/index.php";
	private static final String URI_SITE_PROPERTY = API_DATABASE_VERSION + "/siteProperty/create/select/";
	private static final String URI_MODULE_PROPERTY = API_DATABASE_VERSION + "/moduleProperty/create/select/";
	private static final String URI_GET_SITE_BY_CLIENT = API_DATABASE_VERSION + "/sites/get/from/joinsitetype_client/";
	private static final String URI_GET_MODULE_BY_CLIENT = API_DATABASE_VERSION + "/modules/get/from/client/";
	private static final String URI_GET_MODULE_DATA = API_DATABASE_VERSION + "/moduleData/create/select/";

	private static Client client;
	private static final Logger LOGGER = LoggerFactory.getLogger(GetCSMDataAction.class);

	public GetCSMDataActionOLD() {
		// // TODO: DELETE WHEN COMMIT
		// System.setProperty("http.proxyHost", "10.225.1.1");
		// System.setProperty("http.proxyPort", "3128");
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
	public UserCSM checkLoginOLD(String username, String password) {

		try {
			UserCSM userCSM = new UserCSM();
			String uriCheckLogin = PropertiesReader.getValue(ConfigEnum.URI_PATH_API) + URI_CHECK_LOGIN_OLD;
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

	public List<SiteCSM> getLstSiteByClient(String clientID) {
		List<SiteCSM> siteCSMLst = new ArrayList<SiteCSM>();
		String uriGetSite = PropertiesReader.getValue(ConfigEnum.URI_PATH_API) + URI_GET_SITE_BY_CLIENT + clientID;
		WebResource webResource = client.resource(uriGetSite);

		ClientResponse response = webResource.type("application/json").get(ClientResponse.class);

		if (response.getStatus() != 200) {
			return null;
		}
		String output = response.getEntity(String.class);
		if (!output.isEmpty() || output != null) {
			try {
				JSONParser json = new JSONParser();
				Object obj = json.parse(output);
				JSONArray jsonArray = (JSONArray) obj;
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject jsonObject = (JSONObject) jsonArray.get(i);
					SiteCSM siteCSM = new SiteCSM();
					Object siteId = jsonObject.get("id");
					if (siteId != null) {
						siteCSM.setSiteId(Integer.parseInt(siteId.toString()));
					}
					Object siteName = jsonObject.get("name");
					if (siteName != null) {
						siteCSM.setSiteName(siteName.toString().trim());
					}
					siteCSMLst.add(siteCSM);
				}
			} catch (ParseException e) {
				LOGGER.error(e.getMessage());
			}
		}
		return siteCSMLst;
	}

	private Map<Integer, Map<String, String>> getLstSiteProperty(String input) {
		Map<Integer, Map<String, String>> mapRs = new HashMap<Integer, Map<String, String>>();
		String uriGetSite = PropertiesReader.getValue(ConfigEnum.URI_PATH_API) + URI_SITE_PROPERTY;
		WebResource webResource = client.resource(uriGetSite);

		ClientResponse response = webResource.type("application/json").post(ClientResponse.class, input);

		if (response.getStatus() != 200) {
			return null;
		}
		String output = response.getEntity(String.class);
		if (!output.isEmpty() || output != null) {
			try {
				JSONParser json = new JSONParser();
				Object obj = json.parse(output);
				JSONArray jsonArray = (JSONArray) obj;
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject jsonObject = (JSONObject) jsonArray.get(i);
					Object siteIDObj = jsonObject.get("siteID");
					if (siteIDObj == null) {
						continue;
					}
					Integer siteId = Integer.parseInt(siteIDObj.toString());
					Map<String, String> mapSite = null;
					if (mapRs.containsKey(siteId)) {
						mapSite = mapRs.get(siteId);
					} else {
						mapSite = new HashMap<String, String>();
					}
					Object keyObj = jsonObject.get("propKey");
					if (keyObj != null) {
						String value = null;
						if (jsonObject.get("propValue") != null) {
							value = jsonObject.get("propValue").toString().trim();
						}
						String keyProp = keyObj.toString().trim();

						mapSite.put(keyProp, value);
					} else {
						continue;
					}
					mapRs.put(siteId, mapSite);
				}
			} catch (ParseException e) {
				LOGGER.error(e.getMessage());
			}
		}
		return mapRs;
	}

	public Map<Integer, Map<String, String>> getLstSitePropertyByID(List<Integer> siteLst) {
		if (siteLst == null || siteLst.isEmpty()) {
			return null;
		}
		StringBuilder inputKeySB = new StringBuilder();

		int sizeMinus = siteLst.size() - 1;
		inputKeySB.append("{\"siteID\":[");
		for (int i = 0; i < sizeMinus; i++) {
			inputKeySB.append("\"");
			inputKeySB.append(siteLst.get(i));
			inputKeySB.append("\",");
		}
		inputKeySB.append("\"");
		inputKeySB.append(siteLst.get(sizeMinus));
		inputKeySB.append("\"");
		inputKeySB.append("]}");

		return getLstSiteProperty(inputKeySB.toString());
	}

	private Map<Integer, Map<String, String>> getLstModuleProperty(String input) {
		Map<Integer, Map<String, String>> mapRs = new HashMap<Integer, Map<String, String>>();
		String uriGetSite = PropertiesReader.getValue(ConfigEnum.URI_PATH_API) + URI_MODULE_PROPERTY;
		WebResource webResource = client.resource(uriGetSite);

		ClientResponse response = webResource.type("application/json").post(ClientResponse.class, input);

		if (response.getStatus() != 200) {
			return mapRs;
		}
		String output = response.getEntity(String.class);
		if (!output.isEmpty() || output != null) {
			try {
				JSONParser json = new JSONParser();
				Object obj = json.parse(output);
				JSONArray jsonArray = (JSONArray) obj;
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject jsonObject = (JSONObject) jsonArray.get(i);
					Object siteIDObj = jsonObject.get("moduleID");
					if (siteIDObj == null) {
						continue;
					}
					Integer siteId = Integer.parseInt(siteIDObj.toString());
					Map<String, String> mapSite = null;
					if (mapRs.containsKey(siteId)) {
						mapSite = mapRs.get(siteId);
					} else {
						mapSite = new HashMap<String, String>();
					}
					Object keyObj = jsonObject.get("propKey");
					if (keyObj != null) {
						String value = null;
						if (jsonObject.get("propValue") != null) {
							value = jsonObject.get("propValue").toString().trim();
						}
						String keyProp = keyObj.toString().trim();

						mapSite.put(keyProp, value);
					} else {
						continue;
					}
					mapRs.put(siteId, mapSite);
				}
			} catch (ParseException e) {
				LOGGER.error(e.getMessage());
			}
		}
		return mapRs;
	}

	public Map<Integer, Map<String, String>> getLstModulePropertyByID(Set<Integer> moduleSet) {
		if (moduleSet == null || moduleSet.isEmpty()) {
			return null;
		}
		StringBuilder inputKeySB = new StringBuilder();

		int sizeMinus = moduleSet.size() - 1;
		Iterator<Integer> it = moduleSet.iterator();
		inputKeySB.append("{\"moduleID\":[");
		for (int i = 0; i < sizeMinus; i++) {
			inputKeySB.append("\"");
			inputKeySB.append(it.next());
			inputKeySB.append("\",");
		}
		inputKeySB.append("\"");
		inputKeySB.append(it.next());
		inputKeySB.append("\"");
		inputKeySB.append("]}");

		return getLstModuleProperty(inputKeySB.toString());
	}

	/**
	 * map module - site
	 * 
	 * @param clientID
	 * @return
	 */
	public Map<Integer, ModuleCSM> getMapModuleSiteByClient(String clientID) {
		Map<Integer, ModuleCSM> map = new HashMap<Integer, ModuleCSM>();
		String uriGetSite = PropertiesReader.getValue(ConfigEnum.URI_PATH_API) + URI_GET_MODULE_BY_CLIENT + clientID;
		WebResource webResource = client.resource(uriGetSite);

		ClientResponse response = webResource.type("application/json").get(ClientResponse.class);

		if (response.getStatus() != 200) {
			return map;
		}
		String output = response.getEntity(String.class);
		if (!output.isEmpty() || output != null) {
			try {
				JSONParser json = new JSONParser();
				Object obj = json.parse(output);
				JSONArray jsonArray = (JSONArray) obj;
				for (int i = 0; i < jsonArray.size(); i++) {
					ModuleCSM module = new ModuleCSM();
					JSONObject jsonObject = (JSONObject) jsonArray.get(i);
					Object moduleObjId = jsonObject.get("id");
					if (moduleObjId != null) {
						module.setId(Integer.parseInt(moduleObjId.toString()));
					} else {
						continue;
					}
					Object siteObjId = jsonObject.get("siteID");
					if (siteObjId != null) {
						module.setSiteId(Integer.parseInt(siteObjId.toString()));
					}

					Object numberModule = jsonObject.get("num");
					if (numberModule != null) {
						module.setNumberModule(numberModule.toString());
					}
					if (module.getSiteId() != null && module.getId() != null && module.getNumberModule() != null) {
						map.put(module.getId(), module);
					}
				}
			} catch (ParseException e) {
				LOGGER.error(e.getMessage());
			}
		}
		return map;
	}

	public Map<Integer, SiteCSM> getMapSiteByClient(String clientID) {
		Map<Integer, SiteCSM> siteCSMMap = new HashMap<Integer, SiteCSM>();
		String uriGetSite = PropertiesReader.getValue(ConfigEnum.URI_PATH_API) + URI_GET_SITE_BY_CLIENT + clientID;
		WebResource webResource = client.resource(uriGetSite);

		ClientResponse response = webResource.type("application/json").get(ClientResponse.class);

		if (response.getStatus() != 200) {
			return siteCSMMap;
		}
		String output = response.getEntity(String.class);
		if (!output.isEmpty() || output != null) {
			try {
				JSONParser json = new JSONParser();
				Object obj = json.parse(output);
				JSONArray jsonArray = (JSONArray) obj;
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject jsonObject = (JSONObject) jsonArray.get(i);
					SiteCSM siteCSM = new SiteCSM();
					Object siteId = jsonObject.get("id");
					if (siteId != null) {
						siteCSM.setSiteId(Integer.parseInt(siteId.toString()));
					}
					Object siteName = jsonObject.get("name");
					if (siteName != null) {
						siteCSM.setSiteName(siteName.toString().trim());
					}

					siteCSMMap.put(siteCSM.getSiteId(), siteCSM);
				}
			} catch (ParseException e) {
				LOGGER.error(e.getMessage());
			}
		}
		return siteCSMMap;
	}

	public List<Lab009ModuleData> getModuleData(String fromMonth, String toMonth, Set<String> numberModule) {

		List<Lab009ModuleData> lst = new ArrayList<Lab009ModuleData>();
		String uriGetSite = PropertiesReader.getValue(ConfigEnum.URI_PATH_API) + URI_GET_MODULE_DATA;
		WebResource webResource = client.resource(uriGetSite);

		StringBuilder inputKeySB = new StringBuilder();

		int sizeMinus = numberModule.size() - 1;
		Iterator<String> it = numberModule.iterator();
		inputKeySB.append("{\"modulenum\":[");
		for (int i = 0; i < sizeMinus; i++) {
			inputKeySB.append("\"");
			inputKeySB.append(it.next());
			inputKeySB.append("\",");
		}
		inputKeySB.append("\"");
		inputKeySB.append(it.next());
		inputKeySB.append("\"");
		inputKeySB.append("],\"period\":[\"");
		inputKeySB.append(fromMonth);
		inputKeySB.append("\",\"");
		inputKeySB.append(toMonth);
		inputKeySB.append("\"]}");

		ClientResponse response = webResource.type("application/json").post(ClientResponse.class,
				inputKeySB.toString());

		if (response.getStatus() != 200) {
			return null;
		}
		String output = response.getEntity(String.class);
		if (!output.isEmpty() || output != null) {
			try {
				JSONParser json = new JSONParser();
				Object obj = json.parse(output);
				JSONArray jsonArray = (JSONArray) obj;
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject jsonObject = (JSONObject) jsonArray.get(i);
					Lab009ModuleData moduleData = new Lab009ModuleData();
					if (jsonObject.get("moduleNum") != null) {
						moduleData.setModuleNum(jsonObject.get("moduleNum").toString());
					} else {
						continue;
					}
					if (jsonObject.get("yyyyMM") != null) {
						String date = jsonObject.get("yyyyMM").toString();
						if (date.length() == 6) {
							moduleData.setYear(Integer.parseInt(date.substring(0, 4)));
							moduleData.setMonth(Integer.parseInt(date.substring(4, 6)));
						} else {
							continue;
						}
					} else {
						continue;
					}

					if (jsonObject.get("conso") != null) {
						String conso = jsonObject.get("conso").toString();
						try {
							Double d = LabUtils.convertStringToDouble(conso);
							if (d >= 0) {
								moduleData.setConso(d);
							} else {
								moduleData.setConso(0d);
							}
						} catch (NumberFormatException nfe) {
							continue;
						}

					} else {
						continue;
					}

					if (jsonObject.get("cout") != null) {
						String cout = jsonObject.get("cout").toString();
						Double tmp = LabUtils.convertStringToDouble(cout);
						if (tmp >= 0) {
							moduleData.setCout(tmp);
						} else {
							moduleData.setCout(0d);
						}
					} else {
						continue;
					}
					lst.add(moduleData);
				}
			} catch (ParseException e) {
				LOGGER.error(e.getMessage());
			}
		}

		return lst;
	}
}
