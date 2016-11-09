package com.ifi.common.ws;

import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ifi.common.bean.Lab008BoilerInfo;
import com.ifi.common.bean.Lab008PerformanceData;
import com.ifi.common.util.ConfigEnum;
import com.ifi.common.util.FrontalKey;
import com.ifi.common.util.LabUtils;
import com.ifi.common.util.PropertiesReader;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class IndusHistoryData {

	private static final String URI_PATH_INDUSTRIALIZATION = PropertiesReader
			.getValue(ConfigEnum.URI_PATH_INDUSTRIALIZATION);
	// Dinh dang ngay thang
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static final String FIRST_DATE = "20150101";

	// PERFORMANCE DATA
	public static List<Lab008PerformanceData> getPerformanceData(Integer siteId, Date startDate, Date endDate) {
		String url = URI_PATH_INDUSTRIALIZATION + MessageFormat.format("/indus/getLabPerfomanceData/{0}/{1}/{2}",
				siteId, LabUtils.convertDateByFormat(startDate, FrontalKey.DATE_FORMAT_DAY_1),
				LabUtils.convertDateByFormat(endDate, FrontalKey.DATE_FORMAT_DAY_1));
		String json = getRestResponse(url);
		return parsePerformanceDataList(json);
	}

	public static List<Lab008PerformanceData> getPerformanceData(Integer siteId) {
		String url = URI_PATH_INDUSTRIALIZATION + MessageFormat.format("/indus/getLabPerfomanceData/{0}", siteId);
		String json = getRestResponse(url);
		return parsePerformanceDataList(json);
	}

	public static Date getFirstDayPerformanceData(int siteId) {
		String url = URI_PATH_INDUSTRIALIZATION
				+ MessageFormat.format("/indus/getFirstPerformanceByDateTime/{0}", siteId);
		String json = getRestResponse(url);
		Lab008PerformanceData obj = parsePerformanceDataObject(json);
		if (obj != null) {
			return obj.getMonth();
		}
		return LabUtils.convertDateByFormat(FIRST_DATE, FrontalKey.DATE_FORMAT_DAY_1);
	}

	public static boolean createPerformanceData(List<Lab008PerformanceData> list) {
		Gson gson = getJsonParser();
		String params = gson.toJson(list);
		String url = URI_PATH_INDUSTRIALIZATION + "/indus/createPerformanceData";
		return makePostRequest(url, params);
	}

	public static boolean deletePerformanceData(Integer siteId) {
		String url = URI_PATH_INDUSTRIALIZATION + "/indus/deletePerformanceData/" + siteId;
		return makeDeleteRequest(url);
	}

	private static List<Lab008PerformanceData> parsePerformanceDataList(String json) {
		if (json == null) {
			return null;
		}
		Gson gson = getJsonParser();
		Type collectionType = new TypeToken<List<Lab008PerformanceData>>() {
		}.getType();
		return gson.fromJson(json, collectionType);
	}

	private static Lab008PerformanceData parsePerformanceDataObject(String json) {
		if (json == null) {
			return null;
		}
		Gson gson = getJsonParser();
		return gson.fromJson(json, Lab008PerformanceData.class);
	}

	// BOILER INFO
	public static List<Lab008BoilerInfo> getBoilerInfo(Integer siteId, Date startDate, Date endDate) {
		String url = URI_PATH_INDUSTRIALIZATION + MessageFormat.format("/indus/getLabBoilerInfo/{0}/{1}/{2}", siteId,
				LabUtils.convertDateByFormat(startDate, FrontalKey.DATE_FORMAT_DAY_1),
				LabUtils.convertDateByFormat(endDate, FrontalKey.DATE_FORMAT_DAY_1));
		String json = getRestResponse(url);
		return parseBoilerInfoList(json);
	}

	public static List<Lab008BoilerInfo> getBoilerInfo(Integer siteId) {
		String url = URI_PATH_INDUSTRIALIZATION + MessageFormat.format("/indus/getLabBoilerInfo/{0}", siteId);
		String json = getRestResponse(url);
		return parseBoilerInfoList(json);
	}

	public static Date getFirstDayBoilerInfo(int siteId) {
		String url = URI_PATH_INDUSTRIALIZATION
				+ MessageFormat.format("/indus/getFirstBoilerInfoByDateTime/{0}", siteId);
		String json = getRestResponse(url);
		Lab008BoilerInfo obj = parseBoilerInfoObject(json);
		if (obj != null) {
			return obj.getDateTime();
		}

		return LabUtils.convertDateByFormat(FIRST_DATE, FrontalKey.DATE_FORMAT_DAY_1);
	}

	public static boolean createBoilerInfo(List<Lab008BoilerInfo> list) {
		Gson gson = getJsonParser();
		String params = gson.toJson(list);

		String url = URI_PATH_INDUSTRIALIZATION + "/indus/createBoilerInfo";
		System.out.println(params);
		return makePostRequest(url, params);
	}

	public static boolean deleteBoilerInfo(Integer siteId) {
		String url = URI_PATH_INDUSTRIALIZATION + "/indus/deleteBoilerInfo/" + siteId;
		return makeDeleteRequest(url);
	}

	private static List<Lab008BoilerInfo> parseBoilerInfoList(String json) {
		if (json == null) {
			return null;
		}
		Gson gson = getJsonParser();
		Type collectionType = new TypeToken<List<Lab008BoilerInfo>>() {
		}.getType();
		return gson.fromJson(json, collectionType);
	}

	private static Lab008BoilerInfo parseBoilerInfoObject(String json) {
		if (json == null) {
			return null;
		}
		Gson gson = getJsonParser();
		return gson.fromJson(json, Lab008BoilerInfo.class);
	}

	// UTILITY FUNCTIONS
	private static Gson getJsonParser() {
		return new GsonBuilder().setDateFormat(DATE_FORMAT).create();
	}

	private static String getRestResponse(String url) {
		Client client = Client.create();
		WebResource webResource = client.resource(url);
		ClientResponse res = webResource.accept("application/json").get(ClientResponse.class);
		if (res.getStatus() != 200) {
			System.out.println("Failed with HTTP error code: " + res.getStatus());
			return null;
		} else {
			return res.getEntity(String.class);
		}
	}

	private static boolean makePostRequest(String url, String params) {
		Client client = Client.create();
		WebResource webResource = client.resource(url);
		ClientResponse res = webResource.type("application/json").post(ClientResponse.class, params);
		if (res.getStatus() != 200) {
			System.out.println("Failed with HTTP error code: " + res.getStatus());
			return false;
		} else {
			String result = res.getEntity(String.class);
			System.out.println("result: " + result);
			return Boolean.parseBoolean(result);
		}
	}

	private static boolean makeDeleteRequest(String url) {
		Client client = Client.create();
		WebResource webResource = client.resource(url);
		ClientResponse res = webResource.delete(ClientResponse.class);
		if (res.getStatus() != 200) {
			System.out.println("Failed with HTTP error code: " + res.getStatus());
			return false;
		} else {
			String result = res.getEntity(String.class);
			System.out.println("result: " + result);
			return Boolean.parseBoolean(result);
		}
	}
}
