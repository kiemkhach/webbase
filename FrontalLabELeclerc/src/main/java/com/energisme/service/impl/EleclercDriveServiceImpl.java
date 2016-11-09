package com.energisme.service.impl;

import javax.ws.rs.core.MediaType;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import com.energisme.service.EleclercDriveService;
import com.ifi.common.bean.EleclercDriveBean;
import com.ifi.common.util.ConfigEnum;
import com.ifi.common.util.FrontalKey;
import com.ifi.common.util.PropertiesReader;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class EleclercDriveServiceImpl implements EleclercDriveService{
	private static final String WEB_SERVICE_DRIVE_LINK = "/WebServiceLab/eLeclerc/getELeclercDriveBySite";
	private static final String WEB_SERVICE_LINK = "/WebServiceLab/config/getConfigRange";
	private static final String WEB_SERVICE_CONST_LINK = "/WebServiceLab/config/getConstant";
	private static final String GET_REPORT_LINK = "/WebServiceLab/eLeclerc/getDriveReportLink?siteId={siteId}";
	private static final Logger LOGGER = LoggerFactory.getLogger(EleclercDriveServiceImpl.class);
	@Override
	public EleclercDriveBean getELeclercDrive(String labName, int pageId,Integer siteId) {
		Client client = Client.create();
		//get range ladder
		String path = PropertiesReader.getValue(ConfigEnum.URI_WEB_SERVICE);
		path += WEB_SERVICE_LINK;
		path += "?labName="+labName;
		path += "&pageId="+pageId;
		path += "&type=" + FrontalKey.RANGE_TYPE_LADDER;
		WebResource webResource = client.resource(path);
		
		ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(
				ClientResponse.class);

		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
		}

		String output = response.getEntity(String.class);
		if(output== null || output.isEmpty()){
			return new EleclercDriveBean();
		}

		JSONArray arr = null;
		JSONParser parser = new JSONParser();
		try {
			arr = (JSONArray) parser.parse(output);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		EleclercDriveBean driveBean = new EleclercDriveBean();
		for (int i = 0; i < arr.size(); i++) {
			JSONObject jsonObject = (JSONObject)arr.get(i);
			switch (Integer.parseInt(jsonObject.get("index").toString())) {
			case FrontalKey.RANGE_APLUS:
				driveBean.setRange_APlus_Min(Integer.parseInt(jsonObject.get("minValue").toString()));
				driveBean.setRange_APlus_Max(Integer.parseInt(jsonObject.get("maxValue").toString()));
				break;
			case FrontalKey.RANGE_A:
				driveBean.setRange_A_Min(Integer.parseInt(jsonObject.get("minValue").toString()));
				driveBean.setRange_A_Max(Integer.parseInt(jsonObject.get("maxValue").toString()));
				break;
			case FrontalKey.RANGE_B:
				driveBean.setRange_B_Min(Integer.parseInt(jsonObject.get("minValue").toString()));
				driveBean.setRange_B_Max(Integer.parseInt(jsonObject.get("maxValue").toString()));
				break;
			case FrontalKey.RANGE_C:
				driveBean.setRange_C_Min(Integer.parseInt(jsonObject.get("minValue").toString()));
				driveBean.setRange_C_Max(Integer.parseInt(jsonObject.get("maxValue").toString()));
				break;
			case FrontalKey.RANGE_D:
				driveBean.setRange_D_Min(Integer.parseInt(jsonObject.get("minValue").toString()));
				driveBean.setRange_D_Max(Integer.parseInt(jsonObject.get("maxValue").toString()));
				break;
			case FrontalKey.RANGE_E:
				driveBean.setRange_E_Min(Integer.parseInt(jsonObject.get("minValue").toString()));
				driveBean.setRange_E_Max(Integer.parseInt(jsonObject.get("maxValue").toString()));
				break;
			case FrontalKey.RANGE_F:
				driveBean.setRange_F_Min(Integer.parseInt(jsonObject.get("minValue").toString()));
				driveBean.setRange_F_Max(Integer.parseInt(jsonObject.get("maxValue").toString()));
				break;
			case FrontalKey.RANGE_G:
				driveBean.setRange_G_Min(Integer.parseInt(jsonObject.get("minValue").toString()));
				break;
			default:
				break;
			}
		}
		//get range progress
		path = PropertiesReader.getValue(ConfigEnum.URI_WEB_SERVICE);
		path += WEB_SERVICE_LINK;
		path += "?labName="+labName;
		path += "&pageId="+pageId;
		path += "&type=" + FrontalKey.RANGE_TYPE_PROGRESS;
		webResource = client.resource(path);
		response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
		}
		output = response.getEntity(String.class);
		try {
			arr = (JSONArray) parser.parse(output);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (int i = 0; i < arr.size(); i++) {
			JSONObject jsonObject = (JSONObject)arr.get(i);
			switch (Integer.parseInt(jsonObject.get("index").toString())) {
			case FrontalKey.RANGE_NORMAL:
				driveBean.setRange_Normal_Min(Integer.parseInt(jsonObject.get("minValue").toString()));
				driveBean.setRange_Normal_Max(Integer.parseInt(jsonObject.get("maxValue").toString()));
				break;
			case FrontalKey.RANGE_HIGH:
				driveBean.setRange_High_Min(Integer.parseInt(jsonObject.get("minValue").toString()));
				driveBean.setRange_High_Max(Integer.parseInt(jsonObject.get("maxValue").toString()));
				break;
			case FrontalKey.RANGE_WARNING:
				driveBean.setRange_Warning_Min(Integer.parseInt(jsonObject.get("minValue").toString()));
				break;

			default:
				break;
			}
		}
		//get drive energy
		path = PropertiesReader.getValue(ConfigEnum.URI_WEB_SERVICE);
		path += WEB_SERVICE_DRIVE_LINK;
		path += "?siteId="+siteId;
		webResource = client.resource(path);
		response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
		}
		output = response.getEntity(String.class);
		try {
			JSONObject obj = (JSONObject) parser.parse(output);
			if(obj.get("currentYearEnergy") != null){
				driveBean.setCurrentYearEnergy(Integer.parseInt(obj.get("currentYearEnergy").toString()));
			}
			if(obj.get("pastYearEnergy")!= null){
				driveBean.setPastYearEnergy(Integer.parseInt(obj.get("pastYearEnergy").toString()));
			}
			if(obj.get("discount")!= null){
				driveBean.setDiscount(Integer.parseInt(obj.get("discount").toString()));
			}

			if(obj.get("siteName")!= null){
				driveBean.setSiteName(obj.get("siteName").toString());
			}
			if(obj.get("currentYear")!= null){
				driveBean.setCurrentYear(obj.get("currentYear").toString());
			}
			if(obj.get("drive")!= null){
//				driveBean.setDrive(Integer.parseInt(obj.get("drive").toString()));
				driveBean.setDrive(Double.parseDouble(obj.get("drive").toString()));
			}
			if(obj.get("pastYear")!= null){
				driveBean.setPastYear(obj.get("pastYear").toString());
			}
			
			if(obj.get("siteStr")!= null){
				driveBean.setSiteStr(obj.get("siteStr").toString());
			}
			
			if(obj.get("temperateOut")!= null){
				driveBean.setTemperateOut(Integer.parseInt(obj.get("temperateOut").toString()));
			}
			
		} catch (ParseException e) {
			LOGGER.debug(e.getMessage());
			e.printStackTrace();
		}
		driveBean.setIndexCurrentYear(getIndexOfEnergy(driveBean.getCurrentYearEnergy(), driveBean));
		driveBean.setIndexPastYear(getIndexOfEnergy(driveBean.getPastYearEnergy(), driveBean));
		return driveBean;
	}
	
	
	@Override
	public Integer getMaxValueDrive(String labName, String key) {
		Client client = Client.create();
		//get max value from constant table
		String path = PropertiesReader.getValue(ConfigEnum.URI_WEB_SERVICE);
		path += WEB_SERVICE_CONST_LINK;
		path += "?labName="+labName;
		path += "&key="+key;
		WebResource webResource = client.resource(path);
		ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
		}
		String output = response.getEntity(String.class);
		JSONParser parser = new JSONParser();
		JSONObject json = null;
		try {
			json = (JSONObject) parser.parse(output);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return Integer.parseInt(json.get("value").toString());
	}
	
	@Override
	public String getDriveReportLink(Integer siteId) {
		RestTemplate rest = new RestTemplate();
		String path = PropertiesReader.getValue(ConfigEnum.URI_WEB_SERVICE) + GET_REPORT_LINK;
		String output = rest.getForObject(path, String.class, siteId.toString());
		return output;
	}
	
	
	/**
	 * Method get index of energy
	 * @param energy
	 * @param driveBean
	 * @return
	 */
	private int getIndexOfEnergy(Integer energy, EleclercDriveBean driveBean) {
		if (energy == null) {
			return 0;
		}
		int index = 0;
		if (energy >= driveBean.getRange_APlus_Min()
				&& energy <= driveBean.getRange_APlus_Max()) {
			index = 1;
		} else if (energy >= driveBean.getRange_A_Min()
				&& energy <= driveBean.getRange_A_Max()) {
			index = 2;
		} else if (energy >= driveBean.getRange_B_Min()
				&& energy <= driveBean.getRange_B_Max()) {
			index = 3;
		} else if (energy >= driveBean.getRange_C_Min()
				&& energy <= driveBean.getRange_C_Max()) {
			index = 4;
		} else if (energy >= driveBean.getRange_D_Min()
				&& energy <= driveBean.getRange_D_Max()) {
			index = 5;
		} else if (energy >= driveBean.getRange_E_Min()
				&& energy <= driveBean.getRange_E_Max()) {
			index = 6;
		} else if (energy >= driveBean.getRange_F_Min()
				&& energy <= driveBean.getRange_F_Max()) {
			index = 7;
		} else if (energy >= driveBean.getRange_G_Min()) {
			index = 8;
		}
		return index;
	}
}
