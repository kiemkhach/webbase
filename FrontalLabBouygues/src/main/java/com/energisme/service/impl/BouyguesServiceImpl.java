package com.energisme.service.impl;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.client.RestTemplate;

import com.energisme.service.BouyguesService;
import com.ifi.common.bean.BouyguesBean;
import com.ifi.common.util.ConfigEnum;
import com.ifi.common.util.PropertiesReader;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class BouyguesServiceImpl implements BouyguesService {
	private static final String WEB_SERVICE_LINK = "/WebServiceLab/bouygues/getBouyguesDataBySite?siteId=";
	private static final String GET_REPORT_LINK = "/WebServiceLab/bouygues/getReportLink?siteId={siteId}";
	private static final String GET_LOGO_LINK = "/WebServiceLab/bouygues/getLogo?siteId=";
	private static final String GET_ICON_LINK = "/WebServiceLab/bouygues/getURIIcon?siteId=";

	@Override
	public BouyguesBean getBouyguesDataBySite(String path, Integer siteId) {
		Client client = Client.create();

		String path1 = PropertiesReader.getValue(ConfigEnum.URI_WEB_SERVICE);
		WebResource webResource = client.resource(path1 + WEB_SERVICE_LINK
				+ siteId);
		ClientResponse response = webResource.accept("application/json").get(
				ClientResponse.class);

		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
		}

		String output = response.getEntity(String.class);

		ObjectMapper mapper = new ObjectMapper();
		BouyguesBean bouyguesBean = null;
		try {
			bouyguesBean = mapper.readValue(output, BouyguesBean.class);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return bouyguesBean;
	}

	@Override
	public String getReportLink(Integer siteId) {
		RestTemplate rest = new RestTemplate();
		String path = PropertiesReader.getValue(ConfigEnum.URI_WEB_SERVICE) + GET_REPORT_LINK;
		String output = rest.getForObject(path, String.class, siteId.toString());
		return output;
	}

	@Override
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
	
	@Override
	public String getIcon(Integer siteId) {
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

}
