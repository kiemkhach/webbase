package com.energisme.service.impl;

import com.energisme.service.LoginService;
import com.ifi.common.util.ConfigEnum;
import com.ifi.common.util.PropertiesReader;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class LoginServiceImpl implements LoginService {
	private static final String GET_SITE_USER = "/WebServiceLab/BSLab/getUserByLab?labName=";
	private static final String GET_STATUS_CALOON_USER = "/WebServiceLab/lab011/getStatusCaloonByUser?userName=";

	@Override
	public int getSiteByUser(String userName) {
		Client client = Client.create();
		String path = PropertiesReader.getValue(ConfigEnum.URI_WEB_SERVICE);
		String labName = PropertiesReader.getValue(ConfigEnum.LAB_NAME_008);
		WebResource webResource = client.resource(path + GET_SITE_USER + labName + "&userName=" + userName);
		ClientResponse response = webResource.accept("text/plain").get(ClientResponse.class);
		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
		}
		String output = response.getEntity(String.class);
		return Integer.parseInt(output);
	}

	@Override
	public String getCaloonUserStatusByUser(String userName, String passWord) {
		Client client = Client.create();
		String path = PropertiesReader.getValue(ConfigEnum.URI_WEB_SERVICE);
		WebResource webResource = client.resource(path + GET_STATUS_CALOON_USER + userName + "&passWord=" + passWord);
		ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
		}
		String strCalooUser = response.getEntity(String.class);
		return strCalooUser;
	}

}
