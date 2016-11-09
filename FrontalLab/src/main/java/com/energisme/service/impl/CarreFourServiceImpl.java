package com.energisme.service.impl;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.client.RestTemplate;

import com.energisme.service.CarreFourService;
import com.ifi.common.bean.CarreFourBean;
import com.ifi.common.util.ConfigEnum;
import com.ifi.common.util.PropertiesReader;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class CarreFourServiceImpl implements CarreFourService {
	private static final String WEB_SERVICE_LINK = "/WebServiceLab/lab/getCarreFourBySite?siteId=";
	private static final String GET_REPORT_LINK = "/WebServiceLab/lab/getReportLink?siteId={siteId}";
	private static final String GET_ICON_LINK = "/WebServiceLab/lab/getURIIcon?siteId=";
	private static final String GET_LOGO_LINK = "/WebServiceLab/lab/getLogo?siteId=";
	// private static final String GET_CHECK_USER =
	// "/WebServiceLab/BSLab/checkUser?siteId=";

	public CarreFourBean getCarreFourBySite(Integer siteId) {

		Client client = Client.create();

		String path = PropertiesReader.getValue(ConfigEnum.URI_WEB_SERVICE);
		WebResource webResource = client.resource(path + WEB_SERVICE_LINK + siteId);

		ClientResponse response = webResource.accept("text/plain").get(ClientResponse.class);

		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
		}

		String output = response.getEntity(String.class);

		ObjectMapper mapper = new ObjectMapper();
		CarreFourBean carreFourBean = null;
		try {
			carreFourBean = mapper.readValue(output, CarreFourBean.class);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return carreFourBean;
	}

	public String getReportLink(Integer siteId) {
		RestTemplate rest = new RestTemplate();
		String path = PropertiesReader.getValue(ConfigEnum.URI_WEB_SERVICE) + GET_REPORT_LINK;
		String output = rest.getForObject(path, String.class, siteId.toString());
		return output;
	}

	public String getUriIcon(Integer siteId) {
		Client client = Client.create();

		String path = PropertiesReader.getValue(ConfigEnum.URI_WEB_SERVICE);
		WebResource webResource = client.resource(path + GET_ICON_LINK + siteId);

		ClientResponse response = webResource.accept("text/plain").get(ClientResponse.class);

		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
		}

		String output = response.getEntity(String.class);
		return output;
	}

	public String getLogo(Integer siteId) {
		Client client = Client.create();

		String path = PropertiesReader.getValue(ConfigEnum.URI_WEB_SERVICE);
		WebResource webResource = client.resource(path + GET_LOGO_LINK + siteId);

		ClientResponse response = webResource.accept("text/plain").get(ClientResponse.class);

		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
		}

		String output = response.getEntity(String.class);
		return output;
	}

	// public boolean isCheck(String userName,Integer siteId){
	// Client client = Client.create();
	//
	// String path = PropertiesReader.getValue(ConfigEnum.URI_WEB_SERVICE);
	// WebResource webResource = client
	// .resource(path + GET_CHECK_USER + siteId+"&userName="+userName);
	//
	// ClientResponse response = webResource.accept("text/plain").get(
	// ClientResponse.class);
	//
	// if (response.getStatus() != 200) {
	// return false;
	// }
	//
	// String output = response.getEntity(String.class);
	// if("success".equals(output)){
	// return true;
	// }else{
	// return false;
	// }
	// }
}
