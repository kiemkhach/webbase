package com.energisme.service.impl;

import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.client.RestTemplate;

import com.energisme.service.ELeclercService;
import com.ifi.common.bean.ELeclercBean;
import com.ifi.common.util.ConfigEnum;
import com.ifi.common.util.FrontalKey;
import com.ifi.common.util.PropertiesReader;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class ELeclercServiceImpl implements ELeclercService {
	private static final String WEB_SERVICE_LINK = "/WebServiceLab/eLeclerc/getELeclercBySite?siteId=";
	private static final String WEB_SERVICE_RANGE_LINK = "/WebServiceLab/config/getConfigRange";
	private static final String GET_REPORT_LINK = "/WebServiceLab/eLeclerc/getReportLink?siteId={siteId}";
	private static final String GET_ICON_LINK = "/WebServiceLab/eLeclerc/getURIIcon?siteId=";
	private static final String GET_LOGO_LINK = "/WebServiceLab/eLeclerc/getLogo?siteId=";

	public ELeclercBean getELeclercBySite(Integer siteId, String labName, Integer pageId) {

		Client client = Client.create();

		String path = PropertiesReader.getValue(ConfigEnum.URI_WEB_SERVICE);
		WebResource webResource = client.resource(path + WEB_SERVICE_LINK
				+ siteId);

		ClientResponse response = webResource.accept("text/plain").get(
				ClientResponse.class);

		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
		}

		String output = response.getEntity(String.class);

		ObjectMapper mapper = new ObjectMapper();
		ELeclercBean eLeclercBean = null;
		try {
			eLeclercBean = mapper.readValue(output, ELeclercBean.class);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		//get range from database
		path = PropertiesReader.getValue(ConfigEnum.URI_WEB_SERVICE);
		path += WEB_SERVICE_RANGE_LINK;
		path += "?labName="+labName;
		path += "&pageId="+pageId;
		path += "&type=" + FrontalKey.RANGE_TYPE_LADDER;
		webResource = client.resource(path);
		
		response = webResource.accept(MediaType.APPLICATION_JSON).get(
				ClientResponse.class);

		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
		}

		output = response.getEntity(String.class);
		if(output== null || output.isEmpty()){
			return eLeclercBean;
		}

		JSONArray arr = null;
		JSONParser parser = new JSONParser();
		try {
			arr = (JSONArray) parser.parse(output);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (int i = 0; i < arr.size(); i++) {
			JSONObject jsonObject = (JSONObject)arr.get(i);
			switch (Integer.parseInt(jsonObject.get("index").toString())) {
			case FrontalKey.RANGE_APLUS:
				eLeclercBean.setRange_APlus_Min(Integer.parseInt(jsonObject.get("minValue").toString()));
				eLeclercBean.setRange_APlus_Max(Integer.parseInt(jsonObject.get("maxValue").toString()));
				break;
			case FrontalKey.RANGE_A:
				eLeclercBean.setRange_A_Min(Integer.parseInt(jsonObject.get("minValue").toString()));
				eLeclercBean.setRange_A_Max(Integer.parseInt(jsonObject.get("maxValue").toString()));
				break;
			case FrontalKey.RANGE_B:
				eLeclercBean.setRange_B_Min(Integer.parseInt(jsonObject.get("minValue").toString()));
				eLeclercBean.setRange_B_Max(Integer.parseInt(jsonObject.get("maxValue").toString()));
				break;
			case FrontalKey.RANGE_C:
				eLeclercBean.setRange_C_Min(Integer.parseInt(jsonObject.get("minValue").toString()));
				eLeclercBean.setRange_C_Max(Integer.parseInt(jsonObject.get("maxValue").toString()));
				break;
			case FrontalKey.RANGE_D:
				eLeclercBean.setRange_D_Min(Integer.parseInt(jsonObject.get("minValue").toString()));
				eLeclercBean.setRange_D_Max(Integer.parseInt(jsonObject.get("maxValue").toString()));
				break;
			case FrontalKey.RANGE_E:
				eLeclercBean.setRange_E_Min(Integer.parseInt(jsonObject.get("minValue").toString()));
				eLeclercBean.setRange_E_Max(Integer.parseInt(jsonObject.get("maxValue").toString()));
				break;
			case FrontalKey.RANGE_F:
				eLeclercBean.setRange_F_Min(Integer.parseInt(jsonObject.get("minValue").toString()));
				eLeclercBean.setRange_F_Max(Integer.parseInt(jsonObject.get("maxValue").toString()));
				break;
			case FrontalKey.RANGE_G:
				eLeclercBean.setRange_G_Min(Integer.parseInt(jsonObject.get("minValue").toString()));
				eLeclercBean.setRange_G_Max(Integer.parseInt(jsonObject.get("maxValue").toString()));
				break;
			case FrontalKey.RANGE_H:
				eLeclercBean.setRange_H_Min(Integer.parseInt(jsonObject.get("minValue").toString()));
				eLeclercBean.setRange_H_Max(Integer.parseInt(jsonObject.get("maxValue").toString()));
				break;
			case FrontalKey.RANGE_I:
				eLeclercBean.setRange_I_Min(Integer.parseInt(jsonObject.get("minValue").toString()));
				break;
			default:
				break;
			}
		}
		eLeclercBean.setIndexCurrentYear(getIndexOfEnergy(eLeclercBean.getCurrentYearEnergy(), eLeclercBean));
		eLeclercBean.setIndexPastYear(getIndexOfEnergy(eLeclercBean.getPastYearEnergy(), eLeclercBean));
		return eLeclercBean;
	}

	@Override
	public String getReportLink(Integer siteId) {
		RestTemplate rest = new RestTemplate();
		String path = PropertiesReader.getValue(ConfigEnum.URI_WEB_SERVICE) + GET_REPORT_LINK;
		String output = rest.getForObject(path, String.class, siteId.toString());
		return output;
	}
	
	public String getUriIcon(Integer siteId) {
		Client client = Client.create();

		String path = PropertiesReader.getValue(ConfigEnum.URI_WEB_SERVICE);
		WebResource webResource = client
				.resource(path + GET_ICON_LINK + siteId);

		ClientResponse response = webResource.accept("text/plain").get(
				ClientResponse.class);

		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
		}

		String output = response.getEntity(String.class);
		return output;
	}

	public String getLogo(Integer siteId) {
		Client client = Client.create();

		String path = PropertiesReader.getValue(ConfigEnum.URI_WEB_SERVICE);
		WebResource webResource = client
				.resource(path + GET_LOGO_LINK + siteId);

		ClientResponse response = webResource.accept("text/plain").get(
				ClientResponse.class);

		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
		}

		String output = response.getEntity(String.class);
		return output;
	}
	
	private int getIndexOfEnergy(Integer energy, ELeclercBean eLeclercBean) {
		if (energy == null) {
			return 0;
		}
		int index = 0;
		if (energy >= eLeclercBean.getRange_APlus_Min()
				&& energy <= eLeclercBean.getRange_APlus_Max()) {
			index = 1;
		} else if (energy >= eLeclercBean.getRange_A_Min()
				&& energy <= eLeclercBean.getRange_A_Max()) {
			index = 2;
		} else if (energy >= eLeclercBean.getRange_B_Min()
				&& energy <= eLeclercBean.getRange_B_Max()) {
			index = 3;
		} else if (energy >= eLeclercBean.getRange_C_Min()
				&& energy <= eLeclercBean.getRange_C_Max()) {
			index = 4;
		} else if (energy >= eLeclercBean.getRange_D_Min()
				&& energy <= eLeclercBean.getRange_D_Max()) {
			index = 5;
		} else if (energy >= eLeclercBean.getRange_E_Min()
				&& energy <= eLeclercBean.getRange_E_Max()) {
			index = 6;
		} else if (energy >= eLeclercBean.getRange_F_Min()
				&& energy <= eLeclercBean.getRange_F_Max()) {
			index = 7;
		} else if (energy >= eLeclercBean.getRange_G_Min()
				&& energy <= eLeclercBean.getRange_G_Max()) {
			index = 8;
		} else if (energy >= eLeclercBean.getRange_H_Min()
				&& energy <= eLeclercBean.getRange_H_Max()) {
			index = 9;
		} else if (energy >= eLeclercBean.getRange_I_Min()) {
			index = 10;
		}
		return index;
	}
}
